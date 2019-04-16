package entity;

import config.Settings;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 * Animations.java
 * Provides all block animations.
 */
class Animations {

    /**
     * Creates initial scale animation on given entity.
     * @param entity given entity
     * @return timeline
     */
    static Timeline getScaleAnimation(Entity entity) {

        return new Timeline(
            new KeyFrame(
                Duration.millis(Settings.BLOCK_ANIMATION),
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
    static Timeline getSinkAnimation(Entity entity) {

        return new Timeline(
            new KeyFrame(
                Duration.millis(Settings.BLOCK_ANIMATION),
                new KeyValue(entity.layoutYProperty(), entity.getLayoutY() + Settings.BLOCK_HEIGHT))
        );
    }
}
