package com.dactylogame;

import java.io.IOException;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * Cette classe est la classe mère de GameNormal et GameJeu.
 * Elle permet de définir les méthodes communes à ces deux classes.
 * 
 * @author DIAMANT Alexandre
 */
public sealed abstract class Game implements Initializable permits GameNormal, GameJeu {

    /**
     * Ouvre la fenêtre du mode jeu selectionné par l'utilisateur.
     * 
     * @param stage La fenêtre principale de l'application.
     * @throws IOException Si le fichier FXML n'a pas pu être chargé.
     */
    public void start(Stage stage) throws IOException {}
    /**
     * Teste si les lettres entrées par l'utilisateur sont correctes.
     * 
     * @return true si les lettres entrées par l'utilisateur sont correctes, false sinon.
     */
    public abstract boolean checkError();
    /**
     * Ouvre la fenêtre de résultat.
     */
    public abstract void openResultScene();
    /**
     * Affiche la queue de la liste de mots.
     * 
     * @return Une concaténation des mots de la queue de la liste de mots.
     */
    public abstract String printQueue();
    /**
     * Calcule les résultats du jeu en fonction du mode de jeu.
     */
    public abstract void resultats();
    /**
     * Met a jour le label du temps restant ou écoulé.
     */
    public abstract void updateTimeLabel();
    /**
     * Met a jour le mot courant et le label du mot courant à taper.
     */
    public abstract void updateWord();
    /**
     * Remet par défaut la liste correspondant a la position des caractères incorrects tapés par l'utilisateur.
     */
    public abstract void listPosErrorToDefault();
    /**
     * Termine la boucle de jeu.
     */
    public abstract void endGame();
}
