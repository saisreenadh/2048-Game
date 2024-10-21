import java.util.ArrayList;

import static java.util.Arrays.deepEquals;

public class _2048
{
	private final int rows = 4;
	private final int cols = 4;
	private int[][] board;
	private int[][] previousBoard;
	private int score;
	private int previousScore;

	/**
	 * Initializes board and previousBoard using rows and cols.
	 * Uses the generateTile method to add two random tiles to board.
	 */
	public _2048()
	{
		this.board = new int[rows][cols];
		this.previousBoard = new int[rows][cols];
		this.score = 0;
		this.previousScore = 0;
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
	public _2048(int[][] board)
	{
		this.board = board;
		this.previousBoard = new int[rows][cols];
		this.score = 0;
		this.previousScore = 0;
	}

	/**
	 * Generates a tile and add it to an empty spot on the board.
	 * 80% chance to generate a 2
	 * 20% chance to generate a 4
	 *
	 * Does nothing if the board is full.
	 */
	private void generateTile()
	{
		int tileNumber = 2;
		if(Math.random() < .2)
			tileNumber = 4;											// 20% chance that the tile will be a 4

		ArrayList<Integer> indices = new ArrayList<>();

		if(!full())
		{
			int count = 0;

			for (int r = 0; r < board.length; r++)
			{
				for (int c = 0; c < board[0].length; c++)
				{
					if (board[r][c] == 0)
					{
						indices.add(count);							// adds the positions of blank spaces into an ArrayList
					}

					count++;
				}
			}

			int randomSpot = (int) (Math.random() * indices.size());	// chooses a random index of the ArrayList

			board[indices.get(randomSpot) / 4][indices.get(randomSpot) % 4] = tileNumber;		// sets the corresponding value in the board to the random tile
		}
	}

	/**
	 * Returns false if the board contains a 0, true otherwise.
	 * @return
	 */
	private boolean full()
	{
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[0].length; c++)
			{
				if(board[r][c] == 0)
					return false;
			}
		}

