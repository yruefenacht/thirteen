package bot;

import entity.Location;

/**
 * BotStrategy.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Defines methods of type BotStrategy.
 */
public interface BotStrategy {

    Location getNextMove();
}
