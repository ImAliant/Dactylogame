package com.dactylogame.serveur_client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;

public class Server {

    private static boolean exist = false;
    
    private static int port;
    private final ExecutorService pool;
    private final List<ServerThread> clients;
    private boolean stop;

    private final int NEEDED_CLIENT = 2;

    public Server(int port) {
        this.port = port;
        pool = Executors.newFixedThreadPool(NEEDED_CLIENT);
        clients = new ArrayList<>();
    }
    
    private void runServer() {
        System.out.println("SERVER: Waiting for client");
        try {
            exist = true;
            ServerSocket serverSocket = new ServerSocket(port);
            stop = false;

            int i = 0;
            while (i < NEEDED_CLIENT) {
                Socket socket = serverSocket.accept();
                System.out.println("SERVER: Client connected");
                ServerThread st = new ServerThread(socket);
                pool.execute(st);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        for(ServerThread st: clients) {
            st.stopServerThread();
        }
        stop = true;
        pool.shutdown();
    }

    public void activate() {
        new Thread(()-> runServer()).start();
    }

    /*public void reception() {
        // On veut recevoir le mot tapé par un adversaire.
        try {
            byte[] buffer = new byte[1024];
            in.read(buffer);
            receivedWord = new String(buffer);
            // On envoi ce mot dans la file d'attente du joueur.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String word) {
        // On veut envoyer le mot recu d'un adversaire vers un autre adversaire.
        try {
            out.write(word.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
