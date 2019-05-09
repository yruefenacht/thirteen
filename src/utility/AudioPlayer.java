package utility;

import javax.sound.sampled.*;
import java.io.IOException;

/**
 * AudioPlayer.java
 * Responsible for playing game soundtracks.
 */
public class AudioPlayer {

    private Clip popSound;
    private Clip levelUpSound;
    private Clip gameOverSoundSound;


    /**
     * Class constructor.
     */
    public AudioPlayer() {

        try {
            AudioInputStream popAudioInputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getResourceAsStream("/audio/PopSound.wav")
            );
            this.popSound = AudioSystem.getClip();
            this.popSound.open(popAudioInputStream);

            AudioInputStream levelUpAudioInputStream = AudioSystem.getAudioInputStream(
                    this.getClass().getResourceAsStream("/audio/LevelUpSound.wav")
            );
            this.levelUpSound = AudioSystem.getClip();
            this.levelUpSound.open(levelUpAudioInputStream);

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
     * Played when blocks merge.
     */
    public void playPopSound() {

        this.popSound.setMicrosecondPosition(0);
        this.popSound.start();
    }


    /**
     * Played when level increases.
     */
    public void playLevelUpSound() {

        this.levelUpSound.setMicrosecondPosition(0);
        this.levelUpSound.start();
    }


    /**
     * Played when player clicks Block.
     */
    public void playGameOverSound() {

        this.gameOverSoundSound.setMicrosecondPosition(0);
        this.gameOverSoundSound.start();
    }

}
