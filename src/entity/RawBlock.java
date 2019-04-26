package entity;

/**
 * RawBlock.java
 * Defines mathematical properties of a Block.
 */
public class RawBlock {

    private int x;
    private int y;
    private int value;
    private int sink;


    /**
     * Class constructor.
     * @param x location x
     * @param y location y
     * @param value number
     */
    public RawBlock(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
    }


    /**
     * Increases value.
     */
    public void increaseBlockValue() {

        this.value++;
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


    /**
     * Value getter.
     * @return value
     */
    public int getValue() {

        return this.value;
    }


    /**
     * Increment y.
     */
    public void fall() {

        this.y++;
    }


    /**
     * Decrease y.
     */
    public void rise() {

        this.y--;
    }

}
