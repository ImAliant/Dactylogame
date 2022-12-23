package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ResultJeuSolo {
    private static ResultJeuSolo instance = null;

    private GameJeu game;

    private int wordsCorrectlyTyped;
    private int level;
    private int playTime;

    private ResultJeuSolo() {
        this.game = GameJeu.getInstance();
        this.wordsCorrectlyTyped = game.getWordsCorrectlyTyped();
        this.level = game.getLevel();
        this.playTime = game.getPlayTime();
    }

    public static ResultJeuSolo getInstance() {
        if (instance == null) {
            System.out.println("Creating new ResultJeuSolo instance");
            instance = new ResultJeuSolo();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public void start(Stage window) throws IOException {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/ResultJeuSoloScene.fxml").toURI().toURL();
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

    public int getWordsCorrectlyTyped() {
        return wordsCorrectlyTyped;
    }
    public int getLevel() {
        return level;
    }
    public int getPlayTime() {
        return playTime;
    }
}