package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Options {
    public void start(Stage window) {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/OptionScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);

            Scene scene = new Scene(root);

            window.setTitle("DactyloGame");
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        } catch(IOException e) {
            e.printStackTrace();
            return;
        }
    }
}
