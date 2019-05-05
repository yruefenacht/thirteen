package entity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * UserDataManager.java
 * Stores and retrieves previous game states and scores.
 */
public class UserDataManager {

    private Document document;
    private File userDataFile;


    /**
     * Class constructor.
     */
    public UserDataManager() {

        try {
            //Load xml file
            this.userDataFile = new File("src/resources/data/UserData.xml");

            // create the DOM factory
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setIgnoringElementContentWhitespace(true);

            // Create the document builder and set the error handler
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new DefaultHandler());

            // read the xml file
            this.document = builder.parse(this.userDataFile);

        } catch (ParserConfigurationException | SAXException | IOException e) { e.printStackTrace(); }
    }


    /**
     * Loads Block Elements from UserData.xml.
     * @return list of RawBlocks
     */
    public ArrayList<RawBlock> loadRawBlocks() {

        ArrayList<RawBlock> rawBlocks = new ArrayList<>();
        NodeList xmlRawBlocks = this.document.getElementsByTagName("Block");

        for(int i = 0; i < xmlRawBlocks.getLength(); i++) {

            Element xmlRawBlock = (Element) xmlRawBlocks.item(i);
            RawBlock rawBlock = new RawBlock(
                Integer.parseInt(xmlRawBlock.getAttribute("x")),
                Integer.parseInt(xmlRawBlock.getAttribute("y")),
                Integer.parseInt(xmlRawBlock.getAttribute("value"))
            );
            rawBlocks.add(rawBlock);
        }

        return rawBlocks;
    }


    /**
     * Loads Level from UserData.xml.
     * @return level as int
     */
    public int loadLevel() {

        Element level = (Element) this.document.getElementsByTagName("Level").item(0);
        return Integer.parseInt(level.getTextContent());
    }


    /**
     * Stores given RawBlocks and Level into UserData.xml.
     * @param rawBlocks given Blocks
     * @param level given level
     */
    public void saveGame(ArrayList<RawBlock> rawBlocks, int level) {

        //Save Level
        this.document.getElementsByTagName("Level").item(0).setTextContent(Integer.toString(level));

        //Remove Blocks
        Node blocksElement = this.document.getElementsByTagName("Blocks").item(0);

        while(blocksElement.hasChildNodes())
            blocksElement.removeChild(blocksElement.getFirstChild());

        //Add Blocks
        for(RawBlock rawBlock : rawBlocks) {

            Element block = this.document.createElement("Block");
            block.setAttribute("x", Integer.toString(rawBlock.getX()));
            block.setAttribute("y", Integer.toString(rawBlock.getY()));
            block.setAttribute("value", Integer.toString(rawBlock.getValue()));
            blocksElement.appendChild(block);
        }

        //Save file
        TransformerFactory transFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(this.document);

            transformer.transform(source, new StreamResult(new FileOutputStream(this.userDataFile)));

        } catch (TransformerException | FileNotFoundException e) { e.printStackTrace(); }
    }

}
