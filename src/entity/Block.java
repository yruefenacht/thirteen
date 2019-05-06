package entity;

import config.Settings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.PlayfieldModel;

/**
 * Block.java
 * This class represents a single block on the play grid.
 */
public class Block extends Entity {

    private int x;
    private int y;
    private int value;
    private int sink;
    private Label center;
    private Label valueLabel;
    private PlayfieldModel playfieldModel;


    /**
     * Class constructor.
     * @param x position x
     * @param y position y
     * @param value number on label
     */
    public Block(PlayfieldModel playfieldModel, int x, int y, int value) {

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
        this.setScaleX(0);
        this.setScaleY(0);
        this.setPrefSize(Settings.BLOCK_WIDTH, Settings.BLOCK_HEIGHT );
        this.setCursor(Cursor.HAND);
        this.setAlignment(Pos.CENTER);
        this.getStyleClass().add("playfield__block");
        this.getChildren().addAll(this.center, this.valueLabel);
        this.setOnMouseClicked(e -> this.blockClicked());
    }


    /**
     * Set background color and connectors.
     */
    public void setBackground() {

        Color backgroundColor = (this.value == Settings.LEVEL) ?
            Settings.BLOCK_COLORS.get(0) : Settings.BLOCK_COLORS.get(this.value);

        if(this.value == Settings.LEVEL) backgroundColor = Settings.BLOCK_COLORS.get(0);

        this.center.setBackground(new Background(new BackgroundFill(
            backgroundColor,
            new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
            Insets.EMPTY)
        ));
    }


    /**
     * Sets or removes red border.
     * @param bombMode activate or deactivate
     */
    public void setBombMode(boolean bombMode) {

        if(bombMode)
            this.center.getStyleClass().add("playfield__block--bomb-mode");
        else
            this.center.getStyleClass().remove("playfield__block--bomb-mode");
    }


    /**
     * Sends coordinates to model.
     */
    private void blockClicked() {

        this.playfieldModel.blockClicked(new Location(this.x, this.y));
    }


    /**
     * Let block fall down by n blocks.
     */
    public void prepareToSink() {

        this.y++;
        this.sink++;
    }


    /**
     * Set Layout Y to this.y.
     */
    public void sink() {

        Animations.getSinkAnimation(this, this.sink).play();
        this.sink = 0;
    }


    /**
     * Shake when game is over.
     */
    public void shake() {

        Animations.getShakeAnimation(this).play();
    }


    /**
     * Value setter.
     */
    public void updateValue() {

        this.value++;
        this.valueLabel.setText(Integer.toString(value));
        this.setBackground();
    }


    /**
     * X value getter.
     * @return x
     */
    public int getX() {

        return this.x;
    }


    /**
     * Y value getter.
     * @return y
     */
    public int getY() {

        return this.y;
    }

}
