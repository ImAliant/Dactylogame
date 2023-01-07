package com.dactylogame;

import org.junit.jupiter.api.Test;

import com.dactylogame.controller.MainSceneController;

public class ChoixUtilisateurTest {
    @Test
    public void testChoixUtilisateur() {
        System.out.println("\u001B[34mTESTS DES CHOIX UTILISATEURS\u001B[37m");
        System.out.println("CHOIX POUR LE MODE NORMAL");
        // Initialisation des choix utilisateurs (par défaut)
        MainSceneController.setTime(30);
        MainSceneController.setNbWords(60);
        //On vérifie que les choix utilisateurs sont bien ceux par défaut
        
        GameNormalConfiguration config = GameNormalConfiguration.getInstance();
        assert(config != null);
        System.out.println("Temps : " + config.getTime() + " | Nombre de mots : " + config.getNbWords());
        System.out.printf("Temps: %d ; Choix temps: %d | Nombre de mots: %d; Choix nombre de mots: %d\n", config.getTime(), 30, config.getNbWords(), 60);
        assert(config.getTime() == 30 && config.getNbWords() == 60);

        GameNormal.reset();
        GameNormalConfiguration.reset();

        System.out.println("-------------------------------------------------------");
        System.out.println("CHOIX POUR LE MODE JEU");
        MainSceneController.setPv(100);
        MainSceneController.setQueueLength(25);

        GameJeuConfiguration configJeu = GameJeuConfiguration.getInstance();
        assert(configJeu != null);
        System.out.printf("PV: %d ; Choix PV: %d | Longueur de la file de mots: %d; Choix longueur file de mots: %d\n", GameJeuConfiguration.getPV(), 100, GameJeuConfiguration.getQUEUELENGTH(), 25);
        assert(GameJeuConfiguration.getPV() == 100 && GameJeuConfiguration.getQUEUELENGTH() == 25);

        GameJeu.reset();
        GameJeuConfiguration.reset();
        
        System.out.println("\u001B[34mFIN TESTS DES CHOIX UTILISATEURS\u001B[37m");
    }

    @Test
    public void testWordsQueue() {
        System.out.println("\u001B[34mTESTS DE LA LONGUEUR DE LA FILE DE MOTS\u001B[37m");
        // Initialisation des choix utilisateurs (par défaut)
        MainSceneController.setTime(60);
        MainSceneController.setNbWords(100);

        // Création de l'instance de la classe GameJeuConfiguration
        GameNormal game = GameNormal.getInstance();
        assert(game != null);
        // On vérifie que la file de mots a bien la bonne longueur
        System.out.println("Longueur de la file de mots : " + game.getNbWords());
        assert(game.getNbWords() == 100);
        System.out.println("La file de mots a bien la bonne longueur");

        GameNormalConfiguration.reset();
        GameNormal.reset();

        System.out.println("\u001B[34mFIN TESTS DE LA LONGUEUR DE LA FILE DE MOTS\u001B[37m");
    }
}
