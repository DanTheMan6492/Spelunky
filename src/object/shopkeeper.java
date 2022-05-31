package object;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import Entities.Camera;
import Entities.Entity;
import General.Frame;

public class shopkeeper extends Entity
{
	public boolean aggro, angry;

	public shopkeeper(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		angry = true;
		Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStand.gif");
		dir = 1;
		vx = 15;

		// TODO Auto-generated constructor stub
	}
	
	public void jump() {
		if(grounded) {
			vy = -35;
		}	
	}
	
	public void anger() {
		vx = 10;
		aggro = true;
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(!aggro) {
			if(Math.abs(mapX - spelunkerX) <= 6 && Math.abs(mapY - spelunkerY) < 4) {
				aggro = true;
			}
		}else{
			if(Math.abs(mapX - spelunkerX) <= 3 && Math.abs(mapY - spelunkerY) < 4) {
				System.out.println("shoot");
			}else if(Math.abs(mapX - spelunkerX) > 6 && Math.abs(mapY - spelunkerY) < 4) {
				jump();
			}
		}
	}
	
	public void update() {
		
		if(vx > 0) {
			dir = 1;
		}else if(vx < 0) {
			dir = -1;
		}
		
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
					vx *= -1;
					break;
	
				case 2:
					vx *= -1;
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					vy = 0;
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
		
		if(angry) {
			detect();
		}
		
		if(!grounded) { 
			vy += 2;
		}
		
		if(vx == 0) {
			Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStand.gif");
		}else {
			Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepWalk.gif");
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
}