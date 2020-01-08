package main.java.model;

import main.java.config.Events;
import main.java.entity.RawBlock;
import main.java.entity.RawMergeBlock;
import main.java.game.Game;

import java.beans.PropertyChangeSupport;
import java.util.List;

/**
 * BlockMatrixSupport.java
 *
 * Event shooter.
 * It is preferable to have all property change events stored in one class.
 * Notifies the controllers if any changes occur.
 */
class BlockMatrixSupport extends PropertyChangeSupport {


    /**
     * Constructs a {@code PropertyChangeSupport} object.
     * @param sourceBean The bean to be given as the source for any events.
     */
    BlockMatrixSupport(Object sourceBean) {

        super(sourceBean);
    }


    /**
     * Notify that RawBlocks have been created.
     * @param rawBlocks created RawBlocks.
     */
    void blocksCreated(List<RawBlock> rawBlocks) {

        this.firePropertyChange(Events.BLOCKS_CREATED, null, rawBlocks);
    }


    /**
     * Notify that RawMergeBlocks have been created.
     * @param rawMergeBlocks created RawMergeBlocks
     */
    void mergeBlocksCreated(List<RawMergeBlock> rawMergeBlocks) {

        this.firePropertyChange(Events.MERGE_BLOCKS_CREATED, null, rawMergeBlocks);
    }


    /**
     * Notify that RawBlock has been removed.
     * @param rawBlock removed RawBlock.
     */
    void removeBlock(RawBlock rawBlock) {

        this.firePropertyChange(Events.REMOVE_BLOCK, null, rawBlock);
    }


    /**
     * Notify that RawMergeBlock has been removed.
     * @param rawMergeBlock removed RawMergeBlock.
     */
    void removeMergeBlock(RawMergeBlock rawMergeBlock) {

        this.firePropertyChange(Events.REMOVE_MERGE_BLOCK, null, rawMergeBlock);
    }


    /**
     * Notify that value of give RawBlock has increased.
     * @param rawBlock given RawBlock
     */
    void increaseBlockValue(RawBlock rawBlock) {

        this.firePropertyChange(Events.INCREASE_BLOCK, null, rawBlock);
    }


    /**
     * Notify that RawBlock has fallen down.
     * @param rawBlock given RawBlock
     */
    void prepareToSinkBlock(RawBlock rawBlock) {

        this.firePropertyChange(Events.PREPARE_SINK_BLOCK, null, rawBlock);
    }


    /**
     * Notify Block to sink.
     * @param rawBlock coherent RawBlock
     */
    void sinkBlock(RawBlock rawBlock) {

        this.firePropertyChange(Events.SINK_BLOCK, null, rawBlock);
    }


    /**
     * Notify that new RawBlock has been created.
     * @param rawBlock new RawBlock
     */
    void newBlockCreated(RawBlock rawBlock) {

        this.firePropertyChange(Events.NEW_BLOCK_CREATED, null, rawBlock);
    }


    /**
     * Notify that level has changed.
     * @param level new level
     */
    void levelUp(int level) {

        this.firePropertyChange(Events.LEVEL_UP, null, level);
    }


    /**
     * Notify that bomb mode was toggled.
     */
    void toggleBombMode() {

        this.firePropertyChange(Events.BOMB_MODE, null, 0);
    }


    /**
     * Notify that star count has changed.
     * @param starCount new star count
     */
    void updateStarCount(int starCount) {

        this.firePropertyChange(Events.UPDATE_STAR_COUNT, null, starCount);
    }


    /**
     * Notify game to continue.
     */
    void continueGame() {

        this.firePropertyChange(Events.CONTINUE_GAME, null, 0);
    }


    /**
     * Notify game to restart.
     */
    void restartGame() {

        this.firePropertyChange(Events.RESTART_GAME, null, 0);
    }


    /**
     * Notify game to terminate.
     */
    void quitGame() {

        this.firePropertyChange(Events.QUIT_GAME, null, 0);
    }


    /**
     * Notify game to show game over screen.
     * @param game values to display
     */
    void gameOver(Game game) {

        this.firePropertyChange(Events.GAME_OVER, null, game);
    }


    /**
     * Notify to update undo button.
     * @param enabled true to enable, vice versa
     */
    void setUndoButtonEnabled(boolean enabled) {

        this.firePropertyChange(Events.UNDO_SET_ENABLED, null, enabled);
    }


    /**
     * Notify to update bomb button
     * @param enabled true to enable, vice versa
     */
    void setBombButtonEnabled(boolean enabled) {

        this.firePropertyChange(Events.BOMB_SET_ENABLED, null, enabled);
    }


    /**
     * Notify to clear all MergeBlocks.
     */
    void resetMergeBlocks() {

        this.firePropertyChange(Events.MERGE_BLOCKS_RESET, null, 0);
    }

}
