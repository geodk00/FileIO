import handlers.Handler;
import handlers.Listing;
import handlers.Stats;
import io.UserIO;


public class Main {

    //The options to display to the user.
    static final Handler[] MAIN_OPTIONS = {new Listing(), new Stats()};
    static final String PATH = "../src/main/resources";

    public static void main(String[] args) {
        int selection = 0;
        while (selection >= 0) {
            selection = UserIO.getInput(MAIN_OPTIONS, "Main Menu");

            //since this is a Handler array and not a String array, getInput() 
            //will add a special "exit" entry to index == MAIN_OPTIONS.length
            if (selection < MAIN_OPTIONS.length) {
                MAIN_OPTIONS[selection].handle();
            } else if (selection == MAIN_OPTIONS.length) {
                selection = -1;
            }
        }
        
    }
}