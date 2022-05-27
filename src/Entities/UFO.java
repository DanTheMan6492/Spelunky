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
	
	public int shootTimer;

	public UFO(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		vx = 10;
	}
	
	public void shoot() {
		if(shootTimer == 0) {
			shootTimer = 30;
		}
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(mapX == spelunkerX && spelunkerY - mapY <= 7) {
			shoot();
		}
		
		if(Math.abs(mapX - spelunkerX) <= 10 && spelunkerY - mapY > 7) {
			vy = 15;
		}else {
			vy = 0;
		}
	}
	
	public void update() {
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
				    break;
	
				case 4:
					break;
					
				case 0:
					break;
				}
			}
		}
		
		detect();
		
		if(shootTimer > 0) {
			shootTimer --;
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
