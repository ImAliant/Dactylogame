package com.dactylogame;

public interface GameMethods {
    public boolean checkWord();

    public void updateWord();

    public default void endGame() {
        System.out.println("Fin du jeu");
        System.exit(0);
    }
}
