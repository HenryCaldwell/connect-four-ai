package henrycaldwell;

import java.util.List;
import java.util.Random;

public class AI {

    // The depth to which the minimax algorithm searches (6-7 for slower systems, 10 for faster systems)
    private final int SEARCH_DEPTH = 10; // ***WILL NOT WORK AT 0***

    // Symbol representing the AI player on the game board
    private char symbol;

    // Constructor to initialize an AI player with a given symbol
    public AI(char symbol) {
        this.symbol = symbol;
    }

    // Gets the AI player's choice for the next move on the game board
    public int getChoice(Board board, char oppSymbol) {
        int choice;

        // Loop to repeatedly select a valid column using the minimax algorithm
        do {
            int[] result = minimax(board, SEARCH_DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, true, oppSymbol);
            choice = result[0];
        } while (!board.checkValid(choice));

        return choice;
    }

    // Move this to board class
    // Evaluates and scores the current position on the game board for the AI player
    public int scorePosition(Board board, char oppSymbol) {
        int score = 0;

        // Check for AI dominance in the center column
        char[] centerArray = board.getCol(board.getNumCols()/2);
        int centerCount = 0;
        for (int i = 0; i < centerArray.length; i++) {
            if (centerArray[i] == symbol) {
                centerCount += 1;
            }
        }
        score += centerCount * 3;

        // Evaluate the game board in rows
        for (int i = 0; i < board.getNumRows(); i++) {
            char[] rowArray = board.getRow(i);
            for (int j = 0; j < board.getNumCols() - 3; j++) {
                char[] window = board.getLinearElements(rowArray, j, 4);
                score += evaluateWindow(window, oppSymbol);
            }
        }

        // Evaluate the game board in columns
        for (int i = 0; i < board.getNumCols(); i++) {
            char[] colArray = board.getCol(i);
            for (int j = 0; j < board.getNumRows() - 3; j++) {
                char[] window = board.getLinearElements(colArray, j, 4);
                score += evaluateWindow(window, oppSymbol);
            }
        }

        // Evaluate the game board in positive diagonals
        for (int i = 3; i < board.getNumRows(); i++) {
            for (int j = 0; j < board.getNumCols() - 3; j++) {
                char[] window = board.getDiagonalElements(i, j, 4, true);
                score += evaluateWindow(window, oppSymbol);
            }
        }

        // Evaluate the game board in negative diagonals
        for (int i = 0; i < board.getNumRows() - 3; i++) {
            for (int j = 0; j < board.getNumCols() - 3; j++) {
                char[] window = board.getDiagonalElements(i, j, 4, false);
                score += evaluateWindow(window, oppSymbol);
            }
        }

        return score;
    }

    // Evaluates a window of four consecutive positions on the game board and assigns a score
    private int evaluateWindow(char[] window, char oppSymbol) {
        int score = 0; 

        if (checkNInARow(window, symbol, 4)) {
            score += 100;
        } else if (checkNInARow(window, symbol, 3) && checkNInARow(window, ' ', 1)) {
            score += 5;
        } else if (checkNInARow(window, symbol, 2) && checkNInARow(window, ' ', 2)) {
            score += 2;
        }

        if (checkNInARow(window, oppSymbol, 2) && checkNInARow(window, ' ', 2)) {
            score -= 4;
        } else if (checkNInARow(window, oppSymbol, 3) && checkNInARow(window, ' ', 1)) {
            score -= 10;
        }

        return score;
    }

    // Checks if there are N consecutive occurrences of a target symbol in an array
    private boolean checkNInARow(char[] array, char target, int count) {
        int targetCount = 0;
    
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                targetCount++;
            }
        }
    
        return targetCount == count;
    }

    // Picks the best move for the AI player based on the current game board
    public int pickBestMove(Board board, char oppSymbol) {
        List<Integer> validMoves = board.getValidMoves();

        int bestScore = 0;
        int bestColumn = validMoves.get((int) (Math.random() * validMoves.size()));

        for (int i = 0; i < validMoves.size(); i++) {
            Board boardCopy = new Board(board);
            boardCopy.setCell(symbol, validMoves.get(i));
            int score = scorePosition(boardCopy, oppSymbol);

            if (score > bestScore) {
                bestScore = score;
                bestColumn = validMoves.get(i);
            }
        }

        return bestColumn;
    }

    // Implements the minimax algorithm to find the best move for the AI player
    private int[] minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, char oppSymbol) {
        List<Integer> validLocations = board.getValidMoves();
        boolean isTerminal = (Logic.turnCounter >= 42 || board.checkWin(symbol) || board.checkWin(oppSymbol));

        if (depth == 0 || isTerminal) {
            if (isTerminal) {
                if(board.checkWin(symbol)) {
                    return new int[]{ -1, 1000000000 + depth };
                } else if(board.checkWin(oppSymbol)) {
                    return new int[]{ -1, -1000000000 - depth };
                } else {
                    return new int[]{ -1, 0 };
                }
            } else { 
                return new int[]{ -1, scorePosition(board, oppSymbol) };
            }
        }

        if (maximizingPlayer) {
            int value = Integer.MIN_VALUE;
            int column = validLocations.get(new Random().nextInt(validLocations.size()));

            for (int col : validLocations) {
                Board boardCopy = new Board(board);
                boardCopy.setCell(symbol, col);
                int newScore = minimax(boardCopy, depth - 1, alpha, beta, false, oppSymbol)[1];

                if (newScore > value) {
                    value = newScore;
                    column = col;
                }

                alpha = Math.max(alpha, value);
                if (alpha >= beta) {
                    break;
                }
            }

            return new int[]{ column, value };
        } else {
            int value = Integer.MAX_VALUE;
            int column = validLocations.get(new Random().nextInt(validLocations.size()));

            for (int col : validLocations) {
                Board boardCopy = new Board(board);
                boardCopy.setCell(oppSymbol, col);
                int newScore = minimax(boardCopy, depth - 1, alpha, beta, true, oppSymbol)[1];

                if (newScore < value) {
                    value = newScore;
                    column = col;
                }

                beta = Math.min(beta, value);
                if (alpha >= beta) {
                    break;
                }
            }

            return new int[]{ column, value };
        }
    }

    // Gets the symbol associated with the AI player
    public char getSymbol() {
        return symbol;
    }
}
