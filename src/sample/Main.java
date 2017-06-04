package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Created by Iga Slotwinska on 15.05.2017.
 * Main method, starts Application and sets first scene - main menu
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Data.setActivStage(primaryStage);
        AnchorPane menu = FXMLLoader.load(Main.class.getResource("menu.fxml"));
        Scene sceneMenu = new Scene (menu);
        primaryStage.setScene(sceneMenu);
        primaryStage.setTitle("BRRRUM");
        primaryStage.show();
        View.setMusic();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
