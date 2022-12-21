package com.dactylogame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameSceneController {
    private GameNormal game;

    public static KeyEvent keyTyped = null;

    @FXML private Pane pane;
    @FXML private Button btnQuitter;
    @FXML private Label texteLabel;

    public void initialize() {
        game = GameNormal.getInstance();
        texteLabel.setText(game.getWordsQueue().peek());
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleKeyTyped(KeyEvent event) {
        System.out.println("Key typed: " + event.getCharacter());
    }
}
