package controller;

import config.Settings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.PlayfieldModel;
import java.io.IOException;

/**
 * ViewChange.java
 */
public class ViewChanger {

    private static BorderPane root;
    private static PlayfieldModel playfieldModel;
    private static VBox pauseMenu;
    private static StackPane playfieldContainer;


    /**
     * Creates and returns root element
     * @return root element
     */
    public static BorderPane init() {

        root = new BorderPane();
        return root;
    }


    /**
     * Show MainMenu.fxml
     */
    public static void changeToMainMenu() {

        try {
            Parent mainMenu = FXMLLoader.load(ViewChanger.class.getResource("/view/MainMenu.fxml"));
            root.setCenter(mainMenu);

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Show Playfield.fxml
     */
    static void changeToPlayfield() {

        try {
            playfieldModel = new PlayfieldModel();
            PlayfieldController playfieldController = new PlayfieldController(playfieldModel);
            BlockMatrix blockMatrix = new BlockMatrix(playfieldModel, Settings.GRID_DIMENSION_X, Settings.GRID_DIMENSION_Y);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/Playfield.fxml"));
            loader.setController(playfieldController);
            Parent playfield = loader.load();
            playfieldContainer = playfieldController.getPlayfield();
            playfieldController.addPlayfield();
            blockMatrix.createMatrix();
            root.setCenter(playfield);

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Show PlayfieldMenu.fxml
     */
    static void showPauseMenu() {

        try {
            PlayfieldMenuController playfieldMenuController = new PlayfieldMenuController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/PlayfieldMenu.fxml"));
            loader.setController(playfieldMenuController);
            pauseMenu = loader.load();
            pauseMenu.setPrefWidth(Settings.GRID_DIMENSION_X * Settings.BLOCK_WIDTH);
            pauseMenu.setPrefHeight(Settings.GRID_DIMENSION_X * Settings.BLOCK_HEIGHT);
            playfieldMenuController.setButtons();
            playfieldContainer.getChildren().add(pauseMenu);

        } catch (IOException e) { e.printStackTrace(); }
    }


    /**
     * Remove PlayfieldMenu.fxml
     */
    static void closePauseMenu() {

        playfieldContainer.getChildren().remove(pauseMenu);
    }


    /**
     * Show GameOverMenu.fxml
     */
    static void showGameOverScreen() {

        try {
            GameOverMenuController gameOverMenuController = new GameOverMenuController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/GameOverMenu.fxml"));
            loader.setController(gameOverMenuController);
            VBox gameOverMenu = loader.load();
            gameOverMenu.setPrefWidth(Settings.GRID_DIMENSION_X * Settings.BLOCK_WIDTH);
            gameOverMenu.setPrefHeight(Settings.GRID_DIMENSION_X * Settings.BLOCK_HEIGHT);
            gameOverMenuController.setButtons();
            playfieldContainer.getChildren().add(gameOverMenu);

        } catch (IOException e) { e.printStackTrace(); }
    }

}
