package utility;

import config.Config;
import javax.sound.sampled.*;
import java.io.IOException;

/**
 * AudioPlayer.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Responsible for playing game soundtracks.
 */
public class AudioPlayer {

    private Clip popSound;
    private Clip levelUpSound;
    private Clip gameOverSoundSound;


    /**
     * Constructs a {@code AudioPlayer} object.
     */
    public AudioPlayer() {

        try {
            AudioInputStream popAudioInputStream = AudioSystem.getAudioInputStream(
                this.getClass().getResourceAsStream("../audio/pop_sound.wav")
            );
            this.popSound = AudioSystem.getClip();
            this.popSound.open(popAudioInputStream);

            AudioInputStream levelUpAudioInputStream = AudioSystem.getAudioInputStream(
                this.getClass().getResourceAsStream("../audio/level_up_sound.wav")
            );
            this.levelUpSound = AudioSystem.getClip();
            this.levelUpSound.open(levelUpAudioInputStream);

            AudioInputStream gameOverAudioInputStream = AudioSystem.getAudioInputStream(
                this.getClass().getResourceAsStream("../audio/game_over_sound.wav")
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

        if(Config.SOUND) {
            this.popSound.setMicrosecondPosition(0);
            this.popSound.start();
        }
    }


    /**
     * Played when level increases.
     */
    public void playLevelUpSound() {

        if(Config.SOUND) {
            this.levelUpSound.setMicrosecondPosition(0);
            this.levelUpSound.start();
        }

    }


    /**
     * Played when player clicks Block.
     */
    public void playGameOverSound() {

        if(Config.SOUND) {
            this.gameOverSoundSound.setMicrosecondPosition(0);
            this.gameOverSoundSound.start();
        }
    }

}
