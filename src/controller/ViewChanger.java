package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import model.PlayfieldModel;
import java.io.IOException;

/**
 * ViewChange.java
 */
public class ViewChanger {

    private static BorderPane root;

    /**
     * Creates and returns root element
     * @return root element
     */
    public static BorderPane init() {

        root = new BorderPane();
        return root;
    }

    /**
     * Show Playfield.fxml
     */
    public static void changeToPlayfield() {

        try {
            PlayfieldModel playfieldModel = new PlayfieldModel();
            PlayfieldController playfieldController = new PlayfieldController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/Playfield.fxml"));
            loader.setController(playfieldController);
            Parent playfield = loader.load();
            playfieldController.addPlayfield();
            root.setCenter(playfield);

        } catch (IOException e) { e.printStackTrace(); }
    }

    /**
     * Show MainMenu.fxml
     */
    public static void changeToMainMenu() {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewChanger.class.getResource("/view/MainMenu.fxml"));
            Parent mainMenu = fxmlLoader.load();
            root.setCenter(mainMenu);

        } catch (IOException e) { e.printStackTrace(); }
    }
}
