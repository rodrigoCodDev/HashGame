package application;

import java.util.Random;
import java.util.Scanner;

import enums.RoundGamer;

public class HashGame {

    // MAIN
    public static void main(String[] args) {

        // VARIABLES
        boolean win = false; // Validates the end and continuation of the game
        int[] position = {0, 0};

        Random randomGenerator = new Random(); // Random generator of numbers
        RoundGamer gamer = RoundGamer.PLAYER; // Defines the round of game (player or computer)
        Scanner input = new Scanner(System.in);
        
        // GAME
        System.out.println("Hash Game".toUpperCase());  
        
        do{
            UI.confireDrawGame();

            if (UI.gameState) {
                // Game continuation

                if ( gamer.equals(RoundGamer.PLAYER) ) {
                    UI.clearScreen();
    
                    position = UI.insertPlayerCoordenates(input);    
                    win = UI.setCoordenatesOnTable(position, gamer);
    
                    if (win) {
                        // If the game is end
                        break;
                    }
    
                    gamer = RoundGamer.COMPUTER;
                } else {

                    // Select one validate position on table
                    do {
                        position[0] = randomGenerator.nextInt(3);
                        position[1]= randomGenerator.nextInt(3);

                        if (UI.isEmptyOnTable(position)) {
                            break;
                        }
                    } while(true);
                    
                    win = UI.setCoordenatesOnTable(position, gamer);
                    
                    if (win) {
                        // If the game is end
                        System.out.println(RoundGamer.PLAYER.name() + " LOSE");
                        break;
                    }

                    gamer = RoundGamer.PLAYER;
                } 

            } else {
                // Draw game
                UI.showDrawGame();
                break;
            }

        } while(true);

        // END
        input.close();
    }
}
