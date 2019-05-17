package bot;

import entity.Location;
import entity.RawBlock;
import model.BlockMatrix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * RandomStrategy.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Gets random block from random group.
 */
public class RandomStrategy implements BotStrategy {


    /**
     * Calculates the next move.
     * @return x and y
     */
    @Override
    public Location getNextMove(BlockMatrix blockMatrix) {

        Random random = new Random();
        List<RawBlock> blocksWithNeighbors = getBlocksWithNeighbors(blockMatrix);
        RawBlock chosenBlock = blocksWithNeighbors.get(random.nextInt(blocksWithNeighbors.size()));

        return new Location(chosenBlock.getX(), chosenBlock.getY());
    }


    private ArrayList<RawBlock> getBlocksWithNeighbors(BlockMatrix blockMatrix) {

        return (ArrayList<RawBlock>) blockMatrix.getBlocksAsList().stream().filter(block -> {

            int x = block.getX();
            int y = block.getY();
            int value = block.getValue();

            RawBlock[] neighbors = new RawBlock[]{
                blockMatrix.getBlockAt(x, y - 1),
                blockMatrix.getBlockAt(x + 1, y),
                blockMatrix.getBlockAt(x, y + 1),
                blockMatrix.getBlockAt(x - 1, y)
            };

            for(RawBlock neighbor : neighbors) {
                if(neighbor == null) continue;
                if(neighbor.getValue() == value) return true;
            }
            return false;

        }).collect(Collectors.toList());
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
