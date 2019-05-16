package controller;

import config.Config;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import model.PlayfieldModel;
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

    private PlayfieldModel playfieldModel;
    @FXML
    private Button undoMenuConfirm;
    @FXML
    private Button undoMenuAbort;


    /**
     * Class constructor.
     * @param playfieldModel observable
     */
    public UndoMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    /**
     * Defines click events
     */
    public void setButtons() {

        undoMenuConfirm.setText("BUY " + Config.TOOL_COST);
        undoMenuConfirm.setContentDisplay(ContentDisplay.RIGHT);
        undoMenuConfirm.setOnAction(e ->  { ViewChanger.closeUndoMenu(); this.playfieldModel.undo(); });
        undoMenuAbort.setOnAction(e -> ViewChanger.closeUndoMenu());
    }
    
}
