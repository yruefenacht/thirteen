package entity;

/**
 * Location.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Defines object with sole purpose to store coordinates.
 */
public class Location {

    private int x;
    private int y;


    /**
     * Constructs a {@code Location} object.
     * @param x
     * @param y
     */
    public Location(int x, int y) {

        this.x = x;
        this.y = y;
    }


    /**
     * X getter.
     * @return x
     */
    public int getX() {

        return this.x;
    }


    /**
     * Y getter.
     * @return y
     */
    public int getY() {

        return this.y;
    }

}
