package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import com.dactylogame.serveur_client.Server;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    public static App instance = null;

    private static boolean serverStarted = false;

    @Override
    public void start(Stage stage) {
        instance = new App();

        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/MainScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            
            stage.setTitle("Dactylogame");
            /*try {
                stage.getIcons().add(new Image(App.class.getResourceAsStream("dactylogame/src/main/java/com/dactylogame/resources/icon.jpg")));
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void main(String[] args) {
        launch();
    }

}