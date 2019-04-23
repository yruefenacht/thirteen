package controller;

import config.Events;
import config.Settings;
import entity.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
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
    private NumberGenerator numberGenerator;
    private AudioPlayer audioPlayer;


    /**
     * Class constructor
     * @param playfieldModel Observable to be attached to
     * @param dimensionX Matrix dimension X length
     * @param dimensionY Matrix dimension Y length
     */
    BlockMatrix(PlayfieldModel playfieldModel, int dimensionX, int dimensionY) {

        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.playfieldModel = playfieldModel;
        this.playfieldModel.addPropertyChangeListener(this);
        this.rawBlocks = new RawBlock[dimensionX][dimensionY];
        this.numberGenerator = new NumberGenerator();
        this.audioPlayer = new AudioPlayer();
    }


    /**
     * Creates RawBlocks matrix and RawMergeBlocks
     */
    void createMatrix() {

        this.generateBlocks();
        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.rawMergeBlocks);
        this.playfieldModel.blocksCreated(this.getBlocksAsList());
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
                this.rawBlocks[i][j] = new RawBlock(i, j, this.numberGenerator.getRandomNumber());
            }
        }
        int initialX = this.numberGenerator.getInitialBlockX();
        int initialY = Settings.GRID_DIMENSION_Y - 1;
        this.rawBlocks[initialX][initialY] = new RawBlock(initialX, initialY, Settings.LEVEL);
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

        try {
            return this.rawBlocks[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
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
     * Retrieves smallest blocks
     * @return list of blocks
     */
    private ArrayList<RawBlock> getMinBlocks() {

        ArrayList<RawBlock> minBlocks = new ArrayList<>();

        for(RawBlock rawBlock : this.getBlocksAsList())
            if(rawBlock.getValue() == Settings.LEVEL - Settings.LEVEL_RANGE)
                minBlocks.add(rawBlock);

        return minBlocks;
    }

    
    /**
     * SPECIAL CASE
     * Check for level up and remove smallest blocks
     * @param neighbors blocks to check
     * @param bombMode if bombMode, return
     * @return smallest block or empty list
     */
    private ArrayList<RawBlock> checkForLevelUp(ArrayList<RawBlock> neighbors, boolean bombMode) {

        ArrayList<RawBlock> minBlocks = new ArrayList<>();
        if(bombMode) return minBlocks;

        for(RawBlock neighbor : neighbors) {
            if (neighbor.getValue() == Settings.LEVEL) {
                minBlocks.addAll(this.getMinBlocks());
                Settings.LEVEL++;
                if(Settings.LEVEL < 10) Settings.LEVEL_RANGE++;
                this.playfieldModel.levelUp(Settings.LEVEL);
                break;
            }
        }
        return minBlocks;
    }

    /**
     * STEP 0
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
     * STEP 1
     * Remove neighbors and their MergeBlocks from GUI
     * @param blocks given Blocks
     */
    private void removeBlocks(ArrayList<RawBlock> blocks) {

        for(RawBlock neighbor : blocks) {
            this.playfieldModel.removeBlock(neighbor);
            for(RawMergeBlock rawMergeBlock : this.getMergeBlocksOfBlock(neighbor))
                this.playfieldModel.removeMergeBlock(rawMergeBlock);
        }
    }


    /**
     * STEP 2
     * Update Block value
     * @param x block x
     * @param y block y
     */
    private void increaseBlockValue(int x, int y) {

        this.rawBlocks[x][y].increaseBlockValue();
        this.playfieldModel.increaseBlockValue(this.rawBlocks[x][y]);
    }


    /**
     * STEP 3
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
                this.playfieldModel.prepareToSinkBlock(blockAbove);
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
        for(RawBlock rawBlock : this.getBlocksAsList())
            this.playfieldModel.sinkBlock(rawBlock);
    }


    /**
     * STEP 4
     * Create new Blocks at null positions
     * @param nullBlocks null positions
     */
    private void createNewBlocks(ArrayList<RawBlock> nullBlocks) {

        for(RawBlock rawBlock : nullBlocks) {

            int rx = rawBlock.getX();
            int ry = rawBlock.getY();
            RawBlock newRawBlock = new RawBlock(rx, ry, numberGenerator.getRandomNumber());
            this.rawBlocks[rx][ry] = newRawBlock;
            this.playfieldModel.newBlockCreated(newRawBlock);
        }
    }


    /**
     * STEP 5
     * Generate additional MergeBlocks
     */
    private void generateNewMergeBlocks() {

        this.generateMergeBlocks();
        this.playfieldModel.mergeBlocksCreated(this.rawMergeBlocks);
    }


    /**
     * STEP 6
     * Checks if a pair exists
     */
    private void checkForGameOver() {

        if(this.rawMergeBlocks.isEmpty()) {
            this.playfieldModel.showGameOverScreen();
            this.audioPlayer.playGameOverSound();
        }
    }


    /**
     * Apply rules on grid
     * @param location coordinates from clicked block
     */
    private void blockClicked(Location location) {

        if(Settings.IS_ANIMATING) return;

        int x = location.getX();
        int y = location.getY();
        boolean bombMode = Settings.BOMB_MODE;

        //0. Get Neighbors
        ArrayList<RawBlock> neighbors = this.getEqualNeighbors(x, y, new ArrayList<RawBlock>());

        //Bomb Mode
        if(bombMode) {
            neighbors.clear();
            neighbors.add(this.getBlockAt(x, y));
            this.playfieldModel.toggleBombMode();
            this.playfieldModel.updateStarCount(Settings.STAR_COUNT -= Settings.BOMB_COST);
        }

        //Ignore single blocks
        if(neighbors.isEmpty()) return;

        //Play sound effect

        //Check for level up
        neighbors.addAll(this.checkForLevelUp(neighbors, bombMode));

        //1. Remove neighbors
        final KeyFrame step1 = new KeyFrame(
            Duration.ZERO,
            e -> this.removeBlocks(neighbors)
        );

        //2. Increase value
        final KeyFrame step2 = new KeyFrame(
            Duration.ZERO,
            e -> this.increaseBlockValue(x, y)
        );

        //3. Hide neighbors and bring them to top
        final KeyFrame step3 = new KeyFrame(
            Duration.ZERO,
            e -> this.riseBlocksToTop(neighbors)
        );

        //4. Replace neighbors with new RawBlocks
        final KeyFrame step4 = new KeyFrame(
            Duration.millis(Settings.GRID_ANIMATION),
            e -> this.createNewBlocks(neighbors)
        );

        //5. Create new MergeBlocks
        final KeyFrame step5 = new KeyFrame(
            Duration.millis(Settings.GRID_ANIMATION),
            e -> this.generateNewMergeBlocks()
        );

        //6. Check for game over
        final KeyFrame step6 = new KeyFrame(
            Duration.millis(Settings.GRID_ANIMATION),
            e -> this.checkForGameOver()
        );

        Settings.IS_ANIMATING = true;
        final Timeline timeline = new Timeline(step1, step2, step3, step4, step5, step6);
        timeline.setOnFinished(e -> Settings.IS_ANIMATING = false);
        timeline.play();
    }


    /**
     * Is called whenever model fires propertyChangeEvent
     * @param evt Properties that have changed
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        switch(evt.getPropertyName()) {
            case Events.BLOCK_CLICKED:
                this.blockClicked((Location) evt.getNewValue());
                break;
        }
    }

}
