package controller;

import config.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import model.BlockMatrix;
import utility.ViewChanger;

/**
 * UndoMenuController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Handles undo confirm action of user.
 */
public class UndoMenuController {

    private BlockMatrix blockMatrix;
    @FXML
    private Button undoMenuConfirm;
    @FXML
    private Button undoMenuAbort;


    /**
     * Constructs a {@code UndoMenuController} object.
     * @param blockMatrix observable
     */
    public UndoMenuController(BlockMatrix blockMatrix) {

        this.blockMatrix = blockMatrix;
    }


    /**
     * Defines click events
     */
    public void setButtons() {

        undoMenuConfirm.setText("BUY " + Config.TOOL_COST);
        undoMenuConfirm.setContentDisplay(ContentDisplay.RIGHT);
        undoMenuConfirm.setOnAction(e ->  { ViewChanger.closeUndoMenu(); this.blockMatrix.undo(); });
        undoMenuAbort.setOnAction(e -> ViewChanger.closeUndoMenu());
    }
    
}
