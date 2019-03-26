import config.Settings;
import controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Model;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Model model = new Model();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("view/View.fxml"));
        loader.setController(new Controller(model));

        Parent root = (Parent) loader.load();

        primaryStage.setScene(new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT));
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("app_icon.png")));
        primaryStage.setTitle(Settings.APP_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
