package entity;

import config.Settings;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

/**
 * MergeBlock.java
 * A MergeBlock connects two Blocks graphically in a background layer.
 */
public class MergeBlock extends Label {

    /**
     * Class constructor
     * @param x position on x axis
     * @param y position on y axis
     * @param width label width
     * @param height label height
     * @param value number to be displayed
     */
    MergeBlock(int x, int y, int width, int height, int value) {

        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setBackground(new Background(new BackgroundFill(
                Settings.BLOCK_COLORS.get(value),
                new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
                Insets.EMPTY
        )));
    }


}
