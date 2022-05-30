package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class shopkeeper extends Entity
{
	public int throwTimer;
	public boolean aggro, angry;

	public shopkeeper(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		angry = true;
		Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStand.gif");
		dir = 1;
		vx = 15;

		// TODO Auto-generated constructor stub
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
        	Frame.Ana.y -= 1;
			Frame.Ana.vy = -30;
			Frame.Ana.vx = dir * 30;
			throwTimer = 10;
			System.out.println("throw");
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.y -= 1;
			Frame.Ana.vy = -30;
			Frame.Ana.vx = dir * 30;
			throwTimer = 10;
			System.out.println("throw");
        }
        
        //spelunker is above entity
        if(Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y + Frame.Ana.h < y + h
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + Frame.Ana.w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	Frame.Ana.vy = -20;
        	Frame.Ana.y = y - Frame.Ana.h - 10;
        	if(Frame.Ana.equipables[3]) {
        		die();
        	}else {
        		takeDamage(1);
        	}
        	stunned = true;
        	vx = Frame.Ana.dir * 10;
        	vy = -10;
        }
        
        //spelunker is below entity
        if(Frame.Ana.y < y + h
        && Frame.Ana.y > y
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	Frame.Ana.y -= 1;
			Frame.Ana.vy = -30;
			Frame.Ana.vx = dir * 30;
			throwTimer = 10;
			System.out.println("throw");
        }
    }
	
	public void jump() {
		if(grounded) {
			vy = -35;
		}	
	}
	
	public void anger() {
		vx = 10;
		aggro = true;
	}
	
	public void detect() {
		int mapX = (int) (x / 128), spelunkerX = (int) (Frame.Ana.x / 128);
		int mapY = (int) (y / 128), spelunkerY = (int) (Frame.Ana.y / 128);
		
		if(!aggro) {
			if(Math.abs(mapX - spelunkerX) <= 6 && Math.abs(mapY - spelunkerY) < 4) {
				aggro = true;
			}
		}else{
			if(Math.abs(mapX - spelunkerX) <= 3 && Math.abs(mapY - spelunkerY) < 4) {
				System.out.println("shoot");
			}else if(Math.abs(mapX - spelunkerX) > 6 && Math.abs(mapY - spelunkerY) < 4) {
				jump();
			}
		}
	}
	
	public void update() {
		
		if(vx > 0) {
			dir = 1;
		}else if(vx < 0) {
			dir = -1;
		}
		
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
					break;
	
				case 2:
					vx *= -1;
					break;
	
				case 3:
					vy = 0;
					grounded = true;
					flag = true;
				    break;
	
				case 4:
					vy = 0;
					break;
					
				case 0:
					if(flag == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		if(flag == false) {
			grounded = false;
		}
		
		if(stunned) {
			if(vx > 0) {
				vx --;
			}else if(vx < 0) {
				vx ++;
			}
			
			if(dir == 1) {
				if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunForward.gif");
				}else if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunBackward.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStun.gif");
				}
			}else {
				if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunForward.gif");
				}else if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunBackward.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStun.gif");
				}
			}
			
			if(vy < -20) {
				Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunUpward.gif");
			}else if(vy > 20) {
				Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStunDownward.gif");
			}
		}else {
			collide();
			
			if(angry) {
				detect();
			}
			
			if(vx == 0) {
				Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepStand.gif");
			}else {
				Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepWalk.gif");
			}
			
			if(throwTimer > 0) {
				throwTimer --;
				Sprite = getImage("/imgs/Monsters/Shopkeep/shopkeepThrow.gif");
			}
		}
		
		if(!grounded) { 
			vy += 2;
		}
		
		x += vx;
		y += vy;
	}

	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(y - Camera.y), w, h);
	}
}