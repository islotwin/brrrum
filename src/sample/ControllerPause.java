package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * Created by Iga Slotwinska on 21.05.2017.
 * Controller for the pause menu
 * pause.fxml
 */
public class ControllerPause extends ControllerBase {
    @FXML
    Polygon fxplay;
    @FXML
    Text fxask;
    @FXML
    Text fxscore;

    @FXML
    private void initialize()  {
        assert fxplay != null : "fx:id=\"fxplay\" was not injected: check your FXML file 'pause.fxml'.";
        assert fxscore != null : "fx:id=\"fxscore\" was not injected: check your FXML file 'pause.fxml'.";
        assert fxask != null : "fx:id=\"fxask\" was not injected: check your FXML file 'pause.fxml'.";
        Data.setPrev("pause.fxml");
        fxscore.setText(Integer.toString(Data.getScore()));
    }
}
