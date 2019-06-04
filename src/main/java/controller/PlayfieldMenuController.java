package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.BlockMatrix;

/**
 * PlayfieldMenuController.java
 *
 * Defines click events of buttons from playfield menu.
 */
public class PlayfieldMenuController implements MenuController {

    private BlockMatrix blockMatrix;
    @FXML
    private Button playfieldMenuContinue;
    @FXML
    private Button playfieldMenuRestart;
    @FXML
    private Button playfieldMenuQuit;


    /**
     * Constructs a {@code PlayfieldMenuController} object.
     * @param blockMatrix observable
     */
    public PlayfieldMenuController(BlockMatrix blockMatrix) {

        this.blockMatrix = blockMatrix;
    }


    /**
     * Defines button click events.
     */
    @Override
    public void setButtons() {

        this.playfieldMenuContinue.setOnAction(e -> this.blockMatrix.continueGame());
        this.playfieldMenuRestart.setOnAction(e -> this.blockMatrix.restartGame());
        this.playfieldMenuQuit.setOnAction(e -> this.blockMatrix.quitGame());
    }

}
