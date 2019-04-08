package entity;

import config.Event;
import config.Settings;
import entity.Block;
import entity.MergeBlock;
import model.PlayfieldModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * BlockMatrix.java
 * This class creates and manages a matrix of Block elements.
 */
public class BlockMatrix implements PropertyChangeListener {

    private int dimensionX;
    private int dimensionY;
    private Block[][] blocks;
    private ArrayList<Block> blockList = new ArrayList<>();
    private Random random = new Random();
    private PlayfieldModel playfieldModel;

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
     * Fills 2D Block array with newly instantiated objects.
     */
    public ArrayList<Block> generateBlocks() {

        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {

                Block block = new Block(this.playfieldModel, i, j, this.random.nextInt(5) + 1);
                this.blocks[i][j] = block;
                this.blockList.add(block);
            }
        }
        return this.blockList;
    }

    /**
     * Analyses grid and creates MergeBlocks from that
     * @return List of Blocks
     */
    public ArrayList<MergeBlock> generateMergeBlocks() {

        ArrayList<MergeBlock> mergeBlocks = new ArrayList<>();

        for(Block block : this.blockList) {

            int blockX = block.getX();
            int blockY = block.getY();

            Block topNeighbor = getBlockAt(blockX, blockY - 1);
            Block rightNeighbor = getBlockAt(blockX + 1, blockY);

            if(topNeighbor != null){
                if(topNeighbor.getValue() == block.getValue()) {
                    mergeBlocks.add(new MergeBlock(
                        (topNeighbor.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                        (topNeighbor.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                        Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                        Settings.MERGE_BLOCK_LENGTH,
                        topNeighbor.getValue()
                    ));
                }
            }
            if(rightNeighbor != null){
                if(rightNeighbor.getValue() == block.getValue()) {
                    mergeBlocks.add(new MergeBlock(
                            (block.getX() * Settings.BLOCK_WIDTH) + (Settings.GRID_SPACING / 2),
                            (block.getY() * Settings.BLOCK_HEIGHT) + (Settings.GRID_SPACING / 2),
                            Settings.MERGE_BLOCK_LENGTH,
                            Settings.BLOCK_WIDTH - (Settings.GRID_SPACING - 1),
                            block.getValue()
                    ));
                }
            }
        }
        return mergeBlocks;
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
        ArrayList<Block> visitedNeighbors = new ArrayList<>();

        if(block == null) return equalNeighbors;

        visitedNeighbors.add(block);

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
                if(b.getValue() == block.getValue()) {
                    equalNeighbors.add(b);
                    if(! visitedNeighbors.contains(b))
                        equalNeighbors.addAll(this.getNeighbors(b));
                }
            }
        }

        return equalNeighbors;
    }

    private void blockClicked(Block block) {

        ArrayList<Block> neighbors = this.getNeighbors(block);
        for(Block b : neighbors)
            b.updateValue();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        System.out.println("test");
        Event event = Event.valueOf(evt.getPropertyName());

        switch(event) {
            case BLOCK_CLICK:
                this.blockClicked((Block) evt.getNewValue());
                break;
        }
    }
}
