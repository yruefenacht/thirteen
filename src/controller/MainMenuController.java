package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.ViewChanger;

/**
 * MainMenuController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
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
