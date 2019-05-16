package bot;

import config.Config;
import controller.BlockMatrix;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.Duration;
import java.util.List;

/**
 * Bot.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Handles bot window user interface logic.
 */
public class Bot {

    @FXML
    private Button botPlayButton;
    @FXML
    private ChoiceBox strategySelector;

    private BlockMatrix blockMatrix;
    private Timeline botTimer;
    private List<BotStrategy> strategies;
    private boolean botPlaying = false;


    /**
     * Class constructor
     * @param blockMatrix model.
     */
    public Bot(BlockMatrix blockMatrix, List<BotStrategy> strategies) {

        this.blockMatrix = blockMatrix;
        this.strategies = strategies;
        this.botTimer = new Timeline(new KeyFrame(
            Duration.millis(Config.BOT_INTERVAL),
            e -> this.makeMove()
        ));
    }


    /**
     * Defines click event of BotMain button.
     */
    void setButtons() {

        this.botPlayButton.setOnAction(e -> this.startStopBot());
        this.strategySelector.getItems().addAll(this.strategies);
        this.strategySelector.setValue(this.strategies.get(0));

    }


    /**
     * Fires when main button is clicked.
     */
    private void startStopBot() {

        if(this.botPlaying) {
            this.botTimer.stop();
            this.botPlayButton.setText("Play");
        }
        else {
            this.botTimer.play();
            this.botPlayButton.setText("Stop");
        }

        this.botPlaying = !this.botPlaying;
    }


    /**
     * Clicks block at calculated location.
     */
    private void makeMove() {

    }

}
