package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class yeti extends Entity{
	
	public int waitTimer, moveTimer;

	public yeti(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
	}
	
	public void checkGround() {
		int mapX = (int) (x / 128);
		
		if((mapX == 0 && vx < 0)
		|| (mapX == 32 && vx > 0)) {
			vx = 0;
			dir *= -1;
			return;
		}
		
		if(mapX == 0) {
			if(vx < 0) {
				vx = 0;
				dir *= -1;
			}
			return;
		}else if(mapX == 39) {
			if(vx > 0) {
				vx = 0;
				dir *= -1;
			}
			return;
		}

		int XProj = (int) ((x+vx+64) / 128);
		int YProj = (int) ((y+vy) / 128);

		if(LevelBuilder.level[YProj+1][XProj] == null
		|| LevelBuilder.level[YProj+1][XProj].solid == false) {
			vx = 0;
			dir *= -1;
		}
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(mapX == spelunkerX && mapY == spelunkerY) {
			if(Frame.Ana.x > x) {
				dir = 1;
				Frame.Ana.x = x + w + 10;
			}else {
				dir = -1;
				Frame.Ana.x = x - Frame.Ana.w - 10;
			}
			Frame.Ana.y = y - Frame.Ana.h - 10;
			waitTimer = 60;
			vx = 0;
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
					vx = 0;
					dir *= -1;
					break;
	
				case 2:
					vx = 0;
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
				
		if(!grounded) {
			vy += 2;
		}else {
			if(waitTimer > 0){
				waitTimer--;
				if(waitTimer <= 0){
					vx = 8*dir;
					moveTimer = 22;
				}
			} else if(moveTimer > 0){
				moveTimer--;
			} else{
				vx = 0;
				waitTimer = 20;
			}
		}
		
		checkGround();
		detect();
		
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
