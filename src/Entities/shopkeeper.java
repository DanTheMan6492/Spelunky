package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class shopkeeper extends Entity
{
	public boolean aggro, angry;

	public shopkeeper(int x, int y, int w, int h, boolean visible, String path, boolean aggro) {
		super(x, y, w, h, visible, path);
		this.aggro = aggro;
		// TODO Auto-generated constructor stub
	}
	
	public void jump() {
		if(grounded) {
			vy = -45;
		}	
	}
	
	public void anger() {
		vx = 10;
		aggro = true;
	}
	
	public void detect() {
		if(angry && !aggro) {
			int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
			int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
			
			if(Math.abs(mapX - spelunkerX) <= 5
			&& Math.abs(mapY - spelunkerY) <= 5) {
				aggro = true;
				if(dir == -1) {
					vx = -8;
				}else if(dir == 1) {
					vx = 8;
				}
			}
		}
	}
	
	public void checkGround() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(mapY != spelunkerY) {
			int XProj = (int) ((x+vx+64) / 128);
			int YProj = (int) ((y+vy) / 128);
			
			if(mapX != 0 && mapX != 1 && mapX != 30 && mapX != 31) {
				if(LevelBuilder.level[YProj][XProj + 1].solid == true && dir == 1) {
					jump();
					return;
				}
			}
			
			if(mapX != 0 && mapX != 31) {
				if(LevelBuilder.level[YProj+1][XProj] == null
				|| LevelBuilder.level[YProj+1][XProj].solid == false) {
					jump();
					return;
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
		
		if(!grounded) { 
			vy += 2;
		}
		
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
		
		if(aggro) {
			checkGround();
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