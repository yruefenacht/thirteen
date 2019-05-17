package game;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

/**
 * GameLoader.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Stores and retrieves {@code Game} object into and from xml.
 */
public class GameLoader {

    private String GAME_XML = "src/main/resources/data/Game.xml";
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
            game = (Game) unmarshaller.unmarshal(new FileReader(this.GAME_XML));

        } catch (JAXBException | FileNotFoundException e) { e.printStackTrace(); }

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
            m.marshal(game, new File(this.GAME_XML));

        } catch (JAXBException e) { e.printStackTrace(); }
    }

}
