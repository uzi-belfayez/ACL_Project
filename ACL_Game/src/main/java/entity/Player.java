package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0; // number of keys the player got

    // Sprite sheets and frames for all directions
    private BufferedImage rightSpriteSheet, leftSpriteSheet, upSpriteSheet, downSpriteSheet;
    private BufferedImage[] rightFrames, leftFrames, upFrames, downFrames;
    private int frameIndex = 0; // Current frame index
    private int frameCounter = 0; // Controls the frame rate

    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;

        screenX = (gp.screenWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.screenHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle(8, 16, 32, 32);

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        loadSpriteSheets();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 23;
        speed = 4;
        direction = "down";
        maxLife = 6;
        life = maxLife;
    }

    public void loadSpriteSheets() {
        try {
            // Load sprite sheets for all directions
            rightSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/RIGHT.png"));
            leftSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/LEFT.png"));
            upSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/BACK.png"));
            downSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/FRONT.png"));

            // Extract frames from each sprite sheet
            rightFrames = extractFrames(rightSpriteSheet, 15);
            leftFrames = extractFrames(leftSpriteSheet, 15);
            upFrames = extractFrames(upSpriteSheet, 15);
            downFrames = extractFrames(downSpriteSheet, 15);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage[] extractFrames(BufferedImage spriteSheet, int frameCount) {
        // Extracts frames from a sprite sheet
        int frameWidth = spriteSheet.getWidth() / frameCount; // Adjust to 15 frames
        int frameHeight = spriteSheet.getHeight();
        BufferedImage[] frames = new BufferedImage[frameCount];
        for (int i = 0; i < frameCount; i++) {
            frames[i] = spriteSheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
        }
        return frames;
    }


    public void update() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
                
            }
           
            collisionOn = false;
            gp.cChecker.checkTile(this);

            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                switch (direction) {
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

            // Handle animation frame updates
            frameCounter++;
            if (frameCounter > 3) { // Adjust for smoother/faster animation
                frameIndex = (frameIndex + 1) % 15; // Loop through 15 frames
                frameCounter = 0;
            }
        }
    }


    public void pickUpObject(int i) {
        if (i != 999) {
            String objectName = gp.obj[i].name;
            switch (objectName) {
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
                    new java.util.Timer().schedule(new java.util.TimerTask() {
                        @Override
                        public void run() {
                            speed -= 2; // Reset speed after 3 seconds
                        }
                    }, 3000); // 3000 milliseconds = 3 seconds
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Select the correct frame based on direction
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

        // Scale the character (e.g., 2x size)
        int scaledWidth = gp.tileSize * 2;  // Increase width
        int scaledHeight = gp.tileSize * 2 ; // Increase height

        // Center the larger character properly
        int drawX = screenX - (scaledWidth - gp.tileSize) / 2;
        int drawY = screenY - (scaledHeight - gp.tileSize) / 2;

        g2.drawImage(image, drawX, drawY, scaledWidth, scaledHeight, null);
    }

}
