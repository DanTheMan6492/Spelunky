package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import javax.lang.model.util.ElementScanner6;

import Blocks.Block;
import Blocks.LevelBuilder;

public class snake extends Entity
{
	
	public int waitTimer;
	public int moveTimer;

	public snake(int x, int y) 
	{
		super(x, y, 128, 128, true, "/imgs/Monsters/Snake/snakeStand.gif");
		dir = 1;
		vx = 8;
		vy = 0;
		grounded = true;
		waitTimer = 20;
		Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
		health = 1;
	}
	
	public void checkGround() {
		int XProj = (int) ((x+vx+Math.signum(vx)*64) / 128);;
		int YProj = (int) ((y) / 128 + 1);

		if(LevelBuilder.level[YProj][XProj] == null
		|| LevelBuilder.level[YProj][XProj].solid == false) {
			Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
			waitTimer = 20;
			vx = 0;
			dir *= -1;
		}
	}
	
	public void update() {
		if(dir == -1)
			tx.setToTranslation(x-Camera.x+128-40, y-Camera.y);
		else
			tx.setToTranslation(x-Camera.x-40, y-Camera.y);
		tx.scale(dir, 1);
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					waitTimer = 20;
					Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
					vx = 0;
					dir *= -1;
					break;
	
				case 2:
					waitTimer = 20;
					Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
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
		if(!flag) {
			grounded = false;
		}
		
		checkGround();
		collide();
		
		if(!grounded) {
			vy += 2;
			waitTimer = 20;
			//vx = 0;
		}else {
			if(waitTimer > 0){
				waitTimer--;
				if(waitTimer <= 0){
					vx = 8*dir;
					moveTimer = 22;
				}
			} else if(moveTimer > 0){
				Sprite = getImage("/imgs/Monsters/Snake/snakeWalk.gif");
				moveTimer--;
			} else{
				Sprite = getImage("/imgs/Monsters/Snake/snakeStand.gif");
				vx = 0;
				waitTimer = 20;
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