package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Created by Iga Slotwinska on 28.05.2017.
 * Base for other controllers
 */
public class ControllerBase {

    /**
     * Calls applying and removing of the Glow effect when entering or exiting node
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleMouse (MouseEvent event) throws IOException
    {
        if(event.getEventType() == MouseEvent.MOUSE_ENTERED)
            View.applyEffect((Node) event.getSource());
        else if(event.getEventType() == MouseEvent.MOUSE_EXITED)
            View.removeEffect((Node) event.getSource());
    }

    /**
     * Start the game
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFxPlayAction (MouseEvent event) throws IOException
    {
        try {
            View.loadGameScene("road2.fxml");  /*  STARE DANE NIE NOWE !!!! - pacz model ???*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Go back to previous scene
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFxArrowAction (MouseEvent event) throws IOException
    {
        try {
            View.loadScene(Data.getPrev());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAskAction (MouseEvent event) throws IOException
    {
        try {
            View.loadScene("help.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
