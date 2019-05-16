package controller;

import config.Config;
import game.Game;
import game.GameLoader;
import game.Settings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private Button mainMenuPlayButton;

    @FXML
    private Button soundButton;

    private GameLoader gameLoader;
    private Game game;
    private Settings settings;
    private ImageView soundButtonImageView;
    private Image soundOnIcon;
    private Image soundOffIcon;


    /**
     * Class constructor
     */
    public MainMenuController() {

        this.gameLoader = new GameLoader();
        this.game = this.gameLoader.loadGame();
        this.settings = this.game.getSettings();
        this.soundButtonImageView = new ImageView();
        this.soundButtonImageView.setFitHeight(Config.MAIN_MENU_ICON_SIZE);
        this.soundButtonImageView.setFitWidth(Config.MAIN_MENU_ICON_SIZE);
        this.soundOnIcon = new Image(this.getClass().getResourceAsStream("/images/sound_on.png"));
        this.soundOffIcon = new Image(this.getClass().getResourceAsStream("/images/sound_off.png"));
        Image soundImage = (this.settings.isSound()) ? this.soundOnIcon : this.soundOffIcon;
        this.soundButtonImageView.setImage(soundImage);
        Config.SOUND = this.settings.isSound();
    }


    /**
     * Defines click events of main menu buttons.
     */
    public void setButtons(){

        this.mainMenuPlayButton.setOnAction(e -> ViewChanger.changeToPlayfield(false));
        this.soundButton.setGraphic(soundButtonImageView);
        this.soundButton.setOnAction(e -> this.toggleSound());
    }


    /**
     * Flips sound setting.
     */
    private void toggleSound() {

        Config.SOUND = !Config.SOUND;
        this.settings.toggleSound();
        this.gameLoader.saveGame(this.game);
        Image soundImage = (this.settings.isSound()) ? this.soundOnIcon : this.soundOffIcon;
        this.soundButtonImageView.setImage(soundImage);
    }

}

