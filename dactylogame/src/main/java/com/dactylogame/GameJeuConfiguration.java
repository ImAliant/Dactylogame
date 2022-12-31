package com.dactylogame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Queue;

import com.dactylogame.controller.MainSceneController;
import com.thedeanda.lorem.LoremIpsum;

/**
 * Cette classe permet de définir les paramètres du mode de jeu "Solo (Jeu").
 * 
 * @author DIAMANT Alexandre
 */
public final class GameJeuConfiguration extends GameConfiguration {
    /**
     * Instance de la classe GameJeuConfiguration.
     */
    private static GameJeuConfiguration instance = null;

    /**
     * Liste des mots à taper.
     */
    private ArrayList<String> words;
    /**
     * Liste de 0 et 1 pour savoir si le mot est un bonus ou non.
     */
    private ArrayList<Integer> bonusWords;

    /**
     * Queue des mots à taper.
     */
    private Queue<String> wordsQueue;

    /**
     * Corresponds à la longueur de la queue de mots.
     * Cette valeur est définie en fonction du choix de l'utilisateur.
     */
    public static final int QUEUE_LENGTH = MainSceneController.QUEUELENGTH;
    /**
     * Corresponds au nombre de points de vie.
     * Cette valeur est définie en fonction du choix de l'utilisateur.
     */
    public static final int PV = MainSceneController.PV;

    /**
     * <p>Constructeur privé de la classe GameJeuConfiguration.</p>
     * 
     * <p>On crée une liste de chaine de caractères de 1000 mots (nombre arbitraire).<br>
     * 1) On crée une liste d'entier de 1000 entiers bonus (nombre arbitraire). Si l'entier est égal à 1, alors le mot est un bonus.<br>
     * 2) On crée une queue de mots de longueur {@link #QUEUE_LENGTH}.<br>
     * 3) Ensuite on genere une liste de 1000 mots aléatoires avec la librairie LoremIpsum qu'on ajoute à la queue.<br>
     * 4) On ajoute ensuite les bonus à la liste de bonus. Les mots on une probabilité de 1/5 d'être un bonus.<br>
     * 5) Et enfin on mélange la liste de bonus.</p>
     */
    private GameJeuConfiguration() {
        words = new ArrayList<String>(10000);
        bonusWords = new ArrayList<Integer>(10000);
        wordsQueue = new ArrayDeque<String>(QUEUE_LENGTH);

        LoremIpsum lorem = LoremIpsum.getInstance();
        for (int i = 0; i < 1000; i++) {
            words.add(lorem.getWords(1));
        }
        wordsQueue.add(words.get(0));

        for (int i = 0; i < 1000; i++) {
            if (i % 5 == 0) {
                bonusWords.add(1);
            } else {
                bonusWords.add(0);
            }
        }
        Collections.shuffle(bonusWords);
    }

    /**
     * Création d'une instance de la configuration du jeu (singleton) ou récupération de l'instance existante.
     * 
     * @return Instance de la configuration du jeu.
     */
    public synchronized static GameJeuConfiguration getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameJeuConfiguration instance");
            instance = new GameJeuConfiguration();
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

    /**
     * On obtient un clone de la liste de bonus.
     * 
     * @return Une ArrayList d'Integer correspondant au bonus.
     */
    public ArrayList<Integer> getBonusWords() {
        ArrayList<Integer> clone = new ArrayList<Integer>();
        clone.addAll(bonusWords);
        return clone;
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
        return "GameJeuConfiguration [words=" + words + ", bonusWords=" + bonusWords + ", wordsQueue=" + wordsQueue + "]";
    }
}
