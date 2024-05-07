package henrycaldwell;

import java.util.Scanner;

public class Player {
    
    // Symbol representing the player on the game board
    private char symbol;
    // Scanner object to read player input from the console
    private Scanner input = new Scanner(System.in);

    // Constructor to initialize a Player with a given symbol
    public Player(char symbol) {
        this.symbol = symbol;
    }

    // Gets the player's choice for the next move on the game board
    public int getChoice(Board board) {
        int choice;

        // Loop to prompt the player for a valid column choice
        do {
            System.out.println("Which column would you like to play in? (1-7 and not full)");

            // Loop to handle non-integer inputs
            while (!input.hasNextInt()) {
                System.out.println("Which column would you like to play in? (1-7 and not full)");
                input.next();
            }

            choice = input.nextInt();

        } while (!board.checkValid(choice));

        return choice;
    }

    // Gets the symbol associated with the player
    public char getSymbol() {
        return symbol;
    }
}
