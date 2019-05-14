package game;

import entity.BlockList;
import entity.RawBlock;
import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Game.java
 * Stores state of current game.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "rawBlocks", "previousBlocks", "highscore", "level", "settings" })
public class Game {

    @XmlElementWrapper(name="rawBlocks")
    @XmlElement(name="block")
    private List<RawBlock> rawBlocks;

    @XmlElementWrapper(name="previousBlocks")
    @XmlElement(name="blockList")
    private List<BlockList> previousBlocks;

    @XmlElement(name="highscore")
    private Highscore highscore;

    @XmlElement(name="level")
    private Level level;

    @XmlElement(name="settings")
    private Settings settings;


    /**
     * Default Constructor for JAXB.
     */
    public Game() {}


    /**
     * RawBlocks getter.
     * @return RawBlocks
     */
    public List<RawBlock> getRawBlocks() {

        return this.rawBlocks;
    }


    /**
     * Previous RawBlocks getter.
     * @return RawBlocks
     */
    public List<BlockList> getPreviousBlocks() {

        return this.previousBlocks;
    }


    /**
     * Highscore getter.
     * @return Highscore
     */
    public Highscore getHighscore() {

        return this.highscore;
    }


    /**
     * Level getter.
     * @return Level
     */
    public Level getLevel() {

        return this.level;
    }


    /**
     * Settings getter.
     * @return settings
     */
    public Settings getSettings() {

        return this.settings;
    }


    /**
     * RawBlocks setter.
     * @param rawBlocks RawBlocks
     */
    public void setRawBlocks(List<RawBlock> rawBlocks) {

        this.rawBlocks = rawBlocks;
    }


    /**
     * Previous RawBlocks setter.
     * @param previousBlocks RawBlocks
     */
    public void setPreviousBlocks(List<BlockList> previousBlocks) {

        this.previousBlocks = previousBlocks;
    }

}
