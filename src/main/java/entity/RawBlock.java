package entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * RawBlock.java
 *
 * Defines mathematical properties of a Block.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "x", "y", "value" })
public class RawBlock {

    @XmlElement
    private int x;

    @XmlElement
    private int y;

    @XmlElement
    private int value;


    /**
     * Constructs a {@code RawBlock} object.
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
     * Default Class constructor for JAXB.
     */
    public RawBlock() {}


    /**
     * Increases value.
     */
    public void increaseBlockValue() {

        this.value++;
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

}
