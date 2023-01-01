package com.dactylogame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Queue;

import com.dactylogame.controller.MainSceneController;
import com.thedeanda.lorem.LoremIpsum;

/**
 * Cette classe permet de définir les paramètres du mode de jeu "Solo (Normal").
 * 
 * @author DIAMANT Alexandre
 */
public final class GameNormalConfiguration implements GameConfiguration {
    /**
     * Instance de la classe GameNormalConfiguration.
     */
    private static GameNormalConfiguration instance = null;

    /**
     * Liste des mots à taper.
     */
    private ArrayList<String> words;
    /**
     * File des mots à taper.
     */
    private Queue<String> wordsQueue;
    
    /**
     * Corresponds au temps de jeu.
     * Cette valeur est définie en fonction du choix de l'utilisateur.
     */
    public static final int TIME = MainSceneController.TIME;
    /**
     * Corresponds au nombre de mots à générer dans la file.
     * Cette valeur est définie en fonction du choix de l'utilisateur.
     */
    public static final int NBWORDS = MainSceneController.NBWORDS;

    /**
     * <p>Constructeur privé de la classe GameNormalConfiguration.</p>
     * 
     * <p>
     * 1) On initialise une liste de chaine de caractères de {@link #NBWORDS} mots.<br>
     * 2) On initialise une file de mots de longueur 15.<br>
     * 3) On genere une liste de {@link #NBWORDS} mots aléatoires avec la librairie LoremIpsum qu'on ajoute à la liste.<br>
     * 4) On ajoute ensuite les mots à la file.<br>
     */
    private GameNormalConfiguration() {
        words = new ArrayList<String>(NBWORDS);
        wordsQueue = new ArrayDeque<String>(15);

        LoremIpsum lorem = LoremIpsum.getInstance();
        for (int i = 0; i < NBWORDS; i++) {
            words.add(lorem.getWords(1));
        }

        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words.get(i));
        }
    }

    /**
     * Créationnce d'une instance de la configuration du jeu (singleton) ou récupération de l'instance existante.
     * 
     * @return Instance de la classe GameNormalConfiguration
     */
    public synchronized static GameNormalConfiguration getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameNormalConfiguration instance");
            instance = new GameNormalConfiguration();
        }
        return instance;
    }

    /**
     * On obtient un clone de la liste de mots.
     * 
     * @return Une ArrayList de String correspondant au mot à taper.
     */
    @Override
    public ArrayList<String> getWords() {
        ArrayList<String> clone = new ArrayList<String>();
        clone.addAll(words);
        return clone;
    }

    /**
     * On obtient un clone de la queue de mots.
     * 
     * @return Une Queue de String correspondant au mot à taper.
     */
    @Override
    public Queue<String> getWordsQueue() {
        Queue<String> clone = new ArrayDeque<>();
        clone.addAll(wordsQueue);
        return clone;
    }

    public int getTime() {
        return TIME;
    }

    /**
     * Reinitalise l'instance de la classe.
     */
    public static void reset() {
        instance = null;
    }

    /**
     * Affiche les paramètres de la configuration du jeu.
     */
    @Override
    public String toString() {
        return "GameConfiguration [words= " + printWords() + "; wordsQueue=" + wordsQueue + "]";
    }

    // PARTIE POUR LES TESTS UNITAIRES
    /**
     * Constructeur privé a utilisé uniquement pour les tests de la classe GameNormalConfiguration.
     * 
     * @param time Correspond au temps de jeu.
     * @param nbWords Correspond au nombre de mots à générer dans la file.
     * @param words Correspond à la liste des mots à taper.
     * @param wordsQueue Correspond à la file des mots à taper.
     */
    private GameNormalConfiguration(ArrayList<String> mots) {
        if (mots == null) {
            throw new IllegalArgumentException("Les paramètres ne peuvent pas être null");
        }

        words = new ArrayList<>();
        wordsQueue = new ArrayDeque<>();

        for (int i = 0; i < mots.size(); i++) {
            words.add(mots.get(i));
        }
        System.out.println("words = " + printWords(words));
        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words.get(i));
        }
    }

    private String printWords(ArrayList<String> words) {
        String str = "";
        for (String word : words) {
            str += word + " ";
        }
        return str;
    }

    public synchronized static GameNormalConfiguration getInstanceTest(ArrayList<String> words) {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameNormalConfiguration instance for tests");
            instance = new GameNormalConfiguration(words);
        }
        return instance;
    }

    
}
