package controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class StartfieldController implements PropertyChangeListener {
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


    public StartfieldController() {
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
