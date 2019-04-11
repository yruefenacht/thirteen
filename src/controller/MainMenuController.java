package controller;

import config.ViewChanger;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenuController {

    @FXML
    private Button playButton;


    @FXML
    private void showPlayfield() {

        ViewChanger.changeToPlayfield();
    }

}
