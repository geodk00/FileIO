package util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
 * Simple implementation of a logger.
 * Expects a call to prime() before a
 * call to log(). 
 */

public class Logger {
    private static final Path logDir = Path.of("./log");
    private static final OpenOption[] options = new OpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.APPEND };

    //Singleton instance
    private static Logger instance;
    
    //The file we log to
    private Path file;
    //key store for timing purposes
    private Map<String,Long> times;


    private Logger() {
        createDir();
        String filename = new SimpleDateFormat("yyyy-MM-dd HHmmss").format(new Date()) + ".txt";
        this.file = logDir.resolve(filename);
        this.times = new HashMap<>();
        
    }

    //Create the log directory if it does not exist
    private void createDir() {
        if (!logDir.toFile().exists()) {
            try {
                Files.createDirectory(logDir);
            } catch (IOException ex) {
                System.out.println("Could not create log directory");
                System.exit(-1);
            }
        }
    }

    //call before log().
    public void prime(String key) {
        times.put(key, System.currentTimeMillis());
    }

    public void log(String key, String out) {
        //Get the current time before we do anything to 
        //which would add to the logged duration
        Long now = System.currentTimeMillis();

        if (times.containsKey(key)) {
            String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
            String finalOutput = String.format("%s | %4d ms | %s%n", time, now - this.times.get(key), out);

            //write to the console and the file
            System.out.print(finalOutput);
            writeFile(finalOutput);

        } else {
            System.out.println("Someone didn't prime the logger with key: " + key);
        }
    }


    private void writeFile(String output) {
        try {
            Files.writeString(this.file, output, StandardCharsets.UTF_8, options);
        } catch (IOException ex) {
            System.out.println("Failed to open file: " + this.file);
            System.out.println(ex);
        }
    }

    //Implemented this as a naive singleton for convenience since
    //we haven't covered dependency injection yet
    public static Logger getLogger() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

}
