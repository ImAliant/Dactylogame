package com.dactylogame;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Queue;

import com.dactylogame.controller.MainSceneController;
import com.thedeanda.lorem.LoremIpsum;

public class GameJeuConfiguration {
    private static GameJeuConfiguration instance = null;

    private ArrayList<String> words;
    private ArrayList<Integer> bonusWords; // Liste de 0 et 1 pour savoir si le mot est un bonus ou non.

    private Queue<String> wordsQueue;

    public static final int QUEUE_LENGTH = MainSceneController.QUEUELENGTH;
    public static final int PV = MainSceneController.PV;

    private GameJeuConfiguration() {
        words = new ArrayList<String>(10000);
        bonusWords = new ArrayList<Integer>(10000);
        wordsQueue = new ArrayDeque<String>(QUEUE_LENGTH);

        LoremIpsum lorem = LoremIpsum.getInstance();
        for (int i = 0; i < 1000; i++) {
            words.add(lorem.getWords(1));
        }
        // On ajoute un mot à la queue pour commencer.
        wordsQueue.add(words.get(0));

        // On ajoute des mots bonus.
        for (int i = 0; i < 1000; i++) {
            if (i % 5 == 0) {
                bonusWords.add(1);
            } else {
                bonusWords.add(0);
            }
        }
        Collections.shuffle(bonusWords);
    }

    public synchronized static GameJeuConfiguration getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameJeuConfiguration instance");
            instance = new GameJeuConfiguration();
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

    public ArrayList<Integer> getBonusWords() {
        ArrayList<Integer> clone = new ArrayList<Integer>();
        clone.addAll(bonusWords);
        return clone;
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
        return "GameJeuConfiguration [words=" + words + ", wordsQueue=" + wordsQueue + "]";
    }
}
