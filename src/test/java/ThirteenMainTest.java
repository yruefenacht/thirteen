import entity.Block;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.robot.Motion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ThirteenMainTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Tests GUI.
 * ---------------------------------------
 * WORKS ONLY WITH 1920 x 1080 RESOLUTION
 * ---------------------------------------
 */
@ExtendWith(ApplicationExtension.class)
class ThirteenMainTest {


    /**
     * Launch App.
     * @param stage on given stage
     * @throws Exception required
     */
    @Start
    private void start(Stage stage) throws Exception {

        new ThirteenMain().start(stage);
    }


    /**
     * Clicks through application.
     * @param robot moves cursor
     */
    @Test
    void testApplication(FxRobot robot) {

        //------------------
        // TEST HELP WINDOW
        //------------------

        //Info button
        Button helpButton = robot.lookup("#helpButton").queryButton();
        Assertions.assertThat(helpButton).isEnabled();
        robot.clickOn(helpButton, Motion.DEFAULT);

        //Info window back button
        Button helpBackButton = robot.lookup("#helpBackButton").queryButton();
        Assertions.assertThat(helpBackButton).isEnabled();
        robot.clickOn(helpBackButton, Motion.DEFAULT);

        //Main menu play button
        Button playButton = robot.lookup("#mainMenuPlayButton").queryButton();
        Assertions.assertThat(playButton).isEnabled();
        robot.clickOn(playButton, Motion.DEFAULT);

        //-----------------
        // PLAYFIELD RESET
        //-----------------

        //Playfield pause button
        Button pauseButton = robot.lookup("#playfieldMenuBarButton").queryButton();
        Assertions.assertThat(pauseButton).isEnabled();
        robot.clickOn(pauseButton, Motion.DEFAULT);

        //Playfield menu restart button
        Button restartButton = robot.lookup("#playfieldMenuRestart").queryButton();
        Assertions.assertThat(restartButton).isEnabled();
        robot.clickOn(restartButton, Motion.DEFAULT);

        //----------------------
        // TEST PLAYFIELD BOMB
        //----------------------

        Button bombButton = robot.lookup("#playfieldToolBomb").queryButton();
        Button undoButton = robot.lookup("#playfieldToolUndo").queryButton();

        Assertions.assertThat(undoButton).isDisabled();

        //Click block
        List<Block> blocks = new ArrayList<Block>(robot.lookup(".playfield__block").queryAllAs(Block.class));
        List<Block> blocksWithNeighbors = this.getBlocksWithNeighbors(blocks);
        robot.clickOn(blocksWithNeighbors.get(0), Motion.DEFAULT);

        //Playfield bomb button
        Assertions.assertThat(bombButton).isEnabled();
        robot.clickOn(bombButton, Motion.DEFAULT);

        //Click block
        blocks = new ArrayList<Block>(robot.lookup(".playfield__block").queryAllAs(Block.class));
        blocksWithNeighbors = this.getBlocksWithNeighbors(blocks);
        robot.clickOn(blocksWithNeighbors.get(0), Motion.DEFAULT);

        Assertions.assertThat(bombButton).isDisabled();
        Assertions.assertThat(undoButton).isDisabled();

        //-----------------
        // PLAYFIELD RESET
        //-----------------

        //Playfield pause button
        pauseButton = robot.lookup("#playfieldMenuBarButton").queryButton();
        Assertions.assertThat(pauseButton).isEnabled();
        robot.clickOn(pauseButton, Motion.DEFAULT);

        //Playfield menu restart button
        restartButton = robot.lookup("#playfieldMenuRestart").queryButton();
        Assertions.assertThat(restartButton).isEnabled();
        robot.clickOn(restartButton, Motion.DEFAULT);

        //---------------------
        // PLAYFIELD TEST UNDO
        //--------------------

        //Click block
        blocks = new ArrayList<Block>(robot.lookup(".playfield__block").queryAllAs(Block.class));
        blocksWithNeighbors = this.getBlocksWithNeighbors(blocks);
        robot.clickOn(blocksWithNeighbors.get(0), Motion.DEFAULT);

        //Playfield undo button
        undoButton = robot.lookup("#playfieldToolUndo").queryButton();
        Assertions.assertThat(undoButton).isEnabled();
        robot.clickOn(undoButton, Motion.DEFAULT);

        //Playfield undo button accept
        Button undoMenuConfirm = robot.lookup("#undoMenuConfirm").queryButton();
        Assertions.assertThat(undoMenuConfirm).isEnabled();
        robot.clickOn(undoMenuConfirm, Motion.DEFAULT);

        Assertions.assertThat(undoButton).isDisabled();

        //Playfield pause
        pauseButton = robot.lookup("#playfieldMenuBarButton").queryButton();
        Assertions.assertThat(pauseButton).isEnabled();
        robot.clickOn(pauseButton, Motion.DEFAULT);

        //Playfield menu quit button
        Button quitButton = robot.lookup("#playfieldMenuQuit").queryButton();
        Assertions.assertThat(quitButton).isEnabled();
        robot.clickOn(quitButton, Motion.DEFAULT);

        //-----------------------------------
        // MAIN MENU TEST LANGUAGE AND SOUND
        //-----------------------------------

        //Main menu language button
        Button languageButton = robot.lookup("#languageButton").queryButton();
        Assertions.assertThat(languageButton).isEnabled();
        robot.clickOn(languageButton, Motion.DEFAULT);

        //Main menu sound button
        Button soundButton = robot.lookup("#soundButton").queryButton();
        Assertions.assertThat(soundButton).isEnabled();
        robot.clickOn(soundButton, Motion.DEFAULT);

        //Play
        playButton = robot.lookup("#mainMenuPlayButton").queryButton();
        Assertions.assertThat(playButton).isEnabled();
        robot.clickOn(playButton, Motion.DEFAULT);

        //Click block
        blocks = new ArrayList<Block>(robot.lookup(".playfield__block").queryAllAs(Block.class));
        blocksWithNeighbors = this.getBlocksWithNeighbors(blocks);
        robot.clickOn(blocksWithNeighbors.get(0), Motion.DEFAULT);

        //Playfield pause button
        pauseButton = robot.lookup("#playfieldMenuBarButton").queryButton();
        Assertions.assertThat(pauseButton).isEnabled();
        robot.clickOn(pauseButton, Motion.DEFAULT);

        //Playfield menu quit
        quitButton = robot.lookup("#playfieldMenuQuit").queryButton();
        Assertions.assertThat(quitButton).isEnabled();
        robot.clickOn(quitButton, Motion.DEFAULT);
    }


    /**
     * Gets list of Blocks with neighbors.
     * @param blocks all blocks
     * @return list of Blocks
     */
    private List<Block> getBlocksWithNeighbors(List<Block> blocks) {

        return blocks.stream().filter(block -> {

            int x = block.getX();
            int y = block.getY();
            int value = block.getValue();

            Block[] neighbors = new Block[]{
                this.getBlockAt(blocks, x, y - 1),
                this.getBlockAt(blocks, x + 1, y),
                this.getBlockAt(blocks, x, y + 1),
                this.getBlockAt(blocks, x - 1, y)
            };

            for(Block neighbor : neighbors) {
                if(neighbor == null) continue;
                if(neighbor.getValue() == value) return true;
            }
            return false;

        }).collect(Collectors.toList());
    }


    /**
     * Gets Block from list with x and y.
     * @param blocks list of blocks
     * @param x wanted x
     * @param y wanted y
     * @return found Block or else null
     */
    private Block getBlockAt(List<Block> blocks, int x, int y) {

        return blocks.stream()
            .filter(b -> b.getX() == x && b.getY() == y)
            .findAny()
            .orElse(null);
    }

}