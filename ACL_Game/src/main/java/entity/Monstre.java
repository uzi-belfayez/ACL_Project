package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;

public class Monstre extends Entity {
    GamePanel gp;
    private BufferedImage rightSpriteSheet, leftSpriteSheet, upSpriteSheet, downSpriteSheet;
    private BufferedImage[] rightFrames, leftFrames, upFrames, downFrames;

    private int frameIndex = 0;
    private int frameCounter = 0;

    private boolean isMoving = false;
    private int detectionRange = 7 * 32; // Detection range in pixels (5 tiles)
    private boolean alive = true;

    public Monstre(GamePanel gp, int initialX, int initialY) {
        this.gp = gp;

        worldX = initialX;
        worldY = initialY;

        solidArea = new Rectangle(8, 16, 32, 32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        loadSpriteSheets();
    }

    public Rectangle getSolidArea() {
        return new Rectangle(worldX + solidArea.x, worldY + solidArea.y, solidArea.width, solidArea.height);
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setDefaultValues() {
        speed = 2;
        direction = "down";
    }

    public void loadSpriteSheets() {
        try {
            // Load movement sprite sheets
            rightSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/monster/monsterSlashingRight.png"));
            leftSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/monster/monsterSlashingLeft.png"));
            upSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/monster/monsterSlashingRight.png"));
            downSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/monster/monsterSlashingLeft.png"));

            // Extract frames
            rightFrames = extractFrames(rightSpriteSheet, 12);
            leftFrames = extractFrames(leftSpriteSheet, 12);
            upFrames = extractFrames(upSpriteSheet, 12);
            downFrames = extractFrames(downSpriteSheet, 12);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage[] extractFrames(BufferedImage spriteSheet, int frameCount) {
        int frameWidth = spriteSheet.getWidth() / frameCount;
        int frameHeight = spriteSheet.getHeight();
        BufferedImage[] frames = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }

    public void update(Player player) {
        if (!alive) return;

        // Check if attacked by player
        if (player.isAttacking()) {
            Rectangle attackArea = new Rectangle(player.worldX, player.worldY, player.solidArea.width, player.solidArea.height);

            switch (player.direction) {
                case "up":
                    attackArea.y -= gp.tileSize;
                    break;
                case "down":
                    attackArea.y += gp.tileSize;
                    break;
                case "left":
                    attackArea.x -= gp.tileSize;
                    break;
                case "right":
                    attackArea.x += gp.tileSize;
                    break;
            }

            if (attackArea.intersects(this.getSolidArea())) {
                this.alive = false; // Mark the monster as not alive
                return; // Skip further updates
            }
        }

        // Movement logic
        int distanceX = Math.abs(player.worldX - this.worldX);
        int distanceY = Math.abs(player.worldY - this.worldY);

        if (distanceX <= detectionRange && distanceY <= detectionRange) {
            isMoving = true;
            moveTowardsPlayer(player);
        } else {
            isMoving = false;
        }

        if (isMoving) {
            frameCounter++;
            if (frameCounter > 2) {
                frameIndex = (frameIndex + 1) % 12;
                frameCounter = 0;
            }
        }
    }

    private void moveTowardsPlayer(Player player) {
        if (player.worldX < this.worldX) {
            direction = "left";
            worldX -= speed;
        } else if (player.worldX > this.worldX) {
            direction = "right";
            worldX += speed;
        }

        if (player.worldY < this.worldY) {
            direction = "up";
            worldY -= speed;
        } else if (player.worldY > this.worldY) {
            direction = "down";
            worldY += speed;
        }
    }

    public void draw(Graphics2D g2) {
        if (!alive) return; // Don't draw if not alive

        BufferedImage image = null;
        switch (direction) {
            case "right":
                image = rightFrames[frameIndex];
                break;
            case "left":
                image = leftFrames[frameIndex];
                break;
            case "up":
                image = upFrames[frameIndex];
                break;
            case "down":
                image = downFrames[frameIndex];
                break;
        }

        int scaledWidth = gp.tileSize * 9 / 5;
        int scaledHeight = gp.tileSize * 9 / 5;

        int drawX = worldX - gp.player.worldX + gp.player.screenX;
        int drawY = worldY - gp.player.worldY + gp.player.screenY;

        g2.drawImage(image, drawX, drawY, scaledWidth, scaledHeight, null);
    }
}
