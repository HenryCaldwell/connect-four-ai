package henrycaldwell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    // 2D array representing the game board
    private char[][] board;

    // Constructor to initialize a new board with the specified number of rows and columns
    public Board(int rows, int cols) {
        this.board = new char[rows][cols];
        initializeBoard();
    }

    // Constructor to initialize a new board as a deep copy of a specified board
    public Board(Board original) {
        this.board = new char[original.getNumRows()][];
        for (int i = 0; i < original.getNumRows(); i++) {
            this.board[i] = Arrays.copyOf(original.getRow(i), original.getNumCols());
        }
    }

    // Initialize the board with empty spaces
    private void initializeBoard() {
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(board[i], ' ');
        }
    }

    // Drop the given symbol in to the specified column
    public void setCell(char symbol, int choice) {
        for (int i = board.length - 1; i >= 0; i--){
            if (board[i][choice-1] == ' '){
                board[i][choice-1] = symbol;
                break;
            }
        }
    }

    // Display the current state of the board
    public void displayBoard() {
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("---");
        }
        System.out.println();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print("[");
                System.out.print(board[i][j]);
                System.out.print("]");
            }
            System.out.println();
        }
        
        for (int i = 0; i < board[0].length; i++) {
            System.out.print("---");
        }
        System.out.println();
    }

    // Check if the specified column is a valid move
    public boolean checkValid(int choice) {
        return choice >= 1 && choice <= board[0].length + 1 && board[0][choice - 1] == ' ';
    }

    // Check if the specified symbol has won the game
    public boolean checkWin(char symbol) {
        // Check horizontally
        for (int i = 0; i < board.length; i++){
			for (int j = 0; j < board[0].length - 3; j++){
				if (board[i][j] == symbol && board[i][j + 1] == symbol && board[i][j + 2] == symbol && board[i][j + 3] == symbol) {
					return true;
				}
			}			
		}

        // Check vertically
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == symbol && board[i + 1][j] == symbol && board[i + 2][j] == symbol && board[i + 3][j] == symbol) {
					return true;
				}
            }
        }

        // Check diagonally (positive)
        for (int i = 3; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                if (board[i][j] == symbol && board[i - 1][j + 1] == symbol && board[i - 2][j + 2] == symbol && board[i - 3][j + 3] == symbol) {
					return true;
				}
            }
        }

        // Check diagonally (negative)
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                if (board[i][j] == symbol && board[i + 1][j + 1] == symbol && board[i + 2][j + 2] == symbol && board[i + 3][j + 3] == symbol) {
					return true;
				}
            }
        }

        return false;
    }

    // Get the number of rows in the board
    public int getNumRows() {
        return board.length;
    }

    // Get the number of columns in the board
    public int getNumCols() {
        return board[0].length;
    }

    // Get the elements of the specified row
    public char[] getRow(int row) {
        char[] rowArray = board[row];
        return rowArray;
    }

    // Get the elements of the specified column
    public char[] getCol(int col) {
        char[] colArray = new char[board.length];
        for (int i = 0; i < board.length; i++) {
            colArray[i] = board[i][col];
        }
        return colArray;
    }

    // Get a subarray of linear elements from the specified array
    public char[] getLinearElements(char[] array, int startingPos, int number) {
        char[] result = new char[number];
        System.arraycopy(array, startingPos, result, 0, number);
        return result;
    }

    // Get a subarray of diagonal elements from the specified position
    public char[] getDiagonalElements(int startRow, int startCol, int number, boolean positive) {
        char[] result = new char[number];

        for (int i = 0; i < number; i++) {
            if (positive) {
                result[i] = board[startRow - i][startCol + i];  // Positive diagonal
            } else {
                result[i] = board[startRow + i][startCol + i];  // Negative diagonal
            }
        }

        return result;
    }

    // Get an arraylist of valid column inputs
    public List<Integer> getValidMoves() {
        List<Integer> validMoves = new ArrayList<>();

        for (int i = 0; i < getNumCols() + 1; i++) {
            if (checkValid(i)) {
                validMoves.add(i);
            }
        }

        return validMoves;
    }
}
