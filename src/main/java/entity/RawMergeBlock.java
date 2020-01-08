package main.java.entity;

/**
 * RawMergeBlock.java
 *
 * Defines mathematical properties of a MergeBlock.
 */
public class RawMergeBlock {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int value;


    /**
     * Constructs a {@code RawMergeBlock} object.
     * @param x1 start x
     * @param y1 start y
     * @param x2 end x
     * @param y2 end y
     * @param value used for background color
     */
    public  RawMergeBlock(int x1, int y1, int x2, int y2, int value) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.value = value;
    }


    /**
     * X1 getter.
     * @return x1
     */
    public int getX1() {

        return this.x1;
    }


    /**
     * Y1 getter.
     * @return y1
     */
    public int getY1() {

        return this.y1;
    }


    /**
     * X2 getter.
     * @return x2
     */
    public int getX2() {

        return this.x2;
    }


    /**
     * Y2 getter.
     * @return y2
     */
    public int getY2() {

        return this.y2;
    }


    /**
     * Value getter.
     * @return value
     */
    public int getValue() {

        return this.value;
    }


    /**
     * Checks if given block is at same location.
     * @param rawBlock given block
     * @return true or false
     */
    public boolean hasBlock(RawBlock rawBlock) {

        if(rawBlock == null) return false;
        return(rawBlock.getX() == this.x1 && rawBlock.getY() == this.y1
            || rawBlock.getX() == this.x2 && rawBlock.getY() == this.y2);
    }

}
