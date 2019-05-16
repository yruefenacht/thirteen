package entity;

import javafx.scene.layout.StackPane;

/**
 * Entity.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Defines basic methods of all entities.
 */
public class Entity extends StackPane {


    /**
     * Initial animation
     */
    public void show() {

        Animations.getScaleAnimation(this).play();
    }

}
