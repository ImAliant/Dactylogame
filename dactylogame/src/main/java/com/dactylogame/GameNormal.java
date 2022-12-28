package com.dactylogame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

import org.fxmisc.richtext.*;

public class GameNormal implements GameMethods, Initializable {
    private static GameNormal instance = null;

    private GameNormalConfiguration gameConfiguration;
    private Queue<String> wordsQueue;

    private final int nb_words;

    private static boolean replay = false;

    private int wordUpdateCounter = 0;
    private int charPointer = 0;
    private int error = 0;
    private int errorWord = 0;
    private int appuiTouche = 0;
    private String word;
    private boolean isLauched = false;
    private Timer timer;
    private int time;

    /*
     *  Score
     */
    private static double MPM;
    private static double precision;
    private static double regularity;

    private int caractereUtile = 0;
    private int tempCaraUtile = 0;

    private long tempEcart = 0;
    private int compteur = 0;
    private ArrayList<Float> ecartType = new ArrayList<Float>();

    private ResultNormal result;

    /* 
     *  JAVA FX
     */
    @FXML private Pane pane;
    @FXML private Button btnQuitter;
    @FXML private TextFlow textFlow;
    @FXML private TextExt firstWord;
    @FXML private TextExt textQueue;
    @FXML private Label timeLabel;
    @FXML private Label errorLabel;
    @FXML private Label lauchGameLabel;
    @FXML private Line pointerChar;
    @FXML private Rectangle contourTextFlow;

    public GameNormal() {
        this.gameConfiguration = GameNormalConfiguration.getInstance();
        wordsQueue = gameConfiguration.getWordsQueue();
        wordUpdateCounter = wordsQueue.size();
        nb_words = gameConfiguration.getWords().size();
    }

    //Singleton Game
    public static GameNormal getInstance() {
        if (instance == null) {
            System.out.println("Creating new GameNormal instance");
            instance = new GameNormal();
        }
        return instance;
    }

    public static GameNormal newGame() {
        if (replay) {
            instance = new GameNormal();
            ResultNormal.reset();
            replay = false;
            return instance;
        }
        else {
            throw new IllegalStateException("Game already started");
        }
    }

    // Initialise les variables nécessaires pour le démarage du jeu lors de l'ouverture de la fenetre.
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        word = wordsQueue.peek();

        timeLabel.setText("Temps restant: " + 0);
        errorLabel.setText("Erreurs: " + error);
        firstWord.setText(word + " ");
        textQueue.setText(printQueue());
        
