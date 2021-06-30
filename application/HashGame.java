package application;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import exception.GameException;

public class HashGame {

    // STATIC VARIABLES
    private static boolean gameState = true;
    private static Scanner input = new Scanner(System.in);

    //Table of game
    private static String[][] table = {
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"},
        {"{ }", "{ }", "{ }"}
    };


    // STATIC METHODS

    // This method clear screen
    public static void clearScreen() {  
        // https://stackoverflow.com/questions/2979383/java-clear-the-console
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    
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

    // This method returns the valid coordinates entered by the PLAYER
    private static int[] insertPlayerCoordenates() {
        int row = 0;
        int column = 0;
        String position = "";

        do {
            showTable();

            try {
                System.out.print("Enter a position(row and column) to mark: ");
                position = input.nextLine();
                
                row = charToYPosition(position.charAt(0));
                column = Integer.parseInt("" + position.charAt(1)) - 1;
    
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
        

        int[] positions = {row, column};
        return positions;
    }

    // This method validate the Win of one RoundGamer
    private static boolean validateWin(RoundGamer gamer) {
        String gamerSimbol = gamer.equals(RoundGamer.PLAYER) ? "{X}" : "{O}";
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

    public static void confireDrawGame() {
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


    // MAIN
    public static void main(String[] args) {
        boolean win = false; // Validates the end and continuation of the game
        Random randomGenerator = new Random(); // Random generator of numbers
        RoundGamer gamer = RoundGamer.PLAYER; // Defines the round of game (player or computer)
        
        System.out.println("Hash Game".toUpperCase());  
        
        do{
            confireDrawGame();

            if (gameState) {
                // Game continuation

                if ( gamer.equals(RoundGamer.PLAYER) ) {
                    clearScreen();
    
                    int[] positions = insertPlayerCoordenates();
        
                    table[positions[0]] [positions[1]] = "{X}";
                    win = validateWin(gamer);
    
                    if (win) {
                        clearScreen();
    
                        System.out.println("CONGRATULATIONS");
                        showTable();
                        System.out.println(gamer.name() + " WINS!!!");
                        break;
                    }
    
                    gamer = RoundGamer.COMPUTER;
                    
                } else {
                    int row, column;
    
                    // Select one validate position on table
                    do {
                        row = randomGenerator.nextInt(3);
                        column = randomGenerator.nextInt(3);
                        
                        if (table[row][column].equals("{ }")) {
                            break;
                        }
                    } while(true);
    
                    table[row][column] = "{O}";
                    win = validateWin(gamer);
                    
                    if (win) {
                        clearScreen();
    
                        System.out.println(RoundGamer.PLAYER.name() + " LOSE");
                        showTable();
                        System.out.println(gamer.name() + " WINS!!!");
                        break;
                    }
    
                    gamer = RoundGamer.PLAYER;
    
                } 

            } else {
                // Draw game
                clearScreen();
                showTable();
                System.out.println("DRAW GAME!!!");
                break;
            }

        } while(true);
        
        input.close();
    }
}
