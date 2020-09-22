package com.company;

import java.io.*;

public class TextUtil {
    TextUtil () {

    }

    public String readFile(String file) throws IOException {
        File fileToRead = new File(file);

        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));

        String encryptedText = "";
        String encryptedTextAux = "";

        while ((encryptedTextAux = bufferedReader.readLine()) != null) {
            encryptedText = encryptedTextAux;
            System.out.println(encryptedText);
        }

        return encryptedText;
    }

    //Devida a dificuldade de decifrar pequenos textos com a técnica de Vigenere, existe essa verificação
    public boolean verifySize(String text) {
        if (text.length() <= 30) {
            return false;
        }
        return true;
    }
}
