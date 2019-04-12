package model;

import config.Event;
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

        this.propertyChangeSupport.firePropertyChange(Event.BLOCKS_CREATED, null, rawBlocks);
    }


    public void mergeBlocksCreated(ArrayList<RawMergeBlock> rawMergeBlocks) {

        this.propertyChangeSupport.firePropertyChange(Event.MERGE_BLOCKS_CREATED, null, rawMergeBlocks);
    }


    public void blockClicked(Location location) {

        this.propertyChangeSupport.firePropertyChange(Event.BLOCK_CLICKED, null, location);
    }


    public void removeBlock(RawBlock rawBlock) {

        this.propertyChangeSupport.firePropertyChange(Event.REMOVE_BLOCK, null, rawBlock);
    }


    public void removeMergeBlock(RawMergeBlock rawMergeBlock) {

        this.propertyChangeSupport.firePropertyChange(Event.REMOVE_MERGE_BLOCK, null, rawMergeBlock);
    }


    public void sinkBlock(RawBlock rawBlock, int steps) {

        this.propertyChangeSupport.firePropertyChange(Event.SINK_BLOCK, steps, rawBlock);
    }


    public void increaseBlockValue(Location location) {

        this.propertyChangeSupport.firePropertyChange(Event.INCREASE_BLOCK, null, location);
    }

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
