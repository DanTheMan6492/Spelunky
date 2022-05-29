package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class spider extends Entity
{
	
	public int jumpTimer, dropTimer;
	public boolean hanging;

	public spider(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		jumpTimer = 10;
		hanging = true;
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(mapX == spelunkerX && Math.abs(mapY - spelunkerY) <= 5 && mapY < spelunkerY) {
			hanging = false;
			dropTimer = 10;
		}
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
		}
	}
	
	public void update() {
		tx.setToTranslation(x-Camera.x, y-Camera.y+20);
		tx.scale(1, 1);
		
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
					dropTimer = 0;
					Sprite = getImage("/imgs/Monsters/Spider/spider_stand.gif");
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
		
		if(hanging) {
			Sprite = getImage("/imgs/Monsters/Spider/spider_neutral.gif");
			detect();
		}else {
			if(!grounded) {
				vy += 2;
				if(vy < 0) {
					Sprite = getImage("/imgs/Monsters/Spider/spider_jump.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Spider/spider_fall.gif");
				}
				if(dropTimer > 0) {
					dropTimer --;
					Sprite = getImage("/imgs/Monsters/Spider/spider_drop.gif");
				}
			}else {
				if(jumpTimer > 0) {
					jumpTimer --;
					if(jumpTimer == 0) {
						jump();
						jumpTimer = 20;
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
		g2.drawImage(Sprite, tx, null);
	}
}
