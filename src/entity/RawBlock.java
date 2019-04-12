package entity;

public class RawBlock {

    private int x;
    private int y;
    private int value;

    RawBlock(int x, int y, int value) {

        this.x = x;
        this.y = y;
        this.value = value;
    }

    public void updateValue() {

        this.value++;
    }

    public int getX() {

        return this.x;
    }

    public int getY() {

        return this.y;
    }

    public int getValue() {

        return this.value;
    }
}
