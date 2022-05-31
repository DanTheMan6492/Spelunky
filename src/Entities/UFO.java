package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class UFO extends Entity{	
	
	public boolean ejected;
	public int shootTimer, turnTimer;

	public UFO(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		vx = 10;
		ejected = false;
		Sprite = getImage("/imgs/Monsters/UFO/UFORoam.gif");
	}
	
	public void collide() {    
    	//spelunker is to the left of entity
        if(Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x + Frame.Ana.w < x + w
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.takeDamage(damage);
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.takeDamage(damage);
        }
        
        //spelunker is above entity
        if(Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y + Frame.Ana.h < y + h
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + Frame.Ana.w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	Frame.Ana.vy = -20;
        	Frame.Ana.y = y - Frame.Ana.h - 10;
        	ejected = true;
        	LevelBuilder.enemies.add(new alien(x, y, 128, 128, true, ""));
        }
        
        //spelunker is below entity
        if(Frame.Ana.y < y + h
        && Frame.Ana.y > y
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	Frame.Ana.takeDamage(damage);
        }
    }
	
	public void die() {
		ejected = true;
    	LevelBuilder.enemies.add(new alien(x, y, 128, 128, true, ""));
	}
	
	public void shoot() {
		if(shootTimer == 0) {
			shootTimer = 30;
		}
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(Math.abs(mapX - spelunkerX) <= 8 && Math.abs(mapY - spelunkerY) <= 8) {
			if(mapY < spelunkerY - 4) {
				vy = 5;
			}else if(mapY > spelunkerY - 4) {
				vy = -5;
			}else {
				vy = 0;
			}
			
			if(mapX == spelunkerX) {
				shoot();
			}
		}
	}
	
	public void update() {
		tx.setToTranslation(x-Camera.x, y-Camera.y+20);
		tx.scale(1, 1);
		
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
					if(ejected) {
						//explode
					}
					break;
				}
			}
		}
		
		if(!ejected) {
			detect();
			collide();
			Sprite = getImage("/imgs/Monsters/UFO/UFOEject.gif");
		}else {
			vx = 0;
			vy += 2;
			Sprite = getImage("/imgs/Monsters/UFO/UFORoam.gif");
			
			if(shootTimer > 0) {
				shootTimer --;
				Sprite = getImage("/imgs/Monsters/UFO/UFOCharge.gif");
				if(shootTimer == 0) {
					//spawn a bullet
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
