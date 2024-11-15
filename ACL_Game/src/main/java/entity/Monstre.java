package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Monstre extends Entity {
    GamePanel gp;
    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    private int patrolStartX, patrolEndX; // Horizontal patrol range
    private int patrolStartY, patrolEndY; // Vertical patrol range
    private String patrolDirection = "vertical"; // Set the patrol to vertical or horizontal

    public Monstre(GamePanel gp, int patrolStartX, int patrolEndX, int patrolStartY, int patrolEndY) {
        this.gp = gp;
        this.patrolStartX = patrolStartX;
        this.patrolEndX = patrolEndX;
        this.patrolStartY = patrolStartY;
        this.patrolEndY = patrolEndY;
        
        setDefaultValues();
        getMonstreImage();
        solidArea = new Rectangle(8, 16, 32, 32); // Collision area for the monster
    }

    public void setDefaultValues() {
        // Position the monster at the start of its patrol range
        worldX = patrolStartX;
        worldY = patrolStartY;
        speed = 1;
        direction = "down"; // Starting direction (adjust based on your patrol area)
    }

    public void getMonstreImage() {
        try {
            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_down_2.png"));
            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/skeletonlord_right_2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        // Move the monster back and forth within the patrol area
        if (patrolDirection.equals("vertical")) {
            if (direction.equals("down") && worldY < patrolEndY) {
                worldY += speed;
                if (worldY >= patrolEndY) {
                    direction = "up"; // Change direction to move up
                }
            } else if (direction.equals("up") && worldY > patrolStartY) {
                worldY -= speed;
                if (worldY <= patrolStartY) {
                    direction = "down"; // Change direction to move down
                }
            }
        } else if (patrolDirection.equals("horizontal")) {
            if (direction.equals("right") && worldX < patrolEndX) {
                worldX += speed;
                if (worldX >= patrolEndX) {
                    direction = "left"; // Change direction to move left
                }
            } else if (direction.equals("left") && worldX > patrolStartX) {
                worldX -= speed;
                if (worldX <= patrolStartX) {
                    direction = "right"; // Change direction to move right
                }
            }
        }

        // Animation for the monster
        spriteCounter++;
        if (spriteCounter > 10) {
            spriteNum = (spriteNum == 1) ? 2 : 1;
            spriteCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        switch (direction) {
            case "up":
                image = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                image = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                image = (spriteNum == 1) ? left1 : left2;
                break;
            case "right":
                image = (spriteNum == 1) ? right1 : right2;
                break;
        }
        // Draw the monster using the constant world coordinates
        g2.drawImage(image, worldX - gp.player.worldX + gp.screenWidth / 2 +30 , worldY - gp.player.worldY + gp.screenHeight / 2 +30 , gp.tileSize, gp.tileSize, null);
    }
    
}
