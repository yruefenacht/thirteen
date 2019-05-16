package utility;

import config.Config;
import game.Level;
import java.util.Random;

/**
 * NumberGenerator.java
 * Generates random number for each block.
 */
public class NumberGenerator {

    private Random random;
    private Level level;


    /**
     * Class constructor.
     * @param level level
     */
    public NumberGenerator(Level level) {

        this.level = level;
        this.random = new Random();
    }


    /**
     * Get random x value
     * @return value
     */
    public int getInitialBlockX() {

        return this.random.nextInt(Config.GRID_DIMENSION_X);
    }


    /**
     * Retrieves random number based on level.
     * Creates probabilities with binomial distribution.
     * @return number
     */
    public int getRandomNumber() {

        int max = Config.LEVEL - 1;
        int min = Config.LEVEL - Config.LEVEL_RANGE;
        double range = max - min + 1;
        double p = (range / 3) / (range);

        //Setup 2D Array for numbers and their probabilities.
        double[][] probabilities = new double[2][(int)range];
        double probabilitySum = 0;

        //Set numbers and their probability (Create decimal number line).
        for(int i = 0; i < probabilities[0].length; i++) {

            int number = min + i;
            double probability = this.getProbability(number, (int)range, p);
            probabilitySum += probability;
            probabilities[0][i] = number;
            probabilities[1][i] = probability;
        }

        //Create random value in decimal number line.
        double randomProbability = random.nextDouble() * probabilitySum;
        double probabilityAccumulator = 0;

        //Find area of random decimal in decimal number line.
        for(int i = 0; i < probabilities[0].length; i++) {

            double number = probabilities[0][i];
            double probability = probabilities[1][i];

            probabilityAccumulator += probability;

            if(randomProbability <= probabilityAccumulator)
                return (int) number;
        }

        return min;
    }


    /**
     * Returns probability as decimal value.
     * @param k value in {min, ..., max}
     * @param n max
     * @param p 1 / number of values
     * @return decimal
     */
    private double getProbability(int k, int n, double p) {

        double probability = Math.pow(p, k) * Math.pow(1 - p, n - k);

        double flattenExponentialCurve = 1;
        while (k > 0) {
            flattenExponentialCurve *= ((double) n / (double) k);
            k--;
            n--;
        }
        return flattenExponentialCurve * probability;

    }


    /**
     * Update stars.
     */
    public void increaseStarCount() {

        this.level.increaseStars();
    }


    /**
     * Update level and range.
     */
    public void increaseLevel() {

        Config.LEVEL++;
        this.level.increaseLevel();
        Config.LEVEL_RANGE_HALT_COUNTER++;
        this.level.increaseLevelRangeHaltCounter();
        if(Config.LEVEL_RANGE_HALT_COUNTER % Config.LEVEL_RANGE_HALT != 0) {
            Config.LEVEL_RANGE++;
            this.level.increaseLevelRange();
        }
    }


    /**
     * Sets Level to default values.
     */
    public void resetLevel() {

        this.level = new Level(
            Config.LEVEL_DEFAULT,
            Config.STAR_COUNT_DEFAULT,
            Config.LEVEL_RANGE_DEFAULT, 0
        );
    }


    /**
     * Sets level to given value
     * @param level current level
     */
    public void setLevel(Level level) {

        this.level = level;
        Config.LEVEL = level.getLevel();
        Config.STAR_COUNT = level.getStars();
        Config.LEVEL_RANGE = level.getLevelRange();
        Config.LEVEL_RANGE_HALT_COUNTER = level.getLevelRangeHaltCounter();
    }


    /**
     * Level getter.
     * @return level
     */
    public Level getLevel() {

        return this.level;
    }

}
