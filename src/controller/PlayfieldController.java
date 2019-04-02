package controller;

import entity.BlockMatrix;
import config.Settings;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * PlayfieldController.java
 * The PlayfieldController class is responsible for handling user input.
 */
public class PlayfieldController implements PropertyChangeListener {

    @FXML
    private Pane playfield;
    @FXML
    private HBox playfieldContainer;

    private BlockMatrix blockMatrix;
    private PlayfieldModel model;

    /**
     * Acts as class constructor.
     * @param model Observable to be attached to
     */
    public PlayfieldController(PlayfieldModel model) {

        this.model = model;
        this.model.addPropertyChangeListener(this);
        this.blockMatrix = new BlockMatrix(Settings.GRID_DIMENSION);
    }

    /**
     * Retrieves Block elements and adds them to grid.
     */
    public void addPlayfield() {

        this.playfield.getChildren().addAll(blockMatrix.generatePlayfield());
        playfieldContainer.setAlignment(Pos.CENTER);

    }

    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
