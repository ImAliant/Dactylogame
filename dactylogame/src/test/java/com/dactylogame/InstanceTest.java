package com.dactylogame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.dactylogame.controller.MainSceneController;

public class InstanceTest {

    @Test
    public void testInstanceGameNormalConfiguration() {
        MainSceneController.setNbWords(50);
        MainSceneController.setTime(30);

        System.out.println("\u001B[34mTest de l'instance de la classe GameNormalConfiguration\u001B[37m");
        GameNormalConfiguration config = GameNormalConfiguration.getInstance();
        assert(config != null);
        GameNormalConfiguration testConfig = GameNormalConfiguration.getInstance();
        //On vérifie que config et testConfig sont les mêmes objets
        assertEquals(config, testConfig);
        System.out.printf("config = %s, testConfig = %s\n", config, testConfig);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        GameNormalConfiguration.reset();
    }

    @Test
    public void testInstanceGameJeuConfiguration() {
        System.out.println("\u001B[34mTest de l'instance de la classe GameJeuConfiguration\u001B[37m");
        GameJeuConfiguration configJeu = GameJeuConfiguration.getInstance();
        assert(configJeu != null);
        GameJeuConfiguration testConfigJeu = GameJeuConfiguration.getInstance();
        //On vérifie que configJeu et testConfigJeu sont les mêmes objets
        assertEquals(configJeu, testConfigJeu);
        System.out.printf("configJeu = %s, testConfigJeu = %s\n", configJeu, testConfigJeu);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        GameJeuConfiguration.reset();
    }

    @Test
    public void testInstanceGameNormal() {
        System.out.println("\u001B[34mTest de l'instance de la classe GameNormal\u001B[37m");
        GameNormal gameN = GameNormal.getInstance();
        assert(gameN != null);
        GameNormal testGameN = GameNormal.getInstance();
        //On vérifie que gameN et testGameN sont les mêmes objets
        assertEquals(gameN, testGameN);
        System.out.printf("gameN = %s, testGameN = %s\n", gameN, testGameN);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        GameNormal.reset();
    }

    @Test
    public void testInstanceGameJeu() {
        System.out.println("\u001B[34mTest de l'instance de la classe GameJeu\u001B[37m");
        GameJeu gameJ = GameJeu.getInstance();
        assert(gameJ != null);
        GameJeu testGameJ = GameJeu.getInstance();
        //On vérifie que gameJ et testGameJ sont les mêmes objets
        assertEquals(gameJ, testGameJ);
        System.out.printf("gameJ = %s, testGameJ = %s\n", gameJ, testGameJ);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        GameJeu.reset();
    }

    @Test
    public void testInstanceResultNormal() {
        System.out.println("\u001B[34mTest de l'instance de la classe ResultNormal\u001B[37m");
        ResultNormal resultN = ResultNormal.getInstance();
        assert(resultN != null);
        ResultNormal testResultN = ResultNormal.getInstance();
        //On vérifie que resultN et testResultN sont les mêmes objets
        assertEquals(resultN, testResultN);
        System.out.printf("resultN = %s, testResultN = %s\n", resultN, testResultN);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        ResultNormal.reset();
    }

    @Test
    public void testInstanceResultJeu() {
        System.out.println("\u001B[34mTest de l'instance de la classe ResultJeuSolo\u001B[37m");
        ResultJeuSolo resultJ = ResultJeuSolo.getInstance();
        assert(resultJ != null);
        ResultJeuSolo testResultJ = ResultJeuSolo.getInstance();
        //On vérifie que resultJ et testResultJ sont les mêmes objets
        assertEquals(resultJ, testResultJ);
        System.out.printf("resultJ = %s, testResultJ = %s\n", resultJ, testResultJ);
        System.out.println("\u001B[34mTESTS OK\u001B[37m");
        System.out.println("-------------------------------------------------------");

        ResultJeuSolo.reset();
    }
}
