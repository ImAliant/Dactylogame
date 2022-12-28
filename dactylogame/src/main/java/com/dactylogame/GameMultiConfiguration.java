package com.dactylogame;

import java.util.ArrayList;
import java.util.Queue;

import com.dactylogame.controller.MainSceneController;

public class GameMultiConfiguration {
    private ArrayList<String> words;
    private ArrayList<Integer> bonusAndGotoEnemyWords;
    
    private Queue<String> wordsQueue;
    
    public static final int QUEUE_LENGTH = MainSceneController.QUEUELENGTH;
    public static final int PV = MainSceneController.PV;

    private GameMultiConfiguration() {
        
    }
}
