package main.java.bot;

import main.java.entity.Location;
import main.java.model.BlockMatrix;

/**
 * BotStrategy.java
 *
 * Defines methods of type BotStrategy.
 */
public interface BotStrategy {

    Location getNextMove(BlockMatrix blockMatrix);
}
