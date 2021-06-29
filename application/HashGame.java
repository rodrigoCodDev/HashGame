package application;

import java.util.Scanner;


public class HashGame {

    // STATIC VARIABLES
    private static boolean round = true; // Defines the round of game (player or computer)
    private static String position = ""; // Position especifiqued of player

    //Table of game
    private static String[][] table = {
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"}
    };
    

    // STATIC METHODS

    // This method returns the number of position Y on the table
    private static int charToYPosition(char myChar) {
        return ((int) myChar) - 97;
    }

    // This method returns the letter of position Y on the table
    private static char yPositionToChar(int number) {
        return ((char) (97 + number));
    }

    // This method show the table
    private static void showTable() {
        System.out.println("   1  2  3");
        
        for (int i = 0; i < table.length; i++) {
            char letter = yPositionToChar(i);
            System.out.print(letter + " ");

            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j]);
            }

            System.out.println();
        }
    }

    // MAIN
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        System.out.println("Hash Game".toUpperCase());  
        
        do{
            showTable();
            System.out.print("Enter a position(row and column) to mark: ");
            position = input.nextLine();
            
            int row = charToYPosition(position.charAt(0));
            int column = Integer.parseInt("" + position.charAt(1)) - 1;

            table[row][column] = "{X}";

        } while(true);
        
    }
}
