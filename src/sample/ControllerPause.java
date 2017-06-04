package sample;

import javafx.fxml.FXML;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;


/**
 * Created by iga on 21.05.2017.
 */
public class ControllerPause extends ControllerBase{

    @FXML
    Polygon fxplay;

    @FXML
    Text fxask;

    @FXML
    Text fxscore;


    @FXML
    private void initialize( )  {
        assert fxplay != null : "fx:id=\"fxplay\" was not injected: check your FXML file 'pause.fxml'.";

        Data.setPrev("pause.fxml");

        fxscore.setText(Integer.toString(Data.getScore()));
    }

}
