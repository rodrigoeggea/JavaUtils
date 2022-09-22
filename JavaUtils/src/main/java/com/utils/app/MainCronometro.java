package com.utils.app;

import com.utils.mylibs.Cronometro;

public class MainCronometro {

    public static void main(String[] args) throws InterruptedException {
        Cronometro cronometro = new Cronometro();
        System.out.println("=> Primeiro processo demorado...");
        cronometro.start("Primeiro processo demorado");
        Thread.sleep(1000); // Simula processo demorado
        cronometro.stop();

         System.out.println("=> Segundo processo demorado...");
         cronometro.start("Segundo processo demorado");
         Thread.sleep(3000); // Simula processo demorado
         //cronometro.start("Segundo processo demorado"); // Exception se chamar start sem stop.
         cronometro.stop();
        
         System.out.println("=> Terceiro processo demorado...");
         cronometro.start("Terceiro processo demorado");
         Thread.sleep(2000); // Simula processo demorado
         cronometro.stop();

        // Imprimi a tabela
        cronometro.prettyPrint();
    }
}
