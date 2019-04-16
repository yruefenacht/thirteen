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
 * This class acts as observable.
 * It notifies the controller class if any changes occur.
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

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
