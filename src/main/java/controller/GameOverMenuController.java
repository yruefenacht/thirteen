package controller;

import game.Highscore;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.BlockMatrix;

/**
 * GameOverMenuController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Defines click events of buttons from game over screen.
 */
public class GameOverMenuController {

    private BlockMatrix blockMatrix;
    @FXML
    private Button gameOverMenuPlayAgain;
    @FXML
    private Button gameOverMenuQuit;
    @FXML
    private Label gameOverMenuLevel;
    @FXML
    private Label gameOverMenuStars;


    /**
     * Constructs a {@code GameOverMenuController} object.
     * @param blockMatrix observable
     */
    public GameOverMenuController(BlockMatrix blockMatrix) {

        this.blockMatrix = blockMatrix;
    }


    /**
     * Defines click events.
     */
    public void setButtons() {

        this.gameOverMenuPlayAgain.setOnAction(e -> this.blockMatrix.restartGame());
        this.gameOverMenuQuit.setOnAction(e -> this.blockMatrix.quitGame());
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
