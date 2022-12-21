package com.dactylogame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

import com.thedeanda.lorem.LoremIpsum;

public final class GameConfiguration{
    private static final GameConfiguration instance = null;

    private ArrayList<String> words;
    private Queue<String> wordsQueue;
    
    private int time;

    public static final int TIME_DEFAULT = 60;

    // Version par défaut sans choix du nombre de mots.
    private GameConfiguration() {
        words = new ArrayList<String>(50);
        wordsQueue = new ArrayDeque<String>(15);
        time = TIME_DEFAULT;

        LoremIpsum lorem = LoremIpsum.getInstance();
        for (int i = 0; i < 50; i++) {
            words.add(lorem.getWords(1));
        }

        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words.get(i));
        }
    }

    public static GameConfiguration getInstance() {
        if (instance == null) {
            return new GameConfiguration();
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
        return time;
    }

    public String printWords() {
        String wordsString = "";
        for (String word : words) {
            wordsString += word + " ";
        }
        return wordsString;
    }

    @Override
    public String toString() {
        return "GameConfiguration [words= " + printWords() + "; wordsQueue=" + wordsQueue + "]";
    }
}
