package com.dactylogame;

import java.net.URL;
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;

import org.fxmisc.richtext.TextExt;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;

public class GameMulti implements GameMethods, Initializable {
    private static GameMulti instance = null;

    private GameMultiConfiguration configuration;
    private Queue<String> wordsQueue;
    private int queueLength;

    private String word;

    private int pv;
    private double vitesse;

    private int charPointer = 0;
    private int wordUpdateCounter = 1;
    private int errorWord = 0;
    private boolean isLauched = false;

    private static boolean replay = false;

    @FXML private Pane pane;
    @FXML private Button btnQuitter;
    @FXML private Label playTime;
    @FXML private Label lifeLabel;
    
    @FXML private Pane playPane;
    @FXML private Rectangle gameContour;
    @FXML private TextFlow textFlow;
    @FXML private TextExt currentWord;
    @FXML private TextExt wordQueue;
    
    @FXML private Pane playerInfoPane;
    @FXML private Label playerConnected;
    @FXML private Label pseudo1Label;
    @FXML private Label pseudo2Label;
    @FXML private Button readyButton;
    
    public GameMulti() {
        //configuration = GameMultiConfiguration.getInstance();
        //wordsQueue = configuration.getWordsQueue();
        queueLength = 1;
        pv = GameMultiConfiguration.PV;
    }

    public synchronized static GameMulti getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameMulti instance");
            instance = new GameMulti();
        }
        return instance;
    }

    public GameMulti newGame() {
        if(replay) {
            instance = new GameMulti();
            //ResultMulti.reset();
            replay = false;
            return instance;
        }
        else {
            throw new IllegalStateException("Game already started");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        playPane.setVisible(false);
        playerInfoPane.setVisible(true);
        
        btnQuitter.setOnAction(e -> {
            System.exit(0);
        });
    }

    @Override
    public boolean checkError() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void updateWord() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resultats() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String printQueue() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
