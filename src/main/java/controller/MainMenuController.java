package main.java.controller;

import main.java.config.Config;
import main.java.utility.ViewChanger;
import main.java.entity.SnowFlake;
import main.java.game.Game;
import main.java.game.GameLoader;
import main.java.game.Settings;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.Random;

/**
 * MainMenuController.java
 *
 * Defines click events of main menu screen.
 */
public class MainMenuController implements MenuController {

    @FXML
    private Button mainMenuPlayButton;

    @FXML
    private Button soundButton;

    @FXML
    private Button helpButton;

    @FXML
    private Button languageButton;

    @FXML
    private Pane mainMenuBackground;

    private GameLoader gameLoader;
    private Game game;
    private Settings settings;
    private ImageView soundButtonImageView;
    private Image soundOnIcon;
    private Image soundOffIcon;


    /**
     * Constructs a {@code MainMenuController} object.
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
        ViewChanger.setLanguage(this.game.getSettings().getLanguagePointer());
    }


    /**
     * Defines click events of main menu buttons.
     */
    @Override
    public void setButtons(){

        this.mainMenuPlayButton.setOnAction(e -> ViewChanger.changeToPlayfield(false));
        this.soundButton.setGraphic(soundButtonImageView);
        this.soundButton.setOnAction(e -> this.toggleSound());
        this.helpButton.setOnAction(e -> ViewChanger.showHelpWindow());
        this.languageButton.setOnAction(e -> changeLanguage());
        this.updateLanguageOnButtons();
        this.letItSnow();
    }


    /**
     * Changes bundle of ViewChanger
     */
    private void changeLanguage(){

        ViewChanger.changeLanguage();
        this.updateLanguageOnButtons();
        this.game.getSettings().setLanguagePointer(ViewChanger.getLanguagePointer());
        this.gameLoader.saveGame(this.game);
    }


    /**
     * Sets current text on buttons in current language.
     */
    private void updateLanguageOnButtons() {

        this.mainMenuPlayButton.setText(ViewChanger.getLanguage().getString("MainMenu.play"));
        this.languageButton.setText(ViewChanger.getLanguage().getString("MainMenu.language"));
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


    /**
     * Create a new SnowFlake every second.
     */
    private void letItSnow() {

        Timeline snowFall = new Timeline(new KeyFrame(
            Duration.millis(750),
            e -> this.createSnowFlake()
        ));
        snowFall.setCycleCount(Animation.INDEFINITE);
        snowFall.play();
    }


    /**
     * Create SnowFlake with random values in bound.
     */
    private void createSnowFlake() {

        Random random = new Random();

        int rectangleX = random.nextInt(Config.SCENE_WIDTH);

        int rectangleMinLength = 25;
        int rectangleMaxLength = 50;
        int rectangleLength = random.nextInt((rectangleMaxLength - rectangleMinLength) + 1) + rectangleMinLength;

        int durationMin = 3000;
        int durationMax = 9000;
        int duration = random.nextInt((durationMax - durationMin) + 1) + durationMin;

        int rotate = random.nextInt(360);
        Color rectangleBackground = Config.BLOCK_COLORS.get(random.nextInt(10));

        this.mainMenuBackground.getChildren().add(new SnowFlake(
            this.mainMenuBackground,
            rectangleX,
            rectangleLength,
            rotate,
            duration,
            rectangleBackground
        ));
    }

}