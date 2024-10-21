import java.util.Scanner;

public class _2048Driver 
{
	public static void main(String[] args) 
	{
		Scanner input = new Scanner(System.in);
		_2048 game = new _2048();
		
		// Key Bindings
		String up = "W";
		String down = "S";
		String left = "A";
		String right = "D";
		String undo = "U";
		String quit = "Q";
		
		while(!game.gameOver())
		{
			System.out.println(game);
			System.out.print("Enter a command: ");
			String in = input.next();
			
			in = in.toUpperCase();
			
			if(in.equals(quit))
				break;
			else if(in.equals(up))
				game.move("up");
			else if(in.equals(down))
				game.move("down");
			else if(in.equals(left))
				game.move("left");
			else if(in.equals(right))
				game.move("right");
			else if(in.equals(undo))
				game.undo();
			else
				System.out.println("Invalid Command: " + in);
		}
		
		System.out.println("Game Over!");
		System.out.println("Final Score: " + game.getScore());
	}
}
