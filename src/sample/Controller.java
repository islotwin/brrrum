package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.io.*;
import java.util.Scanner;


public class Controller extends ControllerBase {

    @FXML
    Polygon fxplay;

    @FXML
    Text fxask;

    @FXML
    AnchorPane fxcircle;
    Image image = new Image(new FileInputStream("src/graphics/brum.png"));
    public Controller() throws FileNotFoundException {
    }

    @FXML
    Text fxhigh;


    @FXML
    private void handleCircleAction (MouseEvent event) throws IOException
    {
        try {
            View.loadScene("choose-palette.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize( )  {
        Data.setPrev("menu.fxml");

        assert fxplay != null : "fx:id=\"fxplay\" was not injected: check your FXML file 'menu.fxml'.";
        assert fxcircle != null : "fx:id=\"fxcircle\" was not injected: check your FXML file 'menu.fxml'.";


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
