package controller;

import config.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * MainMenuController.java
 * Defines click events of main menu screen.
 */
public class MainMenuController {

    @FXML
    private Button playButton;


    /**
     * Define main button click event.
     */
    @FXML
    private void showPlayfield() {

        ViewChanger.changeToPlayfield(false);
    }

}
