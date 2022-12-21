package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Result {
    private static Result instance = null;

    private GameNormal game;

    private double MPM;
    private double precision;
    private double regularity;

    private Result() {
        this.game = GameNormal.getInstance();
        this.MPM = game.getMPM();
        this.precision = game.getPrecision();
        this.regularity = game.getRegularity();
    }

    public static Result getInstance() {
        if (instance == null) {
            instance = new Result();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }
    
    public void start(Stage window) throws IOException {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/ResultScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);

            Scene scene = new Scene(root);
            
            window.setTitle("DactyloGame");
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public double getMPM() {
        return MPM;
    }
    public double getPrecision() {
        return precision;
    }
    public double getRegularity() {
        return regularity;
    }
}
