import config.Settings;
import controller.PlayfieldController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.PlayfieldModel;

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

        PlayfieldModel model = new PlayfieldModel();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/Playfield.fxml"));
        loader.setController(new PlayfieldController(model));

        Parent root = (Parent) loader.load();

        primaryStage.setScene(new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT));
        primaryStage.getIcons().add(new Image(ThirteenMain.class.getResourceAsStream("app_icon.png")));
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
