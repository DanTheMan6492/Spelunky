package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class crocMan extends Entity{

	public int waitTimer, moveTimer;
	public boolean frenzy;
	
	public crocMan(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		frenzy = false;
		Sprite = getImage("/imgs/Monsters/CrocMan/crocman_stand.gif");
		dir = 1;

	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(dir == 1) {
			if(spelunkerX > mapX) {
				if(mapY == spelunkerY && Math.abs(mapX - spelunkerX) <= 5) {
					frenzy = true;
				}
			}
		}else if(dir == -1) {
			if(spelunkerX < mapX) {
				if(mapY == spelunkerY && Math.abs(mapX - spelunkerX) <= 5) {
					frenzy = true;
				}
			}
		}
	}
	
	public void jump() {
		if(grounded) {
			vy = -40;
		}
	}
	
	public void update() {
		if(dir == 1) {
			tx.setToTranslation((int)(x - Camera.x), (int)(y - Camera.y));
		}else {
			tx.setToTranslation((int)(x - Camera.x + 128), (int)(y - Camera.y));
		}
		tx.scale(dir, 1);
		
		System.out.println(dir);
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					if(frenzy) {
						vx *= -1;
					}else {
						vx = 0;
					}
					dir *= -1;
					break;
	
				case 2:
					if(frenzy) {
						vx *= -1;
					}else {
						vx = 0;
					}
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
		
		if(!grounded) {
			vy += 2;
		}
		
		if(!frenzy) {
			if(waitTimer > 0){
				waitTimer--;
				if(waitTimer <= 0){
					vx = 8*dir;
					moveTimer = 22;
					Sprite = getImage("/imgs/Monsters/Caveman/crocmanwalk.gif");
				}
			} else if(moveTimer > 0){
				moveTimer--;
			} else{
				vx = 0;
				waitTimer = 20;
				Sprite = getImage("/imgs/Monsters/Caveman/crocmanStand.gif");
			}
		}else {
			vx = 16 * dir;
			jump();
		}
		
		if(vx == 0) {
			Sprite = getImage("/imgs/Monsters/Crocman/crocman_stand.gif");
		}else {
			Sprite = getImage("/imgs/Monsters/Crocman/crocman_walk.gif");
		}
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x-Camera.x), (int)(y-Camera.y), w, h);
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