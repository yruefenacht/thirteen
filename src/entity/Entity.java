package entity;

import javafx.scene.layout.StackPane;

/**
 * Entity.java
 * Defines basic methods of all entities
 */
public class Entity extends StackPane {

    /**
     * Initial animation
     */
    public void show() {

        Animations.getScaleAnimation(this).play();
    }
}
