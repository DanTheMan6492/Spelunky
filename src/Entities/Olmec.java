package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class Olmec extends Entity
{
	public int tickTimer, stompTimer;
	public boolean defeated, stomping;
	
	public Olmec(int x, int y) 
	{
		super(x, y, 5 * 128, 5 * 128, true, "");
		defeated = false;
		Sprite = getImage("/imgs/Monsters/olmec.png");
		tx.scale(1, 1);
	}
	
	public void detect() {
		if(Math.abs(x + w/2 - Frame.Ana.x + Frame.Ana.w) <= 5 * 128) {
			jump();
			tickTimer = 50;
		}
		
		if(!grounded) {
			if(Frame.Ana.x > x && Frame.Ana.x + w < x + w) {
				stomp();
			}
		}
	}
	
	public void jump() {
		if(tickTimer == 0 && grounded) {
			if(x + w/2 < Frame.Ana.x + Frame.Ana.w/2) {
				vx = 20;
			}else {
				vx = -20;
			}
			vy = -45;
		}
	}
	
	public void stomp() {
		stompTimer = 20;
		stomping = true;
	}
	
	public void collide() {    
    	if(stunned)
    		return;
    	
    	//spelunker is to the left of entity
        if(Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x + Frame.Ana.w < x + w
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.x = x - Frame.Ana.w;
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.x = x + w;
        }
        
        //spelunker is above entity
        if(Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y + Frame.Ana.h < y + h
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + Frame.Ana.w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	Frame.Ana.y = y - Frame.Ana.h;
        }
        
        //spelunker is below entity
        if(Frame.Ana.y < y + h
        && Frame.Ana.y > y
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	if(Frame.Ana.grounded) {
        		Frame.Ana.die();
        	}else {
        		Frame.Ana.y = y + h;
        	}
        }
    }
	
	public void update() {
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					vx = 0;
					break;
	
				case 2:
					vx = 0;
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
		
		collide();
		detect();
		
		if(!grounded) {
			vy += 2;
			if(stompTimer > 0) {
				stompTimer --;
				vx = 0;
				vy = 0;
				if(stompTimer == 0) {
					vy = 15;
				}
			}
		}else {
			if(tickTimer > 0) {
				tickTimer --;
			}
		}
		
		tx.setToTranslation((int)(x - Camera.x), (int)(y - Camera.y));
	}
	
	public void paint(Graphics g) {
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