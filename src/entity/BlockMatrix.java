package entity;

import config.Events;
import javafx.application.Platform;
import model.PlayfieldModel;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.*;

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
    private Random random = new Random();


    /**
     * Class constructor
     * @param playfieldModel Observable to be attached to
     * @param dimensionX Matrix dimension X length
     * @param dimensionY Matrix dimension Y length
     */
    public BlockMatrix(PlayfieldModel playfieldModel, int dimensionX, int dimensionY) {

        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
        this.rawBlocks = new RawBlock[dimensionX][dimensionY];
    }


    /**
     * Creates RawBlocks matrix and RawMergeBlocks
     */
    public void createMatrix() {

        this.generateBlocks();
        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.rawMergeBlocks);
        this.playfieldModel.blocksCreated(this.getBlocksAsList());

        printMatrix();
        /*Scanner scanner = new Scanner(System.in);
        while(true) {
            String line = scanner.nextLine();
            int x = Integer.parseInt(line.split(" ")[0]);
            int y = Integer.parseInt(line.split(" ")[1]);
            this.blockClicked(new Location(x, y));
        }*/
    }


    /**
     * Returns 2D RawBlock array as ArrayList
     * @return List of RawBlocks
     */
    private ArrayList<RawBlock> getBlocksAsList() {

        ArrayList<RawBlock> blockList = new ArrayList<>();

        for(int i = 0; i < this.dimensionX; i++)
            blockList.addAll(Arrays.asList(this.rawBlocks[i]).subList(0, this.dimensionY));

        return blockList;
    }


    /**
     * Fills 2D RawBlock array with newly instantiated objects.
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

            if(rawBlock == null) break;

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
     * Retrieves RawBlock at specific location
     * @param x Value x axis
     * @param y Value y axis
     * @return RawBlock
     */
    private RawBlock getBlockAt(int x, int y) {

        if(x >= 0 && x < this.dimensionX && y >=0 && y < this.dimensionY)
            return this.rawBlocks[x][y];
        else
            return null;
    }


    /**
     * Recursively get all equal neighbors from given block
     * @param x given block x
     * @param y given block y
     * @param visitedBlocks Do not visit same block twice
     * @return list of rawBlocks
     */
    private ArrayList<RawBlock> getEqualNeighbors(int x, int y, ArrayList<RawBlock> visitedBlocks) {

        ArrayList<RawBlock> equalNeighbors = new ArrayList<>();
        RawBlock block = this.getBlockAt(x, y);

        if(block == null) return equalNeighbors;

        visitedBlocks.add(block);

        RawBlock[] neighbors = new RawBlock[] {
            this.getBlockAt(x, y - 1),
            this.getBlockAt(x + 1, y),
            this.getBlockAt(x, y + 1),
            this.getBlockAt(x - 1, y)
        };

        for(RawBlock neighbor : neighbors) {
            if(neighbor != null) {
                if(neighbor.getValue() == block.getValue() && ! visitedBlocks.contains(neighbor)) {
                    equalNeighbors.add(neighbor);
                    equalNeighbors.addAll(this.getEqualNeighbors(neighbor.getX(),neighbor.getY(), visitedBlocks));
                }
            }
        }

        return equalNeighbors;
    }


    /**
     * Puts removed RawBlocks (gaps) to the top of matrix
     * @param rawBlocks Empty RawBlocks
     */
    private void riseBlocksToTop(ArrayList<RawBlock> rawBlocks) {

        ArrayList<RawMergeBlock> mergeBlocksToRemove = new ArrayList<>();
        for(RawBlock rawBlock : rawBlocks) {

            int rx = rawBlock.getX();
            int ry = rawBlock.getY();

            for(int i = ry; i >= 1; i--) {
                RawBlock blockAbove = this.rawBlocks[rx][i - 1];
                this.playfieldModel.sinkBlock(blockAbove);
                this.rawBlocks[rx][i - 1]  = rawBlock;
                this.rawBlocks[rx][i] = blockAbove;
                mergeBlocksToRemove.addAll(this.getMergeBlocksOfBlock(this.getBlockAt(rx, i - 1)));
                mergeBlocksToRemove.addAll(this.getMergeBlocksOfBlock(this.getBlockAt(rx, i)));
                rawBlock.rise();
                blockAbove.fall();
            }
        }
        this.rawMergeBlocks.removeAll(mergeBlocksToRemove);
        for(RawMergeBlock rawMergeBlock : mergeBlocksToRemove)
            this.playfieldModel.removeMergeBlock(rawMergeBlock);
    }


    /**
     * Finds RawMergeBlocks at same position as RawBlock
     * @param rawBlock given RawBlock
     */
    private ArrayList<RawMergeBlock> getMergeBlocksOfBlock(RawBlock rawBlock) {

        ArrayList<RawMergeBlock> matchingMergeBlocks = new ArrayList<>();

        for(RawMergeBlock rawMergeBlock : this.rawMergeBlocks) {

            if (rawMergeBlock.hasBlock(rawBlock)) {
                matchingMergeBlocks.add(rawMergeBlock);
            }
        }
        return matchingMergeBlocks;
    }


    /**
     * Apply rules on grid
     * @param location coordinates from clicked block
     */
    private void blockClicked(Location location) {

        int x = location.getX();
        int y = location.getY();

        //1. Get neighbors
        ArrayList<RawBlock> neighbors = this.getEqualNeighbors(x, y, new ArrayList<RawBlock>());
        if(neighbors.isEmpty()) return;

        //2. Increase value
        this.rawBlocks[x][y].increaseBlockValue();
        this.playfieldModel.increaseBlockValue(this.rawBlocks[x][y]);

        //3. Hide neighbors and bring them to top
        this.riseBlocksToTop(neighbors);

        //4. Replace neighbors with new RawBlocks
        for(RawBlock rawBlock : neighbors) {

            int rx = rawBlock.getX();
            int ry = rawBlock.getY();
            this.rawBlocks[rx][ry] = new RawBlock(rx, ry, 7);
            this.playfieldModel.removeBlock(this.rawBlocks[rx][ry]);
        }


        printMatrix();
    }

    private void printMatrix() {
        System.out.println("- - - - -");
        for(int i = 0; i < this.dimensionX; i++) {
            for(int j = 0; j < this.dimensionY; j++) {
                if (this.rawBlocks[j][i] != null) {
                    System.out.print(this.rawBlocks[j][i].getValue() + " ");
                }
                else {
                    System.out.print("X ");
                }
            }
            System.out.println(" ");
        }
    }

    /**
     * Is called whenever model fires propertyChangeEvent
     * @param evt Properties that have changed
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        Platform.runLater(() -> {
            switch(evt.getPropertyName()) {
                case Events.BLOCK_CLICKED:
                    this.blockClicked((Location) evt.getNewValue());
                    break;
            }
        });
    }
}
