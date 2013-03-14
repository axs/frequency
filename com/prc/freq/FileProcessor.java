


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


import java.io.FileReader;
import java.io.IOException;

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



public class FileProcessor implements IProcessor {
    final private ExecutorService exec;
    final private ArrayList<Future<FileResult>> mylist;

    public FileProcessor() {
        mylist = new ArrayList<Future<FileResult>>();
        exec = Executors.newFixedThreadPool( MAXTHREADS );
    }




    public void process(String ff[] ) {
        for ( int i =0;i<ff.length;i++ ) {
            Callable<FileResult> wcall = new FileParser(ff[i]);
            Future<FileResult> fut = exec.submit(wcall );
            mylist.add(fut);
        }

        for ( Future<FileResult> futra : mylist ) {
            try {
                FileResult m = futra.get();
                System.out.println("\n" + m.getFileName() );

                System.out.println("Top5Letters= " + m.topLetters());
                System.out.println("Top3Nums   = " + m.topNums());
                System.out.println("Top10Words = " + m.topWords());
                System.out.println("Top3Chars  = " + m.topChars());
            }
            catch ( Exception ef ) {
                ef.printStackTrace();
            }
        }

        exec.shutdown();
    }



    private  class FileParser implements Callable {
        private ArrayList<Map<String, Integer>> freq;
        final private String filename;
        final private LineParser lparser,wparser,cparser,nparser;
        private FileResult fresult;

        public FileParser(String filename ) {
            freq = new ArrayList<Map<String, Integer>>();
            this.filename = filename;
            this.lparser = new LetterFreq();
            this.wparser = new WordFreq();
            this.nparser = new NumberFreq();
            this.cparser = new CharFreq();
            this.fresult=new FileResult(this.filename,5,3,3,10);
        }

        private void process() {
            try {
                Scanner scanner = new Scanner(new FileReader(this.filename));
                try {
                    while ( scanner.hasNextLine() ) {
                        String line = scanner.nextLine();
                        lparser.parse(line);
                        wparser.parse(line);
                        cparser.parse(line);
                        nparser.parse(line);
                    }
                }
                finally {
                    scanner.close();
                }
            }
            catch ( Exception ex ) {
                ex.printStackTrace();
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
