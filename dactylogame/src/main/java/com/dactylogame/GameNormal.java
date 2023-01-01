package com.dactylogame;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.util.Objects;
import java.util.Queue;
import java.util.ResourceBundle;

import java.util.Timer;
import java.util.TimerTask;

import org.fxmisc.richtext.*;

/**
 * <p>Cette classe correspond au mode de jeu "Solo (Normal)".
 * Elle permet de lancer le jeu et de gérer les évènements clavier.
 * Elle est aussi utilisée pour accéder à l'instance du jeu.</p>
 * 
 * <p>Elle etend la classe {@link Game} qui permet de gérer les méthodes communes à tous les modes de jeu.</p>
 * 
 * <p>La configuration du jeu est définie dans la classe {@link GameNormalConfiguration}.</p>
 * 
 * @see Game
 * @see GameNormalConfiguration
 * 
 * @author DIAMANT Alexandre
 */
public final class GameNormal implements Game {
    /**
     * Instance du jeu.
     */
    private static GameNormal instance = null;

    /**
     * Configuration du jeu.
     * 
     * @see GameNormalConfiguration
     */
    private GameConfiguration gameConfiguration;
    /**
     * File contenant les mots à écrire.
     */
    private Queue<String> wordsQueue;

    /**
     * Nombre de mots dans la file.
     */
    private final int nb_words;

    /**
     * Si replay est à true, le jeu peut être relancé.
     */
    private static boolean replay = false;

    /**
     * Mots parcourus par l'utilisateur.
     */
    private int wordUpdateCounter = 0;
    /**
     * Pointeur de caractère sur le mot courant.
     */
    private int charPointer = 0;
    /**
     * Correspond au nombre de caractères erronés.
     */
    private int error = 0;

    /**
     * 
     */
    private int errorWord = 0;
    /**
     * Correspond au nombre d'appui sur les touches du clavier.
     */
    private int appuiTouche = 0;
    /**
     * Mot courant.
     */
    private String word;
    /**
     * Evalué à {@code true} si le jeu est lancé,
     * {@code false} sinon.
     */
    private boolean isLauched = false;
    /**
     * Timer du jeu. Quand il est lancé, il décrémente le temps restant.
     * Lorsque le temps est écoulé, le jeu est terminé.
     */
    private Timer timer;
    /**
     * Correspond au temps restant.
     */
    private int time;

    /**
     * Liste par défaut des erreurs de positionnement. Il est donc rempli avec des 0.
     */
    private ArrayList<Integer> posErrorDefault = new ArrayList<Integer>(50);
    /**
     * Liste des erreurs de positionnement. Elle est remplie avec des 1 si l'utilisateur a fait une erreur de positionnement.
     * Remise par défaut à chaque nouveau mot.
     */
    private ArrayList<Integer> posError = new ArrayList<Integer>(50);

    /**
     * Valeur correspondant à au mots par minute écrits par l'utilisateur.
     */
    private static double MPM;
    /**
     * Pourcentage de précision de l'utilisateur.
     */
    private static double precision;
    /**
     * Ecart type entre deux caractères correctement tapés écrits par l'utilisateur.
     */
    private static double regularity;

    /**
     * Nombre de caractères utiles (sans espace, sans retour à la ligne, sans tabulation).
     */
    private int caractereUtile = 0;
    /**
     * Nombre de caractères utiles temporairement (sans espace, sans retour à la ligne, sans tabulation).
     */
    private int tempCaraUtile = 0;

    /**
     * Temps écoulé entre deux caractères correctement tapés.
     */
    private long tempEcart = 0;
    /**
     * Sert à calculer l'écart type.
     */
    private int compteur = 0;
    /**
     * Liste des temps écoulés entre deux caractères correctement tapés.
     */
    private ArrayList<Float> ecartType = new ArrayList<Float>();

    /**
     * Fenetre de resultat.
     */
    private Result result;

