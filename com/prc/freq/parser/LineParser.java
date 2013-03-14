

package com.prc.freq.parser;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





public abstract class LineParser {
    Map<String,Integer> freq;
    abstract public void parse(String aLine);

    public LineParser() {
        this.freq= new ConcurrentHashMap<String, Integer>();
    }

    public Map<String,Integer> getfreqMap() {
        return freq;
    }
}

