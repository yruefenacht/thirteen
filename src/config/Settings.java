package config;

import javafx.scene.paint.Color;
import java.util.Map;

/**
 * Settings.java
 * This class stores all constant variables of the project.
 */
public class Settings {

    public static final String APP_TITLE = "Thirteen";
    public static final int FONT_SIZE_DEFAULT = 30;
    public static final int LEVEL = 6;
    public static final int LEVEL_RANGE = 5;

    public static final int GRID_DIMENSION_X = 5;
    public static final int GRID_DIMENSION_Y = 5;
    public static final int GRID_SPACING = 8;
    public static final int BLOCK_WIDTH = 80;
    public static final int BLOCK_HEIGHT = 80;
    public static final int BLOCK_ANIMATION = 250;
    public static final int GRID_ANIMATION = 300;
    public static final int BLOCK_BORDER_RADIUS = 5;
    public static final int MERGE_BLOCK_LENGTH = (2 * BLOCK_WIDTH) - (2 * BLOCK_BORDER_RADIUS);

    private static final int SCENE_TOP = 250;

    private static final int SCENE_SIDE_PADDING = 50;
    public static final int SCENE_WIDTH = (GRID_DIMENSION_X * BLOCK_WIDTH) + SCENE_SIDE_PADDING;
    public static final int SCENE_HEIGHT = (GRID_DIMENSION_Y * BLOCK_HEIGHT) + SCENE_TOP;

    public static final Map<Integer, Color> BLOCK_COLORS = Map.of(
        0, Color.rgb(252, 252, 252),
        1, Color.rgb(181, 207, 97),
        2, Color.rgb(230, 194, 74),
        3, Color.rgb(198, 132, 84),
        4, Color.rgb(192, 83, 78),
        5, Color.rgb(196, 83, 147),
        6, Color.rgb(147, 73, 198),
        7, Color.rgb(96, 61, 165),
        8, Color.rgb(54, 65, 181),
        9, Color.rgb(54, 129, 197)
    );
}
