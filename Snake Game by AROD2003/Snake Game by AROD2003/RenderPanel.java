// package
package snake;

// imports
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

// suppress the warning because its annoying 
@SuppressWarnings("serial")
public class RenderPanel extends JPanel
{
	// background color, can't change
	public static final Color GREEN = new Color(1666073);

	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		// Snake 
		Snake snake = Snake.snake;

		// Background and snake color
		// Snake = blue, background = green, the variable stated above
		g.setColor(GREEN);
		g.fillRect(0, 0, 800, 700);
		g.setColor(Color.BLUE);


		for (Point point : snake.snakeParts)
		{
			g.fillRect(point.x * Snake.SCALE, 
					   point.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		}
		
		g.fillRect(snake.head.x * Snake.SCALE,
				   snake.head.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		// food Color
		g.setColor(Color.RED);
		
		g.fillRect(snake.cherry.x * Snake.SCALE, 
				   snake.cherry.y * Snake.SCALE, Snake.SCALE, Snake.SCALE);
		
		// Stats of the player
		String stats = "Score: " + snake.score + 
						", Length: " + snake.tailLength + 
						", Seconds: " + snake.time / 20;
		
		g.setColor(Color.white);
		
		g.drawString(stats, (int) (getWidth() / 2 - stats.length() * 2.5f), 10);

		stats = "Game Over! Press space to try again!";

		if (snake.over) {
			g.drawString(stats, (int) (getWidth() / 2 - stats.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}

		stats = "Paused!";

		if (snake.paused && !snake.over) {
			g.drawString(stats, (int) (getWidth() / 2 - stats.length() * 2.5f), (int) snake.dim.getHeight() / 4);
		}
	}
}