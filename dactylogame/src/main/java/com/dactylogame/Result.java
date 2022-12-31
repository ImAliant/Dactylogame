package com.dactylogame;

import java.io.IOException;

import javafx.stage.Stage;

/**
 * Classe abstraite mère de ResultNormal et ResultJeuSolo.
 * 
 * @author DIAMANT Alexandre
 */
public sealed abstract class Result permits ResultJeuSolo, ResultNormal {

    /**
     * Méthode permettant de lancer la fenêtre de résultat.
     * 
     * @param window Fenêtre de résultat.
     * @throws IOException Exception levée si le fichier fxml n'est pas trouvé.
     */
    public abstract void start(Stage window) throws IOException;
    
}
