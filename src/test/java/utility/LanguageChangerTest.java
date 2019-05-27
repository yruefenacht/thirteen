package utility;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * LanguageChangerTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Test class of LanguageChanger.java.
 */
class LanguageChangerTest {

    private LanguageChanger languageChanger;


    /**
     * Test initialization.
     */
    @BeforeEach
    void setUp() {

        this.languageChanger = new LanguageChanger();
        assertNotNull(this.languageChanger);
        assertEquals(0, this.languageChanger.getCurrentLocaleIndex());
        assertEquals("en", this.languageChanger.getBundle().getLocale().getLanguage());
    }


    /**
     * Test changing language cycle.
     */
    @Test
    void changeLanguage() {

        this.languageChanger.changeLanguage();
        assertEquals(1, this.languageChanger.getCurrentLocaleIndex());
        assertEquals("de", this.languageChanger.getBundle().getLocale().getLanguage());

        this.languageChanger.changeLanguage();
        assertEquals(2, this.languageChanger.getCurrentLocaleIndex());
        assertEquals("fr", this.languageChanger.getBundle().getLocale().getLanguage());

        this.languageChanger.changeLanguage();
        assertEquals(3, this.languageChanger.getCurrentLocaleIndex());
        assertEquals("ar", this.languageChanger.getBundle().getLocale().getLanguage());

        this.languageChanger.changeLanguage();
        assertEquals(0, this.languageChanger.getCurrentLocaleIndex());
        assertEquals("en", this.languageChanger.getBundle().getLocale().getLanguage());
    }

}