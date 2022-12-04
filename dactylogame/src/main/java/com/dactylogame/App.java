package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/MainScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            
            stage.setTitle("Dactylogame");
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