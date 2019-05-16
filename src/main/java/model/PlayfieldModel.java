package model;

import config.Events;
import entity.Location;
import entity.RawBlock;
import entity.RawMergeBlock;
import game.Highscore;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * PlayfieldModel.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Observable of game logic.
 * Notifies the controllers if any changes occur.
 */
public class PlayfieldModel {

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);


    /**
     * Notify that RawBlocks have been created.
     * @param rawBlocks created RawBlocks.
     */
    public void blocksCreated(List<RawBlock> rawBlocks) {

        this.propertyChangeSupport.firePropertyChange(Events.BLOCKS_CREATED, null, rawBlocks);
    }


    /**
     * Notify that RawMergeBlocks have been created.
     * @param rawMergeBlocks created RawMergeBlocks
     */
    public void mergeBlocksCreated(List<RawMergeBlock> rawMergeBlocks) {

        this.propertyChangeSupport.firePropertyChange(Events.MERGE_BLOCKS_CREATED, null, rawMergeBlocks);
    }


    /**
     * Notify that Block has been clicked.
     * @param location coordinates of clicked Block
     */
    public void blockClicked(Location location) {

        this.propertyChangeSupport.firePropertyChange(Events.BLOCK_CLICKED, null, location);
    }


    /**
     * Notify that RawBlock has been removed.
     * @param rawBlock removed RawBlock.
     */
    public void removeBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.REMOVE_BLOCK, null, rawBlock);
    }


    /**
     * Notify that RawMergeBlock has been removed.
     * @param rawMergeBlock removed RawMergeBlock.
     */
    public void removeMergeBlock(RawMergeBlock rawMergeBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.REMOVE_MERGE_BLOCK, null, rawMergeBlock);
    }


    /**
     * Notify that value of give RawBlock has increased.
     * @param rawBlock given RawBlock
     */
    public void increaseBlockValue(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.INCREASE_BLOCK, null, rawBlock);
    }


    /**
     * Notify that RawBlock has fallen down.
     * @param rawBlock given RawBlock
     */
    public void prepareToSinkBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.PREPARE_SINK_BLOCK, null, rawBlock);
    }


    /**
     * Notify Block to sink.
     * @param rawBlock coherent RawBlock
     */
    public void sinkBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.SINK_BLOCK, null, rawBlock);
    }


    /**
     * Notify that new RawBlock has been created.
     * @param rawBlock new RawBlock
     */
    public void newBlockCreated(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.NEW_BLOCK_CREATED, null, rawBlock);
    }


    /**
     * Notify that level has changed.
     * @param level new level
     */
    public void levelUp(int level) {

        this.propertyChangeSupport.firePropertyChange(Events.LEVEL_UP, null, level);
    }


    /**
     * Notify that bomb mode was toggled.
     */
    public void toggleBombMode() {

        this.propertyChangeSupport.firePropertyChange(Events.BOMB_MODE, null, 0);
    }


    /**
     * Notify that star count has changed.
     * @param starCount new star count
     */
    public void updateStarCount(int starCount) {

        this.propertyChangeSupport.firePropertyChange(Events.UPDATE_STAR_COUNT, null, starCount);
    }


    /**
     * Notify game to continue.
     */
    public void continueGame() {

        this.propertyChangeSupport.firePropertyChange(Events.CONTINUE_GAME, null, 0);
    }


    /**
     * Notify game to restart.
     */
    public void restartGame() {

        this.propertyChangeSupport.firePropertyChange(Events.RESTART_GAME, null, 0);
    }


    /**
     * Notify game to terminate.
     */
    public void quitGame() {

        this.propertyChangeSupport.firePropertyChange(Events.QUIT_GAME, null, 0);
    }


    /**
     * Notify game to show game over screen.
     */
    public void gameOver(Highscore highscore) {

        this.propertyChangeSupport.firePropertyChange(Events.GAME_OVER, null, highscore);
    }


    /**
     * Notify game to go back one step.
     */
    public void undo() {

        this.propertyChangeSupport.firePropertyChange(Events.UNDO, null, 0);
    }


    /**
     * Notify to update undo button.
     * @param enabled true to enable, vice versa
     */
    public void setUndoButtonEnabled(boolean enabled) {

        this.propertyChangeSupport.firePropertyChange(Events.UNDO_SET_ENABLED, null, enabled);
    }


    /**
     * Notify to clear all MergeBlocks.
     */
    public void resetMergeBlocks() {

        this.propertyChangeSupport.firePropertyChange(Events.MERGE_BLOCKS_RESET, null, 0);
    }


    /**
     * This method adds observers to this observable.
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
