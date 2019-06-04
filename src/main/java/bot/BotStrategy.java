package bot;

import entity.Location;
import model.BlockMatrix;

/**
 * BotStrategy.java
 *
 * Defines methods of type BotStrategy.
 */
public interface BotStrategy {

    Location getNextMove(BlockMatrix blockMatrix);
}
