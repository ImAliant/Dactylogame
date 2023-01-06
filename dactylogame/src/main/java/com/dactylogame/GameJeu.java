package com.dactylogame;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * <p>Cette classe correspond au mode de jeu "solo, mode jeu".
 * Elle permet de lancer le jeu et de gérer les évènements clavier.
 * Elle est aussi utilisée pour accéder à l'instance du jeu.</p>
 * 
 * <p>Elle etend la classe {@link Game} qui permet de gérer les méthodes communes à tous les modes de jeu.</p>
 * 
 * <p>La configuration du jeu est définie dans la classe {@link GameJeuConfiguration}.</p>
 * 
 * @see GameMethods
 * @see Initializable
 * 
 * @see GameJeuConfiguration
 * 
 * @author DIAMANT Alexandre
 */
public final class GameJeu implements Game {
    /**
     * Instance du jeu.
     * 
     * @see GameJeu#getInstance()
     * @see GameJeu#newGame()
     * @see GameJeu#endGame()
     */
    private static GameJeu instance = null;

    /**
     * Configuration du jeu.
     * 
     * @see GameJeuConfiguration
     */
    private GameConfiguration configuration;
    /**
     * File des mots.
     * 
     * @see GameJeuConfiguration#getWordsQueue()
     */
    private Queue<String> wordsQueue;
    /**
     * Remplissage de la file du joueur en fonction de la configuration.
     * Si cette valeur est supérieur à {@link GameJeuConfiguration#QUEUE_LENGTH}, le jeu se termine.
     */
    private int queueLength;

    /**
     * Mot courant.
     * 
     * @see GameJeu#currentWord
     */
    private String word;

    /**
     * Nombre de points de vie.
     * Sa valeur est définie par {@link GameJeuConfiguration#PV}.
     * 
     * @see GameJeu#pv
     */
    private int pv;
    /**
     * Niveau du jeu.
     */
    private int niveau = 1;
    /**
     * Vitesse de défilement d'arrivée des mots dans la file.
     */
    private double vitesse = 3*Math.pow(0.9, niveau);

    /**
     * Pointeur de caractère sur le mot courant.
     */
    private int charPointer = 0;
    /**
     * Nombre de mots parcouru.
     */
    private int wordUpdateCounter = 1;
    /**
     * Nombre d'erreurs de frappe.
     */
    private int errorWord = 0;
    /**
     * Liste qui permet de remettre à zéro la liste des erreurs de frappe.
     */
    private ArrayList<Integer> posErrorDefault = new ArrayList<Integer>(100);
    /**
     * Liste qui permet de stocker les positions des erreurs de frappe.
     */
    private ArrayList<Integer> posError = new ArrayList<Integer>(100); 

    /**
     * Evalué à true si le jeu est lancé,
     * false sinon.
     */
    private boolean isLauched = false;

    /**
     * Timer qui permet de mettre à jour le temps de jeu.
     */
    private Timer timeLabelTimer = new Timer();

    /**
     * Nombre temporaire de mots correctement tapés.
     * 
     */
    private int wordsCorrectlyTypedTemp = 0;
    /**
     * Nombre total de mots correctement tapés.
     * 
     */
    private int wordsCorrectlyTypedTotal = 0;
    /**
     * Temps de jeu en secondes. Est incrémenté toutes les secondes.
     */
    private int playTime = 0;

    /**
     * Niveau du jeu à la fin de partie. Est utilisé pour l'affichage du score.
     */
    private static int level;
    /**
     * Mots correctement tapés à la fin de partie. Est utilisé pour l'affichage du score.
     */
    private static int wordsCorrectlyTyped;
    /**
     * Temps de jeu à la fin de partie. Est utilisé pour l'affichage du score.
     */
    private static int time;

    /**
     * Si replay est à true, le jeu peut être relancé.
     */
    private static boolean replay = false;

    /**
     * Fenetre de resultat.
     */
    private Result result;

