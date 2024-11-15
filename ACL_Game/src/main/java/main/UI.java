package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Boots;

public class UI {

	GamePanel gp;
	Font arial_40;
	BufferedImage bootsImage;
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
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		//g2.drawImage(bootsImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
		g2.drawString("Movement Speed = "+gp.player.speed, 15, 30);
		
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
	
	public void showMessage(String text) {
		message = text;
		messageOn = true;
	}
}
