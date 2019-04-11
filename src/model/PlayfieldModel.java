package model;

import config.Event;
import entity.Block;
import entity.MergeBlock;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 * PlayfieldModel.java
 * This class acts as observable.
 * It notifies the controller class if any changes occur.
 */

public class PlayfieldModel {

    private PropertyChangeSupport propertyChangeSupport;

    /**
     * Class constructor
     */
    public PlayfieldModel() {

        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public void blocksCreated(ArrayList<Block> blocks) {

        this.propertyChangeSupport.firePropertyChange(Event.BLOCKS_CREATED, null, blocks);
    }

    public void mergeBlocksCreated(ArrayList<MergeBlock> mergeBlocks) {

        this.propertyChangeSupport.firePropertyChange(Event.MERGE_BLOCKS_CREATED, null, mergeBlocks);
    }

    /**
     * Informs all Observer that a Block has been clicked
     * @param block Clicked block
     */
    public void blockClicked(Block block) {

        this.propertyChangeSupport.firePropertyChange(Event.BLOCK_CLICKED, null, block);
    }

    /**
     * Tells BlockMatrix to remove this block
     * @param block Block to remove
     */
    public void removeBlock(Block block) {

        this.propertyChangeSupport.firePropertyChange(Event.REMOVE_BLOCK, null, block);
    }

    /**
     * Tells BlockMatrix to remove this mergeBlock
     * @param mergeBlock mergeBlock to remove
     */
    public void removeMergeBlock(MergeBlock mergeBlock) {

        this.propertyChangeSupport.firePropertyChange(Event.REMOVE_MERGE_BLOCK, null, mergeBlock);
    }

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
