package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class Model {

    private PropertyChangeSupport propertyChangeSupport;


    public Model() {

        this.propertyChangeSupport = new PropertyChangeSupport(this);
    }


    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        this.propertyChangeSupport.addPropertyChangeListener(pcl);
    }

}
