package main.java.utility;

//import javafx.embed.swing.JFXPanel;
import javafx.scene.layout.BorderPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ViewChangerTest.java
 * @author     Yannick Rüfenacht
 * @author     Mohammed Ali
 * @version    1.0
 *
 * Test class of ViewChanger.java.
 */
class ViewChangerTest {

    private BorderPane root;


    /**
     * Test initialization.
     */
    @BeforeEach
    void setUp() {

        //Declare new JFXPanel to prevent "Toolkit not initialized" error
        //JFXPanel jfxPanel = new JFXPanel();
        this.root = ViewChanger.init();
        assertNotNull(this.root);
        assertNull(this.root.getCenter());
    }


    /**
     * Test if View was added to root.
     */
    @Test
    void changeToMainMenu() {

        this.root = ViewChanger.init();
        ViewChanger.changeToMainMenu();
        assertNotNull(this.root.getCenter());
    }


    /**
     * Test if View was added to root.
     */
    @Test
    void changeToPlayfield() {

        this.root = ViewChanger.init();
        ViewChanger.changeToPlayfield(false);
        assertNotNull(this.root.getCenter());
    }

}