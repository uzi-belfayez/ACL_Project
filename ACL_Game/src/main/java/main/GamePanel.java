package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;
import entity.Ghost;
import entity.Monstre;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	// SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile 
    final int scale = 3; // 16*3 = 48 pixel
    
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 12; // Horizontal
    public final int maxScreenRow = 12; // Vertical
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixel
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixel

    int FPS = 60;
    
    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public Player player = new Player(this, keyH);
    public SuperObject obj[] = new SuperObject[10];
    public EventHandler eHandler = new EventHandler(this);
    
    public Monstre[] monsters = new Monstre[10]; // Array for multiple monsters
    public Ghost[] ghosts = new Ghost[10]; // Array for multiple ghosts

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();

        // Initialize 5 monsters with random starting positions
        Random rand = new Random();
        for (int i = 0; i < monsters.length; i++) {
            int startX = rand.nextInt(maxWorldCol) * tileSize;
            int startY = rand.nextInt(maxWorldRow) * tileSize;
            int patrolEndX = startX + tileSize * 2;
            int patrolEndY = startY + tileSize * 2;

            monsters[i] = new Monstre(this, startX, patrolEndX, startY, patrolEndY);
        }

        // Initialize 5 ghosts with random starting positions
        for (int i = 0; i < ghosts.length; i++) {
            ghosts[i] = new Ghost(this);
            ghosts[i].worldX = rand.nextInt(maxWorldCol) * tileSize;
            ghosts[i].worldY = rand.nextInt(maxWorldRow) * tileSize;
        }
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000 / FPS; // 16 ms
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if (delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }
            if (timer > 1000000000) {
                System.out.println("FPS : " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        player.update();

        // Update each monster
        for (Monstre monster : monsters) {
            if (monster != null) {
                monster.update();
            }
        }

        // Update each ghost
        for (Ghost ghost : ghosts) {
            if (ghost != null) {
                ghost.update();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        tileM.draw(g2); // Draw tiles

        // Draw objects
        for (SuperObject object : obj) {
            if (object != null) {
                object.draw(g2, this);
            }
        }

        player.draw(g2); // Draw player

        // Draw each monster
        for (Monstre monster : monsters) {
            if (monster != null) {
                monster.draw(g2);
            }
        }

        // Draw each ghost
        for (Ghost ghost : ghosts) {
            if (ghost != null) {
                ghost.draw(g2);
            }
        }

        ui.draw(g2);

        g2.dispose();
    }
}