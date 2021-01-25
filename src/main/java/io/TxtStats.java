package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.Logger;


/**
 * Methods to get stats about
 * a .txt file. 
 */

public class TxtStats {
    private static final String NAME_KEY = "NAME";
    private static final String SIZE_KEY = "SIZE";
    private static final String LINE_KEY = "LINE";
    private static final String SEARCH_KEY = "NAME";
    private static final String LOAD_KEY = "NAME";


 /*
    public static void getFileStats(String path) {
        List<String> lines;
        Map<String, Integer> words;

        File file = new File(path);
        Logger logger = Logger.getLogger();

        String searchTerm = UserIO.getStringInput(String.format("Word to search for: "));

        //Name of file
        logger.prime(NAME_KEY);
        logger.log(NAME_KEY, String.format("Name of file: %s", path.substring(path.lastIndexOf('/') + 1)));
        
        //Size of file
        logger.prime(SIZE_KEY);
        logger.log(SIZE_KEY, String.format("Size of file: %d bytes", file.length()));

        //Lines in file
        logger.prime(LINE_KEY);
        lines = loadFile(file);
        if (lines != null)
            logger.log(LINE_KEY, String.format("lines (incl. empty lines): %d lines", lines.size()));

        //Search for word, loading again to compare to other search methods and their way of loading the file       
        logger.prime(SEARCH_KEY);
        lines = loadFile(file);
        long result = doNaiveSearch(lines, searchTerm);
        logger.log(SEARCH_KEY, String.format("Search for '%s' found %d matches", searchTerm, result));

        logger.prime(SEARCH_KEY);
        words = loadFileToMap(file);
        long mapResult = doMapSearch(words, searchTerm);
        logger.log(SEARCH_KEY, String.format("Map search for '%s' found %d matches", searchTerm, mapResult));

    }
*/
    /*
     * Performs a naive search on a list of lines by counting the matches of a 
     * regex on each line
     */ 
    public static long doNaiveSearch(List<String> lines, String word) {
        Pattern pattern = Pattern.compile(String.format("(?:^|\\s)\\Q%s\\E(?:\\s|$)", word), Pattern.CASE_INSENSITIVE);
        //?:    do not remember groups
        //^     beginning
        //\s    whitespace
        //\Q \E escape
        //$     end

        long hits = 0;
        
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            hits += matcher.results().count();
        }

        return hits;
    }

    /*
     * Perform a search on a map of word -> count (aka just a lookup)
    */
    public static long doMapSearch(Map<String, Integer> words, String word) {
        if (words.containsKey(word.toLowerCase()))
            return words.get(word.toLowerCase());
        return 0;
    }

    /*
     * Load the lines of a text file into a List
    */ 
    public static List<String> loadFile(File file) {
        Logger.getLogger().prime(LOAD_KEY);
        ArrayList<String> lines = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger().log(LOAD_KEY, "Stat file not found");
            System.exit(-1);
        }
            
        Logger.getLogger().log(LOAD_KEY, "file loaded into list");
        return lines;
            
    } 

    /*
     * Load the words of a text file in a map such that word -> count
     */
    public static Map<String, Integer> loadFileToMap(File file) {
        Logger.getLogger().prime(LOAD_KEY);
        Map<String, Integer> words = new HashMap<>();
        String next;

        try {
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                next = scanner.next().toLowerCase();
                if (words.containsKey(next))
                    words.put(next, words.get(next) + 1);
                else
                    words.put(next, 1);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger().log(LOAD_KEY, "Stat file not found");
            System.exit(-1);
        }
            
        Logger.getLogger().log(LOAD_KEY, "file loaded into map");
        return words;
    }



}