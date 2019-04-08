package config;

/**
 * Event.java
 * Enum that defines all propertyChangeEvent names
 */
public enum Event {

    BLOCK_CLICK("BlockClick");

    private final String event;

    /**
     * Class constructor
     * @param event event name
     */
    Event(String event) {

        this.event = event;
    }

    /**
     * event getter
     * @return event
     */
    @Override
    public String toString() {

        return event;
    }

}
