# Connect4 Game

This repository contains a simple implementation of the Connect4 game in Java with an artificial intelligence opponent, consisting of four main classes:

## `Logic`

The `Logic` class serves as the main entry point for the Connect4 game. It manages the game loop, player turns, and determines the winner.

- `turnCounter`: Counter to keep track of the turns in the game.
- `hasWinner`: Flag indicating whether there is a winner in the game.
- `ROWS` and `COLS`: Constants representing the number of rows and columns in the game board.
- `board`: Game board instance.
- `player1`: Player 1 instance with symbol 'X'.
- `player2`: AI (Player 2) instance with symbol 'O'.

### Methods:

- `main(String[] args)`: The main method that starts the Connect4 game. Manages the game loop, player turns, and determines the winner.

## `Board`

The `Board` class represents the game board in Connect4. It contains methods for initializing the board, displaying the board, making moves, and checking for a winner.

- `board`: 2D array representing the game board.

### Methods:

- `initializeBoard()`: Initializes the board with empty spaces.
- `setCell(char symbol, int choice)`: Drops the given symbol into the specified column.
- `displayBoard()`: Displays the current state of the board.
- `checkValid(int choice)`: Checks if the specified column is a valid move.
- `checkWin(char symbol)`: Checks if the specified symbol has won the game.
- Various getter methods for retrieving information about the board.

## `Player`

The `Player` class represents a human player in Connect4. It includes methods for getting the player's choice for the next move.

- `symbol`: Symbol representing the player on the game board.
- `input`: Scanner object to read player input from the console.

### Methods:

- `getChoice(Board board)`: Gets the player's choice for the next move on the game board.
- `getSymbol()`: Gets the symbol associated with the player.

## `AI`

The `AI` class represents an AI player in Connect4. It includes methods for the AI player's choice, scoring positions, and implementing the minimax algorithm.

- `SEARCH_DEPTH`: The depth to which the minimax algorithm searches.
- `symbol`: Symbol representing the AI player on the game board.

### Methods:

- `getChoice(Board board, char oppSymbol)`: Gets the AI player's choice for the next move on the game board.
- `scorePosition(Board board, char aiSymbol, char oppSymbol)`: Scores the current position on the game board for the AI player.
- `evaluateWindow(char[] window, char aiSymbol, char oppSymbol)`: Evaluates a window of four consecutive positions on the game board and assigns a score.
- `checkNInARow(char[] array, char target, int count)`: Checks if there are N consecutive occurrences of a target symbol in an array.
- `pickBestMove(Board board, char aiSymbol, char oppSymbol)`: Picks the best move for the AI player based on the current game board.
- `minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, char oppSymbol)`: Implements the minimax algorithm to find the best move for the AI player.
- `getSymbol()`: Gets the symbol associated with the AI player.