package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import main.GamePanel;
public class Fireball extends Entity {
    GamePanel gp;
    BufferedImage image;
    int speed = 8;

    public Fireball(GamePanel gp, int startX, int startY, String direction, BufferedImage image) {
        this.gp = gp;
        this.worldX = startX;
        this.worldY = startY;
        this.direction = direction;
        this.image = image;

        solidArea = new Rectangle(0, 0, gp.tileSize, gp.tileSize);
    }

    public void update() {
        switch (direction) {
            case "up": worldY -= speed; break;
            case "down": worldY += speed; break;
            case "left": worldX -= speed; break;
            case "right": worldX += speed; break;
        }

        // Check for collision with monsters
        for (int i = 0; i < gp.monsters.length; i++) {
            Monstre monster = gp.monsters[i];
            if (monster != null && isColliding(monster)) {
                gp.monsters[i] = null; // Remove the monster
                gp.fireballs.remove(this); // Remove the fireball
                System.out.println("Monster hit!");
                break;
            }
        }

        // Remove fireball if it goes off-screen
        if (worldX < 0 || worldX > gp.worldWidth || worldY < 0 || worldY > gp.worldHeight) {
            gp.fireballs.remove(this);
        }
    }

    // Helper method to check collision
    private boolean isColliding(Monstre monster) {
        Rectangle fireballRect = new Rectangle(worldX, worldY, gp.tileSize, gp.tileSize);
        Rectangle monsterRect = new Rectangle(monster.worldX, monster.worldY, gp.tileSize, gp.tileSize);
        return fireballRect.intersects(monsterRect);
    }


    public void draw(Graphics2D g2) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;
        int scaledWidth = gp.tileSize * 2; // Twice the normal tile size
        int scaledHeight = gp.tileSize * 2;
        screenX -= (scaledWidth - gp.tileSize) / 2;
        screenY -= (scaledHeight - gp.tileSize) / 2;
        g2.drawImage(image, screenX, screenY, scaledWidth, scaledHeight, null);
    }
}
