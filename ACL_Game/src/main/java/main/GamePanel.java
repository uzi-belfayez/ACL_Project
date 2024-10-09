package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.text.PlainDocument;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	
	//SCREEN SETTINGS
	final int originalTileSize = 16 ; // 16x16 tile 
	final int scale = 3 ; // 16*3 = 48 pixel
	
	public final int tileSize = originalTileSize * scale ;
	public final int maxScreenCol = 12 ; // Horizontal
	public final int maxScreenRow = 12 ; // vertical
	public final int screenWidth = tileSize * maxScreenCol ; // 768 pixel
	public final int screenHeight = tileSize * maxScreenRow ; // 576 pixel
	
	int FPS = 60;
	
	//WORLD SETTINGS
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize*maxWorldCol;
	public final int worldHeight = tileSize*maxWorldRow;
	
	public TileManager tileM = new TileManager(this);
	
	KeyHandler keyH = new KeyHandler();
	
	Thread gameThread;
	
	public CollisionChecker cChecker = new CollisionChecker(this);
	
	public Player player = new Player(this, keyH);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; //16 ms
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime) ;
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount ++;
			}
			if (timer > 1000000000) {
				System.out.println("FPS : "+drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		tileM.draw((Graphics2D) g);
		
		player.draw((Graphics2D) g);
		
		g.dispose();
	}

}
