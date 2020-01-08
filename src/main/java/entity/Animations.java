package main.java.entity;

import main.java.config.Config;
import javafx.animation.*;
import javafx.scene.Parent;
import javafx.util.Duration;

/**
 * Animations.java
 *
 * Provides all animations.
 */
public class Animations {

    /**
     * Creates initial scale animation on given entity.
     * @param entity given entity
     * @return timeline
     */
    static Timeline getScaleAnimation(Entity entity) {

        return new Timeline(
            new KeyFrame(
                Duration.millis(Config.BLOCK_ANIMATION),
                new KeyValue(entity.scaleXProperty(), 1),
                new KeyValue(entity.scaleYProperty(), 1)
            )
        );
    }


    /**
     * Creates sinking animation on given entity.
     * @param entity given entity
     * @return timeline
     */
    static Timeline getSinkAnimation(Entity entity, int steps) {

        return new Timeline(
            new KeyFrame(
                Duration.millis(Config.BLOCK_ANIMATION),
                new KeyValue(entity.layoutYProperty(), entity.getLayoutY() + (Config.BLOCK_HEIGHT * steps)))
        );
    }


    /**
     * Fades element in.
     * @param element given element
     * @param delay delay
     * @param duration duration
     * @param fadeIn fadeIn or fadeOut
     * @return FadeTransition
     */
    public static FadeTransition getFadeAnimation(Parent element, double delay, double duration, boolean fadeIn) {

        double from = fadeIn ? 0.0 : 1.0;
        double to = fadeIn ? 1.0 : 0.0;
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), element);
        fadeTransition.setDelay(Duration.millis(delay));
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        return fadeTransition;
    }


    /**
     * Returns heart beat animation.
     * @param element on this element
     * @return ScaleTransition
     */
    public static ScaleTransition getScaleAnimation(Parent element) {

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), element);
        scaleTransition.setFromX(1.0);
        scaleTransition.setFromY(1.0);
        scaleTransition.setToX(1.25);
        scaleTransition.setToY(1.25);
        scaleTransition.setCycleCount(2);
        scaleTransition.setAutoReverse(true);
        return scaleTransition;
    }


    /**
     * Get Shake Animation.
     * @param entity animate this entity
     * @return TimeLine
     */
    static Timeline getShakeAnimation(Entity entity) {

        return new Timeline(
            new KeyFrame(
                Duration.millis(100),
                new KeyValue(entity.rotateProperty(), 20.0)
            ),
            new KeyFrame(
                Duration.millis(300),
                new KeyValue(entity.rotateProperty(), -20.0)
            ),
            new KeyFrame(
                Duration.millis(400),
                new KeyValue(entity.rotateProperty(), 0.0)
            )
        );
    }

}
