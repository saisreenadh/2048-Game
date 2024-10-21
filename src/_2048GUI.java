import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;

@SuppressWarnings("serial")
public class _2048GUI extends JFrame implements KeyListener 
{	
	private static JLabel[][] grid = new JLabel[4][4];
	private static _2048 game;
	private static JLabel score;

	public _2048GUI()
	{
		addKeyListener(this);
		
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				grid[row][col] = new JLabel("", SwingConstants.CENTER);

		Border raisedbevel = BorderFactory.createRaisedBevelBorder();

		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				grid[row][col].setBorder(raisedbevel);
		
		setTitle("2048");
		Container pane = getContentPane();
		pane.setLayout(new GridLayout(5, 4));
		
		score = new JLabel("0", SwingConstants.CENTER);
		
		pane.add(new JLabel("Score: ", SwingConstants.CENTER));
		pane.add(score);
		pane.add(new JLabel("Press U to Undo!", SwingConstants.LEFT));
		pane.add(new JLabel("2048", SwingConstants.CENTER));
		
		for (int row = 0; row < 4; row++)
			for (int col = 0; col < 4; col++)
				pane.add(grid[row][col]);

		setSize(400,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void update() 
	{
		int[][] a = game.getBoard();
		
		for(int r = 0; r < a.length; r++)
			for(int c = 0; c < a[r].length; c++)
			{
				int tile = a[r][c];
				if(tile == 0)
					grid[r][c].setText("");
				else
					grid[r][c].setText(tile + "");
				
				switch (tile) 
				{
					case 0:    grid[r][c].setBackground(Color.white);     break;
					case 2:    grid[r][c].setBackground(Color.black);     break;
					case 4:    grid[r][c].setBackground(Color.blue);      break;
					case 8:    grid[r][c].setBackground(Color.green);     break;
					case 16:   grid[r][c].setBackground(Color.orange);    break;
					case 32:   grid[r][c].setBackground(Color.red);       break;
					case 64:   grid[r][c].setBackground(Color.yellow);    break;
					case 128:  grid[r][c].setBackground(Color.cyan);      break;
					case 256:  grid[r][c].setBackground(Color.darkGray);  break;
					case 512:  grid[r][c].setBackground(Color.gray);      break;
					case 1024: grid[r][c].setBackground(Color.magenta);   break;
					case 2048: grid[r][c].setBackground(Color.pink);      break;
					case 4096: grid[r][c].setBackground(Color.lightGray); break;
				}
			}
		
		score.setText("  " + game.getScore());
	}

	public static void main(String[] args) 
	{
		_2048GUI temp = new _2048GUI();
		game = new _2048();
		update();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			game.move("up");
		if (e.getKeyCode() == KeyEvent.VK_DOWN|| e.getKeyCode() == KeyEvent.VK_S)//move down
			game.move("down");
		if (e.getKeyCode() == KeyEvent.VK_LEFT|| e.getKeyCode() == KeyEvent.VK_A)//move left
			game.move("left");
		if (e.getKeyCode() == KeyEvent.VK_RIGHT|| e.getKeyCode() == KeyEvent.VK_D)//move right
			game.move("right");
		if (e.getKeyCode() == KeyEvent.VK_U)//undo
			game.undo();
		if (e.getKeyCode() == KeyEvent.VK_Q)//exit
			System.exit(0);

		update();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
}
