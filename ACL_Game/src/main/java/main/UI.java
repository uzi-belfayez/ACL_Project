package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import object.SuperObject;
import object.OBJ_Heart;
import object.OBJ_Boots;
import entity.Player;
public class UI {

	GamePanel gp;
	Font arial_40;
	BufferedImage bootsImage, heart_full, heart_half, heart_blank; 
	public boolean messageOn = false;
	public String message;
	int messageCounter = 0;
	
	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		this.gp = gp;
		arial_40 = new Font("Arial", Font.PLAIN, 20);
		OBJ_Boots boots = new OBJ_Boots();
		bootsImage = boots.image;
		
		//CREATE HUD OBJECT
		SuperObject heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		//g2.drawImage(bootsImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		drawPlayerLife(g2);
		//g2.drawString("Movement Speed = "+gp.player.speed, 15, 30);
		String keyText = "Keys: " + gp.player.keysCollected;

		// Calculate the position for bottom-right alignment
		int textWidth = g2.getFontMetrics().stringWidth(keyText);
		int x = gp.screenWidth - textWidth - 20; // 20px padding from the right edge
		int y = gp.screenHeight - 20; // 20px padding from the bottom edge

		g2.drawString(keyText, x, y);
		playTime += (double)1/60;
		g2.drawString("Time = "+dFormat.format(playTime), gp.tileSize*9, 30);
		
		if (messageOn == true) {
			g2.setFont((g2.getFont().deriveFont(30F)));
			g2.drawString(message, gp.tileSize/2, gp.tileSize*5);
			messageCounter++;
			
			if (messageCounter >180) {
				messageCounter = 0;
				messageOn = false;
			}
		}
	}
	
public void drawPlayerLife(Graphics2D g2) {
	int x = gp.tileSize/2;
	int y = gp.tileSize/2;
	int i = 0;
	
	// DRAW MAX LIFE
	while(i < gp.player.maxLife/2) {
		g2.drawImage(heart_blank, x, y, null );
		i++;
		x+= gp.tileSize;
	}
	//RESET
	x = gp.tileSize/2;
	y = gp.tileSize/2;
	i = 0;
	
	// DRAW CURRENT LIFE
	while(i < gp.player.life) {
		g2.drawImage(heart_half, x, y, null);
		i++;
		if(i < gp.player.life) {
			g2.drawImage(heart_full, x, y, null);
		}
		i++;
		x += gp.tileSize;
	}
}
public void drawloseScreen(Graphics2D g2) {
	// Set the font and color for the "You Win" message
	Font winFont = new Font("Arial", Font.BOLD, 80);
	g2.setFont(winFont);
	g2.setColor(Color.RED);
	
	// Define the message
	String winMessage = "GAME OVER!";
	
	// Calculate the position to center the text
	int textWidth = g2.getFontMetrics().stringWidth(winMessage);
	int x = (gp.screenWidth - textWidth) / 2;
	int y = gp.screenHeight / 2;
	
	// Draw the message
	g2.drawString(winMessage, x, y);
}
public void drawWinScreen(Graphics2D g2) {
	// Set the font and color for the "You Win" message
	Font winFont = new Font("Arial", Font.BOLD, 80);
	g2.setFont(winFont);
	g2.setColor(Color.YELLOW);
	
	// Define the message
	String winMessage = "YOU WIN!";
	
	// Calculate the position to center the text
	int textWidth = g2.getFontMetrics().stringWidth(winMessage);
	int x = (gp.screenWidth - textWidth) / 2;
	int y = gp.screenHeight / 2;
	
	// Draw the message
	g2.drawString(winMessage, x, y);
}
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
}
