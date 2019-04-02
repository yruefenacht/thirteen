package entity;

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
    public ArrayList<Block> generatePlayfield() {

        for(int i = 0; i < this.dimension; i++) {
            for(int j = 0; j < this.dimension; j++) {

                Block block = new Block(i, j, random.nextInt(5) + 1);
                this.blocks[i][j] = block;
                this.blockList.add(block);
            }
        }

        return this.blockList;
    }

}
