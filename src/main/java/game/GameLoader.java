package game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.Objects;

/**
 * GameLoader.java
 *
 * Stores and retrieves {@code Game} object into and from xml.
 */
public class GameLoader {

    private JAXBContext jaxbContext;


    /**
     * Constructs a {@code GameLoader} object.
     */
    public GameLoader() {

        try {
            this.jaxbContext = JAXBContext.newInstance(Game.class);
        } catch (JAXBException e) { e.printStackTrace(); }
    }


    /**
     * Loads Game from xml.
     * @return Game
     */
    public Game loadGame() {

        Game game = null;
        try {
            Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            game = (Game) unmarshaller.unmarshal(
                this.getClass().getClassLoader().getResourceAsStream("data/Game.xml")
            );

        } catch (JAXBException e) { e.printStackTrace(); }

        return game;
    }


    /**
     * Store current Game in xml.
     * @param game current Game
     */
    public void saveGame(Game game) {

        try {
            Marshaller m = this.jaxbContext.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.marshal(game,
                new File(
                    Objects.requireNonNull(this.getClass().getClassLoader().getResource("data/Game.xml")).getFile()
                )
            );

        } catch (JAXBException e) { e.printStackTrace(); }
    }

}