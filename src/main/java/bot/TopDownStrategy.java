package main.java.bot;

import main.java.entity.Location;
import main.java.entity.RawBlock;
import main.java.model.BlockMatrix;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TopDownStrategy.java
 *
 * Searches each row from left to right.
 * Starts at top row.
 */
public class TopDownStrategy implements BotStrategy {


    @Override
    public Location getNextMove(BlockMatrix blockMatrix) {

        List<RawBlock> blocksWithNeighbors = this.getBlocksWithNeighbors(blockMatrix);
        blocksWithNeighbors.sort((RawBlock block1, RawBlock block2) -> {
            if (block1.getY() == block2.getY())
                return block1.getX() - block2.getX();
            return block1.getY() - block2.getY();
        });
        RawBlock topLeftBlock = blocksWithNeighbors.get(0);
        return new Location(topLeftBlock.getX(), topLeftBlock.getY());
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

        return "Top Down";
    }

}
