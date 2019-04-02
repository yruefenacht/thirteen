package controller;

import model.PlayfieldModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * PlayfieldController.java
 * The PlayfieldController class is responsible for handling user input.
 */
public class PlayfieldController implements PropertyChangeListener {

    private PlayfieldModel model;

    /**
     * Class constructor
     * @param model Observable that adds this object instance
     */
    public PlayfieldController(PlayfieldModel model) {

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