		return true;
	}

	/**
	 * Returns the board.
	 * @return
	 */
	public int[][] getBoard()
	{
		return board;
	}

	/**
	 * Returns the score.
	 * @return
	 */
	public int getScore()
	{
		return score;
	}

	/**
	 * Sets the first array to equal the second array.
	 * @param one
	 * @param two
	 */
	private static void setArray(int[][] one, int[][] two)
	{
		for (int r = 0; r < one.length; r++)
		{
			for (int c = 0; c < one[0].length; c++)
			{
				one[r][c] = two[r][c];
			}
		}
	}

	/**
	 * Saves board into previousBoard and score into previousScore
	 * then performs a move based on the specified direction:
	 *
	 * Valid directions (not case sensitive):
	 *  up
	 *  down
	 *  left
	 *  right
	 *
	 * Adds a new tile to the board using the generateTile method.
	 *
	 * @param direction
	 */
	public void move(String direction)
	{
	setArray(previousBoard, board);									// calls setArray
		previousScore = score;

		switch(direction)											// switch case to determine which move method to call
		{
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

	/**
	 * Shifts all the tiles up, combines like tiles that collide.
	 */
	private void moveUp()
	{
		for (int c = 0; c < board[0].length; c++)
		{
			ArrayList<Integer> column = new ArrayList<>();			// creates a new ArrayList

			for (int r = 0; r < board.length; r++)
			{
				if(board[r][c] != 0)
					column.add(board[r][c]);						// only adds the number to the ArrayList if it is not 0

				board[r][c] = 0;									// makes the entire column filled with 0's
			}

			cutList(column);										// calls cutList method

			for (int r = 0; r < column.size(); r++)					// inserting the ArrayList back into the board
			{
				board[r][c] = column.get(r);						// starts from the top of the board
			}
		}
	}

	/**
	 * Shifts all the tiles down, combines like tiles that collide.
	 */
	private void moveDown()
	{
		for (int c = 0; c < board[0].length; c++)
		{
			ArrayList<Integer> column = new ArrayList<>();			// creates a new ArrayList

			for (int r = board.length - 1; r >= 0; r--)
			{
				if(board[r][c] != 0)
					column.add(board[r][c]);						// only adds the number to the ArrayList if it is not 0

				board[r][c] = 0;									// makes the entire column filled with 0's
			}

			cutList(column);										// calls cutList method

			for (int r = 0; r < column.size(); r++)				// inserting the ArrayList back into the board
			{
				board[3 - r][c] = column.get(r);						// starts from the bottom of the board
			}
		}
	}

	/**
	 * Shifts all the tiles left, combines like tiles that collide.
	 */
	private void moveLeft()
	{
		for (int r = 0; r < board.length; r++)
		{
			ArrayList<Integer> row = new ArrayList<>();				// creates a new ArrayList

			for (int c = 0; c < board[0].length; c++)
			{
				if(board[r][c] != 0)
					row.add(board[r][c]);							// only adds the number to the ArrayList if it is not 0

				board[r][c] = 0;									// makes the entire column filled with 0's
			}

			cutList(row);											// calls cutList method

			for (int c = 0; c < row.size(); c++)				// inserting the ArrayList back into the board
			{
				board[r][c] = row.get(c);							// starts from the left of the board
			}
		}
	}

	/**
	 * Shifts all the tiles right, combines like tiles that collide.
	 */
	private void moveRight()
	{
		for (int r = 0; r < board.length; r++)
		{
			ArrayList<Integer> row = new ArrayList<>();				// creates a new ArrayList

			for (int c = board[0].length - 1; c >= 0; c--)
			{
				if(board[r][c] != 0)
					row.add(board[r][c]);							// only adds the number to the ArrayList if it is not 0

				board[r][c] = 0;									// makes the entire column filled with 0's
			}

			cutList(row);										// calls cutList method

			for (int c = 0; c < row.size(); c++)				// inserting the ArrayList back into the board
			{
				board[r][3 - c] = row.get(c);						// starts from the bottom of the board
			}
		}
	}

	/**
	 * Adds together adjacent numbers that are the same and removes the second number.
	 *  Also modifies score
	 * @param list
	 */
	private void cutList(ArrayList<Integer> list)
	{
		for (int i = 0; i < list.size() - 1; i++)				// adding together like numbers
		{
			if(list.get(i).equals(list.get(i + 1)))
			{
				list.set(i, list.get(i) * 2);				// multiplies the number by 2, effectively adds the number to itself
				list.remove(i + 1);					// removes the second number
				score += list.get(i);
			}
		}
	}

	/**
	 * Sets board to previousBoard and score to previousScore
	 */
	public void undo()
	{
		setArray(board, previousBoard);
		score = previousScore;
	}

	/**
	 * Returns true if the game is over, false otherwise.
	 * @return
	 */
	public boolean gameOver()
	{
		for (int r = 0; r < board.length; r++)
		{
			for (int c = 0; c < board[0].length - 1; c++)
			{
				if(board[r][c] == board[r][c + 1])				// loops through the board and checks if any numbers on the row can be combined
					return false;
			}
		}


		for (int c = 0; c < board[0].length; c++)
		{
			for (int r = 0; r < board.length - 1; r++)
			{
				if(board[r][c] == board[r + 1][c])				// loops through the board and checks if any numbers on the column can be combined
					return false;
			}
		}


		if(full())						// if nothing has been returned yet and the board is full, return true
			return true;

		return false;
	}


	/**
	 * Returns a String representation of this object.
	 */
	public String toString()
	{
		String rtn = "";

		for(int[] row : board)
		{
			rtn += "|";
			for(int num : row)
				if(num != 0)
				{
					String str = num + "";
					while(str.length() < 4)
						str = " " + str;
					rtn += str;

				}
				else
					rtn += "    ";
			rtn += "|\n";
		}

		rtn += "Score: " + getScore() + "\n";

		return rtn;
	}
}
