package application;

import java.util.InputMismatchException;
import java.util.Scanner;
import enums.RoundGamer;
import exception.GameException;

public class UI {
    // USER INTERFACE

    // STATIC VARIABLES
    protected static boolean gameState = true;

    //Table of game
    private static String[][] table = {
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"}
    };

    
    // STATIC METHODS
    // ---------- TERMINAL MANIPULATION ----------

    // This method clear screen
    protected static void clearScreen() {  
        // https://stackoverflow.com/questions/2979383/java-clear-the-console
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    
    // This method shows the table
    protected static void showTable() {
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

    // This method shows "Draw Game"
    protected static void showDrawGame() {
        clearScreen();
        showTable();
        System.out.println("DRAW GAME!!!");
    }


    // ---------- CONVERTERS ----------

    // This method returns the number of position Y on the table
    private static int charToYPosition(char myChar) {
        return ((int) myChar) - 97;
    }

    // This method returns the letter of position Y on the table
    private static char yPositionToChar(int number) {
        return ((char) (97 + number));
    }

    // This method verify if the position in table is empty
    protected static boolean isEmptyOnTable(int[] position) {
        return table[position[0]][position[1]].equals("{ }");
    }


    // ---------- MATRIZ COORDENATES MANIPULATION ----------

    // This method returns the valid coordinates entered by the PLAYER
    protected static int[] insertPlayerCoordenates(Scanner input) {
        int row = 0;
        int column = 0;
        String linePosition = "";

        do {
            showTable();

            try {
                System.out.print("Enter a position(row and column) to mark: ");
                linePosition = input.nextLine();
                
                row = charToYPosition(linePosition.charAt(0));
                column = Integer.parseInt("" + linePosition.charAt(1)) - 1;
    
                if(row > 3 || column > 3) {
                    throw new GameException("Error: the position is invalid!!! Please, insert again.");
                }

                if (!table[row][column].equals("{ }")) {
                    throw new GameException("Error: already marked position!!! Please, insert again.");
                }

                break;
            } catch (GameException e) {
                clearScreen();
                System.err.println(e.getMessage());
                
            } catch(InputMismatchException e) {
                clearScreen();
                System.err.println("Error: I/O error!!! Please, insert again.");

            }
        } while (true);

        int[] position = {row, column};
        return position;
    }

    // This method set the symbol of RoundGamer on position on the table
    protected static boolean setCoordenatesOnTable(int[] position, RoundGamer gamer) {
        boolean win = true;
        String gamerSimbol = gamer.equals(RoundGamer.PLAYER) ? "{X}" : "{O}";
        
        table[position[0]] [position[1]] = gamerSimbol;
        win = validateWin(gamerSimbol);
        
        if (win) {
            clearScreen();
            showTable();
            System.out.println(gamer.name() + " WINS!!!");
        }
        
        return win;
    }
    
    // This method checks if the game is a draw
    protected static void confireDrawGame() {
        int cont = 0;

        for (int i = 0; i < table.length; i++) {

            for (int j = 0; j < table[i].length; j++) {
                if (!table[i][j].equals("{ }")) {
                    cont++;
                }
            }
        }

        if (cont == 9) {
            // Draw game
            gameState = false;
        }
    }

    // This method validate the Win of one RoundGamer
    private static boolean validateWin(String gamerSimbol) {
        int simbols = 0;

        // Search on rows
        for (int i = 0; i < table.length; i++) {

            simbols = 0;
            for (int j = 0; j < table[i].length; j++) {
                if (table[i][j].equals(gamerSimbol)) {
                    simbols++;
                }
            }

            if (simbols == 3) {
                // The RoundGamer wins
                return true;
            }
        }

        // Search on rows
        simbols = 0;
        for (int i = 0; i < table.length; i++) {

            simbols = 0;
            for (int j = 0; j < table[i].length; j++) {
                if (table[j][i].equals(gamerSimbol)) {
                    simbols++;
                }
            }

            if (simbols == 3) {
                // The RoundGamer wins
                return true;
            }
        }

        // Search on diagonals
        simbols = 0;
        for (int i = 0; i < table.length; i++) {
            if (table[i][i].equals(gamerSimbol)) {
                simbols++;
            }
        }

        if (simbols == 3) {
            // The RoundGamer wins
            return true;
        }
        

        simbols = 0;
        int di = 2; // Inversor (decrescent i)
        for (int i = 0; i < table.length; i++) {
            if (table[i][di].equals(gamerSimbol)) {
                simbols++;
            }

            di--;
        }

        if (simbols == 3) {
            // The RoundGamer wins
            return true;
        }


        // The game continue
        return false;
    }

}
