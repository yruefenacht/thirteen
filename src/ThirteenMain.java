import config.Settings;
import controller.ViewChanger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * ThirteenMain.java
 * This class provides the static main method as entry point for the JVM.
 */
public class ThirteenMain extends Application {

    /**
     * Creates Elements, adds them to scene and shows them eventually.
     * @param primaryStage The stage where scene can be added
     * @throws Exception Is thrown if any resource is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Font
        Font.loadFont(
            this.getClass().getResource("resources/fonts/SourceSansPro-Regular.ttf").toExternalForm(),
            Settings.FONT_SIZE_DEFAULT
        );
        Font.loadFont(
            this.getClass().getResource("resources/fonts/PermanentMarker-Regular.ttf").toExternalForm(),
            Settings.FONT_SIZE_DEFAULT
        );

        //View
        Parent root = ViewChanger.init();
        ViewChanger.changeToMainMenu();

        //Scene
        Scene scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
        scene.getStylesheets().add(this.getClass().getResource("css/style.css").toExternalForm());

        //Stage
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(Settings.SCENE_WIDTH);
        primaryStage.setMinHeight(Settings.SCENE_HEIGHT);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("images/app_icon.png")));
        primaryStage.setTitle(Settings.APP_TITLE);
        primaryStage.show();
    }

    /**
     * Entry point of program.
     * @param args Has command line arguments stored
     */
    public static void main(String[] args) {
        launch(args);
    }

}
