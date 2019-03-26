package controller;

import model.Model;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class Controller implements PropertyChangeListener {

    private Model model;


    public Controller(Model model) {

        this.model = model;
        this.model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}
