package tile;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

import java.awt.image.BufferedImage;
public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp ;
		tile = new Tile[20];
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/map.txt");
	}
	
	public void getTileImage() {
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass/grass0-dirt-mix1.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/floor_vines1.png"));
			tile[1].collision = true ;
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass/grass1.png"));
			
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass/grass_flowers_yellow3.png"));
			
			
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/floor_vines5.png"));
			tile[4].collision = true ;
			
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/cobble_blood4.png"));
			tile[5].collision = true ;
			
			tile[6] = new Tile();
			tile[6].image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/bog_green3.png"));
			tile[6].collision = true ;
			
			tile[7] = new Tile();
			tile[7].image = ImageIO.read(getClass().getResourceAsStream("/tiles/walls/crystal_floor4.png"));
			tile[7].collision = true ;
			
			tile[8] = new Tile();
			tile[8].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava/lava3.png"));
			
			tile[9] = new Tile();
			tile[9].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice/ice3.png"));
			
			tile[10] = new Tile();
			tile[10].image = ImageIO.read(getClass().getResourceAsStream("/tiles/dngn_open_sea.png"));
			tile[10].collision = true ;
			
			tile[11] = new Tile();
			tile[11].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png")); // not used
			
			tile[12] = new Tile();
			tile[12].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice/ice2.png"));
			
			tile[13] = new Tile();
			tile[13].image = ImageIO.read(getClass().getResourceAsStream("/tiles/ice/ice1.png"));
			
			tile[14] = new Tile();
			tile[14].image = ImageIO.read(getClass().getResourceAsStream("/tiles/lava/lava1.png"));
			
			tile[15] = new Tile();
			tile[15].image = ImageIO.read(getClass().getResourceAsStream("/tiles/pebble_red0.png"));
			tile[15].collision = true ;
			
			tile[16] = new Tile();
			tile[16].image = ImageIO.read(getClass().getResourceAsStream("/tiles/crystal_wall11.png"));
			tile[16].collision = true ;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setup(int index, String imagePath, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+imagePath+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision =  collision;
			} catch(IOException e) {
				e.printStackTrace();
			}
	}
		
	
	public void loadMap(String filePath ) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			while (col < gp.maxWorldCol && row < gp.maxWorldRow) {
				String line = br.readLine();
				
				while (col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					mapTileNum[col][row] = num;
					col ++;
				}
				if (col == gp.maxWorldCol) {
					col = 0;
					row ++ ;
				}
			}
			br.close();
			
		} catch (Exception e) {
			
		}
	}
	public void checkDoorCollision() {
	    for (int i = 0; i < gp.obj.length; i++) {
	        if (gp.obj[i] != null && gp.obj[i].name.equals("Door")) { // Assuming doors are named "Door"
	            // Calculate player and object boundaries
	            int playerLeft = gp.player.worldX;
	            int playerRight = gp.player.worldX + gp.player.solidArea.width;
	            int playerTop = gp.player.worldY;
	            int playerBottom = gp.player.worldY + gp.player.solidArea.height;

	            int objLeft = gp.obj[i].worldX;
	            int objRight = gp.obj[i].worldX + gp.tileSize;
	            int objTop = gp.obj[i].worldY;
	            int objBottom = gp.obj[i].worldY + gp.tileSize;

	            // Check for overlap (collision)
	            if (playerRight > objLeft && playerLeft < objRight && playerBottom > objTop && playerTop < objBottom) {
	                // Check if the player has enough keys
	                if (gp.player.keysCollected >= 2) {
	                    // Transition to a new map based on door properties
	                    
	                        gp.changeMap("/maps/map2.txt");
	                    
	                }
	            }
	        }
	    }
	}


	
	public void draw(Graphics2D g2) {
		int worldCol = 0 ;
		int worldRow = 0 ;
	
		
		while (worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol*gp.tileSize;
			int worldY = worldRow*gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
				if ((worldX + gp.tileSize > gp.player.worldX - gp.player.screenX) && (worldX - gp.tileSize < gp.player.worldX + gp.player.screenX) &&(worldY + gp.tileSize > gp.player.worldY - gp.player.screenY) &&(worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) ) {

					g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
					}
				
				worldCol ++;
				
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow ++ ;
			
			
			}
		}
	}

}
