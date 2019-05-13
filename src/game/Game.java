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
@XmlType(propOrder = { "rawBlocks", "previousBlocks", "highscore", "level" })
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


    /**
     * Highscore setter.
     * @param highscore current highscore
     */
    public void setHighscore(Highscore highscore) {

        this.highscore = highscore;
    }


    /**
     * Level setter.
     * @param level level
     */
    public void setLevel(Level level) {

        this.level = level;
    }

}
