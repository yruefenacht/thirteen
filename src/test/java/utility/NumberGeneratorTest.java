package utility;

import config.Config;
import game.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * NumberGeneratorTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Test class of NumberGenerator.java.
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
        assertTrue(initialBlockX < Config.GRID_DIMENSION_X);
        assertTrue(initialBlockX >= 0);
    }


    /**
     * Test frequency of numbers.
     */
    @Test
    void getRandomNumber() {

        int[] randomNumberCounters = new int[Config.LEVEL];

        for(int i = 0; i < 1000; i++) {
            int randomNumber = this.numberGenerator.getRandomNumber();
            randomNumberCounters[Config.LEVEL - randomNumber]++;
        }

        int[] randomNumberCountersSorted = Arrays.copyOf(randomNumberCounters, Config.LEVEL);
        Arrays.sort(randomNumberCountersSorted);

        //Ignore low numbers
        randomNumberCounters[Config.LEVEL - 1] = 0;
        randomNumberCounters[Config.LEVEL - 2] = 0;
        randomNumberCountersSorted[Config.LEVEL - 1] = 0;
        randomNumberCountersSorted[Config.LEVEL - 2] = 0;

        //Ensure that lower numbers have higher frequency
        assertArrayEquals(randomNumberCounters, randomNumberCountersSorted);
    }

}