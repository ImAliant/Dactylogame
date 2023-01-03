package com.dactylogame;

import org.junit.jupiter.api.Test;

public class AppTest {
    //On veut tester que la méthode main ne plante pas
    @Test
    public void testMain() {
        System.out.println("\u001B[34mTEST MAIN APP\u001B[37m");
        boolean test = false;
        App.main(new String[] {});
        //Si on arrive ici, c'est que la méthode main ne plante pas
        test = true;
        assert(test);
        System.out.println("\u001B[34mFIN TEST MAIN APP\u001B[37m");
    }
}
