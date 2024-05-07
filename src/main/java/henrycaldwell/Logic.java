package henrycaldwell;

public class Logic {

    // Counter to keep track of the turns in the game
    public static int turnCounter = 0;
    // Flag indicating whether there is a winner in the game
    private static boolean hasWinner = false;

    // Constants representing the number of rows and columns in the game board
    public static final int ROWS = 6;
    public static final int COLS = 7;

    // Game board instance
    private static Board board = new Board(ROWS, COLS);
    // Player 1 instance with symbol 'X'
    private static Player player1 = new Player('X');
    // Player 2 (AI) instance with symbol 'O'
    private static AI player2 = new AI('O');
    
    // The main method that starts the Connect4 game
    public static void main(String[] args) {
        board.displayBoard();

        // Game loop continues until there is a winner or the board is filled
        while (!hasWinner && turnCounter <= ROWS * COLS) {
            if (turnCounter % 2 == 0) {
                // Player 1's turn
                board.setCell(player1.getSymbol(), player1.getChoice(board));
                hasWinner = board.checkWin(player1.getSymbol());
                board.displayBoard();
                turnCounter++;
            } else {
                // Player 2's (AI) turn
                board.setCell(player2.getSymbol(), player2.getChoice(board, player1.getSymbol()));
                hasWinner = board.checkWin(player2.getSymbol());
                board.displayBoard();
                turnCounter++;
            }
        }
        
        // Determine the winner based on the turn count
        if (turnCounter % 2 == 1 && turnCounter <= ROWS * COLS) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Player 2 wins!");
        }
    }
}
