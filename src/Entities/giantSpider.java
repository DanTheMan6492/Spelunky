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
	public int jumpTimer, dropTimer;
	public boolean hanging;
	
	public giantSpider(int x, int y, boolean visible, String path) {
		super(x, y, 256, 128, visible, path);
		jumpTimer = 20;
		hanging = true;
		Sprite = getImage("/imgs/Monsters/GiantSpider/giantSpiderNeutral.png");
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		int mapX = x/128, spelunkerX = Frame.Ana.x/128;
		int mapY = y/128, spelunkerY = Frame.Ana.y/128;
		
		if((mapX == spelunkerX || mapX + 1 == spelunkerX) && Math.abs(mapY - spelunkerY) <= 6 && mapY < spelunkerY) {
			hanging = false;
			dropTimer = 20;
		}
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
		if(hanging) {
			tx.setToTranslation((int)(x - Camera.x), (int)(x - Camera.y));
		}else {
			tx.setToTranslation((int)(x - Camera.x), (int)(x - Camera.y));
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
					Sprite = getImage("/imgs/Monsters/GiantSpider/giantSpiderStand.gif");
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
		if(flag == false && hanging == false) {
			grounded = false;
		}
		
		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(x - Camera.y), w,h);
	}
}
