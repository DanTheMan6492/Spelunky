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
import General.Frame;

public class mantrap extends Entity
{
	
	public int waitTimer;
	public int moveTimer;

	public mantrap(int x, int y) 
	{
		super(x, y, 128, 128, true, "");
		dir = 1;
		vx = 8;
		vy = 0;
		grounded = true;
		waitTimer = 20;
		Sprite = getImage("/imgs/Monsters/mantrap/mantrap_stand.gif");
		health = 3;
	}
	
	public void collide() {    
    	//spelunker is to the left of entity
        if(Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x + Frame.Ana.w < x + w
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.die();
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.die();
        }
        
        //spelunker is above entity
        if(Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y + Frame.Ana.h < y + h
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + Frame.Ana.w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	die();
        }
        
        //spelunker is below entity
        if(Frame.Ana.y < y + h
        && Frame.Ana.y > y
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	die();
        }
    }
	
	public void detect() {
		if(Frame.Ana.x + Frame.Ana.w > x
		&& Frame.Ana.x < x + w
		&& Frame.Ana.y + Frame.Ana.h > y
		&& Frame.Ana.y < y + h) {
			Frame.Ana.visible = false;
		}
	}
	
	public void checkGround() {
		int XProj = (int) ((x+vx+Math.signum(vx)*64) / 128);
		int YProj = (int) ((y) / 128 + 1);

		if(LevelBuilder.level[YProj][XProj] == null
		|| LevelBuilder.level[YProj][XProj].solid == false) {
			Sprite = getImage("/imgs/Monsters/mantrap/mantrap_stand.gif");
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
					Sprite = getImage("/imgs/Monsters/mantrap/mantrap_stand.gif");
					vx = 0;
					dir *= -1;
					break;
	
				case 2:
					waitTimer = 20;
					Sprite = getImage("/imgs/Monsters/mantrap/mantrap_stand.gif");
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
		
		if(stunned) {
			Sprite = getImage("/imgs/Monsters/Mantrap/mantrap_stun.gif");
		}else {
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
					Sprite = getImage("/imgs/Monsters/mantrap/mantrap_walk.gif");
					moveTimer--;
				} else{
					Sprite = getImage("/imgs/Monsters/mantrap/mantrap_stand.gif");
					vx = 0;
					waitTimer = 20;
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