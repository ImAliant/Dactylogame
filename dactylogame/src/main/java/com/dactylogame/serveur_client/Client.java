package com.dactylogame.serveur_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javafx.application.Platform;

public class Client extends Thread {
    private String pseudo;
    private int port;

    private Socket connection;
    private InputStream in;
    private OutputStream out;

    private String receivedWord;

    public Client(String pseudo, int port) {
        this.pseudo = pseudo;
        this.port = port;
    }
    
    private void runClient() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    connection = new Socket("localhost", port);
                    System.out.println("Connection established");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void activate() {
        new Thread(()-> runClient()).start();
    }

    public void closeClient() {
        try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String word) {
        // Le joueur envoie un mot tapée correctement vers l'adversaire.
        try {
            out.write(word.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reception() {
        // Le joueur recoit un mot tapé correctement par l'adversaire.
        try {
            byte[] buffer = new byte[1024];
            in.read(buffer);
            receivedWord = new String(buffer);
            // On envoi ce mot dans la file d'attente du joueur.
            //TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopClient() {
    }

}
