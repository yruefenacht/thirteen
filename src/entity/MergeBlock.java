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

    private int x1;
    private int x2;
    private int y1;
    private int y2;

    /**
     * Class constructor
     * @param x1 start x
     * @param y1 start y
     * @param x2 end x
     * @param y2 end y
     * @param value number
     */
    public MergeBlock(int x1, int y1, int x2, int y2, int value) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;

        this.setLayoutX((x1 * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2));
        this.setLayoutY((y1 * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2));

        int width;
        int height;

        if(x1 < x2) {
            width = Settings.MERGE_BLOCK_LENGTH;
            height = Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1);
        }
        else {
            height = Settings.MERGE_BLOCK_LENGTH;
            width = Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1);
        }

        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.setBackground(new Background(new BackgroundFill(
                Settings.BLOCK_COLORS.get(value),
                new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
                Insets.EMPTY
        )));
    }

    public int getX1() {

        return this.x1;
    }

    public int getX2() {

        return this.x2;
    }

    public int getY1() {

        return this.y1;
    }

    public int getY2() {

        return this.y2;
    }

}
