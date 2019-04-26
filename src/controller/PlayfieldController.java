package controller;

import config.Events;
import entity.*;
import config.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * PlayfieldController.java
 * The PlayfieldController class is responsible for handling user input.
 */
public class PlayfieldController implements PropertyChangeListener {

    @FXML
    private StackPane playfield;
    @FXML
    private Pane playfieldBlocks;
    @FXML
    private Pane playfieldMergeBlocks;
    @FXML
    private Label playfieldLevel;
    @FXML
    private Label playfieldStarCount;
    @FXML
    private Button playfieldMenuBarButton;
    @FXML
    private Button playfieldToolUndo;
    @FXML
    private Button playfieldToolBomb;

    private ArrayList<Block> blocks;
    private ArrayList<MergeBlock> mergeBlocks;
    private PlayfieldModel playfieldModel;
    private ImageView menuButtonImg;
    private VBox gameOverScreen;


    /**
     * Acts as class constructor.
     * @param playfieldModel Observable to be attached to
     */
    PlayfieldController(PlayfieldModel playfieldModel) {

        this.menuButtonImg = new ImageView();
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
    }


    /**
     * Prepares playfield
     */
    void createPlayfield() {

        //Prepare MergeBlocks
        this.mergeBlocks = new ArrayList<>();

        //Display Level
        this.playfieldLevel.setText(Integer.toString(Settings.LEVEL));

        //Display Money
        this.playfieldStarCount.setText(Integer.toString(Settings.STAR_COUNT));

        //Set MenuBar Button Icon
        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/pause.png")));
        this.menuButtonImg.setFitWidth(this.playfieldMenuBarButton.getPrefWidth());
        this.menuButtonImg.setFitHeight(this.playfieldMenuBarButton.getPrefHeight());
        this.playfieldMenuBarButton.setGraphic(menuButtonImg);

        //Set click events
        this.playfieldMenuBarButton.setOnAction(e -> this.pauseGame());
        this.playfieldToolUndo.setOnAction(e -> this.undoLatestStep());
        this.playfieldToolBomb.setOnAction(e -> this.toggleBombMode());
    }


    /**
     * Return playfield view so that pause menu can be attached
     * @return playfield
     */
    StackPane getPlayfield() {

        return this.playfield;
    }


    /**
     * Creates Block instances and adds them to GUI
     * @param rawBlocks given RawBlocks
     */
    private void blocksCreated(ArrayList<RawBlock> rawBlocks) {

        this.blocks = new ArrayList<>();
        this.playfieldBlocks.getChildren().clear();

        for(RawBlock rawBlock : rawBlocks) {

            Block block = new Block(this.playfieldModel, rawBlock.getX(), rawBlock.getY(), rawBlock.getValue());
            this.blocks.add(block);
            this.playfieldBlocks.getChildren().add(block);
            block.show();
        }
    }


    /**
     * Checks if creating a MergeBlock is even necessary
     * @param rawMergeBlock MergeBlock to check
     * @return true or false
     */
    private boolean mergeBlockExists(RawMergeBlock rawMergeBlock) {

        for(MergeBlock mergeBlock : this.mergeBlocks) {
            if(mergeBlock.getX1() == rawMergeBlock.getX1()
            && mergeBlock.getY1() == rawMergeBlock.getY1()
            && mergeBlock.getX2() == rawMergeBlock.getX2()
            && mergeBlock.getY2() == rawMergeBlock.getY2())
                return true;
        }
        return false;
    }


    /**
     * Creates MergeBlock instances and adds them to GUI
     * @param rawMergeBlocks given MergeBlocks
     */
    private void mergeBlocksCreated(ArrayList<RawMergeBlock> rawMergeBlocks) {

        for(RawMergeBlock rawMergeBlock : rawMergeBlocks) {

            if(mergeBlockExists(rawMergeBlock)) continue;

            MergeBlock newMergeBlock = new MergeBlock(
                rawMergeBlock.getX1(),
                rawMergeBlock.getY1(),
                rawMergeBlock.getX2(),
                rawMergeBlock.getY2(),
                rawMergeBlock.getValue()
            );
            this.mergeBlocks.add(newMergeBlock);
            this.playfieldMergeBlocks.getChildren().add(newMergeBlock);
            newMergeBlock.show();
        }
    }


    /**
     * Retrieves specific MergeBlock
     * @param x1 start x
     * @param y1 start y
     * @param x2 end x
     * @param y2 end y
     * @return MergeBlock
     */
    private MergeBlock getMergeBlockAt(int x1, int y1, int x2, int y2) {

        for(MergeBlock mergeBlock : this.mergeBlocks)
            if(x1 == mergeBlock.getX1() && y1 == mergeBlock.getY1()
            && x2 == mergeBlock.getX2() && y2 == mergeBlock.getY2())
                return mergeBlock;
        return null;
    }


    /**
     * Retrieves specific Block
     * @param x value
     * @param y value
     * @return Block or null
     */
    private Block getBlockAt(int x, int y) {

        for(Block block : this.blocks)
            if(block.getX() == x && block.getY() == y)
                return block;
        return null;
    }


    /**
     * Show playfield menu
     */
    private void pauseGame() {

        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/resume.png")));
        this.playfieldMenuBarButton.setDisable(true);
        ViewChanger.showPauseMenu();
    }


