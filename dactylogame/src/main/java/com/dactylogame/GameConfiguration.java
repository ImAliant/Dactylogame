package com.dactylogame;

import java.util.ArrayDeque;
import java.util.Queue;

import com.thedeanda.lorem.LoremIpsum;

public class GameConfiguration{
    private String[] words;
    private Queue<String> wordsQueue;
    
    public GameConfiguration(GameConfiguration.Builder builder) {
        words = builder.words;
        wordsQueue = builder.wordsQueue;

        LoremIpsum lorem = LoremIpsum.getInstance();
        
        for (int i = 0; i < words.length; i++) {
            words[i] = lorem.getWords(1);
        }

        for (int i = 0; i < 15; i++) {
            wordsQueue.add(words[i]);
        }
    }

    public String[] getWords() {
        return words.clone();
    }

    public Queue<String> getWordsQueue() {
        Queue<String> clone = new ArrayDeque<>();
        clone.addAll(wordsQueue);
        return clone;
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

    public static class Builder {
        private String[] words;
        private Queue<String> wordsQueue;

        public Builder() {
            words = new String[50];
            wordsQueue = new ArrayDeque<>();
        }

        public Builder wordsLength(int wordsLength) {
            words = new String[wordsLength];
            return this;
        }

        public GameConfiguration build() {
            return new GameConfiguration(this);
        }
    }
}
