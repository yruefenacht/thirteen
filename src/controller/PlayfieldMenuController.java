package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.PlayfieldModel;

class PlayfieldMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button playfieldMenuContinue;
    @FXML
    private Button playfieldMenuRestart;
    @FXML
    private Button playfieldMenuQuit;


    PlayfieldMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    void setButtons() {

        this.playfieldMenuContinue.setOnAction(e -> this.playfieldModel.continueGame());
        this.playfieldMenuRestart.setOnAction(e -> this.playfieldModel.restartGame());
        this.playfieldMenuQuit.setOnAction(e -> this.playfieldModel.quitGame());
    }

}
