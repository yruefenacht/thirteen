package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.PlayfieldModel;

/**
 * PlayfieldMenuController.java
 * Defines click events of buttons from playfield menu.
 */
public class PlayfieldMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button playfieldMenuContinue;
    @FXML
    private Button playfieldMenuRestart;
    @FXML
    private Button playfieldMenuQuit;


    /**
     * Class constructor.
     * @param playfieldModel observable
     */
    public PlayfieldMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    /**
     * Defines button click events.
     */
    public void setButtons() {

        this.playfieldMenuContinue.setOnAction(e -> this.playfieldModel.continueGame());
        this.playfieldMenuRestart.setOnAction(e -> this.playfieldModel.restartGame());
        this.playfieldMenuQuit.setOnAction(e -> this.playfieldModel.quitGame());
    }

}
