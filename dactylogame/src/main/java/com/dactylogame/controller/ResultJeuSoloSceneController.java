package com.dactylogame.controller;

import com.dactylogame.GameJeu;
import com.dactylogame.ResultJeuSolo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ResultJeuSoloSceneController {
    
    private GameJeu game;
    private ResultJeuSolo result;

    @FXML private Pane pane;
    @FXML private Button btnQuit;
    @FXML private Button btnReplay;
    @FXML private Label LevelLabel;
    @FXML private Label playTimeLabel;
    @FXML private Label resultTitleLabel;
    @FXML private Label wordsLabel;

    public void initialize() {
        game = GameJeu.getInstance();
        result = ResultJeuSolo.getInstance();

        LevelLabel.setText("Niveau atteint: " + result.getLevel());
        playTimeLabel.setText("Temps de jeu: " + secondsToMinutes(result.getPlayTime()));
        wordsLabel.setText("Mots correctement tapés: " + result.getWordsCorrectlyTyped());

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            System.exit(0);
        });

        btnReplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                GameJeu.setReplay(true);
                game = GameJeu.newGame();
                game.start(new Stage());
                btnReplay.getScene().getWindow().hide();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

    public String secondsToMinutes(int seconds) {
        int minutes = seconds / 60;
        int restSeconds = seconds % 60;
        return String.format("%02d:%02d", minutes, restSeconds);
    }
}
