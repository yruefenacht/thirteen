package model;

import config.Events;
import entity.Location;
import entity.RawBlock;
import entity.RawMergeBlock;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * PlayfieldModel.java
 * Observable of game logic
 * Notifies the controllers if any changes occur.
 */

public class PlayfieldModel {

    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void blocksCreated(ArrayList<RawBlock> rawBlocks) {

        this.propertyChangeSupport.firePropertyChange(Events.BLOCKS_CREATED, null, rawBlocks);
    }

    public void mergeBlocksCreated(ArrayList<RawMergeBlock> rawMergeBlocks) {

        this.propertyChangeSupport.firePropertyChange(Events.MERGE_BLOCKS_CREATED, null, rawMergeBlocks);
    }

    public void blockClicked(Location location) {

        this.propertyChangeSupport.firePropertyChange(Events.BLOCK_CLICKED, null, location);
    }

    public void removeBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.REMOVE_BLOCK, null, rawBlock);
    }

    public void removeMergeBlock(RawMergeBlock rawMergeBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.REMOVE_MERGE_BLOCK, null, rawMergeBlock);
    }

    public void increaseBlockValue(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.INCREASE_BLOCK, null, rawBlock);
    }

    public void prepareToSinkBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.PREPARE_SINK_BLOCK, null, rawBlock);
    }

    public void sinkBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.SINK_BLOCK, null, rawBlock);
    }

    public void newBlockCreated(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Events.NEW_BLOCK_CREATED, null, rawBlock);
    }

    public void levelUp(int level) {

        this.propertyChangeSupport.firePropertyChange(Events.LEVEL_UP, null, level);
    }

    public void showGameOverScreen() {

        this.propertyChangeSupport.firePropertyChange(Events.GAME_OVER, null, 0);
    }

    public void toggleBombMode() {

        this.propertyChangeSupport.firePropertyChange(Events.BOMB_MODE, null, 0);
    }

    public void updateStarCount(int starCount) {

        this.propertyChangeSupport.firePropertyChange(Events.UPDATE_STAR_COUNT, null, starCount);
    }

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
