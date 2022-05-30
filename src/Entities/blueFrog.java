package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class blueFrog extends Entity
{
	public int jumpTimer;

	public blueFrog(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		Sprite = getImage("/imgs/Monsters/BlueFrog/blueFrogStand.gif");
		dir = 1;
		jumpTimer = 60;
		// TODO Auto-generated constructor stub
	}
	
	public boolean detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(Math.abs(mapX - spelunkerX) <= 5 && Math.abs(mapY - spelunkerY) <= 5) {
			return true;
		}
		return false;
	}
	
	public void jump() 
	{
		if(grounded) {
			grounded = false;
			vy = -30;
			if(Frame.Ana.x + Frame.Ana.w/2 < x + w/2) {
				vx = -10;
			}else {
				vx = 10;
			}
			Sprite = getImage("/imgs/Monsters/BlueFrog/blueFrogJump.gif");
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y+20);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y+20);
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		tx.scale(dir, 1);
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					if(!grounded) {
						vx *= -1;
						dir *= -1;
					}
					break;
	
				case 2:
					if(!grounded) {
						vx *= -1;
						dir *= -1;
					}
					break;
	
				case 3:
					vy = 0;
					vx = 0;
					grounded = true;
					flag = true;
					Sprite = getImage("/imgs/Monsters/BlueFrog/blueFrogStand.gif");
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
		
		if(jumpTimer > 0) {
			jumpTimer --;
			if(jumpTimer == 0) {
				if(detect()) {
					jump();
				}
				jumpTimer = 60;
			}
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
