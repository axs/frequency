

package com.prc.freq.parser;

import java.util.Scanner;


public class NumberFreq extends LineParser {
        public void parse(String aLine) {
            Scanner scanner = new Scanner(aLine);
            scanner.useDelimiter("[^0-9]+");


            while ( scanner.hasNext() ) {
                String num  = scanner.next();
                if ( freq.containsKey(num) ) {
                    int count = freq.get(num);
                    freq.put(num, ++count);
                }
                else {
                    freq.put(num,1);
                }
            }
        }
    }
