package com.dactylogame;

import java.util.LinkedList;
import java.util.Queue;

import com.thedeanda.lorem.LoremIpsum;

public class GameConfiguration{
    private String[] words;
    private Queue<String> wordsQueue;
    
    public GameConfiguration() {
        LoremIpsum lorem = LoremIpsum.getInstance();
        words = new String[10];
        
        for (int i = 0; i < 10; i++) {
            words[i] = lorem.getWords(1);
        }
        wordsQueue = new LinkedList<>();

        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words[i]);
        }
    }

    public String[] getWords() {
        return words.clone();
    }

    public Queue<String> getWordsQueue() {
        Queue<String> clone = new LinkedList<>();
        clone.addAll(wordsQueue);
        return clone;
    }

    @Override
    public String toString() {
        return "GameConfiguration [wordsQueue=" + wordsQueue + "]";
    }
}
