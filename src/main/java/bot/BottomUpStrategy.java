package bot;

import entity.Location;
import entity.RawBlock;
import model.BlockMatrix;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BottomUpStrategy.java
 *
 * Searches each row from left to right.
 * Starts at bottom row.
 */
public class BottomUpStrategy implements BotStrategy {


    @Override
    public Location getNextMove(BlockMatrix blockMatrix) {

        List<RawBlock> blocksWithNeighbors = this.getBlocksWithNeighbors(blockMatrix);
        blocksWithNeighbors.sort((RawBlock block1, RawBlock block2) -> {
            if (block1.getY() == block2.getY())
                return block2.getX() - block1.getX();
            return block1.getY() - block2.getY();
        });
        RawBlock bottomLeftBlock = blocksWithNeighbors.get(blocksWithNeighbors.size() - 1);
        return new Location(bottomLeftBlock.getX(), bottomLeftBlock.getY());
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

        return "Bottom Up";
    }

}
