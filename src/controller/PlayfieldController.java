package controller;

import config.Event;
import entity.Block;
import config.Settings;
import entity.MergeBlock;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
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
    private Pane playfield;
    @FXML
    private Label playfieldLevel;
    @FXML
    private Button playfieldMenuBarButton;
    @FXML
    private Button playfieldMenuContinue;
    @FXML
    private Button playfieldMenuQuit;

    private int level = Settings.DEFAULT_LEVEL;
    private PlayfieldModel playfieldModel;
    private ImageView menuButtonImg;
    private VBox pauseMenu;

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
     * Essentially prepares playfield
     * Retrieves and adds Blocks
     * Loads and saves game menu
     * Sets game menu button and defines click events
     * @throws IOException if PlayfieldMenu.fxml could not be found
     */
    void addPlayfield() throws IOException {

        //Display Level
        this.playfieldLevel.setText(Integer.toString(this.level));

        //Prepare pause menu
        FXMLLoader pauseMenuLoader = new FXMLLoader(this.getClass().getResource("/resources/view/PlayfieldMenu.fxml"));
        pauseMenuLoader.setController(this);
        this.pauseMenu = pauseMenuLoader.load();
        this.pauseMenu.setPrefWidth(Settings.GRID_DIMENSION_X * Settings.BLOCK_WIDTH);
        this.pauseMenu.setPrefHeight(Settings.GRID_DIMENSION_X * Settings.BLOCK_HEIGHT);

        //Set MenuBar Button Icon
        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/pause.png")));
        this.menuButtonImg.setFitWidth(this.playfieldMenuBarButton.getPrefWidth());
        this.menuButtonImg.setFitHeight(this.playfieldMenuBarButton.getPrefHeight());
        this.playfieldMenuBarButton.setGraphic(menuButtonImg);

        //Set click events
        this.playfieldMenuBarButton.setOnMouseClicked(e -> this.pauseGame());
        this.playfieldMenuContinue.setOnMouseClicked(e -> this.resumeGame());
        this.playfieldMenuQuit.setOnAction(e -> this.quitGame());
    }

    private void blocksCreated(ArrayList<Block> blocks) {

        this.playfield.getChildren().addAll(blocks);
    }

    private void mergeBlocksCreated(ArrayList<MergeBlock> mergeBlocks) {

        this.playfield.getChildren().addAll(mergeBlocks);
    }

    /**
     * Show playfield menu
     */
    private void pauseGame() {

        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/resume.png")));
        this.playfieldMenuBarButton.setDisable(true);
        this.playfield.getChildren().add(this.pauseMenu);
    }

    /**
     * Remove playfield menu
     */
    private void resumeGame() {

        this.menuButtonImg.setImage(new Image(this.getClass().getResourceAsStream("/images/pause.png")));
        this.playfieldMenuBarButton.setDisable(false);
        this.playfield.getChildren().remove(this.pauseMenu);
    }

    /**
     * Go back to main view
     */
    private void quitGame() {

        ViewChanger.changeToMainMenu();
    }

    /**
     * Remove given block from pane
     * @param block block to remove
     */
    private void removeBlock(Block block) {

        this.playfield.getChildren().remove(block);
    }

    /**
     * Remove mergeBlock from pane
     * @param mergeBlock block to remove
     */
    private void removeMergeBlock(MergeBlock mergeBlock) {

        this.playfield.getChildren().remove(mergeBlock);
    }

    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case Event.BLOCKS_CREATED:
                this.blocksCreated((ArrayList<Block>) evt.getNewValue());
                break;
            case Event.MERGE_BLOCKS_CREATED:
                this.mergeBlocksCreated((ArrayList<MergeBlock>) evt.getNewValue());
                break;
            case Event.REMOVE_BLOCK:
                this.removeBlock((Block) evt.getNewValue());
                break;
            case Event.REMOVE_MERGE_BLOCK:
                this.removeMergeBlock((MergeBlock) evt.getNewValue());
                break;
        }
    }

}
