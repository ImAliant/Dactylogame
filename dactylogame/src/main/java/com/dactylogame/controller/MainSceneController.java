package com.dactylogame.controller;

import java.io.IOException;

import com.dactylogame.Options;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainSceneController {

    @FXML private Label title;
    @FXML private Button btnJouer;
    @FXML private Button btnQuitter;
    @FXML private RadioButton normalRadioButton;
    @FXML private RadioButton jeuSoloRadioButton;
    @FXML private RadioButton jeuMultiRadioButton;

    @FXML
    private void btnJouerClicked(MouseEvent event) throws IOException { 
        new Options().start(new Stage());
        
        btnJouer.getScene().getWindow().hide();
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }
}

