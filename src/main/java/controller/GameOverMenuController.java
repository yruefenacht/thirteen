package controller;

import game.Highscore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.PlayfieldModel;

/**
 * GameOverMenuController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Defines click events of buttons from game over screen.
 */
public class GameOverMenuController {

    private PlayfieldModel playfieldModel;
    @FXML
    private Button gameOverMenuPlayAgain;
    @FXML
    private Button gameOverMenuQuit;
    @FXML
    private Label gameOverMenuLevel;
    @FXML
    private Label gameOverMenuStars;


    /**
     * Class constructor.
     * @param playfieldModel observable
     */
    public GameOverMenuController(PlayfieldModel playfieldModel) {

        this.playfieldModel = playfieldModel;
    }


    /**
     * Defines click events.
     */
    public void setButtons() {

        this.gameOverMenuPlayAgain.setOnAction(e -> this.playfieldModel.restartGame());
        this.gameOverMenuQuit.setOnAction(e -> this.playfieldModel.quitGame());
    }


    /**
     * Shows highscore.
     * @param highscore Highscore
     */
    public void setHighscore(Highscore highscore) {

        this.gameOverMenuLevel.setText(Integer.toString(highscore.getLevel()));
        this.gameOverMenuStars.setText(Integer.toString(highscore.getStars()));
    }

}
