package entity;

import config.Event;
import config.Settings;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * BlockMatrix.java
 * This class creates and manages a matrix of Block elements.
 */
public class BlockMatrix implements PropertyChangeListener {

    private int dimensionX;
    private int dimensionY;
    private Block[][] blocks;
    private PlayfieldModel playfieldModel;
    private Random random = new Random();
    private ArrayList<MergeBlock> mergeBlocks = new ArrayList<>();
    private ArrayList<Block> visitedBlocks = new ArrayList<>();

    /**
     * Class constructor
     * @param dimensionX length of dimensions X, Y
     */
    public BlockMatrix(PlayfieldModel playfieldModel, int dimensionX, int dimensionY) {

        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
        this.blocks = new Block[dimensionX][dimensionY];

    }

    /**
     * Creates Blocks and informs observable
     */
    public void createMatrix() {

        this.generateBlocks();
        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.mergeBlocks);
        this.playfieldModel.blocksCreated(this.getBlocksAsList());
    }

    /**
     * Returns 2d Block array as ArrayList
     * @return Blocks
     */
    private ArrayList<Block> getBlocksAsList() {

        ArrayList<Block> blockList = new ArrayList<>();

        for(int i = 0; i < this.dimensionX; i++)
            blockList.addAll(Arrays.asList(this.blocks[i]).subList(0, this.dimensionY));

        return blockList;
    }

    /**
     * Fills 2D Block array with newly instantiated objects.
     */
    private void generateBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {

                Block block = new Block(this.playfieldModel, i, j, this.random.nextInt(5) + 1);
                this.blocks[i][j] = block;
            }
        }
    }

    /**
     * Analyses grid and creates MergeBlocks from that
     */
    private void generateMergeBlocks() {

        for(Block block : this.getBlocksAsList()) {

            int blockX = block.getX();
            int blockY = block.getY();

            Block topNeighbor = getBlockAt(blockX, blockY - 1);
            Block rightNeighbor = getBlockAt(blockX + 1, blockY);

            if(topNeighbor != null){
                if(topNeighbor.getValue() == block.getValue()) {
                    this.mergeBlocks.add(new MergeBlock(
                        (topNeighbor.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                        (topNeighbor.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                        Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                        Settings.MERGE_BLOCK_LENGTH,
                        topNeighbor.getValue(),
                        topNeighbor, block
                    ));
                }
            }
            if(rightNeighbor != null){
                if(rightNeighbor.getValue() == block.getValue()) {
                    this.mergeBlocks.add(new MergeBlock(
                        (block.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                        (block.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                        Settings.MERGE_BLOCK_LENGTH,
                        Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                        block.getValue(),
                        rightNeighbor, block
                    ));
                }
            }
        }
    }

    /**
     * Retrieves Block at specific location
     * @param x Value x axis
     * @param y Value y axis
     * @return Block
     */
    private Block getBlockAt(int x, int y) {

        if(x >= 0 && x < this.dimensionX && y >=0 && y < this.dimensionY)
            return this.blocks[x][y];
        else
            return null;
    }

    /**
     * Recursively get all equal neighbors
     * @param block location to start recursion
     * @return list of Blocks
     */
    private ArrayList<Block> getNeighbors(Block block) {

        ArrayList<Block> equalNeighbors = new ArrayList<>();

        if(block == null) return equalNeighbors;

        this.visitedBlocks.add(block);

        int x = block.getX();
        int y = block.getY();

        Block[] neighbors = new Block[] {
            this.getBlockAt(x, y - 1),
            this.getBlockAt(x + 1, y),
            this.getBlockAt(x, y + 1),
            this.getBlockAt(x - 1, y)
        };

        for(Block b : neighbors) {
            if(b != null) {
                if(b.getValue() == block.getValue() && ! this.visitedBlocks.contains(b)) {
                    equalNeighbors.add(b);
                    equalNeighbors.addAll(this.getNeighbors(b));
                }
            }
        }

        return equalNeighbors;
    }

    /**
     * Tells all neighbors above to fall down by one step
     * @param location x and y of dead neighbor cell
     */
    private void notifyUpperBlocksToFallDown(Location location) {

        int x = location.getX();
        int y = location.getY();

        for(int i = y; i > 0; i--) {
            Block blockAbove = this.getBlockAt(x, i - 1);
            if(blockAbove != null) {
                blockAbove.fallDown();
                this.blocks[x][i] = blockAbove;
                this.removeMergeBlocksOfBlock(blockAbove);
            }
        }
    }

    /**
     * Finds MergeBlocks at same position as block
     * @param block Block to check
     * @return List of MergeBlocks
     */
    private ArrayList<MergeBlock> findMatchingMergeBlock(Block block) {

        ArrayList<MergeBlock> matchingMergeBlocks = new ArrayList<>();

        for(MergeBlock mergeBlock : this.mergeBlocks)
            if(mergeBlock.hasBlock(block)) matchingMergeBlocks.add(mergeBlock);

        return matchingMergeBlocks;
    }

    /**
     * Removes all MergeBlocks behind given Block
     * @param block given Block
     */
    private void removeMergeBlocksOfBlock(Block block) {

        for(MergeBlock mergeBlock : this.findMatchingMergeBlock(block))
            this.playfieldModel.removeMergeBlock(mergeBlock);
    }

    /**
     * Perform rules
     * @param block clicked Block
     */
    private void blockClicked(Block block) {

        ArrayList<Block> neighbors = this.getNeighbors(block);
        ArrayList<Location> neighborCorpses = new ArrayList<>();
        this.visitedBlocks.clear();

        if (neighbors.size() < 1) return;

        //1. Remove Blocks and their MergeBlocks
        for (Block neighborBlock : neighbors) {

            this.removeMergeBlocksOfBlock(neighborBlock);

            neighborBlock.fadeOut();

            neighborCorpses.add(new Location(neighborBlock.getX(), neighborBlock.getY()));
            this.blocks[neighborBlock.getX()][neighborBlock.getY()] = null;
            this.playfieldModel.removeBlock(neighborBlock);
        }

        //2. Blocks must fall down
        Collections.reverse(neighborCorpses);
        for(Location location : neighborCorpses)
            this.notifyUpperBlocksToFallDown(location);

        //3. Increase clicked block's value
        block.updateValue();
    }

    /**
     * Is called whenever model fires propertyChangeEvent
     * @param evt Properties that have changed
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case Event.BLOCK_CLICKED:
                this.blockClicked((Block) evt.getNewValue());
                break;
        }
    }
}
