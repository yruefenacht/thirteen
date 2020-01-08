package main.java.model;

import main.java.config.Config;
import main.java.entity.BlockList;
import main.java.entity.Location;
import main.java.entity.RawBlock;
import main.java.entity.RawMergeBlock;
import main.java.utility.AudioPlayer;
import main.java.utility.NumberGenerator;
import main.java.game.Highscore;
import main.java.game.Game;
import main.java.game.GameLoader;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.beans.PropertyChangeListener;
import java.util.*;

/**
 * BlockMatrix.java
 *
 * This class creates and manages a matrix of Block elements.
 */
public class BlockMatrix {

    private int dimensionX;
    private int dimensionY;
    private BlockMatrixSupport blockMatrixSupport;
    private RawBlock[][] rawBlocks;
    private List<RawMergeBlock> rawMergeBlocks;
    private List<BlockList> previousBlocks;
    private NumberGenerator numberGenerator;
    private AudioPlayer audioPlayer;
    private GameLoader gameLoader;
    private Game game;


    /**
     * Constructs a {@code BlockMatrix} object.
     * @param dimensionX Matrix dimension X length
     * @param dimensionY Matrix dimension Y length
     */
    public BlockMatrix(int dimensionX, int dimensionY) {

        this.dimensionX = dimensionX;
        this.dimensionY = dimensionY;
        this.blockMatrixSupport = new BlockMatrixSupport(this);
        this.rawBlocks = new RawBlock[dimensionX][dimensionY];
        this.audioPlayer = new AudioPlayer();
        this.gameLoader = new GameLoader();
        this.game = this.gameLoader.loadGame();
        this.numberGenerator = new NumberGenerator(this.game.getLevel());
        this.previousBlocks = this.game.getPreviousBlocks();
    }


    /**
     * Creates or loads Matrix.
     * @param forceRestart forcefully create matrix
     */
    public void initialise(boolean forceRestart) {

        if(forceRestart) {
            this.createMatrix();
            return;
        }

        this.loadBlocks();
        this.generateMergeBlocks();
        if(this.rawMergeBlocks.isEmpty()) {
            this.createMatrix();
        }
        else {
            this.loadMatrix();
        }
    }


    /**
     * Creates RawBlocks matrix and RawMergeBlocks.
     */
    private void createMatrix() {

        this.numberGenerator.resetLevel();
        this.generateBlocks();
        this.generateMergeBlocks();
        this.blockMatrixSupport.mergeBlocksCreated(this.rawMergeBlocks);
        this.blockMatrixSupport.blocksCreated(this.getBlocksAsList());
        this.blockMatrixSupport.setUndoButtonEnabled(false);
        this.blockMatrixSupport.setBombButtonEnabled(true);
        this.blockMatrixSupport.updateStarCount(this.numberGenerator.getLevel().getStars());
        this.blockMatrixSupport.levelUp(this.numberGenerator.getLevel().getLevel());
        this.game.setRawBlocks(this.getBlocksAsList());
        this.game.setPreviousBlocks(new ArrayList<>());
        this.game.setLevel(this.numberGenerator.getLevel());
        this.gameLoader.saveGame(this.game);
    }


    /**
     * Loads Matrix from Game.xml.
     */
    private void loadMatrix() {

        this.numberGenerator.setLevel(this.game.getLevel());
        this.blockMatrixSupport.mergeBlocksCreated(this.rawMergeBlocks);
        this.blockMatrixSupport.blocksCreated(this.getBlocksAsList());
        this.blockMatrixSupport.levelUp(this.game.getLevel().getLevel());
        this.blockMatrixSupport.updateStarCount(this.game.getLevel().getStars());
        this.blockMatrixSupport.setBombButtonEnabled(Config.STAR_COUNT >= Config.TOOL_COST);
        this.blockMatrixSupport.setUndoButtonEnabled(
            !this.previousBlocks.isEmpty() && Config.STAR_COUNT >= Config.TOOL_COST
        );
    }


