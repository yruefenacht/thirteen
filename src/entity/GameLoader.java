package entity;

import config.Game;
import config.Highscore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.ArrayList;

/**
 * GameLoader.java
 * Stores and retrieves previous game states and scores.
 */
public class GameLoader {

    private String GAME_XML = "src/resources/data/Game.xml";
    private JAXBContext jaxbContext;


    /**
     * Class constructor.
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
