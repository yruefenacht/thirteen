package bot;

import config.Config;
import entity.Location;
import javafx.animation.Animation;
import model.BlockMatrix;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.util.Duration;
import utility.ViewChanger;
import java.util.List;

/**
 * Bot.java
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
    private BotStrategy selectedStrategy;
    private boolean isPlaying = false;


    /**
     * Constructs a {@code Bot} object.
     * @param blockMatrix model
     * @param strategies are displayed in ChoiceBox
     */
    public Bot(BlockMatrix blockMatrix, List<BotStrategy> strategies) {

        assert (!strategies.isEmpty());
        this.blockMatrix = blockMatrix;
        this.strategies = strategies;
        this.botTimer = new Timeline(new KeyFrame(
            Duration.millis(Config.BOT_INTERVAL),
            e -> this.makeMove()
        ));
        this.botTimer.setCycleCount(Animation.INDEFINITE);
    }


    /**
     * Defines click event of BotMain button.
     */
    void setButtons() {

        this.botPlayButton.setOnAction(e -> this.startStopBot());
        this.strategySelector.getItems().addAll(this.strategies);
        this.selectedStrategy = this.strategies.get(0);
        this.strategySelector.setValue(this.strategies.get(0));
        this.strategySelector.getSelectionModel().selectedIndexProperty().addListener(
            (observable, oldValue, newValue) -> this.selectedStrategy = this.strategies.get((Integer) newValue)
        );
    }


    /**
     * Fires when main button is clicked.
     */
    private void startStopBot() {

        this.blockMatrix = ViewChanger.getBlockMatrix();

        if(this.isPlaying) {
            this.botTimer.stop();
            this.botPlayButton.setText("Play");
        }
        else {
            this.botTimer.play();
            this.botPlayButton.setText("Stop");
        }

        this.isPlaying = !this.isPlaying;
    }


    /**
     * Clicks block at calculated location.
     */
    private void makeMove() {

        if(blockMatrix.getMergeBlocksAsList().isEmpty()) {
            startStopBot();
            return;
        }

        Location nextMove = this.selectedStrategy.getNextMove(this.blockMatrix);
        this.blockMatrix.blockClicked(nextMove);
    }

}
