package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Model.java
 * This class acts as observable.
 * It notifies the controller class if any changes occur.
 */
public class Model {

    private PropertyChangeSupport propertyChangeSupport;

    /**
     * Class constructor
     */
    public Model() {

        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }

    /**
     * This method adds Observers to this observable
     * @param pcl Observer to add
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
