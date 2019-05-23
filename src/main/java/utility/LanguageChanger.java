package utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * LanguageChanger.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Sets and changes current resource bundle.
 */
class LanguageChanger {

    private ResourceBundle bundle;
    private List<Locale> locales;
    private int currentLocaleIndex = 0;


    /**
     * Constructs a {@code LanguageChanger} object.
     */
    LanguageChanger() {

        this.locales = new ArrayList<>();
        this.locales.add(new Locale("en", "GB"));
        this.locales.add(new Locale("de", "DE"));
        this.locales.add(new Locale("fr", "FR"));
        this.locales.add(new Locale("ar"));
        this.updateBundle();
    }


    /**
     * Applies current locale to resource bundle.
     */
    private void updateBundle() {

        this.bundle = ResourceBundle.getBundle("languages", this.locales.get(this.currentLocaleIndex));
    }


    /**
     * Increments current locale pointer.
     */
    void changeLanguage() {

        this.currentLocaleIndex++;
        if(this.currentLocaleIndex > this.locales.size() - 1) this.currentLocaleIndex = 0;
        this.updateBundle();
    }


    /**
     * Resource bundle getter.
     * @return bundle
     */
    ResourceBundle getBundle() {

        return this.bundle;
    }


    /**
     * Sets current language to new pointer.
     * @param value index
     */
    void setCurrentLocaleIndex(int value) {

        this.currentLocaleIndex = value;
        this.updateBundle();
    }


    /**
     * Language pointer getter.
     * @return index
     */
    int getCurrentLocaleIndex() {

        return this.currentLocaleIndex;
    }

}
