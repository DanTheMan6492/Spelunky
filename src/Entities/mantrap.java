package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class mantrap extends Entity
{
	
	public int moveDuration, waitTimer;

	public mantrap(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		//will put stuff for detecting if edible entities are touching, if so, kill that entity and go to sleep
	}
	
	public void checkGround() {
		int mapX = (int) (x / 128);
		
		if((mapX == 0 && vx < 0)
		|| (mapX == 32 && vx > 0)) {
			Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
			waitTimer = 20;
			vx = 0;
			dir *= -1;
			return;
		}
		
		if(mapX == 0) {
			if(vx < 0) {
				Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
				waitTimer = 20;
				vx = 0;
				dir *= -1;
			}
			return;
		}else if(mapX == 39) {
			if(vx > 0) {
				Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
				waitTimer = 20;
				vx = 0;
				dir *= -1;
			}
			return;
		}

		int XProj = (int) ((x+vx+64) / 128);
		int YProj = (int) ((y+vy) / 128);

		if(LevelBuilder.level[YProj+1][XProj] == null
		|| LevelBuilder.level[YProj+1][XProj].solid == false) {
			Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
			waitTimer = 20;
			vx = 0;
			dir *= -1;
		}
	}
	
	public void update() {
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
		
		detect();
		
		if(!grounded) {
			vy -= 2;
			y -= vy;
			vx = 0;
		}else {
			if(waitTimer > 0) {
				if(moveDuration == 0) {
					waitTimer --;
				}
				if(waitTimer == 0) {
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
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
}
