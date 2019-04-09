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

    private Block[] blocks;

    /**
     * Class constructor
     * @param x layout value
     * @param y layout value
     * @param width layout width
     * @param height layout height
     * @param value number
     * @param blocks foreground Blocks
     */
    MergeBlock(int x, int y, int width, int height, int value, Block... blocks) {

        this.setLayoutX(x);
        this.setLayoutY(y);
        this.setPrefWidth(width);
        this.setPrefHeight(height);
        this.blocks = blocks;
        this.setBackground(new Background(new BackgroundFill(
                Settings.BLOCK_COLORS.get(value),
                new CornerRadii(Settings.BLOCK_BORDER_RADIUS),
                Insets.EMPTY
        )));
    }

    /**
     * Checks whether given Block overlaps with MergeBlock
     * @param block Block to check
     * @return true or false
     */
    boolean hasBlock(Block block) {

        for(Block b : this.blocks)
            if(block.equals(b)) return true;
        return false;
    }


}
