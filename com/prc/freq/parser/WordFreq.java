


package com.prc.freq.parser;


import java.util.Scanner;


public class WordFreq extends LineParser {
    public void parse(String aLine) {
        Scanner scanner = new Scanner(aLine);
        scanner.useDelimiter("[^A-Za-z]+");

        while ( scanner.hasNext() ) {
            String word = scanner.next();
            if ( freq.containsKey(word) ) {
                int count = freq.get(word);
                freq.put(word, ++count);
            }
            else {
                freq.put(word,1);
            }
        }
    }
}

