package sample;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/**
 * Created by iga on 28.05.2017.
 */
public class ControllerBase {

    @FXML
    private void handleMouse (MouseEvent event) throws IOException
    {
        if(event.getEventType() == MouseEvent.MOUSE_ENTERED)
            View.applyEffect((Node) event.getSource());
        else if(event.getEventType() == MouseEvent.MOUSE_EXITED)
            View.removeEffect((Node) event.getSource());
    }

    @FXML
    private void handleFxPlayAction (MouseEvent event) throws IOException
    {
        try {
            View.loadGameScene();  /*  STARE DANE NIE NOWE !!!! - pacz model ???*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
