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
	
	public int moveDuration, moveTimer;

	public mantrap(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		//will put stuff for detecting if edible entities are touching, if so, kill that entity and go to sleep
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
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
}
