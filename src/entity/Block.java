package entity;

import config.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Block.java
 * This class represents a single block on the play grid.
 */
class Block extends StackPane {

    private int x;
    private int y;
    private int value;
    private Label valueLabel;
    private Label background;

    /**
     * Class constructor
     * @param value Each block has a number
     */
    Block(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
        this.valueLabel = new Label(Integer.toString(this.value));
        this.setLayoutX(x * Settings.BLOCK_WIDTH);
        this.setLayoutY(y * Settings.BLOCK_HEIGHT);
        this.setPrefSize(Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT);
        this.setCursor(Cursor.HAND);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("block");
        this.background = new Label();
        this.background.setPrefSize(
            Settings.BLOCK_WIDTH - Settings.BLOCK_BORDER_RADIUS,
            Settings.BLOCK_HEIGHT - Settings.BLOCK_BORDER_RADIUS
        );
        this.background.setBackground(new Background(new BackgroundFill(
            Settings.BLOCK_COLORS.get(value),
            new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
            Insets.EMPTY)
        ));
        this.getChildren().addAll(background, valueLabel);
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
    public int getX() { return this.x; }

    /**
     * Y value getter
     * @return y
     */
    public int getY() { return this.y; }

    /**
     * Value getter
     * @return value
     */
    public int getValue() { return this.value; }

}
