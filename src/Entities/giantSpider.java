package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class giantSpider extends Entity
{
	
	public int jumpTimer;
	public boolean hanging;

	public giantSpider(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		jumpTimer = 60;
		hanging = true;
		// TODO Auto-generated constructor stub
	}
	
	public boolean detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(mapX == spelunkerX && spelunkerY - mapY <= 7) {
			return true;
		}
		return false;
	}
	
	public void jump() {
		jumpTimer = 60;
		vy = 30;
		if(Frame.Ana.x < x) {
			vx = -10;
		}else {
			vx = 10;
		}
	}
	
	public void update() {
		if(!grounded) { 
			vy += 2;
		}
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					vx = 0;
					break;
	
				case 2:
					vx = 0;
					break;
	
				case 3:
					vy = 0;
					vx = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					vy = 0;
					break;
					
				case 0:
					if(flag == false && hanging == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		
		if(detect()) {
			hanging = false;
		}
		
		if(hanging == false) {
			if(jumpTimer > 0) {
				if(grounded) {
					jumpTimer --;
				}
				if(jumpTimer == 0) {
					jump();
				}
			}
			x += vx;
			y += vy;
		}
	}
	
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
}
