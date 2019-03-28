package controller;

import model.Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Controller.java
 * The Controller class is responsible for handling user input.
 */
public class Controller implements PropertyChangeListener {

    private Model model;

    /**
     * Class constructor
     * @param model Observable that adds this object instance
     */
    public Controller(Model model) {

        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    /**
     * This method is fired when changes happen in the model observable.
     * @param evt Object that stores event properties
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
