package model;

import config.Event;
import entity.Block;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

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

    public void blockClicked(Block block) {

        this.propertyChangeSupport.firePropertyChange(Event.BLOCK_CLICK.toString(), block, block);
    }

    public void test() {

        this.propertyChangeSupport.firePropertyChange("test", 1, 1);
    }

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
