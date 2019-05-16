package bot;

import entity.Location;

/**
 * RandomStrategy.java
 * Gets random block from random group.
 */
public class RandomStrategy implements BotStrategy {


    /**
     * Calculates the next move.
     * @return x and y
     */
    @Override
    public Location getNextMove() {
        return null;
    }


    /**
     * ChoiceBox uses toString to display names of objects.
     * @return name of strategy
     */
    @Override
    public String toString() {

        return "Random";
    }

}
