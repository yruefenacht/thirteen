import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.robot.Motion;

/**
 * ThirteenMainTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Test GUI.
 */
@ExtendWith(ApplicationExtension.class)
class ThirteenMainTest {

    private String scale = System.getProperty("SCALE");
    private double scaledPercent = (scale != null) ? Double.parseDouble(scale) : 100;


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

        //Info button
        Button helpButton = robot.lookup("#helpButton").queryButton();
        Assertions.assertThat(helpButton).isEnabled();
        Point2D helpButtonPoint = this.getScaledClickPointForNode(helpButton, this.scaledPercent);
        robot.clickOn(helpButtonPoint, Motion.DEFAULT);

        //Info window back button
        Button helpBackButton = robot.lookup("#helpBackButton").queryButton();
        Assertions.assertThat(helpBackButton).isEnabled();
        Point2D helpBackButtonPoint = this.getScaledClickPointForNode(helpBackButton, this.scaledPercent);
        robot.clickOn(helpBackButtonPoint, Motion.DEFAULT);

        //Main menu play button
        Button playButton = robot.lookup("#mainMenuPlayButton").queryButton();
        Assertions.assertThat(playButton).isEnabled();
        Point2D playButtonPoint = this.getScaledClickPointForNode(playButton, this.scaledPercent);
        robot.clickOn(playButtonPoint, Motion.DEFAULT);

        //Playfield pause button
        Button pauseButton = robot.lookup("#playfieldMenuBarButton").queryButton();
        Assertions.assertThat(pauseButton).isEnabled();
        Point2D pauseButtonPoint = this.getScaledClickPointForNode(pauseButton, this.scaledPercent);
        robot.clickOn(pauseButtonPoint, Motion.DEFAULT);

        //Playfield menu restart button
        Button restartButton = robot.lookup("#playfieldMenuRestart").queryButton();
        Assertions.assertThat(restartButton).isEnabled();
        Point2D restartButtonPoint = this.getScaledClickPointForNode(restartButton, this.scaledPercent);
        robot.clickOn(restartButtonPoint, Motion.DEFAULT);

        //Playfield pause again
        robot.clickOn(pauseButtonPoint, Motion.DEFAULT);

        //Playfield menu quit button
        Button quitButton = robot.lookup("#playfieldMenuQuit").queryButton();
        Assertions.assertThat(quitButton).isEnabled();
        Point2D quitButtonPoint = this.getScaledClickPointForNode(quitButton, this.scaledPercent);
        robot.clickOn(quitButtonPoint, Motion.DEFAULT);

        //Main menu language button
        Button languageButton = robot.lookup("#languageButton").queryButton();
        Assertions.assertThat(languageButton).isEnabled();
        Point2D languageButtonPoint = this.getScaledClickPointForNode(languageButton, this.scaledPercent);
        robot.clickOn(languageButtonPoint, Motion.DEFAULT);

        //Main menu sound button
        Button soundButton = robot.lookup("#soundButton").queryButton();
        Assertions.assertThat(soundButton).isEnabled();
        Point2D soundButtonPoint = this.getScaledClickPointForNode(soundButton, this.scaledPercent);
        robot.clickOn(soundButtonPoint, Motion.DEFAULT);

        //Play
        robot.clickOn(playButtonPoint, Motion.DEFAULT);
    }


    /**
     * Returns location of given Node.
     * @param unscaledNode node
     * @param scaledPercent screen size scale
     * @return Point2D of given Node
     */
    private Point2D getScaledClickPointForNode(Node unscaledNode, double scaledPercent)
    {
        double scaledValue = scaledPercent / 100;
        Point2D topLeftPoint = unscaledNode.localToScreen(0, 0);
        Point2D scaledPos = new Point2D(topLeftPoint.getX() * scaledValue, topLeftPoint.getY() * scaledValue);
        Rectangle scaledNode = new Rectangle(
            scaledPos.getX(), scaledPos.getY(),
            unscaledNode.getBoundsInLocal().getWidth() * scaledValue,
            unscaledNode.getBoundsInLocal().getHeight() * scaledValue
        );

        return new Point2D(
            scaledNode.getX() + scaledNode.getWidth() / 2,
            scaledNode.getY() + scaledNode.getHeight() / 2
        );
    }

}