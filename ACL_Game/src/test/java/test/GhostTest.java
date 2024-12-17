package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import main.GamePanel;
import main.KeyHandler;
import entity.Player;
import entity.Ghost;
import entity.Monstre;

import org.junit.jupiter.api.Test;

class GhostTest {

	private GamePanel gp;
    private Ghost ghost;

    @BeforeEach
    void setUp() {
        gp = new GamePanel();
        KeyHandler keyH = new KeyHandler();
        Player player = new Player(gp,keyH); // Mock Player instance
        gp.player = player;      // Attach player to GamePanel
        ghost = new Ghost(gp);
    }



    @Test
    void testGhostMovesRightAndLeftWithinPatrolBounds() {
        int initialX = ghost.worldX;
        ghost.patrolStartX = 0;
        ghost.patrolEndX = 100;

        // Move ghost to the right
        for (int i = 0; i < 30; i++) {
            ghost.update();
        }
        assertTrue(ghost.worldX > initialX, "Ghost should move to the right initially.");

        // Move ghost to the patrol end and check for direction change
        ghost.worldX = ghost.patrolEndX;
        ghost.update();
        assertEquals("left", ghost.direction, "Ghost should switch direction to left at patrolEndX.");

        // Move ghost to the left
        for (int i = 0; i < 30; i++) {
            ghost.update();
        }
        assertTrue(ghost.worldX < ghost.patrolEndX, "Ghost should move to the left.");
    }

    @Test
    void testSpriteAnimationChanges() {
        int initialSprite = ghost.spriteNum;

        // Update ghost a few times to trigger sprite animation
        for (int i = 0; i < 20; i++) {
            ghost.update();
        }

        assertNotEquals(initialSprite, ghost.spriteNum, "Sprite number should alternate after updates.");
    }



    @Test
    void testGhostImageLoaded() {
        // Ensure ghost images are not null
        assertNotNull(ghost.left1, "Left1 image should be loaded.");
        assertNotNull(ghost.left2, "Left2 image should be loaded.");
        assertNotNull(ghost.right1, "Right1 image should be loaded.");
        assertNotNull(ghost.right2, "Right2 image should be loaded.");
    }
}
