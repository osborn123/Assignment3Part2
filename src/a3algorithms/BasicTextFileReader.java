package a3algorithms;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static a3algorithms.Normaliser.normalise;

public class BasicTextFileReader {
    private BasicTextFileReader() {} // 01/04/2023 updated to have private visibility, do not change

     /* 01/04/2023 updated to remove incorrect comment about being the only code
                   in the package to read a file
      */
    /**
     * TODO: readFile: read all the words of a file.
     *  Process lines in order.
     *  Remove charsToDelete.
     *  Split each line into its words.
     *  Add a normalised version of each word but only if it is not already present.
     *  Do not add "words" that are blank.
     *  Sample input: input/*.txt
     *
     * @param filename
     * @return
     */
    public static List<String> readFile(String filename) {
        /**
         * The characters to remove from a word.
         */
        final String charsToDelete = "[^A-Za-z0-9'\\s]+";
        List<String> result = new ArrayList<>();

        try ( final Scanner sc = new Scanner(new File(filename)) ) {
            while ( sc.hasNextLine() ) {
                String line = sc.nextLine();

                String[] words = normalise(line.replaceAll(charsToDelete, "")).split("\\s+");
                for (String word : words) {
                    if(word.length() > 0) {
                        if(!result.contains(word)){
                            result.add(word);
                        }
                    }
                }

            }
        } catch ( FileNotFoundException e ) {
            throw new RuntimeException(e);
        }

        return result;
    }



}
