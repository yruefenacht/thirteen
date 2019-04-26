package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.PlayfieldModel;

/**
 * GameOverMenuController.java
 * Defines click events of buttons from game over screen.
 */
class GameOverMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button gameOverMenuPlayAgain;
    @FXML
    private Button gameOverMenuQuit;


    /**
     * Class constructor
     * @param playfieldModel observable
     */
    GameOverMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    /**
     * Defines click events
     */
    void setButtons() {

        this.gameOverMenuPlayAgain.setOnAction(e -> this.playfieldModel.restartGame());
        this.gameOverMenuQuit.setOnAction(e -> this.playfieldModel.quitGame());
    }

}
