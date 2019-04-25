package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.PlayfieldModel;

class GameOverMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button gameOverMenuPlayAgain;
    @FXML
    private Button gameOverMenuQuit;


    GameOverMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    void setButtons() {

        this.gameOverMenuPlayAgain.setOnAction(e -> this.playfieldModel.restartGame());
        this.gameOverMenuQuit.setOnAction(e -> this.playfieldModel.quitGame());
    }

}
