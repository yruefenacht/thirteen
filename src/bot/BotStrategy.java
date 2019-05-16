package bot;

import entity.Location;

public interface BotStrategy {

    Location getNextMove();
}
