package com.dactylogame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import com.dactylogame.controller.MainSceneController;
import com.thedeanda.lorem.LoremIpsum;

public final class GameNormalConfiguration {
    private static GameNormalConfiguration instance = null;

    private ArrayList<String> words;
    private Queue<String> wordsQueue;
    
    public static final int TIME = MainSceneController.TIME;
    public static final int NBWORDS = MainSceneController.NBWORDS;

    // Version par défaut sans choix du nombre de mots.
    private GameNormalConfiguration() {
        words = new ArrayList<String>(NBWORDS);
        wordsQueue = new ArrayDeque<String>(15);

        LoremIpsum lorem = LoremIpsum.getInstance();
        for (int i = 0; i < 50; i++) {
            words.add(lorem.getWords(1));
        }

        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words.get(i));
        }
    }

    public static GameNormalConfiguration getInstance() {
        if (instance == null) {
            System.out.println("Creating new GameNormalConfiguration instance");
            instance = new GameNormalConfiguration();
        }
        return instance;
    }

    public ArrayList<String> getWords() {
        ArrayList<String> clone = new ArrayList<String>();
        clone.addAll(words);
        return clone;
    }

    public Queue<String> getWordsQueue() {
        Queue<String> clone = new ArrayDeque<>();
        clone.addAll(wordsQueue);
        return clone;
    }

    public int getTime() {
        return TIME;
    }

    public String printWords() {
        String wordsString = "";
        for (String word : words) {
            wordsString += word + " ";
        }
        return wordsString;
    }

    public static void reset() {
        instance = null;
    }

    @Override
    public String toString() {
        return "GameConfiguration [words= " + printWords() + "; wordsQueue=" + wordsQueue + "]";
    }
}
