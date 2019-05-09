package config;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

/**
 * Settings.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 * This class stores all constant variables of the project.
 */
public class Settings {

    //General
    public static final String APP_TITLE = "Thirteen";
    public static final int FONT_SIZE_DEFAULT = 30;

    //Level
    public static int LEVEL = 6;
    public static int LEVEL_DEFAULT = 6;
    public static int LEVEL_RANGE = 5;
    public static int LEVEL_RANGE_DEFAULT = 5;
    public static int UPDATE_RANGE_MIN = 7;
    public static int UPDATE_RANGE_MAX = 11;

    //Prices
    public static final int TOOL_COST = 50;
    public static final int STAR_COUNT_DEFAULT = 50;
    public static int STAR_COUNT = 50;

    //Boolean
    public static boolean BOMB_MODE = false;
    public static boolean IS_ANIMATING = false;

    //Grid
    public static final int GRID_DIMENSION_X = 5;
    public static final int GRID_DIMENSION_Y = 5;
    public static final int GRID_SPACING = 8;
    public static final int GRID_ANIMATION = 350;

    //Block
    public static final int BLOCK_WIDTH = 80;
    public static final int BLOCK_HEIGHT = 80;
    public static final int BLOCK_ANIMATION = 250;
    public static final int BLOCK_BORDER_RADIUS = 5;
    public static final int MERGE_BLOCK_LENGTH = (2 * BLOCK_WIDTH) - (2 * BLOCK_BORDER_RADIUS);
    public static final int MAX_PREVIOUS_STATES = 3;

    //Scene
    private static final int SCENE_TOP = 275;
    private static final int SCENE_SIDE_PADDING = 50;
    public static final int SCENE_WIDTH = (GRID_DIMENSION_X * BLOCK_WIDTH) + SCENE_SIDE_PADDING;
    public static final int SCENE_HEIGHT = (GRID_DIMENSION_Y * BLOCK_HEIGHT) + SCENE_TOP;

    //Color codes
    public static final Map<Integer, Color> BLOCK_COLORS = new HashMap<>(){{
        put(0, Color.rgb(252, 252, 252));
        put(1, Color.rgb(230, 194, 74));
        put(2, Color.rgb(181, 207, 97));
        put(3, Color.rgb(198, 132, 84));
        put(4, Color.rgb(192, 83, 78));
        put(5, Color.rgb(196, 83, 147));
        put(6, Color.rgb(147, 73, 198));
        put(7, Color.rgb(96, 61, 165));
        put(8, Color.rgb(54, 65, 181));
        put(9, Color.rgb(54, 129, 197));
        put(10, Color.rgb(6, 161, 189));
        put(11, Color.rgb(82, 227, 220));
        put(12, Color.rgb(197, 238, 240));
        put(13, Color.rgb(200, 200, 200));
        put(14, Color.rgb(134, 134, 134));
        put(15, Color.rgb(79, 79, 79));
    }};
}
