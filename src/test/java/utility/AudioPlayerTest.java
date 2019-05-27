package utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * AudioPlayerTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Test class of AudioPlayer.java.
 */
class AudioPlayerTest {

    private AudioPlayer audioPlayer;


    /**
     * Test initialization.
     */
    @BeforeEach
    void setUp() {

        this.audioPlayer = new AudioPlayer();
        assertNotNull(this.audioPlayer);
    }


    /**
     * Test pop sound.
     */
    @Test
    void playPopSound() {

        assertNotNull(this.audioPlayer);
    }


    /**
     * Test level up sound.
     */
    @Test
    void playLevelUpSound() {

        assertNotNull(this.audioPlayer);
    }


    /**
     * Test game over sound.
     */
    @Test
    void playGameOverSound() {

        assertNotNull(this.audioPlayer);
    }

}