    /**
     * Remove playfield menu
     */
    private void continueGame() {

        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/pause.png")));
        this.playfieldMenuBarButton.setDisable(false);
        ViewChanger.closePauseMenu();
    }


    /**
     * Regenerate grid
     */
    private void restartGame() {

        this.resetLevel();
        ViewChanger.changeToPlayfield();
    }


    /**
     * Go back to main view
     */
    private void quitGame() {

        this.resetLevel();
        ViewChanger.changeToMainMenu();
    }


    /**
     * Resets level and NumberGenerator values
     */
    private void resetLevel() {

        Settings.LEVEL = Settings.LEVEL_DEFAULT;
        Settings.LEVEL_RANGE = Settings.LEVEL_RANGE_DEFAULT;
        this.levelUp(Settings.LEVEL);
    }


    /**
     * Revert blocks to previous state
     */
    private void undoLatestStep() {
        System.out.println("undo");
    }


    /**
     * Sets GUI to Bomb-Mode
     */
    private void toggleBombMode() {

        if(Settings.STAR_COUNT < Settings.BOMB_COST) return;

        Settings.BOMB_MODE = !Settings.BOMB_MODE;
        this.playfieldMergeBlocks.setVisible(!Settings.BOMB_MODE);
        for (Block block : this.blocks) block.setBombMode(Settings.BOMB_MODE);
    }


    /**
     * Remove given block from pane
     * @param rawBlock block to remove
     */
    private void removeBlock(RawBlock rawBlock) {

        Block removeMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        this.playfieldBlocks.getChildren().remove(removeMe);
        this.blocks.remove(removeMe);
    }


    /**
     * Remove mergeBlock from pane
     * @param rawMergeBlock block to remove
     */
    private void removeMergeBlock(RawMergeBlock rawMergeBlock) {

        MergeBlock removeMe = this.getMergeBlockAt(
            rawMergeBlock.getX1(),
            rawMergeBlock.getY1(),
            rawMergeBlock.getX2(),
            rawMergeBlock.getY2()
        );
        this.playfieldMergeBlocks.getChildren().remove(removeMe);
        this.mergeBlocks.remove(removeMe);
    }


    /**
     * Update block value
     * @param rawBlock give RawBlock
     */
    private void increaseBlock(RawBlock rawBlock) {

        Block updateMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(updateMe != null) updateMe.updateValue();
    }


    /**
     * Increase y of Block
     * @param rawBlock given Block
     */
    private void prepareToSinkBlock(RawBlock rawBlock) {

        Block sinkMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(sinkMe == null) return;
        sinkMe.prepareToSink();
    }


    /**
     * Sink given Block
     * @param rawBlock given Block
     */
    private void sinkBlock(RawBlock rawBlock) {

        Block sinkMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(sinkMe == null) return;
        sinkMe.sink();
    }


    /**
     * Create and add new Block to GUI
     * @param rawBlock given location and value
     */
    private void createNewBlock(RawBlock rawBlock) {

        Block newBlock = new Block(this.playfieldModel, rawBlock.getX(), rawBlock.getY(), rawBlock.getValue());
        this.blocks.add(newBlock);
        this.playfieldBlocks.getChildren().add(newBlock);
        newBlock.show();
    }


    /**
     * Updates level label
     * @param level new level
     */
    private void levelUp(int level) {

        this.playfieldLevel.setText(Integer.toString(level));
        for(Block block : this.blocks) block.setBackground();
    }


    /**
     * Shake blocks and show game over screen
     */
    private void showGameOverScreen() {

        for(Block block : this.blocks) block.shake();
        ViewChanger.showGameOverScreen();
    }


    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case Events.BLOCKS_CREATED:
                this.blocksCreated((ArrayList<RawBlock>) evt.getNewValue());
                break;
            case Events.MERGE_BLOCKS_CREATED:
                this.mergeBlocksCreated((ArrayList<RawMergeBlock>) evt.getNewValue());
                break;
            case Events.REMOVE_BLOCK:
                this.removeBlock((RawBlock) evt.getNewValue());
                break;
            case Events.REMOVE_MERGE_BLOCK:
                this.removeMergeBlock((RawMergeBlock) evt.getNewValue());
                break;
            case Events.INCREASE_BLOCK:
                this.increaseBlock((RawBlock) evt.getNewValue());
                break;
            case Events.PREPARE_SINK_BLOCK:
                this.prepareToSinkBlock((RawBlock) evt.getNewValue());
                break;
            case Events.SINK_BLOCK:
                this.sinkBlock((RawBlock) evt.getNewValue());
                break;
            case Events.NEW_BLOCK_CREATED:
                this.createNewBlock((RawBlock) evt.getNewValue());
                break;
            case Events.LEVEL_UP:
                this.levelUp((Integer) evt.getNewValue());
                break;
            case Events.BOMB_MODE:
                this.toggleBombMode();
                break;
            case Events.UPDATE_STAR_COUNT:
                this.playfieldStarCount.setText(Integer.toString((Integer) evt.getNewValue()));
                break;
            case Events.CONTINUE_GAME:
                this.continueGame();
                break;
            case Events.RESTART_GAME:
                this.restartGame();
                break;
            case Events.QUIT_GAME:
                this.quitGame();
                break;
            case Events.GAME_OVER:
                this.showGameOverScreen();
                break;
        }
    }

}
