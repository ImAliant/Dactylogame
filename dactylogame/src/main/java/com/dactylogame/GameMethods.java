package com.dactylogame;

public interface GameMethods {
    public boolean checkError();

    public void updateWord();

    public void resultats();

    public String printQueue();

    //public Game newGame();

    default void endGame() {
        System.out.println("Fin du jeu");
        System.exit(0);
    }
}
