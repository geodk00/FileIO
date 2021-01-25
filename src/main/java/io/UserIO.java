package io;

import java.util.Scanner;

import handlers.Handler;

/*
 * Methods to handle io to and 
 * from the user
 *
 */

public class UserIO {
    private static final String PROMPT = "-> ";


    /*
     * Convert a Handler[] to a String[] and pass it to getInput(String[], String).
     * Appends an "Exit" to the String[]. 
     */
    public static int getInput(Handler[] options, String header) {
        String[] stringArray = new String[options.length + 1];

        for (int i = 0; i < options.length; i++) {
            stringArray[i] = options[i].getDescription();
        }

        stringArray[stringArray.length - 1] = "Exit";
        return getInput(stringArray, header);
    }

    /*
     * Displays a String[] as options to the user and gets their
     * selection.
     */ 
    public static int getInput(String[] options, String header) {
        System.out.printf("%n%s%n", header);

        for (int i = 0; i < options.length; i++) {
            System.out.printf("[%3d| %-30s ]%n", i, options[i]);
        }

        return getPositiveInt(PROMPT, options.length - 1);
    }

    /*
     * Get an int from the user such that 
     *  0 <= n < max
     * (I count 0 as positive...)
     */
    public static int getPositiveInt(String prompt, int max) {
        Scanner scanner = new Scanner(System.in);

        //Use newline as separator so we don't leave a '\n' behind when using nextInt() 
        scanner.useDelimiter(System.lineSeparator());

        int input = -1;

        while (input < 0 || input > max) {
            System.out.print(prompt);
            if (scanner.hasNext()) {
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                } else {
                    scanner.next();
                }
            }
        }
        return input;
    }


    /*
     * Get a string from the user. 
     * Only returns the first token entered
     */ 
    public static String getStringInput(String prompt) {
        String input = "";
        Scanner scanner = new Scanner(System.in);

        while (input.isEmpty()) {
            System.out.print(prompt);
            input = scanner.next();
        }
        
        return input;
    }


    /*
     * Print a String[] as a list
    */ 
    public static void printList(String[] output, String header) {
        System.out.print(header);
        for (String item : output) {
            System.out.printf("[ %-34s ]%n", item);
        }
    }   
}
