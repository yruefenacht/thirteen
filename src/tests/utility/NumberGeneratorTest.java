package utility;

import config.Settings;
import game.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NumberGeneratorTest.java
 * Unit Test Class of NumberGenerator.java.
 */
class NumberGeneratorTest {

    private NumberGenerator numberGenerator;


    /**
     * Test initialization.
     */
    @BeforeEach
    void setUp() {

        this.numberGenerator = new NumberGenerator(new Level(0, 0, 0, 0));
        assertNotNull(this.numberGenerator);
    }


    /**
     * Test position of main block.
     */
    @Test
    void getInitialBlockX() {

        int initialBlockX = this.numberGenerator.getInitialBlockX();
        assertTrue(initialBlockX < Settings.GRID_DIMENSION_X);
        assertTrue(initialBlockX >= 0);
    }


    /**
     * Test frequency of numbers.
     */
    @Test
    void getRandomNumber() {

        int[] randomNumberCounters = new int[Settings.LEVEL];

        for(int i = 0; i < 100; i++) {
            int randomNumber = this.numberGenerator.getRandomNumber();
            randomNumberCounters[Settings.LEVEL - randomNumber]++;
        }

        int[] randomNumberCountersSorted = Arrays.copyOf(randomNumberCounters, Settings.LEVEL);
        Arrays.sort(randomNumberCountersSorted);

        //Ensure that lower numbers have higher frequency
        assertArrayEquals(randomNumberCounters, randomNumberCountersSorted);
    }

}