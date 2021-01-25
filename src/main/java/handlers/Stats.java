package handlers;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.TxtStats;
import util.Logger;
import io.UserIO;


/**
 * Handles everything to do with the
 * third requirement.
 * Delegates most of the actual work
 * to io.TxtStats
 */

public class Stats implements Handler {
    private static final String NAME_KEY = "NAME";
    private static final String SIZE_KEY = "SIZE";
    private static final String LINE_KEY = "LINE";
    private static final String SEARCH_KEY = "NAME";

    static final String PATH = "../src/main/resources/Dracula.txt";

    @Override
    public String getDescription() {
        return "Txt-file stats";
    }

    @Override
    public void handle() {
        List<String> lines;
        Map<String, Integer> words;

        File file = new File(PATH);
        Logger logger = Logger.getLogger();

        String searchTerm = UserIO.getStringInput(String.format("Word to search for: "));
    
        //Name of file
        logger.prime(NAME_KEY);
        logger.log(NAME_KEY, String.format("Name of file: %s", PATH.substring(PATH.lastIndexOf('/') + 1)));
        
        //Size of file
        logger.prime(SIZE_KEY);
        logger.log(SIZE_KEY, String.format("Size of file: %d bytes", file.length()));

        //Lines in file
        logger.prime(LINE_KEY);
        lines = TxtStats.loadFile(file);
        if (lines != null)
            logger.log(LINE_KEY, String.format("lines (incl. empty lines): %d lines", lines.size()));

        //Search for word, loading again to compare to other search methods and their way of loading the file       
        lines = TxtStats.loadFile(file);
        logger.prime(SEARCH_KEY);
        long result = TxtStats.doNaiveSearch(lines, searchTerm);
        logger.log(SEARCH_KEY, String.format("Search for '%s' found %d matches", searchTerm, result));

    
        words = TxtStats.loadFileToMap(file);
        logger.prime(SEARCH_KEY);
        long mapResult = TxtStats.doMapSearch(words, searchTerm);
        logger.log(SEARCH_KEY, String.format("Map search for '%s' found %d matches", searchTerm, mapResult));
    }    
}