    /* 
     * JAVA FX
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
     * Bouton qui permet de quitter le jeu.
     */
    @FXML private Button btnQuitter;
    /**
     * Contient le mot courant et la file des mots dans la fenêtre.
     */
    @FXML private TextFlow textFlow;
    /**
     * Correspond au mot courant dans la fenêtre.
     */
    @FXML private TextExt firstWord;
    /**
     * Correspond à la file des mots dans la fenêtre.
     */
    @FXML private TextExt textQueue;
    /**
     * Correspond au temps restant afficher dans la fenêtre.
     */
    @FXML private Label timeLabel;
    /**
     * Correspond au nombre d'erreurs afficher dans la fenêtre.
     */
    @FXML private Label errorLabel;
    /**
     * Lance le jeu lorsque l'utilisateur clique dessus.
     */
    @FXML private Label lauchGameLabel;
    /** 
     * Pointe sur le caractère courant.
     */
    @FXML private Line pointerChar;
    /**
     * Contour du panneau de jeu.
     */
    @FXML private Rectangle contourTextFlow;

    /**
     * <p>Constructeur public du mode normal.</p>
     * 
     * Recupère la configuration du jeu et la file des mots.
     * Initialise le nombre de mots.
     */
    public GameNormal() {
        this.gameConfiguration = GameNormalConfiguration.getInstance();
        wordsQueue = gameConfiguration.getWordsQueue();
        wordUpdateCounter = wordsQueue.size();
        nb_words = gameConfiguration.getWords().size();

        word = wordsQueue.peek();
        for (int i = 0; i < 100; i++) {
            posErrorDefault.add(0);
            posError.add(0);
        }
    }

    /**
     * Création d'une instance du jeu (singleton) ou récupération de l'instance existante.
     * 
     * @return instance du jeu
     */
    public synchronized static GameNormal getInstance() {
        if (Objects.isNull(instance)) {
            System.out.println("Creating new GameNormal instance");
            instance = new GameNormal();
        }
        return instance;
    }

    /**
     * Création d'une nouvelle instance du jeu et réinitialisation des paramètres.
     * 
     * @return nouvelle instance du jeu
     */
    public static GameNormal newGame() {
        if (replay) {
            instance = new GameNormal();
            ResultNormal.reset();
            GameNormalConfiguration.reset();
            replay = false;
            return instance;
        }
        else {
            throw new IllegalStateException("Game already started");
        }
    }

    /**
     * <p>Initialisation des éléments de la fenêtre.</p>
     * 
     * @param location
     * @param resources
     * 
     * @see #word
     * @see #posErrorDefault
     * @see #posError
     * @see #timeLabel
     * @see #errorLabel
     * @see #firstWord
     * @see #textQueue
     * @see #textFlow
     * @see #pointerChar
     * @see #contourTextFlow
     * @see #writingZonePane
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        timeLabel.setText("Temps restant: " + 0);
        errorLabel.setText("Erreurs: " + error);
        firstWord.setText(word + " ");
        textQueue.setText(printQueue());
        
        firstWord.setVisible(false);
        textQueue.setVisible(false);
        textFlow.setVisible(false);
        pointerChar.setVisible(false);
        contourTextFlow.setVisible(false);
        writingZonePane.setVisible(false);
    }

    /**
     * Lance la fenêtre du jeu.
     * 
     * @param window fenêtre du jeu.
     * @throws IOException si le fichier FXML n'est pas trouvé.
     */
    @Override
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

    /**
     * <p>"Boucle" du jeu.</p>
     * <p>Si le jeu est lancé ({@link #isLauched}), et tant que le temps disponible n'est pas écoulé, on met à jour le jeu.
     * Si le joueur appui sur la touche espace et que {@link #charPointer} correspond à la longueur du mot courant, on passe au mot suivant.
     * Sinon, on vérifie si la touche appuyée correspond au caractère du mot courant.</p>
     * 
     * @param event évènement de la touche appuyée.
     */
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