    /**
     * Returns 2D RawBlock array as ArrayList.
     * @return List of RawBlocks
     */
    public List<RawBlock> getBlocksAsList() {

        List<RawBlock> blockList = new ArrayList<>();

        for(int i = 0; i < this.dimensionX; i++)
            blockList.addAll(Arrays.asList(this.rawBlocks[i]).subList(0, this.dimensionY));

        return blockList;
    }


    /**
     * MergeBlocks getter.
     * @return RawMergeBlocks.
     */
    public List<RawMergeBlock> getMergeBlocksAsList() {

        return this.rawMergeBlocks;
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
        //Create main Block somewhere at bottom line.
        int initialX = this.numberGenerator.getInitialBlockX();
        int initialY = Config.GRID_DIMENSION_Y - 1;
        this.rawBlocks[initialX][initialY] = new RawBlock(initialX, initialY, Config.LEVEL);
    }


    /**
     * Loads Blocks and Level from Game.xml.
     */
    private void loadBlocks() {

        List<RawBlock> rawBlocksFromXml = this.game.getRawBlocks();

        //Fill all blocks from xml into 2DArray.
        //rawBlocksFromXml might be bigger or smaller than this.rawBlocks
        //If rawBlocksFromXml is bigger, ignore redundant Blocks.
        for(RawBlock rawBlock : rawBlocksFromXml) {
            try {
                this.rawBlocks[rawBlock.getX()][rawBlock.getY()] = rawBlock;
            }
            catch(ArrayIndexOutOfBoundsException ignored) { }
        }
        //Check for empty spaces in array and fill them with new Blocks.
        //If rawBlocksFromXml was smaller, new Blocks will be added to this.rawBlocks.
        for(int x = 0; x < this.dimensionX; x++){
            for(int y = 0; y < this.dimensionY; y++) {
                if(this.rawBlocks[x][y] == null)
                    this.rawBlocks[x][y] = new RawBlock(x, y, this.numberGenerator.getRandomNumber());
            }
        }
    }


