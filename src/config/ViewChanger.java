package config;

import controller.MainMenuController;
import controller.PlayfieldController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayfieldModel;
import java.io.IOException;

/**
 * ViewChange.java
 */
public class ViewChanger {

    public static void changeToPlayfield(ActionEvent event){
        try {
            PlayfieldModel playfieldModel = new PlayfieldModel();
            PlayfieldController playfieldController = new PlayfieldController(playfieldModel);
            FXMLLoader loader = new FXMLLoader(ViewChanger.class.getResource("/view/Playfield.fxml"));
            loader.setController(playfieldController);
            Parent root = loader.load();
            playfieldController.addPlayfield();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(ViewChanger.class.getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeToMainMenu(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewChanger.class.getResource("/view/MainMenu.fxml"));
            Parent root = fxmlLoader.load();
            fxmlLoader.<MainMenuController>getController().init();
            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(ViewChanger.class.getResource("/css/style.css").toExternalForm());
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
