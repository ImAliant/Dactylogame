package com.dactylogame;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MainSceneController {

    @FXML
    void OnClickedJouer(MouseEvent event) {
        System.out.println("Jouer");
    }

    @FXML
    void OnClickedOptions(MouseEvent event) {
        System.out.println("Options");
    }

    @FXML
    void OnClickedQuitter(MouseEvent event) {
        System.exit(0);
    }
}

