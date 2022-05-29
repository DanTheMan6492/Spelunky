package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class skeleton extends Entity
{
	public int awakenTimer;
	public boolean awakened;

	public skeleton(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		dir = 1;
		vx = 0;
		awakened = false;
	}
	
	public void detect() {
		if(awakenTimer > 0) {return;}
		
		int mapX = x/128, spelunkerX = Frame.Ana.x/128;
		int mapY = y/128, spelunkerY = Frame.Ana.y/128;
		
		if(Math.abs(mapX - spelunkerX) <= 3 && mapY == spelunkerY) {
			awakenTimer = 10;
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y+5);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y+5);
		tx.scale(dir, 1);
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					vx *= -1;
					dir *= -1;
					break;
	
				case 2:
					vx *= -1;
					dir *= -1;
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					break;
					
				case 0:
					if(flag == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		if(flag == false) {
			grounded = false;
		}
		
		if(!grounded) {
			vy += 2;
		}
		
		if(!awakened) {
			detect();
			Sprite = getImage("/imgs/Monsters/Skeleton/skull.gif");
			if(awakenTimer > 0) {
				awakenTimer --;
				if(awakenTimer == 0) {
					awakened = true;
					vx = 8;
				}
				Sprite = getImage("/imgs/Monsters/Skeleton/skeletonAwaken.gif");
			}
		}else {
			Sprite = getImage("/imgs/Monsters/Skeleton/skeletonWalk.gif");
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
