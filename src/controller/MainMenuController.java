package controller;

import config.ViewChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MainMenuController {

    @FXML
    private Pane startPane;
    @FXML
    private Label startLabel ;
    @FXML
    private Button playButt;
    @FXML
    private Button levelsButt;
    @FXML
    private Button languageButt;
    @FXML
    private Button settingButt;
    @FXML
    private Button soundButt;

    public void init() {

        playButt.setOnAction(this::showPlayfield);
    }

    @FXML
    private void showPlayfield(ActionEvent event) {

        ViewChanger.changeToPlayfield(event);
    }

}
