package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class piranha extends Entity{

	public piranha(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
	}
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(LevelBuilder.level[spelunkerX][spelunkerY].toString().equals("water")
		&& Math.abs(mapX - spelunkerX) <= 10) {
			if(mapX < spelunkerX) {
				vx = -5;
			}else if(spelunkerX < mapX){
				vx = 5;
			}else {
				vy = 0;
			}
			
			if(mapY < spelunkerY) {
				vy = -5;
			}else if(spelunkerY < mapY){
				vy = 5;
			}else {
				vy = 0;
			}
		}
	}
	
	public void update() {
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				collide(block);
			}
		}
		
		collide();
		
		x += vx;
		if(vy < 0 && !LevelBuilder.level[(int) (x/128)][(int) (y/128 - 1)].toString().equals("water")) {
			
		}else {
			y += vy;
		}
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}	
}
