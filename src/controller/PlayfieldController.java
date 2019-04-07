package controller;

import entity.Block;
import entity.BlockMatrix;
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

    private int level = Settings.DEFAULT_LEVEL;
    private BlockMatrix blockMatrix;
    private ImageView menuButtonImg;
    private VBox pauseMenu;

    /**
     * Acts as class constructor.
     * @param model Observable to be attached to
     */
    public PlayfieldController(PlayfieldModel model) {

        model.addPropertyChangeListener(this);
        this.blockMatrix = new BlockMatrix(Settings.GRID_DIMENSION);
        this.menuButtonImg = new ImageView();
    }

    /**
     * Retrieves Block elements and adds them to grid.
     */
    public void addPlayfield() throws IOException {

        //Prepare Blocks
        ArrayList<Block> blocks = blockMatrix.generateBlocks();
        ArrayList<MergeBlock> mergeBlocks = blockMatrix.generateMergeBlocks();

        //Add Blocks
        this.playfield.getChildren().addAll(mergeBlocks);
        this.playfield.getChildren().addAll(blocks);

        //Display Level
        this.playfieldLevel.setText(Integer.toString(this.level));

        //Prepare pause menu
        FXMLLoader pauseMenuLoader = new FXMLLoader(this.getClass().getResource("/resources/view/PlayfieldMenu.fxml"));
        pauseMenuLoader.setController(this);
        this.pauseMenu = pauseMenuLoader.load();
        this.pauseMenu.setPrefWidth(Settings.GRID_DIMENSION * Settings.BLOCK_WIDTH);
        this.pauseMenu.setPrefHeight(Settings.GRID_DIMENSION * Settings.BLOCK_HEIGHT);

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

    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
