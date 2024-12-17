package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.GamePanel;
import main.KeyHandler;
import entity.Player;
import entity.Monstre;

class PlayerTest {

    private Player player;
    private GamePanel gp;
    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        // Initialize the GamePanel and KeyHandler mocks (if needed)
        gp = new GamePanel();
        keyHandler = new KeyHandler();
        player = new Player(gp, keyHandler);
    }

    @Test
    void testInitialLife() {
        // Test the initial life value of the player
        assertEquals(6, player.life, "Player should start with 6 life points.");
    }

    @Test
    void testInitialKeysCollected() {
        // Test the initial number of keys collected by the player
        assertEquals(0, player.keysCollected, "Player should start with 0 keys collected.");
    }

    @Test
    
    void testMovement() {
        // Simulate the player moving up
        keyHandler.upPressed = true;
        player.update();
        assertTrue(player.worldY < 23 * gp.tileSize, "Player should have moved up.");

        // Reset movement and test moving down
        keyHandler.upPressed = false;
        keyHandler.downPressed = true;
        player.update();
        assertTrue(player.worldY > 20 * gp.tileSize, "Player should have moved down.");

        // Reset and test left movement
        keyHandler.downPressed = false;
        keyHandler.leftPressed = true;
        player.update();
        assertTrue(player.worldX < 23 * gp.tileSize, "Player should have moved left.");

        // Reset and test right movement
        keyHandler.leftPressed = false;
        keyHandler.rightPressed = true;
        player.update();
        assertTrue(player.worldX > 20 * gp.tileSize, "Player should have moved right.");
    }


   /* @Test
    void testTakeDamage() {
        // Simulate the player taking damage
        player.life = 5;
        player.damageCooldown = 0; // Set cooldown to 0 to allow damage
        
        Monstre monster = new Monstre(gp);
        monster.worldX = player.worldX;
        monster.worldY = player.worldY;

        // Simulate collision with the monster
        player.handleMonsterCollision();
        assertEquals(4, player.life, "Player's life should decrease after taking damage from a monster.");

        // Simulate a second hit, but with cooldown active
        player.handleMonsterCollision();
        assertEquals(4, player.life, "Player's life should not decrease if cooldown is active.");
    }
*/
    @Test
    void testFireballCreation() {
        // Simulate the player pressing the fire button to attack
        keyHandler.firePressed = true;
        player.update();
        
        assertTrue(gp.fireballs.size() > 0, "Fireball should be created when the player attacks.");
    }

   /* @Test
    void testAttackAnimation() {
        // Simulate the player pressing the attack button
        keyHandler.attackPressed = true;
        player.update();
        
        assertTrue(player.isAttacking(), "Player should be attacking after pressing the attack button.");
        
        // Let the attack animation run for a couple of frames
        for (int i = 0; i < 20; i++) {
            player.update();
        }
        
        assertFalse(player.isAttacking(), "Player should stop attacking after the animation ends.");
    }*/
}
