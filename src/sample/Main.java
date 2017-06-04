package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        Data.setActivStage(primaryStage);
        AnchorPane menu = FXMLLoader.load(Main.class.getResource("menu.fxml"));
        Scene sceneMenu = new Scene (menu);
        primaryStage.setScene(sceneMenu);
        primaryStage.setTitle("BRRRUM");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
