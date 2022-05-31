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
		vy = -20;
		parachuting = true;
		grounded = false;
		Sprite = getImage("/imgs/Monsters/Alien/alienEject.gif");
		health = 1;
	}
	
	public void jump() 
	{
		vy = -30;
		Sprite = getImage("/imgs/Monsters/Alien/alienJump.gif");
	}
	
	public void checkGround() {
		int XProj = (int) ((x+dir*vx+Math.signum(vx)*64) / 128);;
		int YProj = (int) ((y) / 128 + 1);

		if(LevelBuilder.level[YProj][XProj] == null
		|| LevelBuilder.level[YProj][XProj].solid == false) {
			waitTimer = 20;
			vx *= -1;
			dir *= -1;
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128, y-Camera.y+20);
		else
			tx.setToTranslation(x-Camera.x, y-Camera.y+20);
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
					Sprite = getImage("/imgs/Monsters/Alien/alienWalk.gif");
					if(parachuting) {
						vx = 5;
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
		
		collide();
		
		if(!flag) {
			grounded = false;
		}
		
		if(!grounded) { 
			if(parachuting == false || (parachuting && vy < 2)) {
				vy += 2;
			}else {
				vy = 5;
			}
		}else {
			checkGround();
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
		if(health <= 0)
			return;
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), w, h);
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
