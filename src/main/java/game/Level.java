package main.java.game;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Level.java
 *
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
     * Constructs a {@code Level} object.
     * @param level level
     * @param stars stars/coins
     * @param levelRange range of numbers for {@code NumberGenerator} to choose from
     * @param levelRangeHaltCounter counter when to increase {@code levelRange}
     */
    public Level(int level, int stars, int levelRange, int levelRangeHaltCounter) {

        this.level = level;
        this.stars = stars;
        this.levelRange = levelRange;
        this.levelRangeHaltCounter = levelRangeHaltCounter;
    }


    /**
     * Constructs a {@code Level} object.
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
     * Stars increment.
     */
    public void increaseStars() {

        this.stars++;
    }


    /**
     * Stars setter.
     * @param value stars
     */
    public void setStars(int value) {

        this.stars = value;
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
