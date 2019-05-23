package entity;

import config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * SnowFlake.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Rectangle falling down.
 */
public class SnowFlake extends Rectangle {


    /**
     * Constructs a {@code SnowFlake} object.
     * @param menuBackground Pane for SnowFlake to be added
     * @param x layout x
     * @param size width and height
     * @param rotate rotation value
     * @param background background color
     */
    public SnowFlake(Pane menuBackground, int x, int size, int rotate, int duration, Color background) {

        super(x, 10, size, size);
        this.setFill(background);
        this.setRotate(rotate);
        this.setArcWidth(5);
        this.setArcHeight(5);

        Timeline fallDown = new Timeline(
            new KeyFrame(
                Duration.millis(duration),
                new KeyValue(this.yProperty(), Config.SCENE_HEIGHT)
            )
        );
        fallDown.setOnFinished(e -> menuBackground.getChildren().remove(this));
        fallDown.play();
    }

}
