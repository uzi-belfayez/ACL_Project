package entity;

import java.awt.Color;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public int hasKey = 0; //number of keys the player got
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = (gp.screenWidth/2) - (gp.tileSize/2);
		screenY = (gp.screenHeight/2) - (gp.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize*23 ;
		worldY = gp.tileSize*23 ;
		speed = 4 ;
		direction = "down";
		// PLAYER STATUS
		maxLife = 6;
		life = maxLife;
		
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		
		if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed  ) {
			if (keyH.upPressed == true) {
				direction = "up";
				
			}
			else if (keyH.downPressed == true) {
				direction = "down";
				
			}
			else if (keyH.leftPressed == true) {
				direction = "left";
				
			}
			else if (keyH.rightPressed == true) {
				direction = "right";
				
			}
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			if(collisionOn == false ) {
				switch(direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
				
			}
			
			spriteCounter ++;
			if (spriteCounter > 12) {
				if (spriteNum ==1)
					spriteNum = 2;
				else if (spriteNum ==2)
					spriteNum = 1;
				spriteCounter = 0;
			}
		}
		
		
	}
	
	public void pickUpObject(int i) {
		if(i != 999) {
			//gp.obj[i] = null; //eats the object
			String objectName = gp.obj[i].name;
			switch(objectName) {
			case "Key":
				gp.obj[i] = null;
				break;
			case "Chest":
				gp.obj[i] = null;
				break;
			case "Door":
				break;
			case "Boots":
				speed += 2; // Increase speed
                gp.obj[i] = null; // Remove the object
                gp.ui.showMessage("Movement Speed Buff !");
                // Create a timer to reset the speed after 3 seconds
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        speed -= 2; // Reset speed after 3 seconds
                        timer.cancel(); // Stop the timer
                    }
                }, 3000); // 3000 milliseconds = 3 seconds
                break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if (spriteNum == 1)
			image = up1;
			if(spriteNum == 2)
			image = up2;
			break;
		case "down":
			if (spriteNum == 1)
			image = down1;
			if(spriteNum == 2)
			image = down2;
			break;
		case "left":
			if (spriteNum == 1)
			image = left1;
			if(spriteNum == 2)
			image = left2;
			break;
		case "right":
			if (spriteNum == 1)
			image = right1;
			if(spriteNum == 2)
			image = right2;
			break;
		}
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		
	}

}
