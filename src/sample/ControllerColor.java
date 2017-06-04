package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Arc;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by iga on 21.05.2017.
 */
public class ControllerColor extends ControllerBase {

    @FXML
    AnchorPane fxarrow;

    @FXML
    ImageView fxauto;
    @FXML
    Arc fxgreen;
    @FXML
    Arc fxred;
    @FXML
    Arc fxyellow;
    @FXML
    Arc fxgrey;
    @FXML
    Arc fxblack;
    @FXML
    Arc fxblue;
    @FXML
    Arc fxviolet;
    @FXML
    Arc fxorange;

    @FXML
    private void handleFxArcAction (MouseEvent event) throws IOException
    {
        Image image;
        if(event.getSource()==fxgreen)
            image = new Image(new FileInputStream("src/graphics/green.png"));
        else if(event.getSource()==fxorange)
            image = new Image(new FileInputStream("src/graphics/orange.png"));
        else if(event.getSource()==fxyellow)
            image = new Image(new FileInputStream("src/graphics/yellow.png"));
        else if(event.getSource()==fxgrey)
            image = new Image(new FileInputStream("src/graphics/grey.png"));
        else if(event.getSource()==fxblack)
            image = new Image(new FileInputStream("src/graphics/black.png"));
        else if(event.getSource()==fxblue)
            image = new Image(new FileInputStream("src/graphics/blue.png"));
        else if(event.getSource()==fxviolet)
            image = new Image(new FileInputStream("src/graphics/violet.png"));
         else
            image = new Image(new FileInputStream("src/graphics/brum.png"));
        fxauto.setImage(image);     /*  do widoku ewentualnie   */
        Data.setAutoColor(image);
    }



    @FXML
    private void initialize( )  {
        assert fxarrow != null : "fx:id=\"fxarrow\" was not injected: check your FXML file 'color-palette.fxml'.";
        assert fxauto != null : "fx:id=\"fxauto\" was not injected: check your FXML file 'color-palette.fxml'.";

    }


}
