package entity;

import config.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Block.java
 * This class represents a single block on the play grid.
 */
public class Block extends StackPane {

    private int x;
    private int y;
    private int value;
    private Label center;
    private Label valueLabel;

    /**
     * Class constructor
     * @param x position x
     * @param y position y
     * @param value number on label
     */
    Block(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
        this.valueLabel = new Label(Integer.toString(value));

        this.setBackground();
        this.setLayoutX(this.x * Settings.BLOCK_WIDTH);
        this.setLayoutY(this.y * Settings.BLOCK_HEIGHT);
        this.setPrefSize(Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT);
        this.setCursor(Cursor.HAND);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("block");

        this.getChildren().addAll(this.center, this.valueLabel);
    }

    /**
     * Set background color and connectors
     */
    private void setBackground() {

        this.center = new Label();

        this.center.setAlignment(Pos.CENTER);
        this.center.setPrefSize(
                Settings.BLOCK_WIDTH - Settings.BLOCK_BORDER_RADIUS,
                Settings.BLOCK_HEIGHT - Settings.BLOCK_BORDER_RADIUS
        );
        this.center.setBackground(new Background(new BackgroundFill(
                Settings.BLOCK_COLORS.get(value),
                new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
                Insets.EMPTY)
        ));
    }

    /**
     * Value setter
     * @param value new value
     */
    public void setValue(int value) {

        this.value = value;
        this.valueLabel.setText(Integer.toString(value));
    }

    /**
     * X value getter
     * @return x
     */
    int getX() { return this.x; }

    /**
     * Y value getter
     * @return y
     */
    int getY() { return this.y; }

    /**
     * Value getter
     * @return value
     */
    int getValue() { return this.value; }

}
