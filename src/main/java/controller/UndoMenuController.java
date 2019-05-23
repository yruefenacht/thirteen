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
public class UndoMenuController implements MenuController {

    @FXML
    private Button undoMenuConfirm;
    @FXML
    private Button undoMenuAbort;

    private BlockMatrix blockMatrix;


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
    @Override
    public void setButtons() {

        undoMenuConfirm.setText(ViewChanger.getLanguage().getString("UndoMenu.buy") +" "+Config.TOOL_COST);
        undoMenuConfirm.setContentDisplay(ContentDisplay.RIGHT);
        undoMenuConfirm.setOnAction(e ->  { ViewChanger.closeUndoMenu(); this.blockMatrix.undo(); });
        undoMenuAbort.setOnAction(e -> ViewChanger.closeUndoMenu());
    }
    
}
