package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;

public class alien extends Entity
{	
	
	public boolean parachuting;
	public int waitTimer;
	public int moveTimer;
	
	public alien(int x, int y, int w, int h, boolean visible, String path) 
	{
		super(x, y, w, h, visible, path);
		dir = 1;
		vx = 0;
		vy = -10;
		parachuting = true;
		grounded = false;
		Sprite = getImage("/imgs/Monsters/Alien/alienEject.gif");
	}
	
	public void jump() 
	{
		vy = -30;
		Sprite = getImage("/imgs/Monsters/Alien/alienJump.gif");
	}
	
	public void checkGround() {
		if(!grounded) {return;}
		
		int mapX = (int) (x / 128);
		
		if((mapX == 0 && vx < 0)
		|| (mapX == 39 && vx > 0)) { //mapx == 32 maybe
			Sprite = getImage("/imgs/Monsters/Alien/alienWalk.gif");
			dir *= -1;
			return;
		}

		int XProj = (int) ((x+vx+64) / 128);
		int YProj = (int) ((y+vy) / 128);

		if(LevelBuilder.level[YProj+1][XProj] == null
		|| LevelBuilder.level[YProj+1][XProj].solid == false) {
			Sprite = getImage("/imgs/Monsters/Alien/alienWalk.gif");
			dir *= -1;
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
					Sprite = getImage("/imgs/Monsters/Alien/alienWalk.gif");
					dir *= -1;
					break;
	
				case 2:
					Sprite = getImage("/imgs/Monsters/Alien/alienWalk.gif");
					dir *= -1;
					break;
	
				case 3:
					grounded = true;
					flag = true;
					if(parachuting) {
						parachuting = false;
					}
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
		
		checkGround();
		
		if(!grounded) {
			if(parachuting == true) {
				vy = 5;
			}else {
				vy += 2;
			}
			waitTimer = 20;
		}else {
			if(waitTimer > 0){
				waitTimer--;
				if(waitTimer == 0){
					jump();
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
		g.drawRect((int) x, (int) y, w, h);
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
