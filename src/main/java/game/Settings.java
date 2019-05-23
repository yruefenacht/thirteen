package game;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Settings.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Stores user settings.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "sound", "languagePointer" })
public class Settings {

    @XmlElement
    private boolean sound;

    @XmlElement
    private int languagePointer;

    /**
     * Constructs a {@code Settings} object.
     * Default class constructor for JAXB.
     */
    public Settings() {}


    /**
     * Sound getter.
     * @return sound
     */
    public boolean isSound() {

        return sound;
    }


    /**
     * Language pointer getter.
     * @return index
     */
    public int getLanguagePointer() {

        return this.languagePointer;
    }


    /**
     * Sound setter.
     */
    public void toggleSound() {

        this.sound = !this.sound;
    }


    /**
     * Language pointer setter.
     * @param value index
     */
    public void setLanguagePointer(int value) {

        this.languagePointer = value;
    }

}
