package io;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Methods related to listing files from 
 * the filesystem
 */
public class FileListing {

    /*
     * return a String[] with all possible extension in a directory
    */
    public static String[] getExtensions(String path) {
        String[] files = getDirListing(path, "");
        
        //Use a set to keep unique extensions
        Set<String> extensions = new HashSet<>();

        for (String file : files) {
            //assume anything after the last '.' in the file is an extension
            int index = file.lastIndexOf('.');
            if (index >= 0)
                extensions.add(file.substring(index));
        }

        return extensions.toArray(String[]::new);
    }

    /*
     * Returns a String[] with files in a directory with the given extension.
     * ext = "" for all files.
     */
    public static String[] getDirListing(String path, String ext) {
        File dir = new File(path);
        String pattern = String.format(".+\\Q%s\\E$", ext);

        return Arrays.stream(dir.list()).filter(s -> s.matches(pattern)).toArray(String[]::new);
    }
}