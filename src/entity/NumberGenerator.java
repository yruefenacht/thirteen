package entity;

import config.Settings;
import java.util.Arrays;
import java.util.Random;

/**
 * NumberGenerator.java
 * Generates random number for each block.
 */
public class NumberGenerator {

    private Random random = new Random();


    /**
     * Get random x value
     * @return value
     */
    public int getInitialBlockX() {

        return this.random.nextInt(Settings.GRID_DIMENSION_X);
    }


    /**
     * Retrieves random number based on level.
     * Creates probabilities with binomial distribution.
     * @return number
     */
    public int getRandomNumber() {

        int max = Settings.LEVEL;
        int min = Settings.LEVEL - Settings.LEVEL_RANGE;
        int randomNum = random.nextInt((max - min) + 1) + min;
        long[][] probabilities = this.getBinomialDistribution(min, max, max);

        if(Settings.LEVEL < 10) {
            Arrays.sort(probabilities[1]);
            this.reverseArray(probabilities[1]);
        }

        int p = 0;
        for(int i = 0; i < probabilities[0].length; i++) {
            p += probabilities[1][i];
            if(randomNum <= p) {
                return (int) probabilities[0][i];
            }
        }
        return min;
    }


    /**
     * Reverses indexes of values in array.
     * @param array affected array
     */
    private void reverseArray(long[] array) {

        for(int i = 0; i < array.length / 2; i++)
        {
            long temp = array[i];
            array[i] = array[array.length - i - 1];
            array[array.length - i - 1] = temp;
        }
    }


    /**
     * Generates probabilities with binomial distribution.
     * @param min start
     * @param max end
     * @param total number of probabilities to distribute
     * @return 1. array containing values 2. array containing probabilities
     */
    private long[][] getBinomialDistribution(int min, int max, long total) {

        int n = max - min;
        long[][] ret = new long[2][n + 1];
        int mean = (n + 1) / 2;
        float p = 1;
        if (n > 0) {
            p = (float) mean / (float) n;
        }

        long count = 0;
        for (int i = 0; i <= n; i++) {
            double p_i = combination(n, i) * Math.pow(p, i)
                    * Math.pow((1 - p), (n - i));
            long count_i = (long) (total * p_i);
            ret[0][i] = i + min;
            ret[1][i] = count_i;
            count += count_i;
        }

        while (count < total) {
            int i = random.nextInt(n + 1);
            ret[1][i]++;
            count++;
        }

        return ret;
    }


    /**
     * Calculates combination.
     * @param n numerator
     * @param k denominator
     * @return combination
     */
    private double combination(int n, int k) {

        double ret = 1;
        while (k > 0) {
            ret = ret * ((double) n / (double) k);
            k--;
            n--;
        }
        return ret;
    }


    /**
     * Update level and range.
     */
    public void updateLevel() {

        Settings.LEVEL++;

        if(Settings.LEVEL <= Settings.UPDATE_RANGE_MAX && Settings.LEVEL >= Settings.UPDATE_RANGE_MIN)
            Settings.LEVEL_RANGE++;
    }

}
