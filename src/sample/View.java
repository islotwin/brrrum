package sample;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by iga on 19.05.2017.
 */
public class View {


    public static void loadGameScene() throws IOException {

        Stage stage = Data.getActivStage();

        //  Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane root = FXMLLoader.load(Main.class.getResource("road2.fxml"));
        if (Data.getAutoColor() != null)
            ((ImageView) root.getChildren().get(root.getChildren().size() - 1)).setImage(Data.getAutoColor());
        stage.getScene().setRoot(root);
        View.setScene(root, stage.getScene());
    }

    public static void loadScene(String name) throws IOException {
        Stage app_stage = Data.getActivStage();
        AnchorPane root = FXMLLoader.load(Main.class.getResource(name));

        if (name.equals("choose-palette.fxml")) {
            if (Data.getAutoColor() != null)
                ((ImageView) root.getChildren().get(root.getChildren().size() - 1)).setImage(Data.getAutoColor());
        }
        app_stage.getScene().setRoot(root);
    }


    public static void setScene(AnchorPane root, Scene scene) {
/*
        Node top =
                root.getChildren().size() > 0
                        ? root.getChildren().get(root.getChildren().size() - 1)
                        : null;


        for (int i=0; i< 5; ++i)
        {
            Rectangle rect = new Rectangle( Data.getActivStage().getScene().getWidth()/2, -100, 5,80);
            rect.setFill(Color.WHITE);
            Path newpath = new Path();
            newpath = createPath(newpath, rect, rect.getY());
            // newpath.getElements().addAll(pathElements);
            PathTransition newanim = new PathTransition();
            newanim.setNode(rect);
            newanim.setPath(newpath);
            newanim.setDuration(Duration.seconds(2));
            newanim.setDelay(Duration.seconds(i*0.4));
            newanim.setAutoReverse(false);
            //newanim.setRate(0.05);
            newanim.setCycleCount(Timeline.INDEFINITE);
            root.getChildren().add(rect);
            newanim.setInterpolator(Interpolator.LINEAR);

            newanim.play();
        }

        top.toFront();
*/
    }

    public static void applyEffect(Node node) {
        Data.setEffect(node.getEffect());
        node.setEffect(new Glow());
    }

    public static void removeEffect(Node node) {
        node.setEffect(Data.getEffect());
    }


    public static Path createPath(Path p, Rectangle r, double y) {
        PathElement[] pathElements =
                {
                        //new MoveTo(r.getX(), y),
                        new MoveTo(r.getX(), -(r.getHeight())),
                        new LineTo(r.getX(), 600 + (r.getHeight() / 2)),
                        new MoveTo(r.getX(), -(r.getHeight())),
                        //new LineTo(r.getX(), y),

                        //   new LineTo(rectangle.getX(), rectangleY),
                        new ClosePath()
                };

        p.getElements().addAll(pathElements);

        return p;
    }


    public static void moveRectangles(double dy) {

        for (Node rectangle : Data.getActivStage().getScene().getRoot().getChildrenUnmodifiable().subList(6, 6)
                ) {
            double moveY = dy + rectangle.getLayoutY();
            if (moveY > Data.getActivStage().getScene().getHeight())
                rectangle.setLayoutY(-1 * 66);
            else
                rectangle.setLayoutY(moveY);
        }
    }


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

    public static void loadModel (AnchorPane fxpane, ImageView fxauto, Text fxscore )
    {
        fxpane.getChildren().addAll(Data.getGates());
        fxpane.getChildren().addAll(Data.getBlockages());
        fxauto.setLayoutX(Data.getStatAuto().getLayoutX() );
        fxauto.setLayoutY(Data.getStatAuto().getLayoutY());
        Data.setStatAuto(fxauto);
       // fxauto.toFront();
        fxscore.setText(Integer.toString(Data.getScore()));
    }

    public static void setUpCar (ImageView fxauto)
    {
        fxauto.toFront();
        fxauto.setFocusTraversable(true);

    }
}