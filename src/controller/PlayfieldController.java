package controller;

import config.Event;
import entity.Block;
import config.Settings;
import entity.MergeBlock;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import entity.BlockMatrix;
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

    private int level = Settings.DEFAULT_LEVEL;
    private PlayfieldModel playfieldModel;
    private BlockMatrix blockMatrix;
    private ImageView menuButtonImg;
    private VBox pauseMenu;

    /**
     * Acts as class constructor.
     * @param playfieldModel Observable to be attached to
     */
    public PlayfieldController(PlayfieldModel playfieldModel) {

        this.blockMatrix = new BlockMatrix(playfieldModel, Settings.GRID_DIMENSION_X, Settings.GRID_DIMENSION_Y);;
        this.menuButtonImg = new ImageView();
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
    }

    /**
     * Retrieves Block elements and adds them to grid.
     */
    public void addPlayfield() throws IOException {

        //Prepare Blocks
        ArrayList<Block> blocks = this.blockMatrix.generateBlocks();
        ArrayList<MergeBlock> mergeBlocks = this.blockMatrix.generateMergeBlocks();

        //Add Blocks
        this.playfield.getChildren().addAll(mergeBlocks);
        this.playfield.getChildren().addAll(blocks);

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
        this.playfieldMenuBarButton.setOnMouseClicked(e -> pauseGame());
        this.playfieldMenuContinue.setOnMouseClicked(e -> resumeGame());
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

    private void removeBlock(Block block) {

        this.playfield.getChildren().remove(block);
    }

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
            case Event.REMOVE_BLOCK:
                this.removeBlock((Block) evt.getNewValue());
                break;
            case Event.REMOVE_MERGE_BLOCK:
                this.removeMergeBlock((MergeBlock) evt.getNewValue());
        }
    }

}
