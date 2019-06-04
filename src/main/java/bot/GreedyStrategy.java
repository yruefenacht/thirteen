package bot;

import entity.Location;
import entity.RawBlock;
import model.BlockMatrix;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GreedyStrategy.java
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

        List<RawBlock> blocksWithNeighbors = this.getBlocksWithNeighbors(blockMatrix);
        RawBlock highestBlock = blocksWithNeighbors.get(0);

        for(RawBlock block : blocksWithNeighbors)
            if(block.getValue() > highestBlock.getValue()) highestBlock = block;

        return new Location(highestBlock.getX(), highestBlock.getY());
    }


    /**
     * Returns all blocks that have one or more neighbors.
     * @param blockMatrix model with all blocks
     * @return list of RawBlocks
     */
    private List<RawBlock> getBlocksWithNeighbors(BlockMatrix blockMatrix) {

        return blockMatrix.getBlocksAsList().stream().filter(block -> {

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

        return "Greedy";
    }

}
