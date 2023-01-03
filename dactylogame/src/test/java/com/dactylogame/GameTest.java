package com.dactylogame;

import static org.junit.jupiter.api.Assertions.fail;

//import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.dactylogame.controller.MainSceneController;

public class GameTest {
    //On va tester le mode solo (Normal)
    /*@Test
    public void testNormal() {
        GameNormal game;
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
    }*/

    @Test
    public void testNewGameNormal() {
        System.out.println("\u001B[34mTEST DE LA RELANCE DU MODE NORMAL\u001B[37m");
        MainSceneController.setTime(30);
        MainSceneController.setNbWords(60);

        GameNormalConfiguration.getInstance();

        GameNormal.getInstance();
        
        System.out.println("Test avec replay = false");
        try {
            GameNormal.newGame();
            fail("Doit lancer une IllegalStateException");
        }
        catch(IllegalStateException e) {
            System.out.println("L'exception a bien été lancée");
        }

        System.out.println("Test avec replay = true");
        GameNormal.setReplay(true);
        try {
            GameNormal.newGame();
        }
        catch(IllegalStateException e) {
            fail("Ne doit pas lancer d'exception");
        }
        System.out.println("Le jeu a bien été relancé");
        System.out.println("\u001B[34mFIN DU TEST DE LA RELANCE DU MODE NORMAL\u001B[37m");
    }

    @Test
    public void testNewGameJeu() {
        System.out.println("\u001B[34mTEST DE LA RELANCE DU MODE JEU\u001B[37m");
        MainSceneController.setPv(30);
        MainSceneController.setQueueLength(15);

        GameJeuConfiguration.getInstance();

        GameJeu.getInstance();
        
        // Test avec replay = false
        try {
            GameJeu.newGame();
            fail("Doit lancer une IllegalStateException");
        }
        catch(IllegalStateException e) {
            System.out.println("L'exception a bien été lancée");
            System.out.println("Le jeu ne peut pas être relancé si le replay n'est pas activé");
        }

        // Test avec replay = true
        GameJeu.setReplay(true);
        try {
            GameJeu.newGame();
        }
        catch(IllegalStateException e) {
            fail("Ne doit pas lancer d'exception");
        }
        System.out.println("Le jeu a bien été relancé");
        System.out.println("\u001B[34mFIN DU TEST DE LA RELANCE DU MODE JEU\u001B[37m");
    }

    /*private String printWords(ArrayList<String> words) {
        String str = "";
        for (String word : words) {
            str += word + " ";
        }
        return str;
    }*/

    
}
