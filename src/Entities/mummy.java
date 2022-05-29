package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class mummy extends Entity{

	public int waitTimer, vomitTimer;
	public boolean shooting;
	
	public mummy(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		vx = 5;
		Sprite = getImage("/imgs/Monsters/Mummy/mummyStand.gif");
	}
	
	public void detect() {
		if(vomitTimer > 0) 
		{	
			Sprite = getImage("/imgs/Monsters/Mummy/mummyStand.gif");
			return;}
		
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if((spelunkerY == mapY - 1 || spelunkerY == mapY || spelunkerY == mapY + 1 || spelunkerY == mapY + 2) && Math.abs(mapX - spelunkerX) <= 12){ {
			if(mapX < spelunkerX) {
				dir = 1;
			}else {
				dir = -1;
			}
			vomitTimer = 60;
			}
		}
	}
	
	public void update() {
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
					dir *= -1;
					break;
	
				case 2:
					vx *= -1;
					dir *= -1;
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					break;
					
				case 0:
					if(flag == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		
		detect();
		
		if(vomitTimer > 0) {
			vomitTimer --;
			if(vomitTimer < 30 && vomitTimer > 20) 
			{
				Sprite = getImage("/imgs/Monsters/Mummy/mummyVomit.gif");
				new mummyFly((int) x, (int) y, w, h, visible, "/imgs/Monsters/Fly.gif", dir);
			}
		}else {
			vx = dir * 8;
			Sprite = getImage("/imgs/Monsters/Mummy/mummyWalk.gif");

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
