package com.dactylogame.controller;

import com.dactylogame.App;
import com.dactylogame.GameJeu;
import com.dactylogame.GameJeuConfiguration;
import com.dactylogame.ResultJeuSolo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe correspondant au contrôleur de la fenêtre de résultat du jeu solo.
 * 
 * @author DIAMANT Alexandre
 */
public class ResultJeuSoloSceneController {
    
    /**
     * Instance de la classe GameJeu.
     */
    private GameJeu game;
    /**
     * Instance de la classe ResultJeuSolo.
     */
    private ResultJeuSolo result;

    /**
     * Panneau principal.
     */
    @FXML private Pane pane;
    /**
     * Bouton qui permet de quitter le jeu.
     */
    @FXML private Button btnQuit;
    /**
     * Bouton qui permet de retourner sur la fenêtre du menu.
     */
    @FXML private Button btnMenu;
    /**
     * Bouton qui permet de rejouer.
     */
    @FXML private Button btnReplay;
    /**
     * Label qui affiche le niveau atteint.
     */
    @FXML private Label LevelLabel;
    /**
     * Label qui affiche le temps de jeu.
     */
    @FXML private Label playTimeLabel;
    /**
     * Label qui affiche le titre du résultat.
     */
    @FXML private Label resultTitleLabel;
    /**
     * Label qui affiche le nombre de mots correctement tapés.
     */
    @FXML private Label wordsLabel;

    /**
     * Méthode qui permet d'initialiser les éléments de la fenêtre.
     * 
     * @see GameJeu
     * @see ResultJeuSolo
     * @see #LevelLabel
     * @see #playTimeLabel
     * @see #wordsLabel
     * @see #btnQuit
     * @see #btnReplay
     * @see #btnMenu
     */
    public void initialize() {
        game = GameJeu.getInstance();
        result = ResultJeuSolo.getInstance();

        LevelLabel.setText("Niveau atteint: " + result.getLevel());
        playTimeLabel.setText("Temps de jeu: " + secondsToMinutes(result.getPlayTime()));
        wordsLabel.setText("Mots correctement tapés: " + result.getWordsCorrectlyTyped());

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            Platform.exit();
        });

        btnReplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                game = GameJeu.newGame();
                game.start(new Stage());
                btnReplay.getScene().getWindow().hide();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        btnMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                // Ce bouton permet de retourner sur la fenetre du menu.
                // Si l'utilisateur clique sur ce bouton cela réinitialise toutes les instances ouvertes précédement.
                ResultJeuSolo.reset();
                GameJeu.reset();
                GameJeuConfiguration.reset();

                App.instance.start(new Stage());
                btnMenu.getScene().getWindow().hide();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    /**
     * Méthode qui permet de convertir les secondes en minutes.
     * 
     * @param seconds Nombre de secondes.
     * @return Le temps en minutes.
     */
    public String secondsToMinutes(int seconds) {
        int minutes = seconds / 60;
        int restSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, restSeconds);
    }
}
