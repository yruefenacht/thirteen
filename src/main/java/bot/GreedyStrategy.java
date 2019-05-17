package bot;

import entity.Location;
import model.BlockMatrix;

/**
 * GreedyStrategy.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Goes for the blocks with highest values.
 */
public class GreedyStrategy implements BotStrategy {


    /**
     * Returns calculated location.
     * @return Location
     */
    @Override
    public Location getNextMove(BlockMatrix blockMatrix) {
        return null;
    }


    /**
     * ChoiceBox uses toString to display names of objects.
     * @return name of strategy
     */
    @Override
    public String toString() {

        return "Greedy";
    }

}
