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

    public int keysCollected = 0; // Number of keys collected by the player

    // Sprite sheets and frames for all directions
    private BufferedImage rightSpriteSheet, leftSpriteSheet, upSpriteSheet, downSpriteSheet;
    private BufferedImage[] rightFrames, leftFrames, upFrames, downFrames;

    // Attack sprite sheets and frames for all directions
    private BufferedImage rightAttackSpriteSheet, leftAttackSpriteSheet, upAttackSpriteSheet, downAttackSpriteSheet;
    private BufferedImage[] rightAttackFrames, leftAttackFrames, upAttackFrames, downAttackFrames;

    // Fireball images for each direction
    private BufferedImage fireballUpImage, fireballDownImage, fireballLeftImage, fireballRightImage;

    private int frameIndex = 0; // Current frame index
    private int frameCounter = 0; // Controls the frame rate
    private boolean isAttacking = false;

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
            // Load movement sprite sheets
            rightSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/RIGHT.png"));
            leftSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/LEFT.png"));
            upSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/BACK.png"));
            downSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/FRONT.png"));

            // Load attack sprite sheets for all directions
            rightAttackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/ATTACK_RIGHT.png"));
            leftAttackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/ATTACK_LEFT.png"));
            upAttackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/ATTACK_UP.png"));
            downAttackSpriteSheet = ImageIO.read(getClass().getResourceAsStream("/player/ATTACK_DOWN.png"));

            // Load fireball images
            fireballUpImage = ImageIO.read(getClass().getResourceAsStream("/Player/0.png"));
            fireballDownImage = ImageIO.read(getClass().getResourceAsStream("/Player/30.png"));
            fireballLeftImage = ImageIO.read(getClass().getResourceAsStream("/Player/29.png"));
            fireballRightImage = ImageIO.read(getClass().getResourceAsStream("/Player/28.png"));

            // Extract movement frames
            rightFrames = extractFrames(rightSpriteSheet, 15);
            leftFrames = extractFrames(leftSpriteSheet, 15);
            upFrames = extractFrames(upSpriteSheet, 15);
            downFrames = extractFrames(downSpriteSheet, 15);

            // Extract attack frames
            rightAttackFrames = extractFrames(rightAttackSpriteSheet, 15);
            leftAttackFrames = extractFrames(leftAttackSpriteSheet, 15);
            upAttackFrames = extractFrames(upAttackSpriteSheet, 15);
            downAttackFrames = extractFrames(downAttackSpriteSheet, 15);

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

    public void update() {
        if (keyH.firePressed && !isAttacking) {
            isAttacking = true;
            frameIndex = 0;
            frameCounter = 0;

            // Calculate fireball starting position
            int fireballX = worldX;
            int fireballY = worldY;
            BufferedImage fireballImage = null;

            switch (direction) {
                case "up":
                    fireballY -= gp.tileSize;
                    fireballImage = fireballUpImage;
                    break;
                case "down":
                    fireballY += gp.tileSize;
                    fireballImage = fireballDownImage;
                    break;
                case "left":
                    fireballX -= gp.tileSize;
                    fireballImage = fireballLeftImage;
                    break;
                case "right":
                    fireballX += gp.tileSize;
                    fireballImage = fireballRightImage;
                    break;
            }

            gp.fireballs.add(new Fireball(gp, fireballX, fireballY, direction, fireballImage));
            return;
        }

        if (keyH.attackPressed && !isAttacking) {
            isAttacking = true;
            frameIndex = 0;
            frameCounter = 0;
            
            return;
        }

        if (isAttacking) {
            frameCounter++;
            if (frameCounter > 0) { // Adjust for smoother animation
                frameIndex++;
                frameCounter = 0;
            }

            if (frameIndex >= 15) { // End attack after one animation cycle
                isAttacking = false;
                frameIndex = 0;
            }
            return;
        }

        handleMovement();
    }
 // In the Player class
    public boolean isAttacking() {
        return isAttacking;
    }

   /* private boolean checkAttackCollision(Monstre monster) {
        Rectangle attackArea = new Rectangle(worldX, worldY, solidArea.width, solidArea.height);

        switch (direction) {
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

        return attackArea.intersects(monster.getSolidArea());
    }*/
    
    private void handleMovement() {
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            direction = keyH.upPressed ? "up" : keyH.downPressed ? "down" : keyH.leftPressed ? "left" : "right";

            collisionOn = false;
            gp.cChecker.checkTile(this);

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

            frameCounter++;
            if (frameCounter > 2) {
                frameIndex = (frameIndex + 1) % 15;
                frameCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        if (isAttacking) {
            switch (direction) {
                case "right":
                    image = rightAttackFrames[frameIndex];
                    break;
                case "left":
                    image = leftAttackFrames[frameIndex];
                    break;
                case "up":
                    image = upAttackFrames[frameIndex];
                    break;
                case "down":
                    image = downAttackFrames[frameIndex];
                    break;
            }
        } else {
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
        }

        int scaledWidth = gp.tileSize * 2;
        int scaledHeight = gp.tileSize * 2;

        int drawX = screenX - (scaledWidth - gp.tileSize) / 2;
        int drawY = screenY - (scaledHeight - gp.tileSize) / 2;

        g2.drawImage(image, drawX, drawY, scaledWidth, scaledHeight, null);
    }
}