package entity;

import config.Event;
import config.Settings;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ArrayList<Block> generateBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {

                Block block = new Block(this.playfieldModel, i, j, this.random.nextInt(5) + 1);
                this.blocks[i][j] = block;
            }
        }
        return this.getBlocksAsList();
    }

    /**
     * Analyses grid and creates MergeBlocks from that
     * @return List of Blocks
     */
    public ArrayList<MergeBlock> generateMergeBlocks() {

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
        return this.mergeBlocks;
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
     * Calculate distance to fall down for given block
     * @param block given block
     * @return number of blocks to fall down
     */
    private int getEmptyBlocksBelow(Block block) {

        int counter = 1;
        Block currentBlock = this.getBlockAt(block.getX() + counter, block.getY());
        while(currentBlock == null){
            currentBlock = this.getBlockAt(block.getX() + counter, block.getY());
            counter++;
        }
        return counter - 1;
    }

    /**
     * Perform rules
     * @param block clicked Block
     */
    private void blockClicked(Block block) {

        ArrayList<Block> neighbors = this.getNeighbors(block);

        if (neighbors.size() >= 1) {

            //1. Remove Blocks and their MergeBlocks
            for (Block neighborBlock : neighbors) {

                for(MergeBlock mergeBlock : this.findMatchingMergeBlock(neighborBlock))
                    this.playfieldModel.removeMergeBlock(mergeBlock);

                FadeTransition fadeTransition = new FadeTransition(
                    Duration.millis(Settings.BLOCK_FADEOUT),
                    neighborBlock
                );
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();

                this.blocks[neighborBlock.getX()][neighborBlock.getY()] = null;
                this.playfieldModel.removeBlock(neighborBlock);
            }

            //2. Blocks must fall down
            for(Block b : this.getBlocksAsList())
                b.fallDown(this.getEmptyBlocksBelow(b));


            block.updateValue();
        }
        this.visitedBlocks.clear();
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
