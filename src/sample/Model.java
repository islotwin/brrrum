package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by Iga Slotwinska on 15.05.2017.
 * Checks collisions between nodes, generates new nodes.
 * Moves visible objects - the car, gates, blockages, strips on the road
 */
public class Model {
    /**
     * Method generating Blockages and Gates
     * @param coefficent - probability of generating next object
     * @return - true if object was generated, false - not
     */
     private static boolean generateGate(double coefficent) {
        Image image;
        int random = ThreadLocalRandom.current().nextInt(0, 1000);
        if (random <= coefficent) {
            random = ThreadLocalRandom.current().nextInt(0, 10);
            if (random >= 2)
                image = Data.getGateImage();
            else
                image = Data.getBlockImage();
            random = ThreadLocalRandom.current().nextInt(0, 2);
            if (image == Data.getGateImage())
                Data.addGates(random);
            else
                Data.addBlockages(random);
            return true;
        }
        return false;
    }

    /**
     * Checks whether car collides with the first gate. Calls gameOver, when gate was missed
     */
     private static boolean checkCollision() {
        if (!Data.getGates().isEmpty()) {
            ImageView first = (Data.getGates()).firstElement();
            if (Data.getFirstGateY() + Data.getGateHeight() >= Data.getAutoY()) {
                if (first.getLayoutX() + Data.getGateWidth() >= Data.getAutoWidth() + Data.getAutoX()) {
                    if(first.getLayoutX() <= Data.getAutoX()) {
                        return true;
                    }
                }
                gameOver();
            }
        }
        return false;
    }

    /**
     * Checks collisions with all blockages. Calls gameOver, after driving at blockage
     */
     private static void checkBlockages() {
        double cy = Data.getAutoY();
        double cx = Data.getAutoX();
        double ch = Data.getAutoHeight();
        double cw = Data.getAutoWidth();
        for (ImageView blockage : Data.getBlockages()) {
            double by = blockage.getLayoutY();
            double bx = blockage.getLayoutX();
            if ((cy <= by + Data.getGateHeight()) && (cy + ch >= by)) {
                if ((cx + cw >= bx) && (cx <= bx + Data.getGateWidth()))
                    gameOver();
            }
        }
    }

    /**
     * Changes the state of the flag gameOver. If new highscore is achieved, writes it into file.
     */
     private static void gameOver() {
        if (Data.getHighscore() < Data.getScore()) {
            Data.setHighscore(Data.getScore());
            try {
                Writer writer = new FileWriter("./score.txt");
                writer.write(String.valueOf(Data.getHighscore()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Data.setGameOver();
    }


    /**
     * While car is driving through the gate, it cant escape the gate
     * @param dx - new X coordinate of the car
     */
     private static void setBoundaries(double dx) {
        if(dx < Data.getFirstGateX())
            Data.setAutoX(Data.getFirstGateX());
        else if(dx + Data.getAutoWidth() > Data.getFirstGateX() + Data.getGateWidth())
            Data.setAutoX(Data.getFirstGateX() + Data.getGateWidth() - Data.getAutoWidth());
        else
            Data.setAutoX(dx);
    }

    /**
     * Sets new coordinates of the car.
     * @param dx - movement in the X coordinate
     * @param dy - movement in the Y coordinate
     */
     private static void moveCar(double dx, double dy) {
        dy+=Data.getAutoY();
        dx+=Data.getAutoX();
        checkBlockages();
        if (checkCollision())
            setBoundaries(dx);
        else if (dx < 0)
            Data.setAutoX(0);
        else if (dx > (Data.getStageWidth()-Data.getAutoWidth()))
            Data.setAutoX(Data.getStageWidth()-Data.getAutoWidth());
        else
            Data.setAutoX(dx);
        if (dy < 0)
            Data.setAutoY(0);
        else if (dy > (Data.getStageHeight() - Data.getAutoHeight()))
            Data.setAutoY(Data.getStageHeight() - Data.getAutoHeight());
        else
            Data.setAutoY(dy);
    }

    /**
     * drop gates
     */
    private static void moveGates() {
        for (ImageView iv : Data.getGates()) {
            iv.setLayoutY(Data.getDELTA() + iv.getLayoutY());
        }
        if (Data.getFirstGateY() >= Data.getAutoY() + Data.getAutoHeight()) {
            Data.addScore();
            Data.removeGates();
        }
    }

     private static void moveBlockages() {
        for (ImageView iv : Data.getBlockages()) {
                iv.setLayoutY(Data.getDELTA() + iv.getLayoutY());
        }
        if (!Data.getBlockages().isEmpty()) {
            if (Data.getFirstBlockageY() >= (Data.getStageHeight() + Data.getGateHeight()))
                Data.removeBlockages();
        }
    }

    /**
     * Changes the probality of generating new elements. Increments the velocity and probability with each call
     * @param dx - movement of the car in the X coordinate
     * @param dy - movement of the car in the Y coordinate
     */
     static void update(double dx, double dy) {
        moveCar(dx, dy);
        if(!Data.getBlockages().isEmpty() || !Data.getGates().isEmpty()) {
            if (Data.getLastGate() >= (Data.getAutoHeight()+ Data.getGateHeight())) {
                if (generateGate(Data.getProbability()))
                    Data.setProbability(Data.getPROBA());
                else
                    Data.setProbability(Data.getProbability() * 1.1);
            }
        }
        else {
            if(generateGate(Data.getProbability()))
                Data.setProbability(Data.getPROBA());
            else
                Data.setProbability(Data.getProbability() * 1.1);
        }
        moveBlockages();
        moveGates();
        View.moveRectangles();
        Data.setDELTA(Data.getDELTA() + 0.002);
        Data.setPROBA(Data.getPROBA() + 0.002);
    }
}
