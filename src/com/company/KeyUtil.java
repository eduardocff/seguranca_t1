package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyUtil {
    public static final double PORTUGUESE_IC = 0.072723;
    public static final double ENGLISH_IC = 0.0;

    //Dicionário (map) para auxiliar no cálculo dos indices
    public Map<String, Double> alphabetAux;

    //Map que contem os guardar os indices para medir e definir o tamnho da chave
    public Map<Integer, Double> keySizeAux;

    //Apenas definição de um tamanho máximo da chave. 26, apenas o tamanho do alfabeto.
    public static final int keyMaxSize = 26;

    public List<Double> indexCoincidenceList;

    KeyUtil () {
        alphabetAux = new HashMap<>();
        alphabetAux.put("a", 0.0);
        alphabetAux.put("b", 0.0);
        alphabetAux.put("c", 0.0);
        alphabetAux.put("d", 0.0);
        alphabetAux.put("e", 0.0);
        alphabetAux.put("f", 0.0);
        alphabetAux.put("g", 0.0);
        alphabetAux.put("h", 0.0);
        alphabetAux.put("i", 0.0);
        alphabetAux.put("j", 0.0);
        alphabetAux.put("k", 0.0);
        alphabetAux.put("l", 0.0);
        alphabetAux.put("m", 0.0);
        alphabetAux.put("n", 0.0);
        alphabetAux.put("o", 0.0);
        alphabetAux.put("p", 0.0);
        alphabetAux.put("q", 0.0);
        alphabetAux.put("r", 0.0);
        alphabetAux.put("s", 0.0);
        alphabetAux.put("t", 0.0);
        alphabetAux.put("u", 0.0);
        alphabetAux.put("v", 0.0);
        alphabetAux.put("w", 0.0);
        alphabetAux.put("x", 0.0);
        alphabetAux.put("y", 0.0);
        alphabetAux.put("z", 0.0);

        keySizeAux = new HashMap<>();
        keySizeAux.put(1, 0.0);
        keySizeAux.put(2, 0.0);
        keySizeAux.put(3, 0.0);
        keySizeAux.put(4, 0.0);
        keySizeAux.put(5, 0.0);
        keySizeAux.put(6, 0.0);
        keySizeAux.put(7, 0.0);
        keySizeAux.put(8, 0.0);
        keySizeAux.put(9, 0.0);
        keySizeAux.put(10, 0.0);
        keySizeAux.put(11, 0.0);
        keySizeAux.put(12, 0.0);
        keySizeAux.put(13, 0.0);
        keySizeAux.put(14, 0.0);
        keySizeAux.put(15, 0.0);
        keySizeAux.put(16, 0.0);
        keySizeAux.put(17, 0.0);
        keySizeAux.put(18, 0.0);
        keySizeAux.put(19, 0.0);
        keySizeAux.put(20, 0.0);
        keySizeAux.put(21, 0.0);
        keySizeAux.put(22, 0.0);
        keySizeAux.put(23, 0.0);
        keySizeAux.put(24, 0.0);
        keySizeAux.put(25, 0.0);
        keySizeAux.put(26, 0.0);

        indexCoincidenceList = new ArrayList<>();
    }

    public int keySize(String encryptedText, String language) {

        //verifica a lingua informada pelo usuário e seta o indice de coincidencia
        //para a respectiva lingua
        double languageIc = 0.0;
        if (language.equalsIgnoreCase("portuguese")) {
            languageIc = PORTUGUESE_IC;
        } else if (language.equalsIgnoreCase("english")) {
            languageIc = ENGLISH_IC;
        }

        double average = 0.0;
        for (int mIndex = 0; mIndex < keyMaxSize; mIndex++) {
            int i = 0;

            for (; i <= mIndex; i++) {
                frequencyChars(encryptedText, i, mIndex);
            }

            System.out.println("frequency finished..");
            average = average();
            System.out.println("Average for mIndex: " + mIndex + " finished...");
            indexCoincidenceList.clear();

            System.out.println("mIndex: " + mIndex);
            System.out.println("average: " + average);
            System.out.println(" ----- ");
            keySizeAux.replace(mIndex, average);
            average = 0.0;
        }

        int bestKey = getBestKey(languageIc);
        return bestKey;
    }

    public double average() {
        double average = 0;
        double averageAux = 0.0;

        for(double ic : indexCoincidenceList) {
            averageAux = averageAux + ic;
        }

        if (averageAux > 0) {
            average = averageAux / indexCoincidenceList.size();
        }

        return average;
    }

    public void frequencyChars(String encryptedText, int i, int m) {
//        System.out.println("------");
//        System.out.println("i: " + i);
//        System.out.println("m: " + m);
        int aux = 0;
        double sum = 0;
        double indexCoincidence = 0;

        String encryptedSubString = encryptedText.substring(i,m);
//System.out.println("substring: " + encryptedSubString);
        char[] charArrayEncrypted = encryptedSubString.toCharArray();

        for(char a : charArrayEncrypted) {
            String charA = Character.toString(a);
            alphabetAux.replace(charA,alphabetAux.get(charA) + 1);
            aux++;
        };

        for(String letter : alphabetAux.keySet()) {
            sum = sum + (alphabetAux.get(letter) * (alphabetAux.get(letter) - 1));
            alphabetAux.replace(letter, 0.0);
        }

//System.out.println("sum: " + sum);

        //Realiza essa verificação para não ocorrerem NaN, pois poderia ter 0/algo..
        if (sum > 0) {
            indexCoincidence = sum / ( aux * ( aux - 1));
        }

//System.out.println("indexCoincidence: " + indexCoincidence);

//System.out.println("------");
        indexCoincidenceList.add(indexCoincidence);
    };

    //Passa por todos os ic gerados e pega o com "menor" distância do IC da linguagem escolhida
    public int getBestKey (double languageIc) {
        int bestKey = 0;
        double iDistance;
        double distance = 0;
        for (int i : keySizeAux.keySet()) {
            iDistance = Math.abs(keySizeAux.get(i) - languageIc);

            if (distance == 0) {
                distance = iDistance;
            }

            if(iDistance <= distance) {
//System.out.println("iDistance <= distance");
//System.out.println("iDistance: " + iDistance);
//System.out.println("Distance: " + distance);
//.out.println("i: " + i);
                distance = iDistance;
                bestKey = i;
            }
        }
        System.out.println(" ---------");
        System.out.println(" - Melhor tamanho de chave encontrado: " + bestKey);
        System.out.println(" ---------");
        return bestKey;
    }
}
