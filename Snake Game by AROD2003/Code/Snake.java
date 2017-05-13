// package 
package snake;

// imports
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

// MAIN CLASS
public class Snake implements ActionListener, KeyListener {

	// Snake, jframe, renderpanel, and timer variables
	public static Snake snake;
	public JFrame game;
	public RenderPanel renderPanel;
	public Timer timer = new Timer(20, this);

	// Snake parts and tail length stuffs
	public ArrayList<Point> snakeParts = new ArrayList<Point>();
	public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;
	public int ticks = 0, direction = DOWN, score, tailLength = 10, time;

	// Cherry, random, gameOver, and Dimension variables
	public Point head, cherry;
	public Random random;
	public boolean over = false, paused;
	public Dimension dim;

	// Frame's width and height
	public static final int WIDTH = 805, HEIGHT = 700;

	// snake object, and set up the frame
	public Snake() {
		dim = Toolkit.getDefaultToolkit().getScreenSize();
		game = new JFrame("Snake by AROD2003");
		game.setVisible(true);
		game.setSize(WIDTH, HEIGHT);
		game.setResizable(false);
						// Location of width
		game.setLocation(dim.width / 2 - game.getWidth() / 2, 
						// Location of height
						 dim.height / 2 - game.getHeight() / 2);

		game.add(renderPanel = new RenderPanel());
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.addKeyListener(this);

		// start game function after everything with the frame is done setting up
		startGame();
	}

	// Setting up the game stuff
	public void startGame() {
		over = false;
		paused = false;
		time = 0;
		score = 0;
		tailLength = 5;
		ticks = 0;
		direction = DOWN;
		head = new Point(0, -1);
		random = new Random();
		snakeParts.clear();
		cherry = new Point(random.nextInt(79), random.nextInt(66));
		timer.start();
	}

	// Directional movements
	@Override
	public void actionPerformed(ActionEvent arg0) {
		renderPanel.repaint();
		ticks++;

		if (ticks % 2 == 0 && head != null && !over && !paused) {
			time++;

			snakeParts.add(new Point(head.x, head.y));

			// If the direction is up, move up
			if (direction == UP) {
				if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) {
					head = new Point(head.x, head.y - 1);
				} else {
					over = true;
				}
			}

			// If the direction is down, move down
			if (direction == DOWN) {
				if (head.y + 1 < 67 && noTailAt(head.x, head.y + 1)) {
					head = new Point(head.x, head.y + 1);
				} else {
					over = true;
				}
			}

			// If the direction is left, move left
			if (direction == LEFT) {
				if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) {
					head = new Point(head.x - 1, head.y);
				} else {
					over = true;
				}
			}

			// If the direction is right, move right
			if (direction == RIGHT) {
				if (head.x + 1 < 80 && noTailAt(head.x + 1, head.y)) {
					head = new Point(head.x + 1, head.y);
				} else {
					over = true;
				}
			}

			// If the size is greater than the tailLength, then make snake parts 0
			if (snakeParts.size() > tailLength) {
				snakeParts.remove(0);
			}
			/* If cherry is NOT null, add stuff to the store and tailLength, then set the cherry's location randomly
			*  between a random integer between 79 and 66
			*/
			if (cherry != null) {
				if (head.equals(cherry)) {
					score += 10;
					tailLength++;
					cherry.setLocation(random.nextInt(79),
									   random.nextInt(66));
				}
			}
		}
	}

	// Tail object 
	public boolean noTailAt(int x, int y) {
		for (Point point : snakeParts) {
			if (point.equals(new Point(x, y))) {
				return false;
			}
		}
		return true;
	}

	// Main method
	public static void main(String[] args) {
		snake = new Snake();
	}

	// Controls 
	@Override
	public void keyPressed(KeyEvent e) {
		int i = e.getKeyCode();

		// Right
		if ((i == KeyEvent.VK_A || 
			 i == KeyEvent.VK_LEFT) 
			 && direction != RIGHT) {
			
			direction = LEFT;
		}

		// Left
		if ((i == KeyEvent.VK_D ||
			 i == KeyEvent.VK_RIGHT)
			 && direction != LEFT) {
			
			direction = RIGHT;
		}

		// Down
		if ((i == KeyEvent.VK_W || 
			 i == KeyEvent.VK_UP) 
			 && direction != DOWN) {
			
			direction = UP;
		}

		// Up
		if ((i == KeyEvent.VK_S || 
			 i == KeyEvent.VK_DOWN)
			 && direction != UP) {
			
			direction = DOWN;
		}

		// When the game is over, press space
		if (i == KeyEvent.VK_SPACE)	{
			if (over) {
				startGame();
			} else {
				paused = !paused;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e)	{
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}