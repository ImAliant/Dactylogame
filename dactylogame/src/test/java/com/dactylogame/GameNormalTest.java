package com.dactylogame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.dactylogame.controller.MainSceneController;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameNormalTest {
    private GameNormal game;

    //On va tester le mode solo (Normal)
    @Test
    public void testNormal() {
        System.out.println("Test du mode normal");
        //On créé la configuration pour les tests
        //On veut 200 mots pour être sûr de ne pas avoir de problème
        ArrayList<String> words = new ArrayList<String>(Arrays.asList("Ceci", "est", "un", "test", "unitaire", "pour", "le", "mode", "normal", "du", "jeu", "dactylo", "game"));
        for(int i = 0; i < 188; i++) {
            words.add("test");
        }
        GameNormalConfiguration.getInstanceTest(words);
        //On lance le jeu
        MainSceneController.setTime(30);
        game = GameNormal.getInstance();

        Platform.startup(() ->{
            try {
                game.start(new Stage());
            } catch (IOException e) {
                System.out.println("Erreur lors du lancement du mode de jeu normal");
                return;
            }
        });
        

        //On clique sur le label sur sa position sur l'écran
        game.lauchTest();
        //On transforme words en une liste d'appui de touche
        ArrayList<KeyEvent> events = new ArrayList<KeyEvent>();
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                events.add(new KeyEvent(KeyEvent.KEY_PRESSED, word.charAt(i) + "", word.charAt(i) + "", KeyCode.getKeyCode(word.charAt(i) + ""), false, false, false, false));
            }
            events.add(new KeyEvent(KeyEvent.KEY_PRESSED, " ", " ", KeyCode.SPACE, false, false, false, false));
        }
        //On simule l'appui des touches
        for (KeyEvent event : events) {
            //On appui sur la touche toutes les 1 secondes
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Erreur lors de l'attente");
                return;
            }
            game.handle(event);
        }
    }

    private String printWords(ArrayList<String> words) {
        String str = "";
        for (String word : words) {
            str += word + " ";
        }
        return str;
    }
}
