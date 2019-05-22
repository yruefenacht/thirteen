package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import utility.ViewChanger;

/**
 * HelpWindowController.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
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
