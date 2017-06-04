package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by iga on 15.05.2017.
 */
public class Model {


     static double getFirstGate ()
    {
        if(!Data.getGates().isEmpty())
       return Data.getGates().firstElement().getLayoutY();
        return -1;
    }

     static double getLastGate ()
    {
        if(!Data.getGates().isEmpty()) {
            if(!Data.getBlockages().isEmpty()) {
                return Math.min(Data.getGates().lastElement().getLayoutY(), Data.getBlockages().lastElement().getLayoutY());
            }
            return Data.getGates().lastElement().getLayoutY();
        }
        if(!Data.getBlockages().isEmpty())
            return Data.getBlockages().lastElement().getLayoutY();
        return -1;
    }

     static boolean generateGate (double coefficent)
    {
        Image image;
        int random = ThreadLocalRandom.current().nextInt(0, 1000);
        if (random<=coefficent)
        {
            random = ThreadLocalRandom.current().nextInt(0, 10);
            if(random>=2)
                image = Data.getGateImage();
            else
                image = Data.getBlockImage();
            random = ThreadLocalRandom.current().nextInt(0, 2);
            if(image==Data.getGateImage())
                Data.addGates( random);
            else
                Data.addBlockages(random);
            return true;
        }
        return false;
    }

     static boolean checkCollision()
    {
        if(!Data.getGates().isEmpty()) {
            ImageView first =  (Data.getGates()).firstElement();

            if (getFirstGate() + Data.getGateHeight() >= Data.getAutoY()) {
                if (first.getLayoutX() + Data.getGateWidth() >= Data.getAutoWidth() + Data.getAutoX()) {
                    if(first.getLayoutX()<=Data.getAutoX()) {
                        return true;
                    }
                }
                gameOver();
            }
        }
        return false;
    }

     static void checkBlockages ()
    {
        double cy = Data.getAutoY();
        double cx = Data.getAutoX();
        double ch = Data.getAutoHeight();
        double cw = Data.getAutoWidth();

        for (ImageView blockage: Data.getBlockages()
             ) {
            double by = blockage.getLayoutY();
            double bx = blockage.getLayoutX();
            if ((cy <= by + Data.getGateHeight()) && (cy + ch >= by))
            {
                if (( cx + cw >= bx) && (cx <= bx + Data.getGateWidth()))
                    gameOver();
            }

        }
    }

     static void gameOver()
    {
        if (Data.getHighscore()<Data.getScore())
        {
            Data.setHighscore(Data.getScore());
            try {
                Writer writer = new FileWriter("./score.txt");
                writer.write(String.valueOf(Data.getHighscore()));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Data.setGameOver(true);
    }


     static void setBoundaries (double dx)
    {
        ImageView first = Data.getGates().firstElement(); /*    tak jak z autem !!! TODO*/
        if(dx<first.getLayoutX())
            Data.setAutoX(first.getLayoutX());
        else if(dx+Data.getAutoWidth()>first.getLayoutX()+first.getFitWidth())
            Data.setAutoX(first.getLayoutX()+Data.getGateWidth()-Data.getAutoWidth());
        else
            Data.setAutoX(dx);
    }


     static void moveCar( double dx, double dy) /*    potrzebuje imageview tu???*/
    {
        dy+=Data.getAutoY();
        dx+=Data.getAutoX();
        checkBlockages();
        if(checkCollision())
            setBoundaries(dx);
        else if (dx<0 )
            Data.setAutoX(0);
        else if (dx>(Data.getActivStage().getWidth()-Data.getAutoWidth()))
            Data.setAutoX(Data.getActivStage().getWidth()-Data.getAutoWidth());
        else
            Data.setAutoX(dx);

        if (dy<0 )
            Data.setAutoY(0);
        else if (dy>(Data.getActivStage().getHeight()-Data.getAutoHeight()))
            Data.setAutoY(Data.getActivStage().getHeight()-Data.getAutoHeight());
        else
            Data.setAutoY(dy);
    }



     static void moveGates (double dy)
    {
        for (ImageView iv:  Data.getGates()
             ) {
            double moveY = dy+iv.getLayoutY();
            iv.setLayoutY(moveY);
        }
        if (getFirstGate()>=Data.getAutoY()+Data.getAutoHeight()) {
            Data.addScore(1);
            Data.removeGates();
        }

    }

     static void moveBlockages (double dy) {
        for (ImageView iv : Data.getBlockages()
                ) {
            double moveY = dy + iv.getLayoutY();
                iv.setLayoutY(moveY);
        }
        if (!Data.getBlockages().isEmpty()) {
            if (Data.getBlockages().firstElement().getLayoutY() >= (Data.getActivStage().getHeight()+ Data.getGateHeight())) {
                Data.removeBlockages();
            }
        }
    }

     static void moveRectangles (double dy) {
        for (Node iv : Data.getRectanglePane().getChildren() // ( (AnchorPane)Data.getActivStage().getScene().getRoot().getChildrenUnmodifiable().get(1)).getChildren()
                ) {
            double moveY = dy + iv.getLayoutY();

            if (moveY >=Data.getRectanglePane().getPrefHeight())
                iv.setLayoutY(0);
            else
                iv.setLayoutY(moveY);
        }
    }


     static void update(double dx, double dy)
    {
        Data.setDELTA(Data.getDELTA()+0.002);

        moveCar( dx, dy);
        if(!Data.getBlockages().isEmpty() || !Data.getGates().isEmpty())
        {
            if ( getLastGate()>=(Data.getAutoHeight()+ Data.getGateHeight()))
            {
                if(generateGate(Data.getProbability())==true)
                    Data.setProbability(Data.getPROBA ());
                else
                    Data.setProbability(Data.getProbability ()*1.1);
            }
        }
        else
        {
            if(generateGate(Data.getProbability())==true)
                Data.setProbability(Data.getPROBA ());
            else
                Data.setProbability(Data.getProbability ()*1.1);

        }
        moveBlockages(Data.getDELTA());
        moveGates (Data.getDELTA());
        moveRectangles (Data.getDELTA()); /*   wez to napraw   */
        Data.setPROBA(Data.getPROBA() +0.002);
    }
}
