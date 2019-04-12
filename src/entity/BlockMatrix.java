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
    private PlayfieldModel playfieldModel;
    private RawBlock[][] rawBlocks;
    private ArrayList<RawMergeBlock> rawMergeBlocks;
    private ArrayList<RawBlock> visitedBlocks = new ArrayList<>();
    private Random random = new Random();

    /**
     * Class constructor
     * @param dimensionX length of dimensions X, Y
     */
    public BlockMatrix(PlayfieldModel playfieldModel, int dimensionX, int dimensionY) {

        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
        this.rawBlocks = new RawBlock[dimensionX][dimensionY];

    }

    /**
     * Creates Blocks and informs observable
     */
    public void createMatrix() {

        this.generateBlocks();
        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.rawMergeBlocks);
        this.playfieldModel.blocksCreated(this.getBlocksAsList());
    }

    /**
     * Returns 2d Block array as ArrayList
     * @return Blocks
     */
    private ArrayList<RawBlock> getBlocksAsList() {

        ArrayList<RawBlock> blockList = new ArrayList<>();

        for(int i = 0; i < this.dimensionX; i++)
            blockList.addAll(Arrays.asList(this.rawBlocks[i]).subList(0, this.dimensionY));

        return blockList;
    }

    /**
     * Fills 2D Block array with newly instantiated objects.
     */
    private void generateBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {
                this.rawBlocks[i][j] = new RawBlock(i, j, this.random.nextInt(5) + 1);
            }
        }
    }

    /**
     * Analyses grid and creates MergeBlocks from that
     */
    private void generateMergeBlocks() {

        this.rawMergeBlocks = new ArrayList<>();

        for(RawBlock rawBlock : this.getBlocksAsList()) {

            int rawBlockX = rawBlock.getX();
            int rawBlockY = rawBlock.getY();
            int rawBlockValue = rawBlock.getValue();

            RawBlock topNeighbor = this.getBlockAt(rawBlockX, rawBlockY - 1);
            RawBlock rightNeighbor = this.getBlockAt(rawBlockX + 1, rawBlockY);

            if (topNeighbor != null) {
                if (topNeighbor.getValue() == rawBlockValue) {
                    this.rawMergeBlocks.add(new RawMergeBlock(
                        topNeighbor.getX(), topNeighbor.getY(), rawBlockX, rawBlockY, rawBlockValue
                    ));
                }
            }
            if (rightNeighbor != null) {
                if (rightNeighbor.getValue() == rawBlockValue) {
                    this.rawMergeBlocks.add(new RawMergeBlock(
                        rawBlockX, rawBlockY, rightNeighbor.getX(), rightNeighbor.getY(), rawBlockValue
                    ));
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
    private RawBlock getBlockAt(int x, int y) {

        if(x >= 0 && x < this.dimensionX && y >=0 && y < this.dimensionY)
            return this.rawBlocks[x][y];
        else
            return null;
    }

    /**
     * Recursively get all neighbor cells with equal value
     * @param x x axis value
     * @param y y axis value
     */
    private ArrayList<RawBlock> getEqualNeighbors(int x, int y) {

        RawBlock block = this.getBlockAt(x, y);
        ArrayList<RawBlock> equalNeighbors = new ArrayList<>();

        if(block == null) return equalNeighbors;

        this.visitedBlocks.add(block);

        RawBlock[] neighbors = new RawBlock[] {
                this.getBlockAt(x, y - 1),
                this.getBlockAt(x + 1, y),
                this.getBlockAt(x, y + 1),
                this.getBlockAt(x - 1, y)
        };

        for(RawBlock b : neighbors) {
            if(b != null) {
                if(b.getValue() == block.getValue() && ! this.visitedBlocks.contains(b)) {
                    equalNeighbors.add(b);
                    equalNeighbors.addAll(this.getEqualNeighbors(b.getX(),b.getY()));
                }
            }
        }

        return equalNeighbors;
    }

    private void killRawBlock(RawBlock rawBlock) {

        int x = rawBlock.getX();
        int y = rawBlock.getY();
        this.removeMatchingMergeBlock(this.getBlockAt(x, y));
        this.playfieldModel.removeBlock(this.getBlockAt(x, y));
        this.rawBlocks[x][y] = null;
    }

    /**
     * Counts null blocks below given block
     * @param block given block
     * @return count
     */
    private int getFallDownSteps(RawBlock block) {

        int stepCounter = 0;
        for(int i = block.getY(); i < Settings.GRID_DIMENSION_Y - 1; i++) {
            if(this.rawBlocks[block.getX()][i + 1] == null) {
                stepCounter++;
                this.removeMatchingMergeBlock(block);
            }
        }
        return stepCounter;
    }

    /**
     * Finds MergeBlocks at same position as block
     * @param rawBlock Block to check
     */
    private void removeMatchingMergeBlock(RawBlock rawBlock) {

        for(RawMergeBlock rawMergeBlock : this.rawMergeBlocks)
            if(rawMergeBlock.hasBlock(rawBlock)) this.playfieldModel.removeMergeBlock(rawMergeBlock);
    }


    private void blockClicked(Location location) {

        int x = location.getX();
        int y = location.getY();

        //1. Kill neighbors
        ArrayList<RawBlock> neighbors = this.getEqualNeighbors(x, y);
        this.visitedBlocks.clear();
        if(neighbors.isEmpty()) return;

        for(RawBlock rawBlock : neighbors) this.killRawBlock(rawBlock);

        //2. Update value
        this.rawBlocks[x][y].updateValue();
        this.playfieldModel.increaseBlockValue(location);

        //3. Blocks must fall down
        /*for(int i = 0; i < Settings.GRID_DIMENSION_X; i++) {
            for(int j = Settings.GRID_DIMENSION_Y - 1; j >= 0; j--) {

                RawBlock rawBlock = this.rawBlocks[i][j];
                if (rawBlock != null) {

                    int steps = this.getFallDownSteps(rawBlock);
                    if(steps > 0) {
                        this.playfieldModel.sinkBlock(rawBlock, steps);
                        this.rawBlocks[rawBlock.getX()][rawBlock.getY() + steps] = rawBlock;
                        this.rawBlocks[rawBlock.getX()][rawBlock.getY()] = null;
                    }
                }
            }
        }*/

    }

    /**
     * Is called whenever model fires propertyChangeEvent
     * @param evt Properties that have changed
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case Event.BLOCK_CLICKED:
                this.blockClicked((Location) evt.getNewValue());
                break;
        }
    }
}
