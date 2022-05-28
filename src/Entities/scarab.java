package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class scarab extends Entity
{

	public int moveTimer, moveDuration;
	
	public scarab(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		moveTimer = 0;
		// TODO Auto-generated constructor stub
	}
	
	public void update() {
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				collide(block);
			}
		}
		
		if(moveTimer > 0 && moveDuration == 0) {
			moveTimer --;
			if(moveTimer == 0) {
				moveDuration = 10;
				switch((int)(Math.random() * 3)) {
				case 0:
					vx = 3;
					break;
					
				case 1:
					vx = -3;
					break;
					
				case 2: 
					vx = 0;
					break;
				}
				
				switch((int)(Math.random() * 3)) {
				case 0:
					vy = 3;
					break;
					
				case 1:
					vy = -3;
					break;
					
				case 2: 
					vy = 0;
					break;
				}
			}
		}
		
		if(moveDuration > 0) {
			moveDuration --;
			if(moveDuration == 0) {
				vx = 0;
				vy = 0;
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

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Entity.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}	
}