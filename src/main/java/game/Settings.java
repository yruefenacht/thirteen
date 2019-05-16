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
@XmlType(propOrder = { "sound" })
public class Settings {

    @XmlElement
    private boolean sound;


    /**
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
     * Sound setter.
     */
    public void toggleSound() {

        this.sound = !this.sound;
    }

}
