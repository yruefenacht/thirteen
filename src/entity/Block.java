package entity;

import config.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.PlayfieldModel;

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
    private PlayfieldModel playfieldModel;

    /**
     * Class constructor
     * @param x position x
     * @param y position y
     * @param value number on label
     */
    Block(PlayfieldModel playfieldModel, int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
        this.center = new Label();
        this.valueLabel = new Label(Integer.toString(value));
        this.playfieldModel = playfieldModel;

        this.setBackground();
        this.center.setAlignment(Pos.CENTER);
        this.center.setPrefWidth(Settings.BLOCK_WIDTH - Settings.GRID_SPACING);
        this.center.setPrefHeight(Settings.BLOCK_HEIGHT - Settings.GRID_SPACING);
        this.setLayoutX(this.x * Settings.BLOCK_WIDTH);
        this.setLayoutY(this.y * Settings.BLOCK_HEIGHT);
        this.setPrefSize(Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT);
        this.setCursor(Cursor.HAND);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("block");
        this.getChildren().addAll(this.center, this.valueLabel);
        this.setOnMouseClicked(e -> this.blockClicked());
    }

    /**
     * Set background color and connectors
     */
    private void setBackground() {

        this.center.setBackground(new Background(new BackgroundFill(
            Settings.BLOCK_COLORS.get(this.value),
            new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
            Insets.EMPTY)
        ));
    }

    /**
     * Sends coordinates to model
     */
    private void blockClicked() {

        this.playfieldModel.blockClicked(this);
    }


    public void falldown(int blocks) {


    }

    /**
     * Value setter
     */
    void updateValue() {

        this.value++;
        this.valueLabel.setText(Integer.toString(value));
        this.setBackground();
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

    /**
     * Checks whether 2 Block objects have equal position
     * @param block element to check
     * @return true or false
     */
    boolean equals(Block block) {

        if(block == null) return false;
        return block.getX() == this.getX() && block.getY() == this.getY();
    }

}
