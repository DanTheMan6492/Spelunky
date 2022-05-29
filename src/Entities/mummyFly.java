package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class mummyFly extends Entity
{
	public boolean active;

	public mummyFly(int x, int y, int w, int h, boolean visible, String path, int dir) {
		super(x, y, w, h, visible, path);
		Sprite = getImage("/imgs/Monsters/Fly.gif");

		// TODO Auto-generated constructor stub
		active = true;
		this.dir = dir;
		if(dir == -1) {
			vx = (int)(-Math.random()*5 + 6);
		}else {
			vx = (int)(-Math.random()*5 + 6);
		}
		vy = (int)(Math.random() * 5 - 2);
	}
	
	public void update() {
		tx.scale(dir, 1);
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 0:
					break;
					
				default:
					active = false;	
					break;
				}
			}
		}
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		if(active) {
			update();
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
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
