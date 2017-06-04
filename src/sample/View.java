package sample;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Iga Slotwinska on 19.05.2017.
 * View - adjust Nodes' properties for Graphics
 */
public class View {


    /**
     * Method used when switching scenes, changes color of the car
     * @param name  name of the fxml file to open
     * @throws IOException  when file not found
     */
    public static void loadGameScene(String name) throws IOException {
        Stage stage = Data.getActivStage();
        AnchorPane root = FXMLLoader.load(Main.class.getResource(name));
        if (Data.getAutoColor() != null)
            ((ImageView) root.getChildren().get(root.getChildren().size() - 1)).setImage(Data.getAutoColor());
        stage.getScene().setRoot(root);
    }

    /**
     * Method used when switching scenes
     * @param name name of the fxml file to open
     * @throws IOException  when file not found
     */
    public static void loadScene(String name) throws IOException {
        Stage stage = Data.getActivStage();
        AnchorPane root = FXMLLoader.load(Main.class.getResource(name));
        stage.getScene().setRoot(root);
    }

    /**
     * Adds Glow effect, which is shown on mouse entering the node
     * @param node  node, which mouse enters
     */
    public static void applyEffect(Node node) {
        Data.setEffect(node.getEffect());
        node.setEffect(new Glow());
    }

    /**
     * Removes Glow effect, on mouse exit
     * @param node  node, which mouse exits
     */
    public static void removeEffect(Node node) {
        node.setEffect(Data.getEffect());
    }

    /**
     * Method to move rectangles on the road across the screen
     */
    static void moveRectangles () {
        for (Node iv : Data.getRectanglePane().getChildren()  ) {
            double moveY = Data.getDELTA() + iv.getLayoutY();

            if (moveY >=Data.getRectanglePane().getPrefHeight())
                iv.setLayoutY(0);
            else
                iv.setLayoutY(moveY);
        }
    }

    /**
     * Method creates path transition animation, called when game is over
     * @param node  node to which animation will be applied
     */
    public static void endingPath(Node node) {
        Path path = new Path();
        PathTransition pathTransition = new PathTransition();
        path.getElements().add(new MoveTo(Data.getStatAuto().getX() + (Data.getStatAuto().getFitWidth() / 2), Data.getStatAuto().getY() + (Data.getStatAuto().getFitHeight() / 2)));//-1*Data.getStatAuto().getImage().getHeight()));
        path.getElements().add(new LineTo(Data.getStatAuto().getX() + (Data.getStatAuto().getFitWidth() / 2), -1 * (node.getLayoutY() + (Data.getStatAuto().getFitHeight() / 2))));//-1*Data.getStatAuto().getImage().getHeight()));
        pathTransition.setNode(node);
        pathTransition.setPath(path);
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.setDelay(Duration.seconds(0.2));
        pathTransition.setCycleCount(1);
        pathTransition.play();
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Data.clearData();
                try {
                    View.loadScene("menu.fxml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sets up the features of each Gate, Blockage
     * @param image  which image: Gate or Blockage
     * @param offset  which part of the road
     * @return  processed image
     */
   public static ImageView setGate (Image image, int offset)   /*  czy to nie moze byc static ? w modelu i tak biere imageview i w ogle dziwne to*/
    {
        ImageView imageview = new ImageView();
        imageview.setImage(image);
        imageview.setLayoutX(75 + (offset*125));
        imageview.setLayoutY(-1*Data.getGateHeight());
        imageview.setPreserveRatio(true);
        imageview.setFitHeight(Data.getGateHeight());
        imageview.setFitWidth(Data.getGateWidth());
        imageview.setVisible(true);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.valueOf("#1a1919"));
        imageview.setEffect(dropShadow);
        return imageview;

    }

    public static Image LoadGate (String name)
    {
        try {
            return  new Image (new FileInputStream("src/graphics/" + name +".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method loads the previous state of the game (before pause)
     * @param fxpane  model of the pane
     * @param fxauto  model of the car
     * @param fxscore  previous score
     */
    public static void loadModel (AnchorPane fxpane, ImageView fxauto, Text fxscore )
    {
        fxpane.getChildren().addAll(Data.getGates());
        fxpane.getChildren().addAll(Data.getBlockages());
        fxauto.setLayoutX(Data.getStatAuto().getLayoutX() );
        fxauto.setLayoutY(Data.getStatAuto().getLayoutY());
        Data.setStatAuto(fxauto);
        fxscore.setText(Integer.toString(Data.getScore()));
    }

    public static void setUpCar (ImageView fxauto)
    {
        fxauto.toFront();
        fxauto.setFocusTraversable(true);
    }

    public static void setMusic ()
    {
        URL source = Main.class.getResource("/graphics/szczurek.mp3");
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(source.toString()));
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
}