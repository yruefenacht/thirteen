package entity;

import config.Config;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * MergeBlock.java
 *
 * A MergeBlock connects two Blocks graphically in a background layer.
 */
public class MergeBlock extends Entity {

    private int x1;
    private int x2;
    private int y1;
    private int y2;


    /**
     * Constructs a {@code MergeBlock} object.
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

        //Set position
        this.setLayoutX((x1 * Config.BLOCK_WIDTH) + (Config.GRID_SPACING / 2));
        this.setLayoutY((y1 * Config.BLOCK_HEIGHT) + (Config.GRID_SPACING / 2));

        this.setScaleX(0);
        this.setScaleY(0);

        //Set width and height
        int width;
        int height;

        if(x1 < x2) {
            width = Config.MERGE_BLOCK_LENGTH;
            height = Config.BLOCK_WIDTH - (Config.GRID_SPACING - 1);
        }
        else {
            height = Config.MERGE_BLOCK_LENGTH;
            width = Config.BLOCK_WIDTH - (Config.GRID_SPACING - 1);
        }

        this.setPrefWidth(width);
        this.setPrefHeight(height);

        //Set background color
        Color backgroundColor = (value == Config.LEVEL) ?
                Config.BLOCK_COLORS.get(0) : Config.BLOCK_COLORS.get(value);

        this.setBackground(new Background(new BackgroundFill(
                backgroundColor,
                new CornerRadii(Config.BLOCK_BORDER_RADIUS),
                Insets.EMPTY
        )));
    }


    /**
     * X1 getter.
     * @return x1
     */
    public int getX1() {

        return this.x1;
    }


    /**
     * X2 getter.
     * @return x2
     */
    public int getX2() {

        return this.x2;
    }


    /**
     * Y1 getter.
     * @return y1
     */
    public int getY1() {

        return this.y1;
    }


    /**
     * Y2 getter.
     * @return y2
     */
    public int getY2() {

        return this.y2;
    }

}
