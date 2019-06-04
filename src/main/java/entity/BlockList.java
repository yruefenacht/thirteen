package entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;
import java.util.List;

/**
 * BlockList.java
 *
 * Has sole purpose of storing blocks as list.
 * This is necessary, since JAXB cannot store elements of type {@code List<List<T>>}
 */
@XmlType
public class BlockList {

    @XmlElementWrapper(name="blocks")
    @XmlElement(name="block")
    private List<RawBlock> rawBlocks;


    /**
     * Constructs a {@code BlockList} object.
     * @param rawBlocks RawBlocks
     */
    public BlockList(List<RawBlock> rawBlocks) {

        this.rawBlocks = rawBlocks;
    }


    /**
     * Default Class constructor for JAXB.
     */
    public BlockList() {}


    /**
     * RawBlocks getter.
     * @return RawBlocks
     */
    public List<RawBlock> getRawBlocks() {

        return rawBlocks;
    }

}
