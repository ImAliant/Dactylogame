package com.dactylogame.controller;

import java.text.DecimalFormat;

import com.dactylogame.App;
import com.dactylogame.GameNormal;
import com.dactylogame.GameNormalConfiguration;
import com.dactylogame.ResultNormal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe correspondant au contrôleur de la fenêtre de résultat du jeu normal.
 * 
 * @author DIAMANT Alexandre
 */
public class ResultNormalSceneController {

    /**
     * Instance de la classe GameNormal.
     */
    private GameNormal game;
    /**
     * Instance de la classe ResultNormal.
     */
    private ResultNormal result;

    /**
     * Panneau principal.
     */
    @FXML private Pane pane;
    /**
     * Bouton qui permet de quitter le jeu.
     */
    @FXML private Button btnQuit;
    /**
     * Bouton qui permet de rejouer.
     */
    @FXML private Button btnReplay;
    /**
     * Bouton qui permet de retourner sur la fenêtre du menu.
     */
    @FXML private Button btnMenu;
    /**
     * Affiche le titre de la fenêtre.
     */
    @FXML private Label resultTitleLabel;
    /**
     * Affiche le MPM du joueur sur la fenêtre de résultat.
     */
    @FXML private Label MPMLabel;
    /**
     * Affiche la précision du joueur sur la fenêtre de résultat.
     */
    @FXML private Label precisionLabel;
    /**
     * Affiche l'ecart type du joueur sur la fenêtre de résultat.
     */
    @FXML private Label regularityLabel;

    /**
     * Méthode qui initialise la fenêtre de résultat du jeu normal.
     * 
     * @see GameNormal
     * @see ResultNormal
     * @see #MPMLabel
     * @see #precisionLabel
     * @see #regularityLabel
     * @see #btnQuit
     * @see #btnReplay
     */
    public void initialize() {
        game = GameNormal.getInstance();
        result = ResultNormal.getInstance();

        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);

        MPMLabel.setText("MPM : " + f.format(result.getMPM()));
        precisionLabel.setText("Precision : " + f.format(result.getPrecision()) + "%");
        regularityLabel.setText("Regularité : " + f.format(result.getRegularity()) + "s");

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            System.exit(0);
        });
        
        btnReplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                GameNormal.setReplay(true);
                game = GameNormal.newGame();
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
                ResultNormal.reset();
                GameNormal.reset();
                GameNormalConfiguration.reset();

                App.instance.start(new Stage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

}

