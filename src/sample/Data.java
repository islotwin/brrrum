package sample;

import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Vector;

/**
 * Created by Iga Slotwinska on 21.05.2017.
 * Contains data and setter/getter methods
 */
public class Data {
    private static int gateHeight = 48;
    private static int gateWidth = 125;

    private static int highscore = 0;
    private static int score =0;
    private static String prev= null;

    private static Effect effect = null;
    private static double      DELTA = 4;

    private static ImageView statAuto = null;
    private static Stage activStage = null;
    private static Image autoColor = null;
    private static AnchorPane rectanglePane = null;
    private static AnchorPane anchorPane = null;
    private static Image gateImage = View.LoadGate("gate");
    private static Image blockImage = View.LoadGate("blockage");

    private static double PROBA = 1;
    private static double probability = PROBA;
    private static Vector<javafx.scene.image.ImageView> gates = new Vector<>();
    private static Vector<javafx.scene.image.ImageView> blockages = new Vector<>();

    private static boolean gameOver = false;

    static public String getPrev ()
    {
        return prev;
    }
    static  void setPrev (String temp)
    {
        prev = temp;
    }

    static public int getHighscore ()
    {
        return highscore;
    }
    static  void setHighscore (int temp)
    {
        highscore = temp;
    }

    /**
     * Method used to prepare for new game
     */
    static public void clearData ()
    {
      gates.clear();
      blockages.clear();
      gameOver = false;
      PROBA = 1;
      DELTA = 4;
      statAuto=null;
      rectanglePane=null;
      anchorPane = null;
      score =0;
    }

    /**
     * @return vector containing visible gates
     */
    static public Vector<ImageView> getGates ()
    {
        return gates;
    }

    static  void setAnchorPane (AnchorPane temp)
    {
        anchorPane =temp;
    }

    /**
     * add new Gate
     * @param random - offset of X
     */
    static  void addGates ( int random)
    {
        gates.add(View.setGate(gateImage, random));
        anchorPane.getChildren().add(gates.lastElement());
        gates.lastElement().toFront();
        statAuto.toFront();
    }

    /**
     * remove first Gate after passing
     */
    static  void removeGates ()
    {
        anchorPane.getChildren().remove(gates.firstElement());
        gates.remove(0);
    }

    /**
     * add new Blockage
     * @param random - offset of X
     */
    static  void addBlockages (int random )
    {
        blockages.add(View.setGate(blockImage, random));
        anchorPane.getChildren().add(blockages.lastElement());
        blockages.lastElement().toFront();
        statAuto.toFront();
    }

    /**
     * @return vector containing visible blockages
     */
    static public Vector<ImageView> getBlockages ()
    {
        return blockages;
    }

    /**
     * remove Blockage after moving accross the screen
     */
    static  void removeBlockages ()
    {
        anchorPane.getChildren().remove(blockages.firstElement());
        blockages.remove(0);
    }

    static public double getPROBA()
    {
        return PROBA;
    }
    static void setPROBA (double temp)
    {
        PROBA = temp;
    }

    static public double getProbability()
    {
        return probability;
    }
    static void setProbability (double temp)
    {
        probability = temp;
    }

    static public double getDELTA ()
    {
        return DELTA;
    }
    static  void setDELTA (double value)
    {
        DELTA = value;
    }

    static public Integer getScore ()
    {
        return score;
    }
    static  void addScore (Integer temp)
    {
        score +=temp;
        ( (Text) anchorPane.getChildren().get(3)).setText(Integer.toString(score));
    }

    static public boolean isGameOver ()
    {
        return gameOver;
    }
    static  void setGameOver (boolean temp)
    {
        gameOver = temp;
    }

    static public Image getGateImage()
    {
        return gateImage;
    }
    static public Image getBlockImage()
    {
        return blockImage;
    }

    static public Image getAutoColor()
    {
        return autoColor;
    }
    static  void setAutoColor (Image image)
    {
        autoColor= image;
    }

    static public Stage getActivStage()
    {
        return activStage;
    }
    static public void setActivStage (Stage stage)
    {
        activStage = stage;
    }

    static public AnchorPane getRectanglePane()
    {
        return rectanglePane;
    }
    static  void setRectanglePane (AnchorPane temp)
    {
        rectanglePane = temp;
    }


    static public ImageView getStatAuto()
    {
        return statAuto;
    }

    static public Effect getEffect ()
    {
        return effect;
    }
    static  void setEffect (Effect ef)
    {
        effect = ef;
    }

    static  void setStatAuto (ImageView imageView)
    {
        statAuto = imageView;
    }

    static public int getGateHeight ()
    {
        return gateHeight;
    }
    static public int getGateWidth()
    {
    return gateWidth;
    }

    static public double getAutoX ()
    {
        return statAuto.getLayoutX();
    }

    static public double getAutoY ()
    {
        return statAuto.getLayoutY();
    }

    static public double getAutoWidth ()
    {
        return statAuto.getFitWidth();
    }

    static public double getAutoHeight ()
    {
        return statAuto.getFitHeight();
    }

    static void setAutoX (double temp)
    {
        statAuto.setLayoutX(temp);
    }

    static void setAutoY (double temp)
    {
        statAuto.setLayoutY(temp);
    }

    static double getFirstGateY ()
    {
        if(!gates.isEmpty())
            return gates.firstElement().getLayoutY();
        return -1;
    }
    static double getFirstGateX ()
    {
        if(!gates.isEmpty())
            return gates.firstElement().getLayoutX();
        return -1;
    }

    static double getFirstBlockageY ()
    {
        if(!blockages.isEmpty())
            return blockages.firstElement().getLayoutY();
        return -1;
    }

    /**
     *
     * @return - Y coordinate of the most recent element - Blockage or Gate
     */
    static double getLastGate ()
    {
        if(!gates.isEmpty()) {
            if(!blockages.isEmpty()) {
                return Math.min(gates.lastElement().getLayoutY(), blockages.lastElement().getLayoutY());
            }
            return gates.lastElement().getLayoutY();
        }
        if(!blockages.isEmpty())
            return blockages.lastElement().getLayoutY();
        return -1;
    }

    static double getStageHeight ()
    {
        return activStage.getHeight();
    }

    static double getStageWidth ()
    {
        return activStage.getWidth();
    }

}
