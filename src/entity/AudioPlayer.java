package entity;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * AudioPlayer.java
 * Responsible for playing game soundtracks
 */
public class AudioPlayer {

    private Clip gameOverSoundSound;


    /**
     * Class constructor
     */
    public AudioPlayer() {

        try {
            AudioInputStream gameOverAudioInputStream = AudioSystem.getAudioInputStream(
                this.getClass().getResourceAsStream("/audio/GameOverSound.wav")
            );
            this.gameOverSoundSound = AudioSystem.getClip();
            this.gameOverSoundSound.open(gameOverAudioInputStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    /**
     * Played when player clicks Block
     */
    public void playGameOverSound() {

        this.gameOverSoundSound.setMicrosecondPosition(0);
        this.gameOverSoundSound.start();
    }

}