    /**
     * Analyses grid and creates MergeBlocks from that.
     */
    private void generateMergeBlocks() {

        this.rawMergeBlocks = new ArrayList<>();

        for(RawBlock rawBlock : this.getBlocksAsList()) {

            if(rawBlock == null) break;

            int rawBlockX = rawBlock.getX();
            int rawBlockY = rawBlock.getY();
            int rawBlockValue = rawBlock.getValue();

            //Only one horizontal and one vertical neighbor is necessary

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
     * Retrieves RawBlock at specific location.
     * @param x Value x axis
     * @param y Value y axis
     * @return RawBlock
     */
    public RawBlock getBlockAt(int x, int y) {

        try {
            return this.rawBlocks[x][y];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }


    /**
     * Finds RawMergeBlocks at same position as RawBlock.
     * @param rawBlock given RawBlock
     */
    private List<RawMergeBlock> getMergeBlocksOfBlock(RawBlock rawBlock) {

        List<RawMergeBlock> matchingMergeBlocks = new ArrayList<>();

        for(RawMergeBlock rawMergeBlock : this.rawMergeBlocks) {

            if (rawMergeBlock.hasBlock(rawBlock)) {
                matchingMergeBlocks.add(rawMergeBlock);
            }
        }
        return matchingMergeBlocks;
    }


    /**
     * Retrieves smallest blocks.
     * @return list of blocks
     */
    private List<RawBlock> getMinBlocks() {

        List<RawBlock> minBlocks = new ArrayList<>();

        for(RawBlock rawBlock : this.getBlocksAsList())
            if(rawBlock.getValue() == Config.LEVEL - Config.LEVEL_RANGE)
                minBlocks.add(rawBlock);

        return minBlocks;
    }


    /**
     * Revert matrix back to previous step.
     */
    public void undo() {

        if(this.previousBlocks.isEmpty()) return;

        //Subtract price from stars and remove current blocks from history.
        this.game.getLevel().setStars(Config.STAR_COUNT -= Config.TOOL_COST);
        BlockList latestPreviousBlocks = this.previousBlocks.remove(this.previousBlocks.size() - 1);

        //Set previous blocks on 2DArray.
        for(RawBlock block : latestPreviousBlocks.getRawBlocks())
            this.rawBlocks[block.getX()][block.getY()] = block;

        //Regenerate MergeBlocks.
        this.rawMergeBlocks.clear();
        this.generateMergeBlocks();

        //Save changes to xml.
        this.game.setRawBlocks(latestPreviousBlocks.getRawBlocks());
        this.game.setPreviousBlocks(this.previousBlocks);
        this.gameLoader.saveGame(this.game);

        //Update changes to GUI.
        this.blockMatrixSupport.blocksCreated(latestPreviousBlocks.getRawBlocks());
        this.blockMatrixSupport.resetMergeBlocks();
        this.blockMatrixSupport.mergeBlocksCreated(this.rawMergeBlocks);
        this.blockMatrixSupport.updateStarCount(Config.STAR_COUNT);
        this.blockMatrixSupport.setUndoButtonEnabled(
            !this.previousBlocks.isEmpty() && Config.STAR_COUNT >= Config.TOOL_COST
        );
    }


    /**
     * Save properties for undo action.
     */
    private void saveCurrentStates() {

        //Copy all RawBlocks and add them to previous block list.
        List<RawBlock> currentBlocks = new ArrayList<>();

        for(RawBlock block : this.getBlocksAsList())
            currentBlocks.add(new RawBlock(block.getX(), block.getY(), block.getValue()));

        this.previousBlocks.add(new BlockList(currentBlocks));

        //Limit size of previous blocks list.
        if(this.previousBlocks.size() > Config.MAX_PREVIOUS_STATES)
            this.previousBlocks.remove(0);

        //Store new previous block list to xml.
        this.game.setPreviousBlocks(this.previousBlocks);
    }


    /**
     * Remove play field menu.
     */
    public void continueGame() {

        this.blockMatrixSupport.continueGame();
    }


    /**
     * Reloads play field.
     */
    public void restartGame() {

        this.blockMatrixSupport.restartGame();
    }


    /**
     * Goes back to main menu.
     */
    public void quitGame() {

        this.blockMatrixSupport.quitGame();
    }

    
    /**
     * SPECIAL CASE
     * Check for level up and remove smallest blocks.
     * @param neighbors blocks to check
     * @param bombMode if bombMode, return
     * @return smallest block or empty list
     */
    private List<RawBlock> checkForLevelUp(List<RawBlock> neighbors, boolean bombMode) {

        List<RawBlock> minBlocks = new ArrayList<>();

        //Level up is not possible by removing a single block (bomb mode).
        if(bombMode) return minBlocks;

        //If highest blocks are being merged, return all blocks with min value.
        for(RawBlock neighbor : neighbors) {
            if (neighbor.getValue() == Config.LEVEL) {
                minBlocks.addAll(this.getMinBlocks());
                this.numberGenerator.increaseLevel();
                this.blockMatrixSupport.levelUp(Config.LEVEL);
                this.audioPlayer.playLevelUpSound();
                break;
            }
        }
        return minBlocks;
    }


    /**
     * STEP 0
     * Recursively get all equal neighbors from given block.
     * @param x given block x
     * @param y given block y
     * @param visitedBlocks Do not visit same block twice
     * @return list of rawBlocks
     */
    private List<RawBlock> getEqualNeighbors(int x, int y, List<RawBlock> visitedBlocks) {

        //List that will contain all total neighbors after recursion.
        List<RawBlock> equalNeighbors = new ArrayList<>();
        RawBlock block = this.getBlockAt(x, y);

        visitedBlocks.add(block);

        //Get top, right, bottom and left neighbor
        RawBlock[] neighbors = new RawBlock[] {
            this.getBlockAt(x, y - 1),
            this.getBlockAt(x + 1, y),
            this.getBlockAt(x, y + 1),
            this.getBlockAt(x - 1, y)
        };

        //If current neighbor has equal value and not been visited before,
        //add neighbors of current neighbor to total list.
        for(RawBlock neighbor : neighbors) {
            if(neighbor == null) continue;
            if(neighbor.getValue() == block.getValue() && ! visitedBlocks.contains(neighbor)) {
                equalNeighbors.add(neighbor);
                equalNeighbors.addAll(this.getEqualNeighbors(neighbor.getX(),neighbor.getY(), visitedBlocks));
            }
        }

        return equalNeighbors;
    }


    /**
     * STEP 1
     * Remove neighbors and their MergeBlocks from GUI.
     * @param blocks given Blocks
     */
    private void removeBlocks(List<RawBlock> blocks) {

        for(RawBlock neighbor : blocks) {
            this.blockMatrixSupport.removeBlock(neighbor);
            for(RawMergeBlock rawMergeBlock : this.getMergeBlocksOfBlock(neighbor))
                this.blockMatrixSupport.removeMergeBlock(rawMergeBlock);
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
        this.blockMatrixSupport.increaseBlockValue(this.rawBlocks[x][y]);
    }


    /**
     * STEP 3
     * Instead of making all the blocks to fall down,
     * we simply put the empty blocks to the top of matrix.
     * @param rawBlocks Empty RawBlocks
     */
    private void riseBlocksToTop(List<RawBlock> rawBlocks) {

        //Store MergeBlocks and remove them all together eventually
        //to prevent conflicts in animations.
        List<RawMergeBlock> mergeBlocksToRemove = new ArrayList<>();

        for(RawBlock emptyRawBlock : rawBlocks) {

            int rx = emptyRawBlock.getX();
            int ry = emptyRawBlock.getY();

            //Swap each empty RawBlock with Block above until top is reached.
            for(int i = ry; i >= 1; i--) {
                RawBlock blockAbove = this.rawBlocks[rx][i - 1];
                this.blockMatrixSupport.prepareToSinkBlock(blockAbove);
                this.rawBlocks[rx][i - 1]  = emptyRawBlock;
                this.rawBlocks[rx][i] = blockAbove;
                mergeBlocksToRemove.addAll(this.getMergeBlocksOfBlock(this.getBlockAt(rx, i - 1)));
                mergeBlocksToRemove.addAll(this.getMergeBlocksOfBlock(this.getBlockAt(rx, i)));
                emptyRawBlock.rise();
                blockAbove.fall();
            }
        }
        //Update changes to GUI.
        this.rawMergeBlocks.removeAll(mergeBlocksToRemove);
        for(RawMergeBlock rawMergeBlock : mergeBlocksToRemove)
            this.blockMatrixSupport.removeMergeBlock(rawMergeBlock);
        for(RawBlock rawBlock : this.getBlocksAsList())
            this.blockMatrixSupport.sinkBlock(rawBlock);
    }


    /**
     * STEP 4
     * Create new Blocks at null positions.
     * @param nullBlocks null positions
     */
    private void createNewBlocks(List<RawBlock> nullBlocks) {

        for(RawBlock rawBlock : nullBlocks) {

            int rx = rawBlock.getX();
            int ry = rawBlock.getY();
            RawBlock newRawBlock = new RawBlock(rx, ry, numberGenerator.getRandomNumber());
            this.rawBlocks[rx][ry] = newRawBlock;
            this.blockMatrixSupport.newBlockCreated(newRawBlock);
        }
    }


    /**
     * STEP 5
     * Generate additional MergeBlocks.
     */
    private void generateNewMergeBlocks() {

        this.generateMergeBlocks();
        this.blockMatrixSupport.mergeBlocksCreated(this.rawMergeBlocks);
    }


    /**
     * STEP 6
     * Checks if a pair exists.
     */
    private void checkForGameOver() {

        this.numberGenerator.increaseStarCount();

        if(this.rawMergeBlocks.isEmpty()) {

            //Set Highscore
            Highscore highscore = this.game.getHighscore();
            if(Config.LEVEL == highscore.getLevel()) {
                if(Config.STAR_COUNT > highscore.getStars())
                    highscore.setStars(Config.STAR_COUNT);
            }
            if(Config.LEVEL > this.game.getHighscore().getLevel()) {
                highscore.setLevel(Config.LEVEL);
                highscore.setStars(Config.STAR_COUNT);
            }

            //Show Game Over screen
            this.blockMatrixSupport.gameOver(this.game);
            this.audioPlayer.playGameOverSound();
        }
    }


    /**
     * Apply rules on grid.
     * @param location coordinates from clicked block
     */
    public void blockClicked(Location location) {

        if(Config.IS_ANIMATING) return;

        int x = location.getX();
        int y = location.getY();
        boolean bombMode = Config.BOMB_MODE;

        //STEP 0 - Get neighbors
        List<RawBlock> neighbors = this.getEqualNeighbors(x, y, new ArrayList<>());

        //Bomb Mode
        if(bombMode) {
            if(this.getBlockAt(x, y).getValue() == Config.LEVEL) return;
            neighbors.clear();
            neighbors.add(this.getBlockAt(x, y));
            this.blockMatrixSupport.toggleBombMode();
            this.game.getLevel().setStars(Config.STAR_COUNT -= Config.TOOL_COST);
            this.blockMatrixSupport.updateStarCount(Config.STAR_COUNT);
        }

        //Ignore single blocks
        if(neighbors.isEmpty()) return;

        //Enable or disable tool buttons
        this.blockMatrixSupport.setBombButtonEnabled(Config.STAR_COUNT >= Config.TOOL_COST);
        this.blockMatrixSupport.setUndoButtonEnabled(
            !this.previousBlocks.isEmpty() && Config.STAR_COUNT >= Config.TOOL_COST
        );

        //Store current state
        this.saveCurrentStates();

        //Play sound effect
        this.audioPlayer.playPopSound();

        //SPECIAL CASE - Check for level up
        neighbors.addAll(this.checkForLevelUp(neighbors, bombMode));

        //STEP 1 - Remove neighbors
        final KeyFrame step1 = new KeyFrame(
            Duration.ZERO,
            e -> this.removeBlocks(neighbors)
        );

        //STEP 2 - Increase value
        final KeyFrame step2 = new KeyFrame(
            Duration.ZERO,
            e -> this.increaseBlockValue(x, y)
        );

        //STEP 3 - Hide neighbors and bring them to top
        final KeyFrame step3 = new KeyFrame(
            Duration.ZERO,
            e -> this.riseBlocksToTop(neighbors)
        );

        //STEP 4 - Replace neighbors with new RawBlocks
        final KeyFrame step4 = new KeyFrame(
            Duration.millis(Config.GRID_ANIMATION),
            e -> this.createNewBlocks(neighbors)
        );

        //STEP 5 - Create new MergeBlocks
        final KeyFrame step5 = new KeyFrame(
            Duration.millis(Config.GRID_ANIMATION),
            e -> this.generateNewMergeBlocks()
        );

        //STEP 6 - Check for game over
        final KeyFrame step6 = new KeyFrame(
            Duration.millis(Config.GRID_ANIMATION),
            e -> this.checkForGameOver()
        );

        Config.IS_ANIMATING = true;
        final Timeline timeline = new Timeline(step1, step2, step3, step4, step5, step6);
        timeline.setOnFinished(e -> {
            this.blockMatrixSupport.updateStarCount(Config.STAR_COUNT);
            this.game.setRawBlocks(this.getBlocksAsList());
            this.gameLoader.saveGame(this.game);
            Config.IS_ANIMATING = false;
        });
        timeline.play();
    }


    /**
     * This method adds observers to this observable.
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.blockMatrixSupport.addPropertyChangeListener(pcl);
    }

}