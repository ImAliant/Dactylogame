package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe correspondant à la fenêtre de résultat du mode de jeu "Solo (Normal)".
 * Hérite de la classe abstraite Result.
 * 
 * @author DIAMANT Alexandre
 */
public final class ResultNormal implements Result {
    /**
     * Instance de la classe ResultNormal.
     */
    private static ResultNormal instance = null;

    /**
     * Instance de la classe GameNormal.
     */
    private GameNormal game;

    /**
     * Correspond au mots par minute (MPM).
     */
    private double MPM;
    /**
     * Correspond à la précision.
     */
    private double precision;
    /**
     * Correspond à l'écart type.
     */
    private double regularity;

    /**
     * <p>Constructeur privé de la classe ResultNormal.</p>
     * 
     * <p>
     * 1) On récupère l'instance de la classe GameNormal.<br>
     * 2) On récupère le MPM.<br>
     * 3) On récupère la précision.<br>
     * 4) On récupère l'écart type.<br>
     * </p>
     */
    private ResultNormal() {
        this.game = GameNormal.getInstance();
        this.MPM = game.getMPM();
        this.precision = game.getPrecision();
        this.regularity = game.getRegularity();
    }

    /**
     * Création d'une instance de la classe ResultNormal ou récupération de l'instance déjà existante.
     * 
     * @return Instance de la classe ResultNormal.
     */
    public static ResultNormal getInstance() {
        if (instance == null) {
            System.out.println("Creating new ResultNormal instance");
            instance = new ResultNormal();
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
            URL url = new File("src/main/java/com/dactylogame/fxml/ResultNormalScene.fxml").toURI().toURL();
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