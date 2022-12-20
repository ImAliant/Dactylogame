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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

import org.fxmisc.richtext.*;

public class Game implements GameMethods, Initializable {
    private static Game instance = null;

    private GameConfiguration gameConfiguration;
    private Queue<String> wordsQueue;

    private final int nb_words;

    private static boolean replay = false;

    private int wordUpdateCounter = 0;
    private int charPointer = 0;
    private int error = 0;
    private int errorWord = 0;
    private String word;
    private boolean isLauched = false;
    private Timer timer;
    private int time;

    /*
     *  Score
     */
    private static double MPM = 0;
    private static double precision = 0;
    private static int regularity = 0;

    private int caractereUtile = 0;
    private int tempCaraUtile = 0;

    private Result result;

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

    public Game() {
        this.gameConfiguration = GameConfiguration.getInstance();
        wordsQueue = gameConfiguration.getWordsQueue();
        wordUpdateCounter = wordsQueue.size();
        nb_words = gameConfiguration.getWords().size();
    }

    //Singleton Game
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public static Game newGame() {
        if (replay) {
            instance = new Game();
            Result.reset();
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
    }

    // Charge la scene depuis le dossier fxml et l'affiche sur l'écran.
    public void start(Stage window) throws IOException {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/GameScene.fxml").toURI().toURL();
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
            if (charPointer == word.length()) {
                firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");

                charPointer = 0;
                if (checkWord()) {
                    caractereUtile += tempCaraUtile;
                }
                errorWord = 0;
                tempCaraUtile = 0;
                updateWord();
            }
            // Test si la touche appuyée est la bonne.
            else {
                // Test si on appui sur la touche backspace.
                if (event.getCharacter().charAt(0) == 8) {
                    if (charPointer > 0) {
                        firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");
                        charPointer--;
                        tempCaraUtile--;
                        if (errorWord > 0) {
                            error--;
                            errorWord--;
                            errorLabel.setText("Erreurs: " + error);
                        }
                    }
                }

                else if (event.getCharacter().charAt(0) == word.charAt(charPointer)) {
                    firstWord.setStyle("-fx-fill: #00ff00; -fx-font-weight: bold;");
            
                    charPointer++;
                    tempCaraUtile++;
                } else {
                    firstWord.setStyle("-fx-fill: #ff0000; -fx-font-weight: bold;");

                    charPointer++;
                    error++;
                    errorWord++;
                    errorLabel.setText("Erreurs: " + error);
                }
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
        time = GameConfiguration.getInstance().getTime();

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

    public void resultats() {
        float minute = (float) GameConfiguration.TIME_DEFAULT / 60;
        MPM = (double)(caractereUtile / minute)/5;
        precision = ((double) caractereUtile / (double) (caractereUtile + error)) * 100;

        //TODO : regularity

        openResultScene();
    }

    public void openResultScene() {
        try {
            result = Result.getInstance();
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
    public boolean checkWord() {
        return errorWord == 0;
    }

    // Créer une chaine de caractere correspondant aux mots présents dans la file.
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
    public int getRegularity() {
        return regularity;
    }
    
    public static void setReplay(boolean b) {
        replay = b;
    }
}

