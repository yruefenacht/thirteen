package controller;

import config.Config;
import config.Events;
import entity.*;
import game.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.BlockMatrix;
import utility.ViewChanger;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * PlayfieldController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * The PlayfieldController class is responsible for handling user input.
 */
public class PlayfieldController implements PropertyChangeListener {

    @FXML
    private StackPane playfield;
    @FXML
    private VBox playfieldFrame;
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
    private BlockMatrix blockMatrix;
    private ImageView menuButtonImg;
    private BoxBlur blurEffect;


    /**
     * Constructs a {@code PlayfieldController} object.
     * @param blockMatrix Observable to be attached to.
     */
    public PlayfieldController(BlockMatrix blockMatrix) {

        this.menuButtonImg = new ImageView();
        this.blockMatrix = blockMatrix;
        this.blockMatrix.addPropertyChangeListener(this);
    }


    /**
     * Prepares playfield.
     */
    public void createPlayfield() {

        //Prepare MergeBlocks
        this.mergeBlocks = new ArrayList<>();

        //Display Level
        this.playfieldLevel.setText(Integer.toString(Config.LEVEL));

        //Display Money
        this.playfieldStarCount.setText(Integer.toString(Config.STAR_COUNT));

        //Set blur effect
        this.blurEffect = new BoxBlur();
        this.blurEffect.setWidth(5);
        this.blurEffect.setHeight(5);
        this.blurEffect.setIterations(0);
        this.playfieldFrame.setEffect(this.blurEffect);

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
     * Return playfield view so that pause menu can be attached.
     * @return playfield
     */
    public StackPane getPlayfield() {

        return this.playfield;
    }


    /**
     * Sets blur effect.
     * 0: None, 5: Max.
     * @param value blur value
     */
    public void setBlur(int value) {

        this.blurEffect.setIterations(value);
    }


    /**
     * Creates Block instances and adds them to GUI.
     * @param rawBlocks given RawBlocks
     */
    private void blocksCreated(ArrayList<RawBlock> rawBlocks) {

        this.blocks = new ArrayList<>();
        this.playfieldBlocks.getChildren().clear();

        for(RawBlock rawBlock : rawBlocks) {

            Block block = new Block(this.blockMatrix, rawBlock.getX(), rawBlock.getY(), rawBlock.getValue());
            this.blocks.add(block);
            this.playfieldBlocks.getChildren().add(block);
            block.show();
        }
    }


    /**
     * Checks if creating a MergeBlock is even necessary.
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
     * Creates MergeBlock instances and adds them to GUI.
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
     * Retrieves specific MergeBlock.
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
     * Retrieves specific Block.
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
     * Show playfield menu.
     */
    private void pauseGame() {

        if(Config.BOT_MODE) return;
        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/resume.png")));
        this.playfieldMenuBarButton.setDisable(true);
        ViewChanger.showPauseMenu();
    }


    /**
     * Remove playfield menu.
     */
    private void continueGame() {

        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/pause.png")));
        this.playfieldMenuBarButton.setDisable(false);
        ViewChanger.closePauseMenu();
    }


    /**
     * Regenerate grid.
     */
    private void restartGame() {

        ViewChanger.changeToPlayfield(true);
    }


    /**
     * Go back to main view.
     */
    private void quitGame() {

        ViewChanger.changeToMainMenu();
    }


    /**
     * Revert blocks to previous state.
     */
    private void undoLatestStep() {

        if(Config.BOT_MODE) return;
        if(Config.STAR_COUNT < Config.TOOL_COST) return;
        ViewChanger.showUndoMenu();
    }


    /**
     * Sets GUI to Bomb-Mode.
     */
    private void toggleBombMode() {

        if(Config.BOT_MODE) return;
        if(Config.STAR_COUNT < Config.TOOL_COST) return;

        Config.BOMB_MODE = !Config.BOMB_MODE;
        this.playfieldMergeBlocks.setVisible(!Config.BOMB_MODE);
        for (Block block : this.blocks) block.setBombMode(Config.BOMB_MODE);
    }


    /**
     * Remove given block from pane.
     * @param rawBlock block to remove
     */
    private void removeBlock(RawBlock rawBlock) {

        Block removeMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        this.playfieldBlocks.getChildren().remove(removeMe);
        this.blocks.remove(removeMe);
    }


    /**
     * Remove mergeBlock from pane.
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
     * Update block value.
     * @param rawBlock give RawBlock
     */
    private void increaseBlock(RawBlock rawBlock) {

        Block updateMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(updateMe != null) updateMe.updateValue();
    }


    /**
     * Increase y of Block.
     * @param rawBlock given Block
     */
    private void prepareToSinkBlock(RawBlock rawBlock) {

        Block sinkMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(sinkMe == null) return;
        sinkMe.prepareToSink();
    }


    /**
     * Sink given Block.
     * @param rawBlock given Block
     */
    private void sinkBlock(RawBlock rawBlock) {

        Block sinkMe = this.getBlockAt(rawBlock.getX(), rawBlock.getY());
        if(sinkMe == null) return;
        sinkMe.sink();
    }


    /**
     * Create and add new Block to GUI.
     * @param rawBlock given location and value
     */
    private void createNewBlock(RawBlock rawBlock) {

        Block newBlock = new Block(this.blockMatrix, rawBlock.getX(), rawBlock.getY(), rawBlock.getValue());
        this.blocks.add(newBlock);
        this.playfieldBlocks.getChildren().add(newBlock);
        newBlock.show();
    }


    /**
     * Updates level label.
     * @param level new level
     */
    private void levelUp(int level) {

        this.playfieldLevel.setText(Integer.toString(level));
        Animations.getScaleAnimation(this.playfieldLevel).play();
        for(Block block : this.blocks) block.setBackground();
    }


    /**
     * Shake blocks and show game over screen.
     */
    private void showGameOverScreen(Game game) {

        for(Block block : this.blocks) block.shake();
        ViewChanger.showGameOverScreen(game);
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
                this.showGameOverScreen((Game) evt.getNewValue());
                break;
            case Events.MERGE_BLOCKS_RESET:
                this.mergeBlocks.clear(); this.playfieldMergeBlocks.getChildren().clear();
                break;
            case Events.UNDO_SET_ENABLED:
                this.playfieldToolUndo.setDisable(! (Boolean) evt.getNewValue());
                break;
            case Events.BOMB_SET_ENABLED:
                this.playfieldToolBomb.setDisable(! (Boolean) evt.getNewValue());
                break;
        }
    }

}