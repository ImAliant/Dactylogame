package com.dactylogame.controller;

import java.io.IOException;

import com.dactylogame.GameJeu;
import com.dactylogame.GameNormal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainSceneController {

    /* Types de jeu */
    private GameNormal gameNormal;
    private GameJeu gameJeu;

    @FXML private Label title;
    @FXML private Button btnJouer;
    @FXML private Button btnQuitter;
    @FXML private RadioButton normalRadioButton;
    @FXML private RadioButton jeuSoloRadioButton;
    @FXML private RadioButton jeuMultiRadioButton;

    private final ToggleGroup group = new ToggleGroup();

    public void initialize() throws Exception{
        normalRadioButton.setToggleGroup(group);
        jeuSoloRadioButton.setToggleGroup(group);
        jeuMultiRadioButton.setToggleGroup(group);

        normalRadioButton.setSelected(true);
    }

    @FXML
    private void btnJouerClicked(MouseEvent event) throws IOException { 
        if(normalRadioButton.isSelected()) {
            gameNormal = GameNormal.getInstance();
            gameNormal.start(new Stage());
        }
        else if(jeuSoloRadioButton.isSelected()) {
            gameJeu = GameJeu.getInstance();
            gameJeu.start(new Stage());
        }
        else if(jeuMultiRadioButton.isSelected()) {
            // TODO
        }
        /*gameNormal = GameNormal.getInstance();
        gameNormal.start(new Stage());*/
        btnJouer.getScene().getWindow().hide();
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }
}

