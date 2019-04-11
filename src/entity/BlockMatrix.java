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
    private int[][] blocks;
    private int[][] mergeBlocks;
    private PlayfieldModel playfieldModel;
    private Random random = new Random();
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
        this.blocks = new int[dimensionX][dimensionY];

    }

    /**
     * Creates Blocks and informs observable
     */
    public void createMatrix() {

        this.generateBlocks();
        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.mergeBlocks);
        this.playfieldModel.blocksCreated(this.blocks);
    }

    /**
     * Returns 2d Block array as ArrayList
     * @return Blocks
     */
    /*private ArrayList<Block> getBlocksAsList() {

        ArrayList<Block> blockList = new ArrayList<>();

        for(int i = 0; i < this.dimensionX; i++)
            blockList.addAll(Arrays.asList(this.blocks[i]).subList(0, this.dimensionY));

        return blockList;
    }*/

    /**
     * Fills 2D Block array with newly instantiated objects.
     */
    private void generateBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {
                this.blocks[i][j] = this.random.nextInt(5) + 1;
            }
        }
    }

    /**
     * Analyses grid and creates MergeBlocks from that
     */
    private void generateMergeBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {

                int block = this.blocks[i][j];
                int topNeighbor = this.getBlockAt(i, j - 1);
                int rightNeighbor = this.getBlockAt(i + 1, j);

                if (topNeighbor != -1) {
                    if (topNeighbor == block) {
                        this.mergeBlocks[i][j] = block;
                        /*this.mergeBlocks.add(new MergeBlock(
                                (topNeighbor.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                                (topNeighbor.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                                Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                                Settings.MERGE_BLOCK_LENGTH,
                                topNeighbor.getValue(),
                                topNeighbor, block
                        ));*/
                    }
                }
                if (rightNeighbor != -1) {
                    if (rightNeighbor == block) {
                        /*this.mergeBlocks.add(new MergeBlock(
                                (block.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                                (block.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                                Settings.MERGE_BLOCK_LENGTH,
                                Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                                block.getValue(),
                                rightNeighbor, block
                        ));*/
                    }
                }
            }
        }
    }

    /**
     * Retrieves Block at specific location
     * @param x Value x axis
     * @param y Value y axis
     * @return value
     */
    private int getBlockAt(int x, int y) {

        if(x >= 0 && x < this.dimensionX && y >=0 && y < this.dimensionY)
            return this.blocks[x][y];
        else
            return -1;
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


    private int getFallDownSteps(Block block) {

        int stepCounter = 0;
        for(int i = block.getY(); i < Settings.GRID_DIMENSION_Y - 1; i++) {
            if(this.blocks[block.getX()][i + 1] == null) {
                stepCounter++;
                this.removeMatchingMergeBlock(block);
            }
        }
        return stepCounter;
    }

    /**
     * Finds MergeBlocks at same position as block
     * @param block Block to check
     */
    private void removeMatchingMergeBlock(Block block) {

        for(MergeBlock mergeBlock : this.mergeBlocks)
            if(mergeBlock.hasBlock(block)) this.playfieldModel.removeMergeBlock(mergeBlock);
    }

    /**
     * Perform rules
     * @param block clicked Block
     */
    private void blockClicked(Block block) {

        ArrayList<Block> neighbors = this.getNeighbors(block);
        this.visitedBlocks.clear();

        if (neighbors.size() < 1) return;

        //1. Remove Blocks and their MergeBlocks
        for (Block neighborBlock : neighbors) {

            this.removeMatchingMergeBlock(neighborBlock);

            neighborBlock.fadeOut();

            this.blocks[neighborBlock.getX()][neighborBlock.getY()] = null;
            this.playfieldModel.removeBlock(neighborBlock);
        }

        //2. Blocks must fall down
        for(Block b : this.getBlocksAsList()) {
            if (b != null) {
                int steps = this.getFallDownSteps(b);
                b.fallDown(steps);
                this.blocks[b.getX()][b.getY() + b.getFallenDown()] = b;
                this.blocks[b.getX()][b.getY()] = null;
            }
        }

        for(Block block1 : this.getBlocksAsList()) {
            if(block != null)
                block.updateValue();
        }

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
