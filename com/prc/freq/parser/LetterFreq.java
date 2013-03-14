


package com.prc.freq.parser;


import java.util.Scanner;

public class LetterFreq extends LineParser {
    public void parse(String aLine) {
        String sp[]=aLine.split("");
        for ( int i=0;i<sp.length;i++ ) {
            String s = sp[i];
            if ( s.matches("[A-Za-z]") ) {
                if ( freq.containsKey(s) ) {
                    int count = freq.get(s);
                    freq.put(s, ++count);
                }
                else {
                    freq.put(s,1);
                }
            }
        }
    }
}

