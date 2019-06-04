package controller;

import config.Config;
import game.Highscore;
import game.Level;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.BlockMatrix;

/**
 * GameOverMenuController.java
 *
 * Defines click events of buttons from game over screen.
 */
public class GameOverMenuController implements MenuController {

    @FXML
    private Label gameOverMenuScore;
    @FXML
    private Label gameOverMenuLevel;
    @FXML
    private Label gameOverMenuStars;
    @FXML
    private Button gameOverMenuPlayAgain;
    @FXML
    private Button gameOverMenuQuit;

    private BlockMatrix blockMatrix;


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
    @Override
    public void setButtons() {

        this.gameOverMenuPlayAgain.setOnAction(e -> this.blockMatrix.restartGame());
        this.gameOverMenuQuit.setOnAction(e -> this.blockMatrix.quitGame());
        if(Config.BOT_MODE) this.gameOverMenuQuit.setVisible(false);
    }


    /**
     * Displays reached level.
     * @param level level
     */
    public void setLevel(Level level) {

        this.gameOverMenuScore.setText(String.valueOf(level.getLevel()));
    }


    /**
     * Displays highscore.
     * @param highscore Highscore
     */
    public void setHighscore(Highscore highscore) {

        this.gameOverMenuLevel.setText(Integer.toString(highscore.getLevel()));
        this.gameOverMenuStars.setText(Integer.toString(highscore.getStars()));
    }

}
