package main.java.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import main.java.utility.ViewChanger;

/**
 * HelpWindowController.java
 *
 * Defines action for back button in main menu.
 */
public class HelpWindowController implements MenuController {


    @FXML
    private Button helpBackButton;


    /**
     * Define back button event.
     */
    @Override
    public void setButtons() {

        this.helpBackButton.setOnAction(e -> ViewChanger.changeToMainMenu());
    }

}
