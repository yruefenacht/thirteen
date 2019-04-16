package entity;

public class RawMergeBlock {

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int value;

    RawMergeBlock(int x1, int y1, int x2, int y2, int value) {

        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.value = value;
    }

    public int getX1() {

        return this.x1;
    }

    public int getY1() {

        return this.y1;
    }

    public int getX2() {

        return this.x2;
    }

    public int getY2() {

        return this.y2;
    }

    public int getValue() {

        return this.value;
    }

    boolean hasBlock(RawBlock rawBlock) {

        if(rawBlock == null) return false;
        return(rawBlock.getX() == this.x1 && rawBlock.getY() == this.y1
            || rawBlock.getX() == this.x2 && rawBlock.getY() == this.y2);
    }
}