    /*
     *  JAVA FX
     */
    /**
     * Panneau principal.
     */
    @FXML private Pane pane;
    /**
     * Panneau de jeu.
     */
    @FXML private Pane writingZonePane;
    /**
     * Label du niveau.
     */
    @FXML private Label levelLabel;
    /**
     * Label des points de vie.
     */
    @FXML private Label pvLabel;
    /**
     * Label du nombre de mots correctement tapés.
     */
    @FXML private Label wordsTypedLabel;
    /**
     * Label du temps de jeu.
     */
    @FXML private Label playTimeLabel;
    /**
     * Label pour lancer le jeu.
     */
    @FXML private Label lauchGameLabel;
    /**
     * Bouton pour quitter le jeu.
     */
    @FXML private Button quitButton;
    /**
     * TextFlow qui contient le mot courant et la file de mots du joueur.
     */
    @FXML private TextFlow textFlow;
    /**
     * TextExt qui correspond au mot courant.
     */
    @FXML private TextExt currentWord;
    /**
     * TextExt qui correspond à la file de mots du joueur.
     */
    @FXML private TextExt textQueue;
    /**
     * Line qui correspond au pointeur de caractère.
     */
    @FXML private Line pointerChar;
    /**
     * Contour du TextFlow.
     */
    @FXML private Rectangle contourTextFlow;

    /**
     * Constructeur public du mode jeu.
     * 
     * @see GameJeu#getInstance()
     * @see GameJeu#newGame()
     */
    public GameJeu() {
        configuration = GameJeuConfiguration.getInstance();
        wordsQueue = configuration.getWordsQueue();
        queueLength = 1;
        pv = GameJeuConfiguration.getPV();
    }

