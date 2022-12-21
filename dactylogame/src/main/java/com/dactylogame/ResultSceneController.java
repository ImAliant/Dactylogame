package com.dactylogame;

import java.text.DecimalFormat;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ResultSceneController {

    private GameNormal game;
    private Result result;

    @FXML private Pane pane;
    @FXML private Button btnQuit;
    @FXML private Button btnReplay;
    @FXML private Label resultTitleLabel;
    @FXML private Label MPMLabel;
    @FXML private Label precisionLabel;
    @FXML private Label regularityLabel;

    public void initialize() {
        game = GameNormal.getInstance();
        result = Result.getInstance();

        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(2);

        MPMLabel.setText("MPM : " + f.format(result.getMPM()));
        precisionLabel.setText("Precision : " + f.format(result.getPrecision()) + "%");
        regularityLabel.setText("Regularité : " + f.format(result.getRegularity()) + "s");

        btnQuit.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            System.exit(0);
        });
        
        btnReplay.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
            try {
                GameNormal.setReplay(true);
                game = GameNormal.newGame();
                game.start(new Stage());
                btnReplay.getScene().getWindow().hide();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }

}

