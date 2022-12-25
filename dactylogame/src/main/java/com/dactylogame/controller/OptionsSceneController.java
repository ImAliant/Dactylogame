package com.dactylogame.controller;

import java.io.IOException;

import com.dactylogame.GameJeu;
import com.dactylogame.GameNormal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class OptionsSceneController {

    public static int TIME;
    public static int NBWORDS;
    public static int PV;
    public static int QUEUELENGTH;

    private GameNormal gameNormal;
    private GameJeu gameJeu;

    @FXML private Button btnJouer;
    @FXML private Button btnQuitter;
    @FXML private Label title;

    @FXML private RadioButton normalRadioButton;
    @FXML private RadioButton jeuSoloRadioButton;
    @FXML private RadioButton jeuMultiRadioButton;

    @FXML private Pane optionNormalPane;
    @FXML private Slider nbwordsSlider;
    @FXML private Slider timeSlider; 

    @FXML private Pane optionJeuPane;
    @FXML private Slider pvSlider;
    @FXML private Slider queueLengthSlider;

    private final ToggleGroup group = new ToggleGroup();

    public void initialize() throws Exception{
        normalRadioButton.setToggleGroup(group);
        jeuSoloRadioButton.setToggleGroup(group);
        jeuMultiRadioButton.setToggleGroup(group);

        normalRadioButton.setSelected(true);
        optionNormalPane.setVisible(true);
        optionJeuPane.setVisible(false);

        normalRadioButton.setOnAction(e -> {
            optionNormalPane.setVisible(true);
            optionJeuPane.setVisible(false);
        });

        jeuSoloRadioButton.setOnAction(e -> {
            optionNormalPane.setVisible(false);
            optionJeuPane.setVisible(true);
        });
    }

    @FXML
    private void btnJouerClicked(MouseEvent event) throws IOException {
        if(normalRadioButton.isSelected()) {
            NBWORDS = (int) nbwordsSlider.getValue();
            TIME = (int) timeSlider.getValue();
            gameNormal = GameNormal.getInstance();
            gameNormal.start(new Stage());
        }
        else if(jeuSoloRadioButton.isSelected()) {
            PV = (int) pvSlider.getValue();
            QUEUELENGTH = (int) queueLengthSlider.getValue();
            gameJeu = GameJeu.getInstance();
            gameJeu.start(new Stage());
        }
        else if(jeuMultiRadioButton.isSelected()) {
            // TODO
        }

        btnJouer.getScene().getWindow().hide();
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }

}

