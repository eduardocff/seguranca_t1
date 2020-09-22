package com.company;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        TextUtil textUtil = new TextUtil();
        KeyUtil keyUtil = new KeyUtil();

        System.out.println("Começando mais um trab e esperando meu peixe cru...");

        String encryptedText = "";
        encryptedText = textUtil.readFile("cifrado.txt");
        encryptedText.toLowerCase();

        //Verifica o tamanho do texto (maior que 30 caracteres)
        if (!textUtil.verifySize(encryptedText)) {
            System.out.println("Texto com poucos caracteres (menor que 30).");
            System.out.println("Finalizando.. tente novamente com um texto maior.");
        }

        int keySize = keyUtil.keySize(encryptedText, "portuguese");

        



    }
}
