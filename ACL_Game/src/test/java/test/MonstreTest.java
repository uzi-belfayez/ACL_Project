package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.GamePanel;
import main.KeyHandler;
import entity.Player;
import entity.Monstre;

class MonstreTest {
    
	private GamePanel gp;
    private Player player;
    private Monstre monstre;
    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        KeyHandler keyH = new KeyHandler(); // Initialize KeyHandler
        player = new Player(gp, keyH); // Pass KeyHandler to Player
        monstre = new Monstre(gp, 100, 100); // Initial position of the monster
        player.worldX = 150;  // Initial player position
        player.worldY = 150;  // Initial player position
    }

    @Test
    void testMovementTowardsPlayer() {
        // Move the player towards the monster
        player.worldX = 150;
        player.worldY = 150;

        monstre.update(player);
        
        // After the update, the monster should be moving towards the player
        assertTrue(monstre.worldX < player.worldX || monstre.worldY < player.worldY, "Monster should move towards the player's position.");
    }

    @Test
    void testMonsterDetectionRange() {
        // Move the player out of detection range
        player.worldX = 500;
        player.worldY = 500;

        monstre.update(player);

        // Check if the monster hasn't moved by verifying its position
        int initialX = monstre.worldX;
        int initialY = monstre.worldY;
        monstre.update(player); // Call update again

        assertEquals(initialX, monstre.worldX, "Monster's X position should not change when player is out of range.");
        assertEquals(initialY, monstre.worldY, "Monster's Y position should not change when player is out of range.");
    }

    /*@Test
    void testMonsterDeathOnAttack() {
        // Simulate the player attacking (set a flag directly)
        player.setAttacking(true); // Assuming this method exists, otherwise use an alternative flag
        player.direction = "down";  // Attack in the down direction
        
        // Monster should be alive before the attack
        assertTrue(monstre.isAlive(), "Monster should be alive before the attack.");

        // Update the monster
        monstre.update(player);

        // After the player attack, the monster should be dead
        assertFalse(monstre.isAlive(), "Monster should be dead after the player attacks it.");
    }*/

    @Test
    void testMonsterDoesNotMoveWhenDead() {
        // Simulate the monster dying
        monstre.setAlive(false);
        
        // Save initial position
        int initialX = monstre.worldX;
        int initialY = monstre.worldY;

        // Update the monster (it should not move)
        monstre.update(player);
        
        // Monster's position should remain unchanged
        assertEquals(initialX, monstre.worldX, "Monster's X position should not change when dead.");
        assertEquals(initialY, monstre.worldY, "Monster's Y position should not change when dead.");
    }

   /* @Test
    void testMonsterSpriteSelection() {
        // Test if the correct sprite is chosen based on the monster's direction
        
        // Set direction to "right"
        monstre.direction = "right";
        monstre.update(player);
        
        // The frame index should be 0 or valid, as the sprite is being updated
        assertNotNull(monstre.rightFrames[monstre.frameIndex], "Monster should have a valid sprite when moving right.");
        
        // Set direction to "down"
        monstre.direction = "down";
        monstre.update(player);
        
        // The frame index should still be valid for the "down" direction
        assertNotNull(monstre.downFrames[monstre.frameIndex], "Monster should have a valid sprite when moving down.");
    }*/
}
