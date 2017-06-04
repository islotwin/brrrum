package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;

/**
 * Created by Iga Slotwinska on 15.05.2017.
 * Controller for the gameplay
 * road2.fxml
 */
public class ControllerGame extends ControllerBase {
    private AnimationTimer timer;
    private boolean up, down, left, right;
    @FXML
    AnchorPane fxpane;
    @FXML
    ImageView fxauto;
    @FXML
    AnchorPane fxpause;
    @FXML
    Text fxscore;

    /**
     * Event handler for the car movement
     * @param event  key pressed
     */
    @FXML
    public void KeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case UP:  up = true ;  break;
            case DOWN:   down = true ; break;
            case LEFT:  left = true; break;
            case RIGHT: right = true; break;
        }
    }

    /**
     * Event handler for the car movement
     * @param event  key realeased
     */
    @FXML
    public void KeyReleased(KeyEvent event) {
        switch (event.getCode()) {
            case UP:  up = false ;  break;
            case DOWN:   down = false ; break;
            case LEFT:  left = false; break;
            case RIGHT: right = false; break;
        }
    }

    @FXML
    private void initialize()  {
        assert fxpane != null : "fx:id=\"fxpane\" was not injected: check your FXML file 'road2.fxml'.";
        assert fxauto != null : "fx:id=\"fxauto\" was not injected: check your FXML file 'road2.fxml'.";
        startGame();
    }

    /**
     * Pausing the game
     * @param event
     * @throws IOException
     */
    @FXML
    private void handleFxPauseAction(MouseEvent event) throws IOException {
        Data.setStatAuto(fxauto);
        timer.stop();
        try {
            View.loadScene("pause.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start the game, load data after break or start with new model
     */
    private void startGame() {
        if (Data.getPrev().equals("menu.fxml"))
            Data.setStatAuto(fxauto);
        else
            View.loadModel(fxpane, fxauto, fxscore);
        Data.setRectanglePane((AnchorPane )(fxpane.getChildren()).get(1));
        Data.setAnchorPane (fxpane);
        View.setUpCar(fxauto);
         timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx = 0, dy = 0;
                if (up)
                    dy -= Data.getDELTA();
                if (down)
                    dy += Data.getDELTA();
                if (right)
                    dx += Data.getDELTA();
                if (left)
                    dx -= Data.getDELTA();
                Model.update(dx, dy);
                if (Data.isGameOver()) {
                    timer.stop();
                    View.endingPath(fxauto);
                }
            }
        };
        timer.start();
    }
}
