package config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Highscore.java
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
     * Class constructor.
     * @param level level
     * @param stars number of stars
     */
    public Highscore(int level, int stars) {

        this.level = level;
        this.stars = stars;
    }


    /**
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
