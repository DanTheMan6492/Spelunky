package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import Blocks.Block;
import Blocks.LevelBuilder;

public class snake extends Entity
{
	
	public int moveDuration, moveTimer;

	public snake(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		dir = -1;
		moveTimer = 0;
		Sprite = getImage("/imgs/Characters/Spliced/snakeStandRight.gif");
	}
	
	public void checkGround() {
		int mapX = (int) (x / 128);
		int mapY = (int) (y / 128);
		
		if((mapX == 0 && vx < 0)
		|| (mapX == 32 && vx > 0)) {
			vx *= -1;
			return;
		}
		
		if(mapY != 40) {
			if(dir == -1) {
				if(LevelBuilder.level[mapX-1][mapY+1] != null) {
					vx *= -1;
					dir = 1;
				}
			}else {
				if(LevelBuilder.level[mapX+1][mapY+1] != null) {
					vx *= -1;
					dir = -1;
				}
			}
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y);
		tx.scale(dir, 1);
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					vx = 0;
					dir *= -1;
					break;
	
				case 2:
					vx = 0;
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
		
		//checkGround();
		
		if(vx < -0.5) 
			dir = -1;
		else if(vx > 0.5)
			dir = 1;
		
		
		if(!grounded) {
			vy += 2;
			vx = 0;
		}else {
			if(moveTimer > 0) {
				if(moveDuration == 0) {
					moveTimer --;
				}
				if(moveTimer == 0) {
					moveDuration = 30;
				}
				if(moveDuration > 0) {
					moveDuration --;
					if(dir == 1) {
						vx = 8;
					}else {
						vx = -8;
					}
				}
			}
		}
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		//g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
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