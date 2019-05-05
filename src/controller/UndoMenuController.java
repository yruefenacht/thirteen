package controller;

import config.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import model.PlayfieldModel;

class UndoMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button undoMenuConfirm;
    @FXML
    private Button undoMenuAbort;


    /**
     * Class constructor.
     * @param playfieldModel observable
     */
    UndoMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    /**
     * Defines click events
     */
    void setButtons() {

        undoMenuConfirm.setText("BUY " + Settings.TOOL_COST);
        undoMenuConfirm.setContentDisplay(ContentDisplay.RIGHT);
        undoMenuConfirm.setOnAction(e ->  { ViewChanger.closeUndoMenu(); this.playfieldModel.undo(); });
        undoMenuAbort.setOnAction(e -> ViewChanger.closeUndoMenu());
    }
}