package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;

import org.fxmisc.richtext.TextExt;

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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class GameJeu implements GameMethods, Initializable {
    private static GameJeu instance = null;

    private GameJeuConfiguration configuration;
    private Queue<String> wordsQueue;
    private int queueLength;

    private String word;

    private int pv;
    private int niveau = 1;
    private double vitesse = 3*Math.pow(0.9, niveau);

    private int charPointer = 0;
    private int wordUpdateCounter = 1;
    private int errorWord = 0;
    private boolean isLauched = false;

    private Timer timeLabelTimer = new Timer();

    /*
     *  Resultats
     */
    private int wordsCorrectlyTypedTemp = 0;
    private int wordsCorrectlyTypedTotal = 0;
    private int playTime = 0;

    private static int level;
    private static int wordsCorrectlyTyped;
    private static int time;

    private static boolean replay = false;

    /*
     *  JAVA FX
     */
    @FXML private Pane pane;
    @FXML private Label levelLabel;
    @FXML private Label pvLabel;
    @FXML private Label wordsTypedLabel;
    @FXML private Label playTimeLabel;
    @FXML private Label lauchGameLabel;
    @FXML private Button quitButton;
    @FXML private TextFlow currentWordTextFlow;
    @FXML private TextFlow queueTextFlow;
    @FXML private TextExt currentWord;
    @FXML private TextExt textQueue;
    @FXML private Line pointerChar;

    public GameJeu() {
        configuration = GameJeuConfiguration.getInstance();
        wordsQueue = configuration.getWordsQueue();
        queueLength = 1;
        pv = GameJeuConfiguration.PV;
    }

    public synchronized static GameJeu getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameJeu instance");
            instance = new GameJeu();
        }
        return instance;
    }

    public static GameJeu newGame() {
        if(replay) {
            instance = new GameJeu();
            ResultJeuSolo.reset();
            replay = false;
            return instance;
        }
        else {
            throw new IllegalStateException("Game already started");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        word = wordsQueue.peek();

        levelLabel.setText("Niveau: " + niveau);
        pvLabel.setText(Integer.toString(pv));
        wordsTypedLabel.setText("Mots tapés: " + wordsCorrectlyTypedTotal);
        playTimeLabel.setText("Temps de jeu: " + playTime);
        currentWord.setText(word);
        textQueue.setText(printQueue());

        currentWord.setVisible(false);
        textQueue.setVisible(false);
        pointerChar.setVisible(false);
        currentWordTextFlow.setVisible(false);
        queueTextFlow.setVisible(false);
    }

    public void start(Stage window) throws IOException {
        Parent root;
        try {
            URL url = new File("src/main/java/com/dactylogame/fxml/GameSoloJeuScene.fxml").toURI().toURL();
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

    @FXML
    private void play(KeyEvent event) {
        if (isLauched) {
            // Mise a jour du mot courant.
            // On ne change le mot que si la barre espace est appuyée.
            if (word != null) {
                if (charPointer == word.length() && event.getCharacter().charAt(0) != 8) {
                    updateCurrentWord(event);
                }
                else {
                    checkKeyTyped(event);
                }
            }
        }
    }

    private void updateCurrentWord(KeyEvent event) {
        if (event.getCharacter().charAt(0) == 32) {
            currentWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");

            pointerChar.setLayoutX(205);

            charPointer = 0;
            if(checkError()) {
               pv -= errorWord;
               pvLabel.setText(Integer.toString(pv));
            }
            else {
                wordsCorrectlyTypedTemp++;
                wordsCorrectlyTypedTotal++;
                wordsTypedLabel.setText("Mots tapés: " + wordsCorrectlyTypedTotal);
            }
            errorWord = 0;
            updateWord();
        }
    }

    private void checkKeyTyped(KeyEvent event) {
        if (event.getCharacter().charAt(0) == 8) {
            if (charPointer > 0) {
                if (errorWord == 1) 
                    currentWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");
                
                pointerChar.setLayoutX(pointerChar.getLayoutX() - 16);

                charPointer--;
                if (errorWord > 0) {
                    errorWord--;
                }
            }
        }
        else {
            if (event.getCharacter().charAt(0) == word.charAt(charPointer)) {
                if (errorWord == 0) 
                    currentWord.setStyle("-fx-fill: #00ff00; -fx-font-weight: bold;");
                charPointer++;
            } else {
                currentWord.setStyle("-fx-fill: #ff0000; -fx-font-weight: bold;");
                charPointer++;
                errorWord++;
            }
            pointerChar.setLayoutX(pointerChar.getLayoutX() + 16);
        }
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void lauchGame(MouseEvent event) {
        isLauched = true;
        lauchGameLabel.setVisible(false);
        currentWord.setVisible(true);
        textQueue.setVisible(true);
        pointerChar.setVisible(true);
        currentWordTextFlow.setVisible(true);
        queueTextFlow.setVisible(true);
        
        timeTimer();
        
        wordAndLevelTimer();
    }

    public void timeTimer() {
        timeLabelTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                public void run() {
                    updateTimeLabel();

                    if (pv <= 0) {
                        endGame();
                        timeLabelTimer.cancel();
                    }
                }
            });
        }
        }, 0, 1000);
    }

    public void wordAndLevelTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                public void run() {
                    addNewWordInQueue();

                    if (queueLength == GameJeuConfiguration.QUEUE_LENGTH) {
                        // On enleve une vie pour chaque caractere restant.
                        pv -= (word.length() - charPointer)-1 ;
                        pvLabel.setText(Integer.toString(pv));
                        updateWord();
                    }
                    if (wordsCorrectlyTypedTemp % 100 == 0 && wordsCorrectlyTypedTemp != 0) {
                        niveau++;
                        levelLabel.setText("Niveau: " + niveau);
                        vitesse = 3*Math.pow(0.9, niveau);
                        wordsCorrectlyTypedTemp = 0;
                        timer.cancel();
                        wordAndLevelTimer();
                    }
                    if (pv <= 0) {
                        timer.cancel();
                    }
                }
            });
        }
        }, 0, (long) (vitesse * 1000));
    }

    public void addNewWordInQueue() {
        wordsQueue.add(configuration.getWords().get(wordUpdateCounter));
        if (word == null) {
            word = wordsQueue.peek();
            currentWord.setText(word);
        }
        wordUpdateCounter++;
        queueLength++;
        textQueue.setText(printQueue());
    }

    public void updateTimeLabel() {
        playTime++;
        playTimeLabel.setText("Temps de jeu: " + playTime);
    }

    @Override
    public boolean checkError() {
        return errorWord > 0;
    }

    @Override
    public void updateWord() {
        wordsQueue.remove();
        queueLength--;
        word = wordsQueue.peek();
        currentWord.setText(word);
        textQueue.setText(printQueue());
        wordUpdateCounter++;
    }

    @Override
    public void resultats() {
        wordsCorrectlyTyped = wordsCorrectlyTypedTotal;
        time = playTime;
        level = niveau;

        openResultScene();
    }

    public void openResultScene() {
        try {
            ResultJeuSolo result = ResultJeuSolo.getInstance();
            result.start(new Stage());
            pane.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void endGame() {
        resultats();
    }

    @Override
    public String printQueue() {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < wordsQueue.size(); i++) {
            sb.append(wordsQueue.toArray()[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    public static void reset() {
        instance = null;
    }
    
    public int getLevel() {
        return level;
    }

    public int getPlayTime() {
        return time;
    }

    public int getWordsCorrectlyTyped() {
        return wordsCorrectlyTyped;
    }

    public static void setReplay(boolean b) {
        replay = b;
    }
}
