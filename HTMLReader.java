

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



import com.prc.freq.HTMLProcessor;
import com.prc.freq.IProcessor;
import com.prc.freq.FileResult;
import com.prc.freq.parser.*;



public class HTMLReader {

    public static void main(String[] ff) {
        //String ff[] = {"http://www.google.com"};
        IProcessor rd = new HTMLProcessor();

        rd.process(ff);
    }



}
