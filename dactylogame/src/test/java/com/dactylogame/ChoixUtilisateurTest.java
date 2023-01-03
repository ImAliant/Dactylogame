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
        assert(config.getTime() == 30 && config.getNbWords() == 60);

        System.out.println("-------------------------------------------------------");
        System.out.println("CHOIX POUR LE MODE JEU");
        MainSceneController.setPv(100);
        MainSceneController.setQueueLength(25);

        GameJeuConfiguration configJeu = GameJeuConfiguration.getInstance();
        assert(configJeu != null);
        System.out.println("PV : " + GameJeuConfiguration.getPV() + " | Longueur de la file : " + GameJeuConfiguration.getQUEUELENGTH());
        assert(GameJeuConfiguration.getPV() == 100 && GameJeuConfiguration.getQUEUELENGTH() == 25);
        
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

        System.out.println("\u001B[34mFIN TESTS DE LA LONGUEUR DE LA FILE DE MOTS\u001B[37m");
    }
}
