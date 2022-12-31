package com.dactylogame.controller;

import java.io.IOException;
import java.lang.IllegalStateException;

import com.dactylogame.Game;
import com.dactylogame.GameJeu;
import com.dactylogame.GameNormal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * Classe correspondant au contrôleur de la fenêtre principale.
 * 
 * @author DIAMANT Alexandre
 */
public class MainSceneController {

    /**
     * Correspond au temps de jeu choisit par l'utilisateur.
     */
    public static int TIME;
    /**
     * Correspond au nombre de mots pouvant être tapés choisit par l'utilisateur.
     */
    public static int NBWORDS;
    /**
     * Correspond au nombre de points de vie choisit par l'utilisateur.
     */
    public static int PV;
    /**
     * Correspond la longueur de la queue choisit par l'utilisateur.
     */
    public static int QUEUELENGTH;

    /**
     * Instance de la classe Game.
     */
    private Game game;

    /**
     * Bouton qui permet de lancer le jeu.
     */
    @FXML private Button btnJouer;
    /**
     * Bouton qui permet de quitter le jeu.
     */
    @FXML private Button btnQuitter;
    /**
     * Label qui affiche le titre du jeu.
     */
    @FXML private Label title;

    /**
     * RadioButton qui permet de choisir le mode de jeu "Solo (Normal)".
     */ 
    @FXML private RadioButton normalRadioButton;
    /**
     * RadioButton qui permet de choisir le mode de jeu "Solo (Jeu)".
     */
    @FXML private RadioButton jeuSoloRadioButton;
    /**
     * RadioButton qui permet de choisir le mode de jeu "Multi (Jeu)".
     * Ce mode de jeu n'est pas encore implémenté.
     */
    @FXML private RadioButton jeuMultiRadioButton;

    /**
     * Pane qui contient les options du mode de jeu "Solo (Normal)".
     */
    @FXML private Pane optionNormalPane;
    /**
     * Slider qui permet de choisir le nombre de mots pouvant être tapés.
     */
    @FXML private Slider nbwordsSlider;
    /**
     * Slider qui permet de choisir le temps de jeu.
     */
    @FXML private Slider timeSlider; 

    /**
     * Pane qui contient les options du mode de jeu "Solo (Jeu)".
     */
    @FXML private Pane optionJeuPane;
    /**
     * Slider qui permet de choisir le nombre de points de vie.
     */
    @FXML private Slider pvSlider;
    /**
     * Slider qui permet de choisir la longueur de la queue.
     */
    @FXML private Slider queueLengthSlider;

    /**
     * Pane qui contient les options du mode de jeu "Multi (Jeu)".
     * Ce mode de jeu n'est pas encore implémenté.
     */
    @FXML private Pane optionMultiPane;
    /**
     * Label qui affiche "Vitesse" dans la fenêtre principale.
     * Ce label s'affiche uniquement lorsque le mode de jeu "Multi (Jeu)" est sélectionné.
     */
    @FXML private Label vitesseLabel;
    /**
     * Slider qui permet de choisir la vitesse d'arrivée des mots dans la file.
     * Ce label s'affiche uniquement lorsque le mode de jeu "Multi (Jeu)" est sélectionné.
     */
    @FXML private Slider vitesseSlider; 
    /**
     * Bouton qui permet de rejoindre une partie.
     * Ce bouton s'affiche uniquement lorsque le mode de jeu "Multi (Jeu)" est sélectionné.
     */
    @FXML private Button btnJoin;

    /**
     * ToggleGroup qui permet de gérer les RadioButton.
     */
    private final ToggleGroup group = new ToggleGroup();

    /**
     * Méthode qui initialise la fenêtre principale.
     * 
     * @throws Exception Exception lancée si une erreur survient lors de l'initialisation de la fenêtre principale.
     */
    public void initialize() throws Exception{
        normalRadioButton.setToggleGroup(group);
        jeuSoloRadioButton.setToggleGroup(group);
        jeuMultiRadioButton.setToggleGroup(group);

        normalRadioButton.setSelected(true);
        optionNormalPane.setVisible(true);
        optionJeuPane.setVisible(false);
        optionMultiPane.setVisible(false);
        btnJoin.setVisible(false);

        normalRadioButton.setOnAction(e -> {
            optionNormalPane.setVisible(true);
            optionJeuPane.setVisible(false);
            optionMultiPane.setVisible(false);
            btnJoin.setVisible(false);
            btnJouer.setText("Jouer");
        });

        jeuSoloRadioButton.setOnAction(e -> {
            optionNormalPane.setVisible(false);
            optionJeuPane.setVisible(true);
            optionMultiPane.setVisible(false);
            btnJoin.setVisible(false);
            btnJouer.setText("Jouer");
        });

        jeuMultiRadioButton.setOnAction(e -> {
            optionNormalPane.setVisible(false);
            optionJeuPane.setVisible(true);
            optionMultiPane.setVisible(true);
            btnJoin.setVisible(true);
            btnJouer.setText("Jouer / Heberger");
        });
    }

    /**
     * Appartient au bouton "Jouer".
     * 
     * Lance le mode de jeu sélectionné.
     * 
     * @param event Evènement qui se produit lors du clic sur le bouton "Jouer".
     * @throws IOException Exception lancée si une erreur survient lors du lancement du mode de jeu.
     */
    @FXML
    private void btnJouerClicked(MouseEvent event) throws IOException {
        if(normalRadioButton.isSelected()) {
            NBWORDS = (int) nbwordsSlider.getValue();
            TIME = (int) timeSlider.getValue();
            game = GameNormal.getInstance();
            game.start(new Stage());
            btnJouer.getScene().getWindow().hide();
        }
        else if(jeuSoloRadioButton.isSelected()) {
            PV = (int) pvSlider.getValue();
            QUEUELENGTH = (int) queueLengthSlider.getValue();
            game = GameJeu.getInstance();
            game.start(new Stage());
            btnJouer.getScene().getWindow().hide();
        }
        else if(jeuMultiRadioButton.isSelected()) {
            System.out.println("Ce mode n'est pas encore disponible");
        }
    }

    /**
     * Appartient au bouton "Rejoindre".
     * 
     * Rejoint une partie en mode "Multi (Jeu)".
     * 
     * <p>Ce mode de jeu n'est pas encore implémenté.</p>
     * 
     * @param event Evènement qui se produit lors du clic sur le bouton "Rejoindre".
     * @throws IOException Exception lancée si une erreur survient lors de la tentative de connexion à une partie.
     */
    @FXML 
    private void btnJoinClicked(MouseEvent event) throws IOException {
        if (normalRadioButton.isSelected() || jeuSoloRadioButton.isSelected())
            throw new IllegalStateException("Impossible de rejoindre une partie en mode normal ou solo");

        System.out.println("Mode multi non disponible");
    }

    /**
     * Appartient au bouton "Quitter".
     * 
     * Quitte le jeu.
     * 
     * @param event Evènement qui se produit lors du clic sur le bouton "Quitter".
     */
    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }
}

