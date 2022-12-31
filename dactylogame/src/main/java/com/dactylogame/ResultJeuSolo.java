package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe correspondant à la fenêtre de résultat du mode de jeu "Solo (Jeu)".
 * Hérite de la classe abstraite Result.
 * 
 * @author DIAMANT Alexandre
 */
public final class ResultJeuSolo extends Result {
    /**
     * Instance de la classe ResultJeuSolo.
     */
    private static ResultJeuSolo instance = null;

    /**
     * Instance de la classe GameJeu.
     */
    private GameJeu game;

    /**
     * Correspond au nombre de mots correctement tapés.
     */
    private int wordsCorrectlyTyped;
    /**
     * Correspond au niveau atteint.
     */
    private int level;
    /**
     * Correspond au temps de jeu.
     */
    private int playTime;

    /**
     * <p>Constructeur privé de la classe ResultJeuSolo.</p>
     * 
     * <p>
     * 1) On récupère l'instance de la classe GameJeu.<br>
     * 2) On récupère le nombre de mots correctement tapés.<br>
     * 3) On récupère le niveau atteint.<br>
     * 4) On récupère le temps de jeu.<br>
     * </p>
     */
    private ResultJeuSolo() {
        this.game = GameJeu.getInstance();
        this.wordsCorrectlyTyped = game.getWordsCorrectlyTyped();
        this.level = game.getLevel();
        this.playTime = game.getPlayTime();
    }

    /**
     * Création d'une instance de la classe ResultJeuSolo ou récupération de l'instance déjà existante.
     * 
     * @return Instance de la classe ResultJeuSolo.
     */
    public synchronized static ResultJeuSolo getInstance() {
        if (instance == null) {
            System.out.println("Creating new ResultJeuSolo instance");
            instance = new ResultJeuSolo();
        }
        return instance;
    }

    /**
     * Réinitialisation de l'instance de la classe ResultJeuSolo.
     */
    public static void reset() {
        if (instance != null) {
            instance = null;
        }
        else {
            System.out.println("ResultJeuSolo instance is already null");
        }
    }

    @Override
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