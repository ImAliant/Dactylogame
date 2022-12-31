package com.dactylogame;

import java.util.ArrayList;
import java.util.Queue;

/**
 * Cette classe est la classe mère de GameNormalConfiguration et GameJeuConfiguration.
 * Elle permet de définir les méthodes communes à ces deux classes.
 * 
 * @author DIAMANT Alexandre
 */
public sealed abstract class GameConfiguration permits GameNormalConfiguration, GameJeuConfiguration {
    /**
     * Affiche la liste de mots.
     * 
     * @return Une concaténation des mots de la liste de mots.
     */
    public String printWords() {
        String wordsString = "";
        for (String word : getWords()) {
            wordsString += word + " ";
        }
        return wordsString;
    }
    /**
     * On obtient la liste de mots.
     * 
     * @return Une ArrayList contenant les mots.
     */
    public abstract ArrayList<String> getWords();
    /**
     * On obtient la queue de mots.
     * 
     * @return Une Queue contenant les mots.
     */
    public abstract Queue<String> getWordsQueue();
}