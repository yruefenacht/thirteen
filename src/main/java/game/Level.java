package game;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Level.java
 * Saves level and related values.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "level", "stars", "levelRange", "levelRangeHaltCounter"})
public class Level {

    @XmlElement
    private int level;

    @XmlElement
    private int stars;

    @XmlElement
    private int levelRange;

    @XmlElement
    private int levelRangeHaltCounter;


    /**
     * Default class constructor for JAXB.
     */
    public Level(int level, int stars, int levelRange, int levelRangeHaltCounter) {

        this.level = level;
        this.stars = stars;
        this.levelRange = levelRange;
        this.levelRangeHaltCounter = levelRangeHaltCounter;
    }


    /**
     * Default constructor for JAXB.
     */
    public Level() {}


    /**
     * Level getter.
     * @return level
     */
    public int getLevel() {

        return level;
    }


    /**
     * Stars getter.
     * @return stars
     */
    public int getStars() {

        return stars;
    }


    /**
     * Range getter.
     * @return range
     */
    public int getLevelRange() {

        return levelRange;
    }


    /**
     * Range halt counter getter.
     * @return range halt counter
     */
    public int getLevelRangeHaltCounter() {

        return levelRangeHaltCounter;
    }


    /**
     * Level setter.
     */
    public void increaseLevel() {

        this.level++;
    }


    /**
     * Stars setter.
     */
    public void increaseStars() {

        this.stars++;
    }


    /**
     * Range setter.
     */
    public void increaseLevelRange() {

        this.levelRange++;
    }


    /**
     * Range halt counter setter.
     */
    public void increaseLevelRangeHaltCounter() {

        this.levelRangeHaltCounter++;
    }

}
