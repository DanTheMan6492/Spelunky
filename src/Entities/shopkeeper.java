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
	public boolean aggro;

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
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(Math.abs(mapX - spelunkerX) <= 10) {
			if(mapY - spelunkerY > 0) {
				jump();
			}
			//shoot();
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
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
}