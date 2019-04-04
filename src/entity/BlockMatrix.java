package entity;

import config.Settings;

import java.util.ArrayList;
import java.util.Random;

/**
 * BlockMatrix.java
 * This class creates and manages a matrix of Block elements.
 */
public class BlockMatrix {

    private int dimension;
    private Block[][] blocks;
    private ArrayList<Block> blockList = new ArrayList<>();
    private Random random = new Random();

    /**
     * Class constructor
     * @param dimension length of dimensions X, Y
     */
    public BlockMatrix(int dimension) {

        this.dimension = dimension;
        this.blocks = new Block[this.dimension][this.dimension];
    }

    /**
     * Fills 2D Block array with newly instantiated objects.
     */
    public ArrayList<Block> generateBlocks() {

        for(int i = 0; i < this.dimension; i++) {
            for(int j = 0; j < this.dimension; j++) {

                Block block = new Block(i, j, random.nextInt(5) + 1);
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

            Block topNeighbor = this.getBlockAt(blockX, blockY - 1);
            Block rightNeighbor = this.getBlockAt(blockX + 1, blockY);

            if(topNeighbor != null){
                if(topNeighbor.getValue() == block.getValue()) {
                    mergeBlocks.add(new MergeBlock(
                        (topNeighbor.getX() * Settings.BLOCK_WIDTH) + (Settings.BLOCK_BORDER_RADIUS / 2),
                        (topNeighbor.getY() * Settings.BLOCK_HEIGHT) + (Settings.BLOCK_BORDER_RADIUS / 2),
                        Settings.BLOCK_WIDTH - (Settings.BLOCK_BORDER_RADIUS - 1),
                        Settings.MERGE_BLOCK_LENGTH,
                        topNeighbor.getValue()
                    ));
                }
            }
            if(rightNeighbor != null){
                if(rightNeighbor.getValue() == block.getValue()) {
                    mergeBlocks.add(new MergeBlock(
                            (block.getX() * Settings.BLOCK_WIDTH) + (Settings.BLOCK_BORDER_RADIUS / 2),
                            (block.getY() * Settings.BLOCK_HEIGHT) + (Settings.BLOCK_BORDER_RADIUS / 2),
                            Settings.MERGE_BLOCK_LENGTH,
                            Settings.BLOCK_WIDTH - (Settings.BLOCK_BORDER_RADIUS - 1),
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

        if(x >= 0 && x < this.dimension && y >=0 && y < this.dimension)
            return this.blocks[x][y];
        else
            return null;
    }

}
