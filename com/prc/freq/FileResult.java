


package com.prc.freq;



import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.ArrayList;




public class FileResult {
    Map<String, Integer> wfreq,lfreq,cfreq,nfreq;
    final String filename;
    String topletters,topchars,topnums,topwords;
    final private int letterlmt,  charlmt,  numlmt,  wordlmt;

    public FileResult(String filename, int letterlmt,int charlmt,int numlmt,int wordlmt  ) {
        this.filename = filename;
        this.letterlmt=letterlmt;
        this.charlmt  =charlmt;
        this.numlmt=numlmt;
        this.wordlmt=wordlmt;
    }


    public void setWordFreq(Map<String, Integer> g) {
        this.wfreq=g;
    }
    public void setNumberFreq(Map<String, Integer> g) {
        this.nfreq=g;
    }
    public void setLetterFreq(Map<String, Integer> g) {
        this.lfreq=g;
    }
    public void setCharFreq(Map<String, Integer> g) {
        this.cfreq=g;
    }
    public Map<String, Integer> getWordFreq() {
        return wfreq;
    }
    public Map<String, Integer> getNumberFreq() {
        return nfreq;
    }
    public Map<String, Integer> getLetterFreq() {
        return lfreq;
    }
    public Map<String, Integer> getCharFreq() {
        return cfreq;
    }
    public String getFileName() {
        return this.filename;
    }

    private String report(Map<String, Integer> m, int limit) {
        List skeys = sortByValue(m);
        StringBuilder sb = new StringBuilder();
        int done = Math.min(limit,skeys.size());

        for ( int i =0;i<done;i++ ) {
            sb.append( skeys.get(i) ).append(" ");
        }
        return sb.toString();
    }

    public void runreport() {
        topletters = report(lfreq,letterlmt);
        topchars   = report(cfreq,charlmt);
        topnums    = report(nfreq,numlmt);
        topwords   = report(wfreq,wordlmt);
    }

    public String topLetters() {
        return topletters ==null ? report(lfreq,letterlmt) : topletters;
    }

    public String topChars() {
        return topchars == null ? report(cfreq,charlmt) : topchars;
    }

    public String topNums() {
        return topnums == null ? report(nfreq,numlmt) : topnums;
    }

    public String topWords() {
        return topwords == null ? report(wfreq,wordlmt) : topwords;
    }


    private List sortByValue(final Map m) {
        List keys = new ArrayList();
        keys.addAll(m.keySet());
        Collections.sort(keys, new Comparator() {
                             public int compare(Object o1, Object o2) {
                                 Integer v1 = (Integer)m.get(o1);
                                 Integer v2 = (Integer)m.get(o2);
                                 if ( v1>v2 ) {
                                     return -1;
                                 }
                                 else if ( v2>v1 ) {
                                     return 1;
                                 }
                                 else return 0;


                             }
                         });
        return keys;
    }
}