    /**
     * <p>Met à jour le mot courant.</p>
     * <p>On met le mot en blanc, on remet le curseur au début du mot, 
     * on incrémente les caractères utiles si le mot précédent n'a pas d'erreur et enfin on met
     * à 0 {@link #errorWord} et {@link #tempCaraUtile}.</p>
     * @param event évènement de la touche appuyée.
     */
    private void updateCurrentWord(KeyEvent event) {
        if (event.getCharacter().charAt(0) == 32) {
            firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");
            
            pointerChar.setLayoutX(28);
            
            charPointer = 0;
            if (checkError()) {
                caractereUtile += tempCaraUtile;
            }
            errorWord = 0;
            tempCaraUtile = 0;
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
        // Test si on appui sur la touche backspace.
        if (event.getCharacter().charAt(0) == 8) {
            if (charPointer > 0) {
                if (errorWord == 1)
                    firstWord.setStyle("-fx-fill: #ffffff; -fx-font-weight: bold;");

                pointerChar.setLayoutX(pointerChar.getLayoutX() - 13);

                charPointer--;
                tempCaraUtile--;
                if (errorWord > 0 && posError.get(charPointer) == 1) {
                    error--;
                    errorWord--;
                    posError.set(charPointer, 0);
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

                    posError.set(charPointer, 1);
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

   /**
    * Bouton qui permet de quitter le jeu. 
    *
    * @param event évènement de la souris.
    */
    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        Platform.exit();
    }

    /**
     * Fonction appartenant au label {@link #lauchGameLabel} qui lance le jeu.
     * 
     * <p>Rend les éléments du jeu visibles et lance le timers.</p>
     * 
     * @param event clique souris sur le label
     */
    @FXML
    public void lauchGame(MouseEvent event) {
        isLauched = true;
        lauchGameLabel.setVisible(false);
        firstWord.setVisible(true);
        textQueue.setVisible(true);
        textFlow.setVisible(true);
        pointerChar.setVisible(true);
        contourTextFlow.setVisible(true);
        writingZonePane.setVisible(true);

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

    /**
     * Quand le joueur a perdu, on affiche les résultats et on arrête le timer.
     */
    @Override
    public void endGame() {
        timer.cancel();
        
        resultats();
    }

    /**
     * Resultats du jeu.
     * 
     * @see #MPM
     * @see #precision
     * @see #regularity
     */
    @Override
    public void resultats() {
        float minute = (float) GameNormalConfiguration.TIME / 60;
        MPM = (double)(caractereUtile / minute)/5;
        precision = ((double) caractereUtile / (double) (appuiTouche)) * 100;
        regularity = calcRegularity();

        openResultScene();
    }

    /**
     * Calcule la régularité du joueur.
     * 
     * @return l'écart type
     */
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

    /**
     * Ouvre la fenêtre de résultats.
     * 
     * @see ResultNormal
     */
    public void openResultScene() {
        try {
            result = ResultNormal.getInstance();
            result.start(new Stage());
            pane.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Met à jour le label du temps de jeu.
     */
    @Override
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

    /**
     * Met à jour le mot courant et la file.
     * 
     * <p>
     * 1) Tant que le compteur de mots est inférieur au nombre de mots, on ajoute le mot correspondant au compteur dans la file.<br>
     * 2) Tant que la file n'est pas vide, on supprime le premier élément de la file et on met à jour le mot courant.
     * </p>
     */
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
            listPosErrorToDefault();
        }
    }

    @Override
    public void listPosErrorToDefault() {
        posError.clear();
        posError.addAll(posErrorDefault);
    }

    /**
     * Réinitialise l'instance du jeu.
     */
    public static void reset() {
        instance = null;
    }

    /**
     * On obtient un clone de la file de mots.
     * 
     * @return la file de mots
     */
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

    public Result getResult() {
        return result;
    }

    public int getError() {
        return error;
    }

    public String getWord() {
        return word;
    }

    // PARTIE POUR TESTS UNITAIRES
    /**
     * Même méthode que {@link #lauchGame()} mais attendre un clique sur le label.
     */
    public void lauchTest() {
        isLauched = true;
        lauchGameLabel.setVisible(false);
        firstWord.setVisible(true);
        textQueue.setVisible(true);
        textFlow.setVisible(true);
        pointerChar.setVisible(true);
        contourTextFlow.setVisible(true);
        writingZonePane.setVisible(true);

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

    public void handle(KeyEvent event) {
        play(event);
    }
}