    /**
     * Création d'une instance du jeu (singleton) ou récupération de l'instance existante.
     * 
     * @return instance du jeu
     */
    public synchronized static GameJeu getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameJeu instance");
            instance = new GameJeu();
        }
        return instance;
    }

    /**
     * Création d'une nouvelle instance du jeu et réinitialisation des paramètres.
     * 
     * @return nouvelle instance du jeu
     */
    public static GameJeu newGame() {
        if(replay) {
            instance = new GameJeu();
            ResultJeuSolo.reset();
            GameJeuConfiguration.reset();
            replay = false;
            return instance;
        }
        else {
            throw new IllegalStateException("Game already started");
        }
    }

    /**
     * Initialise les éléments de la fenêtre.
     * 
     * @param location
     * @param resources
     * 
     * @see #word
     * @see #levelLabel
     * @see #pvLabel
     * @see #wordsTypedLabel
     * @see #playTimeLabel
     * @see #currentWord
     * @see #textQueue
     * @see #textFlow
     * @see #pointerChar
     * @see #contourTextFlow
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        word = wordsQueue.peek();
        //posError = new int[word.length()];
        for (int i = 0; i < 100; i++) {
            posErrorDefault.add(0);
            posError.add(0);
        }

        levelLabel.setText("Niveau: " + niveau);
        pvLabel.setText(Integer.toString(pv));
        wordsTypedLabel.setText("Mots tapés: " + wordsCorrectlyTypedTotal);
        playTimeLabel.setText("Temps de jeu: " + playTime);
        currentWord.setText(word + " ");
        textQueue.setText(printQueue());

        currentWord.setVisible(false);
        textQueue.setVisible(false);
        pointerChar.setVisible(false);
        textFlow.setVisible(false);
        contourTextFlow.setVisible(false);
        writingZonePane.setVisible(false);
    }

    /**
     * Lance la fenêtre du jeu.
     * 
     * @param window fenêtre du jeu
     * @throws IOException si le fichier FXML n'est pas trouvé
     */
    @Override
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

    /**
     * <p>"Boucle" du jeu.</p>
     * <p>Si le jeu est lancé ({@link #isLauched}), et tant que le joueur a des points de vie, on met à jour le jeu.
     * Si le joueur appui sur la touche espace et que {@link #charPointer} correspond à la longueur du mot courant, on passe au mot suivant.
     * Sinon, on vérifie si la touche appuyée correspond au caractère du mot courant.</p>
     * 
     * @param event
     */
    @FXML
    private void play(KeyEvent event) {
        if (isLauched) {
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

    /**
     * <p>Met à jour le mot courant.</p>
     * <p>Si le nouveau mot est un bonus, on change la couleur du mot courant en bleu.
     * Sinon, on change la couleur du mot courant en blanc.</p>
     * 
     * <p>On remet le pointeur de caractère au début du mot.
     * On vérifie si le joueur a fait des erreurs.
     * Si oui, on lui retire des points de vie et on met à jour le label des points de vie.
     * Sinon, on incrémente le nombre de mots tapés correctement.
     * Si le mot était un bonus, on lui donne des points de vie.</p>
     * 
     * <p>Enfin on met à jour le mot courant et la queue.</p>
     * 
     * @param event
     */
    private void updateCurrentWord(KeyEvent event) {
        if (event.getCharacter().charAt(0) == 32) {
            if (((GameJeuConfiguration) configuration).getBonusWords().get(wordUpdateCounter-1) == 1)
                currentWord.setStyle("-fx-fill: #1e88e5; -fx-font-weight: bold;");
            else 
                currentWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");

            pointerChar.setLayoutX(28);

            charPointer = 0;
            if(checkError()) {
               pv -= errorWord;
               pvLabel.setText(Integer.toString(pv));
            }
            else {
                wordsCorrectlyTypedTemp++;
                wordsCorrectlyTypedTotal++;
                if (((GameJeuConfiguration) configuration).getBonusWords().get(wordUpdateCounter-1) == 1 && pv < GameJeuConfiguration.getPV()) {
                    pv += (word.length() + pv < GameJeuConfiguration.getPV()) ? word.length() : GameJeuConfiguration.getPV() - pv;
                    pvLabel.setText(Integer.toString(pv));
                }
                wordsTypedLabel.setText("Mots tapés: " + wordsCorrectlyTypedTotal);
            }
            errorWord = 0;
            updateWord();
        }
    }

    /**
     * Méthode qui compare le caractère tapé avec le caractère pointé par {@link #charPointer} du mot courant.
     * 
     * <p>Si le caractère tapé est la touche backspace, on décrémente le pointeur de caractère et on déplace le pointeur de caractère.
     * Si le dernier caractère tapé était une erreur, on décrémente le nombre d'erreurs.</p>
     * 
     * <p>Si le caractère tapé est le même que le caractère pointé par {@link #charPointer}, on incrémente le pointeur de caractère et on déplace le pointeur de caractère.
     * La couleur du mot est vert si le joueur n'a pas fait d'erreur, sinon elle est rouge.</p>
     * 
     * @param event évènement de la touche tapée
     */
    private void checkKeyTyped(KeyEvent event) {
        // Backspace
        if (event.getCharacter().charAt(0) == 8) {
            if (charPointer > 0) {
                if (errorWord == 1) 
                    currentWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");
                
                pointerChar.setLayoutX(pointerChar.getLayoutX() - 13);

                charPointer--;
                if (errorWord > 0 && posError.get(charPointer) == 1) {
                    errorWord--;
                    posError.set(charPointer, 0);
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
                posError.set(charPointer, 1);
                charPointer++;
                errorWord++;
            }
            pointerChar.setLayoutX(pointerChar.getLayoutX() + 13);
        }
    }

    /**
     * Bouton pour quitter le jeu.
     * 
     * @param event clique souris sur le bouton
     */
    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }

    /**
     * Fonction appartenant au label {@link #lauchGameLabel} qui lance le jeu.
     * 
     * <p>Rend les éléments du jeu visibles et lance les timers.</p>
     * 
     * @param event clique souris sur le label
     */
    @FXML
    private void lauchGame(MouseEvent event) {
        isLauched = true;
        lauchGameLabel.setVisible(false);
        currentWord.setVisible(true);
        textQueue.setVisible(true);
        pointerChar.setVisible(true);
        textFlow.setVisible(true);
        contourTextFlow.setVisible(true);
        writingZonePane.setVisible(true);
        
        timeTimer();
        
        wordAndLevelTimer();
    }

    /**
     * Fonction qui met à jour le label du temps.
     * 
     * <p> Le timer s'arrête si le joueur n'a plus de points de vie.</p>
     * 
     * @see #timeLabelTimer
     */
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

    /**
     * Méthode qui met à jour la queue de mots et le mot courant en fonction de la vitesse du jeu ({@link #vitesse}) avec la méthode {@link #addNewWordInQueue()}.
     * 
     * <p>Si la queue est pleine, on enlève une vie au joueur pour chaque caractère restant du mot courant.</p>
     * <p>Si le joueur a tapé 100 mots correctement, on augmente le niveau et on réinitialise le compteur de mots temporaire correctement tapés.</p>
     * 
     * @see #vitesse
     */
    public void wordAndLevelTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                public void run() {
                    addNewWordInQueue();

                    if (queueLength == GameJeuConfiguration.getQUEUELENGTH()) {
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

    /**
     * <p>Ajoute un nouveau mot dans la queue.</p>
     * 
     * <p>1) Si le mot courant est null, on met le mot de la queue en tant que mot courant.<br>
     * 2) On incrémente le compteur de mots ajoutés dans la queue.<br>
     * 3) On incrémente le remplissage de la file.<br>
     * 4) On met à jour le TextExt de la queue.<br>
     * 
     * @see #wordsQueue
     * @see #wordUpdateCounter
     * @see #queueLength
     * @see #textQueue
     */
    public void addNewWordInQueue() {
        wordsQueue.add(configuration.getWords().get(wordUpdateCounter));
        if (word == null) {
            word = wordsQueue.peek();
            currentWord.setText(word+" ");
        }
        wordUpdateCounter++;
        queueLength++;
        textQueue.setText(printQueue());
    }

    /**
     * Met à jour le label du temps de jeu.
     */
    @Override
    public void updateTimeLabel() {
        playTime++;
        playTimeLabel.setText("Temps de jeu: " + playTime);
    }

    /**
     * Teste si les lettres entrées par l'utilisateur sont correctes.
     * 
     * @return {@code false} si le mot est correct, {@code true} sinon
     */
    @Override
    public boolean checkError() {
        return errorWord > 0;
    }

    /**
     * Met à jour le mot courant et la queue.
     * 
     * <p>1) On enlève le mot courant de la queue.<br>
     * 2) On met à jour le remplissage de la queue.<br>
     * 3) On met à jour le TextExt du mot courant.<br>
     * 4) On met à jour le TextExt de la queue.<br>
     * 5) On incrémente le compteur de mots mis à jour.</p>
     */
    @Override
    public void updateWord() {
        wordsQueue.remove();
        queueLength--;
        word = wordsQueue.peek();

        if (word != null)
            //posError = new int[word.length()];
            listPosErrorToDefault();
        
        if (word == null)
            currentWord.setText("");
        else 
            currentWord.setText(word+" ");
        textQueue.setText(printQueue());
        wordUpdateCounter++;
    }

    /**
     * Resultats du jeu.
     * 
     * @see #wordsCorrectlyTyped
     * @see #time
     * @see #level
     */
    @Override
    public void resultats() {
        wordsCorrectlyTyped = wordsCorrectlyTypedTotal;
        time = playTime;
        level = niveau;

        openResultScene();
    }

    /**
     * Ouvre la fenêtre de résultats.
     * 
     * @see ResultJeuSolo
     */
    public void openResultScene() {
        try {
            result = ResultJeuSolo.getInstance();
            result.start(new Stage());
            pane.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Quand le joueur a perdu, on affiche les résultats.
     */
    @Override
    public void endGame() {
        resultats();
        replay = true;
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

    /**
     * Réinitialise l'instance du jeu.
     */
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

    public Queue<String> getWordsQueue() {
        Queue<String> clone = new ArrayDeque<>();
        clone.addAll(wordsQueue);
        return clone;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public String getWord() {
        return word;
    }

    public int getPv() {
        return pv;
    }

    public int getNiveau() {
        return niveau;
    }

    public double getVitesse() {
        return vitesse;
    }

    public int getCharPointer() {
        return charPointer;
    }

    public int getWordUpdateCounter() {
        return wordUpdateCounter;
    }

    public int getErrorWord() {
        return errorWord;
    }

    public boolean isLauched() {
        return isLauched;
    }

    public int getWordsCorrectlyTypedTemp() {
        return wordsCorrectlyTypedTemp;
    }

    public int getWordsCorrectlyTypedTotal() {
        return wordsCorrectlyTypedTotal;
    }

    public static int getTime() {
        return time;
    }

    public static boolean isReplay() {
        return replay;
    }

    @Override
    public void listPosErrorToDefault() {
        posError.clear();
        posError.addAll(posErrorDefault);
    }

    // METHODE DE TEST UNITAIRES //
    // NE DOIT PAS ETRE UTILISEE A PART POUR EFFECTUER DES TESTS //
    public static void setReplay(boolean b) {
        replay = b;
    }
}
