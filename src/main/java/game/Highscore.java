package game;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Highscore.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 * 
 * Stores current highscore.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "level", "stars" })
public class Highscore {

    @XmlElement
    private int level;

    @XmlElement
    private int stars;


    /**
     * Constructs a {@code Highscore} object.
     * Default Class constructor for JAXB.
     */
    public Highscore() {}


    /**
     * Level setter.
     * @param level level
     */
    public void setLevel(int level) {

        this.level = level;
    }


    /**
     * Stars setter.
     * @param stars stars
     */
    public void setStars(int stars) {

        this.stars = stars;
    }


    /**
     * Level getter.
     * @return level
     */
    public int getLevel() {

        return this.level;
    }


    /**
     * Stars getter.
     * @return stars
     */
    public int getStars() {

        return this.stars;
    }

}
