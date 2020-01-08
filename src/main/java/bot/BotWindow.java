package main.java.bot;

import javafx.application.Preloader;
import main.java.config.Config;
import main.java.utility.ViewChanger;
import main.java.model.BlockMatrix;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * BotMain.java
 *
 * Launches application with BotMain-Window.
 */
public class BotWindow extends Preloader {

    /**
     * Creates and launches BotMain-Window.
     * @param primaryStage game
     * @throws Exception Is thrown if any resource is not found
     */
    @Override
    public void start(Stage primaryStage) throws Exception {


        //BotMain stage
        Stage botStage = new Stage();


        //Font
        Font.loadFont(
            this.getClass().getClassLoader().getResourceAsStream("fonts/SourceSansPro-Regular.ttf"),
            Config.FONT_SIZE_DEFAULT
        );
        Font.loadFont(
            this.getClass().getClassLoader().getResourceAsStream("fonts/PermanentMarker-Regular.ttf"),
            Config.FONT_SIZE_DEFAULT
        );


        //Icon
        Image icon = new Image(
            Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/app_icon.png"))
        );


        //Stylesheet
        String stylesheet = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource("css/style.css")
        ).toString();
        String botStylesheet = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource("css/bot.css")
        ).toString();


        //Enable bot mode
        Config.BOT_MODE = true;


        //View
        Parent root = ViewChanger.init();
        ViewChanger.changeToPlayfield(true);
        BlockMatrix blockMatrix = ViewChanger.getBlockMatrix();


        //Scene
        Scene mainScene = new Scene(root, Config.SCENE_WIDTH, Config.SCENE_HEIGHT);
        mainScene.getStylesheets().add(stylesheet);


        //Stage
        primaryStage.setScene(mainScene);
        primaryStage.setMinWidth(Config.SCENE_WIDTH + Config.STAGE_WIDTH_PADDING);
        primaryStage.setMinHeight(Config.SCENE_HEIGHT + Config.STAGE_HEIGHT_PADDING);
        primaryStage.getIcons().add(icon);
        primaryStage.setTitle(Config.APP_TITLE);
        primaryStage.setOnCloseRequest(e -> botStage.close());
        primaryStage.show();


        //BotMain window
        List<BotStrategy> botStrategies = new ArrayList<>(Arrays.asList(
            new RandomStrategy(),
            new GreedyStrategy(),
            new BottomUpStrategy(),
            new TopDownStrategy()
        ));
        Bot botController = new Bot(blockMatrix, botStrategies);
        FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource("view/BotWindow.fxml"));
        loader.setController(botController);
        Parent botWindow = loader.load();
        botController.setButtons();


        //BotMain scene
        Scene botScene = new Scene(botWindow, Config.BOT_SCENE_WIDTH, Config.BOT_SCENE_HEIGHT);
        botScene.getStylesheets().add(botStylesheet);


        //BotMain Stage
        botStage.setScene(botScene);
        botStage.setMinWidth(Config.BOT_SCENE_WIDTH);
        botStage.setMinHeight(Config.BOT_SCENE_HEIGHT);
        botStage.getIcons().add(icon);
        botStage.setTitle(Config.BOT_TITLE);
        botStage.setOnCloseRequest(e -> primaryStage.close());
        botStage.show();
    }
}
