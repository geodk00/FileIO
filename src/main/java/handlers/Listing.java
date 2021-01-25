package handlers;
import io.FileListing;
import io.UserIO;

/**
 * Handles everything to do with the
 * first and second requirements.
 * Delegates most of the actual work
 * to io.FileListing
 */
public class Listing implements Handler{
    private static final String PATH = "../src/main/resources/";

    static final String[] OPTIONS = {"List all files", "List files by extension", "Go Back"};
    static final int LIST = 0;
    static final int LISTEXT = 1;
    static final int EXIT = 2;


    @Override
    public String getDescription() {
        return "List files";
    }

    @Override
    public void handle() {
        int selection = 0;

        while (selection >= 0) {
            selection = UserIO.getInput(OPTIONS, "Listing Menu");

            switch(selection) {
                case LIST:
                    listFiles();
                    break;
                case LISTEXT:
                    listFilexExt();
                    break;
                case EXIT:
                    selection = -1;
                    break;
            }
        }
    }


    private static void listFiles() {
        String[] files = FileListing.getDirListing(PATH, "");

        if (files != null)
            UserIO.printList(files, String.format("%nListing: %s%n", PATH));
        else
            System.out.println("Path is not accessible");
    }


    private static void listFilexExt() {
        int selection = -1;
        String[] extensions = FileListing.getExtensions(PATH);

        while (selection < 0 || selection >= extensions.length) {
            selection = UserIO.getInput(extensions, "Please Pick an extension");
        }

        UserIO.printList(FileListing.getDirListing(PATH,extensions[selection]), String.format("%nListing files of type %s%n", extensions[selection]));

    }



}