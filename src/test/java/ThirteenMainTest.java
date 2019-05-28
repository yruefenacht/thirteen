import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

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
     * Clicks on info button.
     * @param robot moves cursor
     */
    @Test
    void showHelpWindow(FxRobot robot) {

        robot.sleep(2000);
        Assertions.assertThat(robot.lookup("#helpButton").queryButton()).hasText("");
        robot.clickOn("#helpButton");
        robot.sleep(2000);
    }

}