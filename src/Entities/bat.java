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
		dir = 1;
	}
	
	public void detect() 
	{
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(hanging == true) {
			if(Math.abs(mapX - spelunkerX) <= 6 && Math.abs(mapY - spelunkerY) <= 6 && mapY < spelunkerY) 
			{
				hanging = false;
				aggro = true;
			}
		}else {
			if(aggro) {
				if(Math.abs(mapX - spelunkerX) <= 5 && Math.abs(mapY - spelunkerY) <= 5) 
				{
					follow();
				}else {
					vx = 0;
					vy = -5;
				}
			}
		}
	}
	
	public void follow(){
		if(Frame.Ana.x + Frame.Ana.w/2 > x + w/2) {
			vx = 5;
		}
		
		if(Frame.Ana.x + Frame.Ana.w/2 < x + w/2) {
			vx = -5;
		}
		
		if(Frame.Ana.y + Frame.Ana.h/2 > y + h/2) {
			vy = 5;
		}
		
		if(Frame.Ana.y + Frame.Ana.h/2 < y + h/2) {
			vy = -5;
		}
	}
	
	public void update() 
	{
		if(dir == 1) {
			tx.setToTranslation((int)(x - Camera.x), (int)(y - Camera.y));
		}else {
			tx.setToTranslation((int)(x - Camera.x + 128), (int)(y - Camera.y));
		}
		tx.scale(dir, 1);
		
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				if(collide(block) == 4) {
					if(aggro) {
						hanging = true;
						vy = 0;
						Sprite = getImage("/imgs/Monsters/Bat/batIdle.gif");
					}
				}
			}
		}
		
		
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		detect();
		
		if(!hanging) {
			Sprite = getImage("/imgs/Monsters/Bat/batFly.gif");
		}else {
			Sprite = getImage("/imgs/Monsters/Bat/batIdle.gif");
		}

		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), w, h);
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