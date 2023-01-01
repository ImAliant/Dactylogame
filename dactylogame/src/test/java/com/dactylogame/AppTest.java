package com.dactylogame;

import org.junit.jupiter.api.Test;

public class AppTest {
    //On veut tester que la méthode main ne plante pas
    @Test
    public boolean testMain() {
        System.out.println("Test du lanceur de l'application");
        boolean test = false;
        App.main(new String[] {});
        //Si on arrive ici, c'est que la méthode main ne plante pas
        test = true;
        assert(test);
        return test;
    }
}
