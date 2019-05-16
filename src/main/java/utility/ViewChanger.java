package utility;

import config.Config;
import controller.*;
import entity.Animations;
import game.Highscore;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.PlayfieldModel;
import java.io.IOException;

/**
 * ViewChange.java
 * Changes views.
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 * Accessible from everywhere.
 */
public class ViewChanger {

    private static BorderPane root;
    private static PlayfieldController playfieldController;
    private static PlayfieldModel playfieldModel;
    private static BlockMatrix blockMatrix;
    private static StackPane playfieldContainer;
    private static VBox pauseMenu;
    private static VBox undoMenu;


    /**
     * Creates and returns root element.
     * @return root element
     */
    public static BorderPane init() {

        root = new BorderPane();
        return root;
    }


    /**
     * Model getter.
     * @return playfieldModel
     */
    public static BlockMatrix getBlockMatrix() {

        return blockMatrix;
    }


    /**
     * Show MainMenu.fxml.
     */
    public static void changeToMainMenu() {

        try {
            playfieldModel = new PlayfieldModel();
            MainMenuController mainMenuController = new MainMenuController();
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/MainMenu.fxml"));
            loader.setController(mainMenuController);
            root.setCenter(loader.load());
            mainMenuController.setButtons();

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Show Playfield.fxml.
     * @param forceRestart if true: generate new blocks
     */
    public static void changeToPlayfield(boolean forceRestart) {

        try {
            playfieldController = new PlayfieldController(playfieldModel);
            blockMatrix = new BlockMatrix(playfieldModel, Config.GRID_DIMENSION_X, Config.GRID_DIMENSION_Y);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/Playfield.fxml"));
            loader.setController(playfieldController);
            Parent playfield = loader.load();
            playfieldContainer = playfieldController.getPlayfield();
            playfieldController.createPlayfield();
            blockMatrix.initialise(forceRestart);
            root.setCenter(playfield);

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Show PlayfieldMenu.fxml.
     */
    public static void showPauseMenu() {

        try {
            PlayfieldMenuController playfieldMenuController = new PlayfieldMenuController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/PlayfieldMenu.fxml"));
            loader.setController(playfieldMenuController);
            pauseMenu = loader.load();
            pauseMenu.setPrefWidth(Config.GRID_DIMENSION_X * Config.BLOCK_WIDTH);
            pauseMenu.setPrefHeight(Config.GRID_DIMENSION_X * Config.BLOCK_HEIGHT);
            pauseMenu.setOpacity(0.0);
            playfieldMenuController.setButtons();
            playfieldContainer.getChildren().add(pauseMenu);
            playfieldController.setBlur(3);
            Animations.getFadeAnimation(pauseMenu, 0.0, 300.0, true).play();

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Remove PlayfieldMenu.fxml.
     */
    public static void closePauseMenu() {

        playfieldController.setBlur(0);
        FadeTransition fadeTransition = Animations.getFadeAnimation(pauseMenu, 0.0, 300.0, false);
        fadeTransition.setOnFinished(e -> playfieldContainer.getChildren().remove(pauseMenu));
        fadeTransition.play();
    }


    /**
     * Show GameOverMenu.fxml.
     */
    public static void showGameOverScreen(Highscore highscore) {

        try {
            GameOverMenuController gameOverMenuController = new GameOverMenuController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/GameOverMenu.fxml"));
            loader.setController(gameOverMenuController);
            VBox gameOverMenu = loader.load();
            gameOverMenu.setPrefWidth(Config.GRID_DIMENSION_X * Config.BLOCK_WIDTH);
            gameOverMenu.setPrefHeight(Config.GRID_DIMENSION_X * Config.BLOCK_HEIGHT);
            gameOverMenu.setOpacity(0.0);
            gameOverMenuController.setButtons();
            gameOverMenuController.setHighscore(highscore);
            playfieldContainer.getChildren().add(gameOverMenu);
            playfieldController.setBlur(3);
            Animations.getFadeAnimation(gameOverMenu, 500.0, 1000.0, true).play();
        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Show UndoMenu.fxml
     */
    public static void showUndoMenu() {

        try {
            UndoMenuController undoMenuController = new UndoMenuController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/UndoMenu.fxml"));
            loader.setController(undoMenuController);
            undoMenu = loader.load();
            undoMenu.setPrefWidth(Config.GRID_DIMENSION_X * Config.BLOCK_WIDTH);
            undoMenu.setPrefHeight(Config.GRID_DIMENSION_X * Config.BLOCK_HEIGHT);
            undoMenu.setOpacity(0.0);
            undoMenuController.setButtons();
            playfieldContainer.getChildren().add(undoMenu);
            playfieldController.setBlur(3);
            Animations.getFadeAnimation(undoMenu, 0, 500.0, true).play();

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Remove UndoMenu.fxml
     */
    public static void closeUndoMenu() {

        playfieldController.setBlur(0);
        FadeTransition fadeTransition = Animations.getFadeAnimation(undoMenu, 0.0, 300.0, false);
        fadeTransition.setOnFinished(e -> playfieldContainer.getChildren().remove(undoMenu));
        fadeTransition.play();
    }

}
