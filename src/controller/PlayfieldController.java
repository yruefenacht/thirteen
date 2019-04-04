package controller;

import entity.Block;
import entity.BlockMatrix;
import config.Settings;
import entity.MergeBlock;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private int level = Settings.DEFAULT_LEVEL;
    private BlockMatrix blockMatrix;

    /**
     * Acts as class constructor.
     * @param model Observable to be attached to
     */
    public PlayfieldController(PlayfieldModel model) {

        model.addPropertyChangeListener(this);
        this.blockMatrix = new BlockMatrix(Settings.GRID_DIMENSION);
    }

    /**
     * Retrieves Block elements and adds them to grid.
     */
    public void addPlayfield() {

        ArrayList<Block> blocks = blockMatrix.generateBlocks();
        ArrayList<MergeBlock> mergeBlocks = blockMatrix.generateMergeBlocks();

        this.playfield.getChildren().addAll(mergeBlocks);
        this.playfield.getChildren().addAll(blocks);
        this.playfieldLevel.setText(Integer.toString(this.level));
    }

    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
