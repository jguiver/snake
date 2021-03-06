package snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import javax.swing.Timer;

import javax.swing.JPanel;

/**
 * @author Group Alpha
 * Date: 11/04/17
 * Class: CSIS 2450
 * Assignment: Group Project
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel implements ActionListener {

	/**
	 * Attributes
	 */
	private Snake player1Snake;
	
	private PlayerScore player1Info;
	
	/**
	 * To be implemented
	private PlayerScore[] highScores;
	**/
	
	private int counter;
		
	private ArrayList<Item> items;
	
	private int height;
	
	private int width;
	
	private int dotSize;
	
	private int moveSpeed;
	
	private Timer gameTimer;
	
	private boolean gameOver;
		
	/**
	 * Game Board Constructor
	 */
	public GameBoard(int height, int width, String playerName) {
		this.addKeyListener(new Keys());
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.setHeight(height);
		this.setWidth(width);
		this.setPlayer1Info(new PlayerScore(playerName, 0));
		this.setPlayer1Snake(new Snake(9.0, 10.0));
		
		//Method that reads high scores to go here
		
		this.items = new ArrayList<Item>();
		this.dotSize = 30;
		this.moveSpeed = 70;
		this.setGameOver(false);
		this.gameTimer = new Timer(moveSpeed, this);
		
		gameTimer.start();
	}

	/**
	 * Player Snake Getter 
	 * @return a Snake object representing the player 
	 */
	public Snake getPlayer1Snake() {
		return player1Snake;
	}

	/**
	 * Player Snake Setter
	 * @param player1Snake is the player's snake
	 */
	public void setPlayer1Snake(Snake player1Snake) {
		this.player1Snake = player1Snake;
	}

	/**
	 * Player Info Getter
	 * @return
	 */
	public PlayerScore getPlayer1Info() {
		return player1Info;
	}

	/**
	 * Player Info Setter
	 * @param player1Info
	 */
	public void setPlayer1Info(PlayerScore player1Info) {
		this.player1Info = player1Info;
	}
	
	/**
	 * 
	 * @return
	 *//*
	public PlayerScore[] getHighScores() {
		return highScores;
	}

	*//**
	 * 
	 * @param highScores
	 *//*
	public void setHighScores(PlayerScore[] highScores) {
		this.highScores = highScores;
	}*/

	/**
	 * Bad Item Time Counter Getter
	 * @return the time a bad item has appeared on the screen
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Bad Item Time Counter Setter
	 * @param counter
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	/**
	 * Item list Getter
	 * @return
	 */
	public ArrayList<Item> getItems() {
		return items;
	}
	
	/**
	 * Height Getter
	 * @return the height of the board
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Height Setter
	 * @param height is the new height of the board
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Width Getter
	 * @return width of the board
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Width Setter
	 * @param width is the new width of the board
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * GameOver Getter
	 * @return true value if game status is over
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * GameOver Setter
	 * @param gameOver is the boolean flag of whether or not game has ended
	 */
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	/**
	 * Method that handles a "round" of game play
	 */
	public void play() {
		boolean appleEaten = false;
		
		if (collisionOccurred() && !isGameOver()) {
			appleEaten = true;
		}
		
		items.add(new Item(true, getRandomPoint(player1Snake)));
		
		player1Snake.move(appleEaten);
	}
	
	/**
	 * Method that detects if snake has made contact with an object or itself
	 * @return boolean indicating whether or not a collision has occurred
	 */
	public boolean collisionOccurred() {
		return false;
	}
	
	/**
	 * METHOD getRandomPoint generates a random point that does not intersect
	 * the snake.
	 * 
	 * @param s is the Snake object
	 * @return point is the Point2D object that does not intersect the Snake
	 *         object
	 */
	public Point2D getRandomPoint(Snake s) {
		Point2D point = new Point2D.Double();
		boolean search = true;
		while (search) {
			int x = (int) (Math.random() * 20) + 1;
			int y = (int) (Math.random() * 20) + 1;
			point.setLocation(x, y);

			if (s.getSnake().contains(point) || (s.getSnakeHead().distance(point) < 2)) {
				System.out.println("Point found.");
				search = true;
			} else {
				System.out.println("Point NOT found.");
				search = false;
			}
		}
		return point;
	}
	
	/**
	 * Action listener that runs the game in the initialized timer.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!this.isGameOver()) {
			play();
		}
		Toolkit.getDefaultToolkit().sync();
		repaint();
	}
	
	/**
	 * Paints components onto JPanel by calling draw method.
	 *  @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);

	    draw(g);
	}

	/**
	 * Method that paints objects on screen. Apples are red and round, snake is blue and square.
	 * @param g
	 */
	private void draw(Graphics g) {		
		g.setColor(Color.red);
		for(Item apple : items) {
			g.fillOval((int) (apple.getPosition().getX() - 1) * dotSize, (int) (apple.getPosition().getY() - 1) * dotSize, dotSize, dotSize);
		}
		
		g.setColor(Color.blue);
		
		for(Point2D p : player1Snake.getSnake()) {
			g.fillRect((int) (p.getX() - 1) * dotSize, (int) (p.getY() - 1) * dotSize, dotSize, dotSize);
		}
	}
	
	/**
	 * Class to handle user key inputs
	 */
	private class Keys extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if (((key == KeyEvent.VK_LEFT) || (key == KeyEvent.VK_A)) && (player1Snake.getSnakeDirection() != Direction.RIGHT)) {
				player1Snake.setSnakeDirection(Direction.LEFT);
			} else if (((key == KeyEvent.VK_RIGHT) || (key == KeyEvent.VK_D)) && (player1Snake.getSnakeDirection() != Direction.LEFT)) {
				player1Snake.setSnakeDirection(Direction.RIGHT);
			} else if (((key == KeyEvent.VK_UP) || (key == KeyEvent.VK_W)) && (player1Snake.getSnakeDirection() != Direction.DOWN)) {
				player1Snake.setSnakeDirection(Direction.UP);
			} else if (((key == KeyEvent.VK_DOWN) || (key == KeyEvent.VK_S)) && (player1Snake.getSnakeDirection() != Direction.UP)) {
				player1Snake.setSnakeDirection(Direction.DOWN);
			}
		}
	}
}
