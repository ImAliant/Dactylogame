package com.dactylogame;

import static org.junit.jupiter.api.Assertions.assertEquals;

//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.dactylogame.controller.MainSceneController;

//import com.dactylogame.controller.MainSceneController;

//import javafx.application.Platform;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Stage;

public class InstanceTest {
    //On va tester l'instance du mode solo (Normal)
    @Test
    public void testInstance() {
        System.out.println("\u001B[34mTESTS DES INSTANCES DE DACTYLOGAME\u001B[37m");
        // Initialisation des choix utilisateurs (par défaut)
        MainSceneController.setTime(30);
        MainSceneController.setNbWords(60);

        System.out.println("Test de l'instance de la classe GameNormalConfiguration");
        GameNormalConfiguration config = GameNormalConfiguration.getInstance();
        assert(config != null);
        GameNormalConfiguration testConfig = GameNormalConfiguration.getInstance();
        //On vérifie que config et testConfig sont les mêmes objets
        assertEquals(config, testConfig);
        System.out.printf("config = %s, testConfig = %s\n", config, testConfig);
        System.out.println("-------------------------------------------------------");
        System.out.println("Test de l'instance de la classe GameJeuConfiguration");
        GameJeuConfiguration configJeu = GameJeuConfiguration.getInstance();
        assert(configJeu != null);
        GameJeuConfiguration testConfigJeu = GameJeuConfiguration.getInstance();
        //On vérifie que configJeu et testConfigJeu sont les mêmes objets
        assertEquals(configJeu, testConfigJeu);
        System.out.printf("configJeu = %s, testConfigJeu = %s\n", configJeu, testConfigJeu);
        System.out.println("-------------------------------------------------------");
        System.out.println("Test de l'instance de la classe GameNormal");
        GameNormal gameN = GameNormal.getInstance();
        assert(gameN != null);
        GameNormal testGameN = GameNormal.getInstance();
        //On vérifie que gameN et testGameN sont les mêmes objets
        assertEquals(gameN, testGameN);
        System.out.printf("gameN = %s, testGameN = %s\n", gameN, testGameN);
        System.out.println("-------------------------------------------------------");
        System.out.println("Test de l'instance de la classe GameJeu");
        GameJeu gameJ = GameJeu.getInstance();
        assert(gameJ != null);
        GameJeu testGameJ = GameJeu.getInstance();
        //On vérifie que gameJ et testGameJ sont les mêmes objets
        assertEquals(gameJ, testGameJ);
        System.out.printf("gameJ = %s, testGameJ = %s\n", gameJ, testGameJ);
        System.out.println("-------------------------------------------------------");
        System.out.println("Test de l'instance de la classe ResultNormal");
        ResultNormal resultN = ResultNormal.getInstance();
        assert(resultN != null);
        ResultNormal testResultN = ResultNormal.getInstance();
        //On vérifie que resultN et testResultN sont les mêmes objets
        assertEquals(resultN, testResultN);
        System.out.printf("resultN = %s, testResultN = %s\n", resultN, testResultN);
        System.out.println("-------------------------------------------------------");
        System.out.println("Test de l'instance de la classe ResultJeuSolo");
        ResultJeuSolo resultJ = ResultJeuSolo.getInstance();
        assert(resultJ != null);
        ResultJeuSolo testResultJ = ResultJeuSolo.getInstance();
        //On vérifie que resultJ et testResultJ sont les mêmes objets
        assertEquals(resultJ, testResultJ);
        System.out.printf("resultJ = %s, testResultJ = %s\n", resultJ, testResultJ);
        System.out.println("-------------------------------------------------------");
        System.out.println("\u001B[34mTESTS INSTANCE OK\u001B[37m");
    }
}
