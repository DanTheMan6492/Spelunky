package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class bat extends Entity
{
	public boolean hanging, aggro;

	public bat(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		hanging = true;
		Sprite = getImage("/imgs/Monsters/Bat/batIdle.gif");

	}
	
	public boolean detect() 
	{
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(Math.abs(mapX - spelunkerX) <= 6 && Math.abs(mapY - spelunkerY) <= 6 && mapY < spelunkerY) 
		{
			hanging = false;
			Sprite = getImage("/imgs/Monsters/Bat/batFly.gif");
			return true;
		}
		return false;
	}
	
	public void follow(){
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		Sprite = getImage("/imgs/Monsters/Bat/batFly.gif");
		
		if(mapX < spelunkerX) {
			vx = -5;
		}else if(spelunkerX < mapX){
			vx = 5;
		}else {
			vy = 0;
		}
		
		if(mapY < spelunkerY) {
			vy = -5;
		}else if(spelunkerY < mapY){
			vy = 5;
		}else {
			vy = 0;
		}
	}
	
	public void update() 
	{
		tx.setToTranslation(x, y);
		
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				if(collide(block) == 4) {
					if(!aggro) {
						hanging = true;
						vy = 0;
						Sprite = getImage("/imgs/Monsters/Bat/batIdle.gif");
					}
				}
			}
		}
		
		if(detect()) {
			aggro = true;
		}else {
			aggro = false;
		}
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		if(aggro) 
		{
			follow();
		}else {
			vx = 0;
			if(!hanging) {
				vy = -5;
			}
		}
		
		if(hanging) {
			Sprite = getImage("imgs/Monsters/Bat/batFly.gif");
		}else {
			Sprite = getImage("imgs/Monsters/Bat/batIdle.gif");
		}

		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}	
}
