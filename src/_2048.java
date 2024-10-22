import java.util.ArrayList;
import java.util.Arrays; // Added for better array comparison
import java.util.Random;

public class _2048 {
    private final int rows = 4;
    private final int cols = 4;
    private int[][] board;
    private int[][] previousBoard;
    private int score;
    private int previousScore;
    private Random random; // Added for random tile generation

    /**
     * Initializes board and previousBoard using rows and cols.
     * Uses the generateTile method to add two random tiles to board.
     */
    public _2048() {
        this.board = new int[rows][cols];
        this.previousBoard = new int[rows][cols];
        this.score = 0;
        this.previousScore = 0;
        this.random = new Random(); // Initialize random generator
        generateTile();
        generateTile();
    }

    /**
     * Initializes the board of this object using the specified board.
     * Initializes previousBoard using rows and cols.
     *
     * Precondition: the specified board is a 4x4 2D Array.
     *
     * @param board
     */
    public _2048(int[][] board) {
        this.board = board;
        this.previousBoard = new int[rows][cols];
        this.score = 0;
        this.previousScore = 0;
        this.random = new Random(); // Initialize random generator
    }

    /**
     * Generates a tile and adds it to an empty spot on the board.
     * 80% chance to generate a 2, 20% chance to generate a 4.
     *
     * Does nothing if the board is full.
     */
    private void generateTile() {
        if (full()) return; // Exit early if the board is full

        int tileNumber = (random.nextFloat() < 0.2) ? 4 : 2; // Simplified tile generation logic

        ArrayList<Integer> indices = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (board[r][c] == 0) {
                    indices.add(r * cols + c); // Calculate the index directly
                }
            }
        }

        if (!indices.isEmpty()) {
            int randomIndex = indices.get(random.nextInt(indices.size())); // Choose a random index from available spots
            board[randomIndex / cols][randomIndex % cols] = tileNumber; // Set the corresponding value in the board
        }
    }

    /**
     * Returns false if the board contains a 0, true otherwise.
     * @return
     */
    private boolean full() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) return false; // If there's any empty cell, return false
            }
        }
        return true; // If no empty cells found, the board is full
    }

    /**
     * Returns the board.
     * @return
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Returns the score.
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Saves board into previousBoard and score into previousScore
     * then performs a move based on the specified direction:
     *
     * Valid directions (not case sensitive):
     * up, down, left, right
     *
     * Adds a new tile to the board using the generateTile method.
     *
     * @param direction
     */
    public void move(String direction) {
        previousBoard = deepCopy(board); // Create a copy of the board
        previousScore = score;

        switch (direction.toLowerCase()) { // Use toLowerCase for case insensitivity
            case "up":
                moveUp();
                break;
            case "down":
                moveDown();
                break;
            case "left":
                moveLeft();
                break;
            case "right":
                moveRight();
                break;
        }

        generateTile();
    }

    private void moveUp() {
        for (int c = 0; c < cols; c++) {
            ArrayList<Integer> column = new ArrayList<>();
            for (int r = 0; r < rows; r++) {
                if (board[r][c] != 0) column.add(board[r][c]); // Add non-zero elements to the column list
                board[r][c] = 0; // Reset the column
            }
            cutList(column);
            for (int r = 0; r < column.size(); r++) {
                board[r][c] = column.get(r); // Populate the column back into the board
            }
        }
    }

    private void moveDown() {
        for (int c = 0; c < cols; c++) {
            ArrayList<Integer> column = new ArrayList<>();
            for (int r = rows - 1; r >= 0; r--) {
                if (board[r][c] != 0) column.add(board[r][c]);
                board[r][c] = 0;
            }
            cutList(column);
            for (int r = 0; r < column.size(); r++) {
                board[rows - 1 - r][c] = column.get(r); // Fill from the bottom
            }
        }
    }

    private void moveLeft() {
        for (int r = 0; r < rows; r++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int c = 0; c < cols; c++) {
                if (board[r][c] != 0) row.add(board[r][c]);
                board[r][c] = 0;
            }
            cutList(row);
            for (int c = 0; c < row.size(); c++) {
                board[r][c] = row.get(c); // Fill from the left
            }
        }
    }

    private void moveRight() {
        for (int r = 0; r < rows; r++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int c = cols - 1; c >= 0; c--) {
                if (board[r][c] != 0) row.add(board[r][c]);
                board[r][c] = 0;
            }
            cutList(row);
            for (int c = 0; c < row.size(); c++) {
                board[r][cols - 1 - c] = row.get(c); // Fill from the right
            }
        }
    }

    private void cutList(ArrayList<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).equals(list.get(i + 1))) {
                list.set(i, list.get(i) * 2); // Combine numbers
                list.remove(i + 1); // Remove the second number
                score += list.get(i); // Update score
            }
        }
    }

    public void undo() {
        board = previousBoard; // Restore the previous state of the board
        score = previousScore; // Restore the previous score
    }

    public boolean gameOver() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols - 1; c++) {
                if (board[r][c] == board[r][c + 1]) return false; // Check horizontal combinations
            }
        }
        for (int c = 0; c < cols; c++) {
            for (int r = 0; r < rows - 1; r++) {
                if (board[r][c] == board[r + 1][c]) return false; // Check vertical combinations
            }
        }
        return full(); // Return true if the board is full and no combinations left
    }

    private int[][] deepCopy(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new); // Deep copy the board
    }

    public String toString() {
        StringBuilder rtn = new StringBuilder();
        for (int[] row : board) {
            rtn.append("|");
            for (int num : row) {
                String str = (num != 0) ? String.format("%4d", num) : "    "; // Format numbers for alignment
                rtn.append(str);
            }
            rtn.append("|\n");
        }
        rtn.append("Score: ").append(getScore()).append("\n");
        return rtn.toString();
    }
}