        firstWord.setVisible(false);
        textQueue.setVisible(false);
        textFlow.setVisible(false);
        pointerChar.setVisible(false);
        contourTextFlow.setVisible(false);
    }

    // Charge la scene depuis le dossier fxml et l'affiche sur l'écran.
    public void start(Stage window) throws IOException {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/GameNormalScene.fxml").toURI().toURL();
            root = FXMLLoader.load(url);

            Scene scene = new Scene(root);

            window.setTitle("Dactylogame");
            window.setScene(scene);
            window.setResizable(false);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    // Jeu : Tant que le timer n'est pas terminé, elle attend un evenement de clavier.
    @FXML
    private void play(KeyEvent event) {
        if (isLauched) {
            // Si il n'y a plus de mot dans la file, le jeu est terminé.
            if (wordsQueue.isEmpty()) {
                endGame();
            }
            // Mise a jour du mot courant.
            // On ne change le mot que si la barre espace est appuyée.
            if (charPointer == word.length() && event.getCharacter().charAt(0) != 8) {
                updateCurrentWord(event);
            }

            else {
                checkKeyTyped(event);
            }
        }
    }

    private void updateCurrentWord(KeyEvent event) {
        if (event.getCharacter().charAt(0) == 32) {
            firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");
            
            pointerChar.setLayoutX(345);
            
            charPointer = 0;
            if (checkError()) {
                caractereUtile += tempCaraUtile;
            }
            errorWord = 0;
            tempCaraUtile = 0;
            updateWord();
        }
    }

    private void checkKeyTyped(KeyEvent event) {
        // Test si on appui sur la touche backspace.
        if (event.getCharacter().charAt(0) == 8) {
            if (charPointer > 0) {
                if (errorWord == 1)
                    firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");

                pointerChar.setLayoutX(pointerChar.getLayoutX() - 13);

                charPointer--;
                tempCaraUtile--;
                if (errorWord > 0) {
                    error--;
                    errorWord--;
                    errorLabel.setText("Erreurs: " + error);
                }
            }
        }
        else {
            if (word != null) {
                if (event.getCharacter().charAt(0) == word.charAt(charPointer)) {
                    if (errorWord == 0)
                        firstWord.setStyle("-fx-fill: #00ff00; -fx-font-weight: bold;");

                    if (caractereUtile > 0 || tempCaraUtile > 0) {
                        ecartType.add((float) ((System.nanoTime() - tempEcart)/1000000000f));
                        compteur++;
                    }
                    tempEcart = System.nanoTime();

                    charPointer++;
                    tempCaraUtile++;
                } else {
                    firstWord.setStyle("-fx-fill: #ff0000; -fx-font-weight: bold;");

                    charPointer++;
                    error++;
                    errorWord++;
                    errorLabel.setText("Erreurs: " + error);
                }
                pointerChar.setLayoutX(pointerChar.getLayoutX() + 13);
                appuiTouche++;
            }
        }
    }

    // Quitte le jeu.
    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }

    // Lance le jeu lorsque on appui sur le label.
    @FXML
    private void lauchGame(MouseEvent event) {
        isLauched = true;
        lauchGameLabel.setVisible(false);
        firstWord.setVisible(true);
        textQueue.setVisible(true);
        textFlow.setVisible(true);
        pointerChar.setVisible(true);
        contourTextFlow.setVisible(true);

        time = GameNormalConfiguration.getInstance().getTime();

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                public void run() {
                    updateTimeLabel();
                }
            });
        }
        }, 0, 1000);
    }

    // Termine le jeu.
    @Override
    public void endGame() {
        timer.cancel();
        
        resultats();
    }

    @Override
    public void resultats() {
        float minute = (float) GameNormalConfiguration.TIME / 60;
        MPM = (double)(caractereUtile / minute)/5;
        precision = ((double) caractereUtile / (double) (appuiTouche)) * 100;
        regularity = calcRegularity();

        openResultScene();
    }

    public double calcRegularity() {
        float sommeMoy = 0;
        for (float x : ecartType) {
            sommeMoy += x;
        }
        float moyenne = (float) sommeMoy / compteur-1;

        float somme = 0;
        for (int i = 0; i < compteur-1; i++) {
            somme += (float) Math.abs(Math.pow(ecartType.get(i) - moyenne, 2));
        }

        return Math.sqrt(somme / compteur-1);
    }

    public void openResultScene() {
        try {
            result = ResultNormal.getInstance();
            result.start(new Stage());
            pane.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Met a jour le label du temps restants.
    public void updateTimeLabel() {
        if (isLauched) {
            time--;
            timeLabel.setText("Temps restant: " + time);
            
            if (time == 0) endGame();
        }
    }

    @Override
    public boolean checkError() {
        return errorWord == 0;
    }

    // Créer une chaine de caractere correspondant aux mots présents dans la file.
    @Override
    public String printQueue() {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < wordsQueue.size(); i++) {
            sb.append(wordsQueue.toArray()[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    // Met a jour le mot courant.
    @Override
    public void updateWord() {
        if (wordUpdateCounter < nb_words) {
            wordsQueue.add(gameConfiguration.getWords().get(wordUpdateCounter));
        }

        if (wordsQueue.size() > 0) {
            wordsQueue.remove();
            word = wordsQueue.peek();
            firstWord.setText(word+ " ");
            textQueue.setText(printQueue());
            wordUpdateCounter++;
        }
    }

    public static void reset() {
        instance = null;
    }

    // Retourne un clone de la file.
    public Queue<String> getWordsQueue() {
        Queue<String> clone = new ArrayDeque<>();
        clone.addAll(wordsQueue);
        return clone;
    }

    public double getMPM() {
        return MPM;
    }
    public double getPrecision() {
        return precision;
    }
    public double getRegularity() {
        return regularity;
    }
    
    public static void setReplay(boolean b) {
        replay = b;
    }

    public ResultNormal getResult() {
        return result;
    }
}

