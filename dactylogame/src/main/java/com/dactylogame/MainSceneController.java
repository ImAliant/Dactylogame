package com.dactylogame;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainSceneController {

    private GameNormal game;

    @FXML
    private Label title;
    
    @FXML
    private Button btnJouer;

    @FXML
    private Button btnQuitter;

    @FXML
    private Button btnOptions;

    public void initialize() throws Exception{
        game = GameNormal.getInstance();
    }

    @FXML
    private void btnJouerClicked(MouseEvent event) throws IOException { 
        try {
            game.start(new Stage());
            btnJouer.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnOptionsClicked(MouseEvent event) {
        System.out.println("Options\n");
    }

    @FXML
    private void btnQuitterClicked(MouseEvent event) {
        System.exit(0);
    }
}

