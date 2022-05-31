package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class UFOBullet extends Entity{	

	public boolean active;
	
	public UFOBullet(int x, int y) {
		super(x, y, 50, 80, true, "");
		active = true;
		vy = 6;
		tx.scale(1, 1);
		Sprite = getImage("/imgs/Monsters/UFO/UFO_bullet.png");
	}
	
	public void collide() {    
    	//spelunker touches bullet
		if(Frame.Ana.x + Frame.Ana.w > x
		&& Frame.Ana.x < x + w
		&& Frame.Ana.y + Frame.Ana.h > y
		&& Frame.Ana.y < y + h) {
			active = false;
			Frame.Ana.takeDamage(1);
		}
    }
	
	public void update() {
		tx.setToTranslation(x-Camera.x, y-Camera.y);
		
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				if(collide(block) == 3) {
					int mapX = block.x / 128, mapY = block.y /128;
					LevelBuilder.level[mapY][mapX].Break();
					active = false;
				}
			}
		}
		
		collide();
		
		y += vy;
	}

	public void paint(Graphics g) {
		if(active) {
			update();
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(Sprite, tx, null);
		}
	}
}
