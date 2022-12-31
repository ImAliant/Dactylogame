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
 * Cette classe est le point d'entrée de l'application.
 * Elle permet de lancer l'application et de charger le fichier FXML de la fenêtre principale.
 * Elle est aussi utilisée pour accéder à l'instance de l'application.
 * 
 * @author DIAMANT Alexandre
 */
public class App extends Application {
    public static App instance = null;

    /**
     * Cette méthode est appelée par la méthode launch() de la classe Application.
     * Elle permet de charger le fichier FXML de la fenêtre principale.
     * 
     * @param stage La fenêtre principale de l'application.
     * 
     * @throws IOException Si le fichier FXML n'a pas pu être chargé.
     */
    @Override
    public void start(Stage stage) {
        instance = this;

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

    /**
     * Lance l'application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}