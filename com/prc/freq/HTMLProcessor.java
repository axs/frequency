
/*

javac Reader.java
%JAVA_HOME%\bin\java -cp . Reader

*/



/*

1. Develop an application in Java that will satisfy the following requirements:

- Read in any number of files text files.
- Calculate 5 most commonly used letters
- Calculate 3 most commonly used characters
- Calculate 3 most commonly used numbers
- Calculate 10 most commonly used words
- Concurrently parse all files, however, limit your application to 2 parser threads.
If there are more files than available parser threads, you will need to queue files for parsing.


2. Write an application in Java that will pull down the HTML content of any number of
specified websites - single file per URL, no depth.  Strip out all metadata and generate
statistics by showing 2 most commonly used letters, numbers, characters and words.



*/



package com.prc.freq;


import java.net.*;

import java.io.*;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Scanner;


import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import com.prc.freq.FileResult;
import com.prc.freq.parser.*;



public class HTMLProcessor implements IProcessor {
    final private ExecutorService exec;
    final private ArrayList<Future<FileResult>> mylist;


    public HTMLProcessor() {
        mylist = new ArrayList<Future<FileResult>>();
        exec = Executors.newFixedThreadPool( MAXTHREADS );
    }


    public void process(String ff[] ) {
        for ( int i =0;i<ff.length;i++ ) {

            try {
                Callable<FileResult> wcall = new FileParser(ff[i]);
                Future<FileResult> fut = exec.submit(wcall );
                mylist.add(fut);


            }
            catch ( Exception exc ) {
            }
        }

        for ( Future<FileResult> futra : mylist ) {
            try {
                FileResult m = futra.get();
                System.out.println("\n" + m.getFileName() );

                System.out.println("Top2Letters= " + m.topLetters());
                System.out.println("Top2Nums   = " + m.topNums());
                System.out.println("Top2Words = " + m.topWords());
                System.out.println("Top2Chars  = " + m.topChars());
            }
            catch ( Exception ef ) {
                ef.printStackTrace();
            }
        }

        exec.shutdown();
    }



    private  class FileParser implements Callable {
        private ArrayList<Map<String, Integer>> freq;
        final private String urlname;
        final private LineParser lparser,wparser,cparser,nparser;
        private FileResult fresult;

        public FileParser(String urlname ) {
            freq = new ArrayList<Map<String, Integer>>();
            this.urlname = urlname;
            this.lparser = new LetterFreq();
            this.wparser = new WordFreq();
            this.nparser = new NumberFreq();
            this.cparser = new CharFreq();
            this.fresult=new FileResult(this.urlname,2,2,2,2);
        }

        private void process() {
            try {

                StringBuilder sb = new StringBuilder();

                URL url = new URL(urlname);
                URLConnection urlc = url.openConnection();
                BufferedReader in = new BufferedReader(
                                                      new InputStreamReader(urlc.getInputStream()));
                String inputLine;

                while ( (inputLine = in.readLine()) != null ) {
                    //System.out.println(inputLine);
                    sb.append(inputLine);
                }
                in.close();

                String noHTMLString = sb.toString().replaceAll("\\<.*?\\>", "");
                lparser.parse(noHTMLString);
                wparser.parse(noHTMLString);
                cparser.parse(noHTMLString);
                nparser.parse(noHTMLString);

            }
            catch ( Exception exc ) {
                exc.printStackTrace();
            }
        }


        public FileResult call() {
            process();
            fresult.setCharFreq( cparser.getfreqMap());
            fresult.setWordFreq( wparser.getfreqMap());
            fresult.setLetterFreq( lparser.getfreqMap());
            fresult.setNumberFreq( nparser.getfreqMap());
            fresult.runreport();
            return fresult;
        }
    }


}
