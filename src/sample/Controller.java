package sample;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

/**
  * Created by Iga Slotwinska on 21.05.2017.
  * Controller for the main menu
 *  menu.fxml
 */
public class Controller extends ControllerBase {

    @FXML
    Polygon fxplay;

    @FXML
    Text fxask;

    @FXML
    AnchorPane fxcircle;

    @FXML
    Text fxhigh;


    /**
     * Switches scene to
     * @param event mouse clicked on the color palette
     * @throws IOException
     */
    @FXML
    private void handleCircleAction (MouseEvent event) throws IOException
    {
        try {
            View.loadGameScene("choose-palette.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Loads highscore. If file not found, creates a new one with the value of 0.
     */
    @FXML
    private void initialize( )  {
        Data.setPrev("menu.fxml");

        assert fxplay != null : "fx:id=\"fxplay\" was not injected: check your FXML file 'menu.fxml'.";
        assert fxcircle != null : "fx:id=\"fxcircle\" was not injected: check your FXML file 'menu.fxml'.";
        assert fxask != null : "fx:id=\"fxask\" was not injected: check your FXML file 'menu.fxml'.";
        assert fxhigh != null : "fx:id=\"fxhigh\" was not injected: check your FXML file 'menu.fxml'.";

        if (!new File("score.txt").exists()) {
            try {
                Writer writer = new FileWriter("./score.txt");
                writer.write(String.valueOf(0));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try
        {
            File file = new File("./score.txt");
            Scanner scanner = new Scanner(file, "UTF-8");
            if (scanner.hasNext())
            {
                fxhigh.setText(scanner.next());
                Data.setHighscore(Integer.parseInt(fxhigh.getText()));
            }
            scanner.close();
        } catch (IOException e) {e.printStackTrace();}

    }

    }
