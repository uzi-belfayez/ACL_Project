package entity;


import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Ghost extends Entity {
    GamePanel gp;
    public BufferedImage left1, left2, right1, right2;
    public int patrolStartX;
	public int patrolEndX; 

    public Ghost(GamePanel gp) {
        this.gp = gp;
        
 
        this.patrolStartX = 0;
        this.patrolEndX = gp.worldWidth;

        setDefaultValues();
        getGhostImage();
        solidArea = new Rectangle(8, 16, 32, 32);
    }

    public void setDefaultValues() {

        worldX = patrolStartX;
        worldY = gp.tileSize * 10; 
        speed = 4; 
        direction = "right";
    }

    public void getGhostImage() {
        try {
            left1 = ImageIO.read(getClass().getResourceAsStream("/Ghost/ghostleft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/Ghost/ghostleft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/Ghost/ghostright1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/Ghost/ghostright2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (direction.equals("right") && worldX < patrolEndX) {
            worldX += speed;
            if (worldX >= patrolEndX) {
                direction = "left"; 
            }
        } else if (direction.equals("left") && worldX > patrolStartX) {
            worldX -= speed;
            if (worldX <= patrolStartX) {
                direction = "right";
            }
        }

        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
        
        if (gp.player.solidArea.intersects(this.getCollisionArea())) {
            gp.player.reduceLife(); 
        }
    }

    public Rectangle getCollisionArea() {
        Rectangle collisionArea = new Rectangle(worldX + solidArea.x, worldY + solidArea.y,
                                                solidArea.width, solidArea.height);
        return collisionArea;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        if (direction.equals("left")) {
            image = (spriteNum == 1) ? left1 : left2;
        } else if (direction.equals("right")) {
            image = (spriteNum == 1) ? right1 : right2;
        }
        

        g2.drawImage(image, worldX - gp.player.worldX + gp.screenWidth / 2, 
                     worldY - gp.player.worldY + gp.screenHeight / 2, 
                     gp.tileSize, gp.tileSize, null);
    }
}
