package main.java;

import main.java.config.Config;
import main.java.utility.ViewChanger;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * main.java.ThirteenMain.java
 *
 * This class provides the static main method as entry point for the JVM.
 */
public class Thirteen extends Application {

    /**
     * Creates Elements, adds them to scene and shows them eventually.
     * @param primaryStage The stage where scene can be added
     * @throws Exception Is thrown if any resource is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Font
        Font.loadFont(
            this.getClass().getClassLoader().getResourceAsStream("fonts/SourceSansPro-Regular.ttf"),
            Config.FONT_SIZE_DEFAULT
        );
        Font.loadFont(
            this.getClass().getClassLoader().getResourceAsStream("fonts/PermanentMarker-Regular.ttf"),
            Config.FONT_SIZE_DEFAULT
        );

        //View
        Parent root = ViewChanger.init();
        ViewChanger.changeToMainMenu();

        //Scene
        Scene scene = new Scene(root, Config.SCENE_WIDTH, Config.SCENE_HEIGHT);
        scene.getStylesheets().add(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("css/style.css")).toString()
        );

        //Stage
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(Config.SCENE_WIDTH + Config.STAGE_WIDTH_PADDING);
        primaryStage.setMinHeight(Config.SCENE_HEIGHT + Config.STAGE_HEIGHT_PADDING);
        primaryStage.getIcons().add(new Image(
            Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/app_icon.png"))
        ));
        primaryStage.setTitle(Config.APP_TITLE);
        primaryStage.show();
    }

}
