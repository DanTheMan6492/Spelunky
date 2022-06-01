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
	
	public crocMan(int x, int y) 
	{
		super(x, y, 128, 128, true, "");
		frenzy = false;
		Sprite = getImage("/imgs/Monsters/CrocMan/crocman_stand.gif");
		dir = 1;
		health = 4;
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
	
	public void collide() {  
		if(stunned)
			return;
		
		//spelunker is above entity
        if(Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y + Frame.Ana.h < y + h
        && Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x < x + w
        && Frame.Ana.x + Frame.Ana.w - 20 > x
        && Frame.Ana.x + 20 < x + w) {
        	if(!Frame.Ana.stunned) {
	        	Frame.Ana.vy = -20;
	        	Frame.Ana.y = y - Frame.Ana.h - 10;
	        	if(Frame.Ana.equipables[3]) {
	        		die();
	        	}else {
	        		takeDamage(1);
	        	}
	        	stunned = true;
	        	stunTimer = 240;
	        	vx = Frame.Ana.dir * 10;
	        	vy = -10;
        	}
        }
		
    	//spelunker is to the left of entity
        if(Frame.Ana.x + Frame.Ana.w > x
        && Frame.Ana.x + Frame.Ana.w < x + w
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.takeDamage(damage);
        	Frame.Ana.stunned = true;
        	Frame.Ana.stunTimer = 240;
        	Frame.Ana.y -= 1;
			Frame.Ana.vy = -30;
			Frame.Ana.vx = dir * 30;
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.takeDamage(damage);
        	Frame.Ana.stunned = true;
        	Frame.Ana.stunTimer = 240;
        	Frame.Ana.y -= 1;
			Frame.Ana.vy = -30;
			Frame.Ana.vx = dir * 30;
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
		
		if(stunned || health == 0) {
			if(vx > 0) {
				vx --;
			}else if(vx < 0) {
				vx ++;
			}
			
			if(dir == 1) {
				if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunForward.gif");
				}else if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunBackward.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStun.gif");
				}
			}else {
				if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunForward.gif");
				}else if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunBackward.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStun.gif");
				}
			}
			
			if(vy < -20) {
				Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunUpward.gif");
			}else if(vy > 20) {
				Sprite = getImage("/imgs/Monsters/Crocman/crocmanStunDownward.gif");
			}
			
			if(stunTimer > 0 && health > 0) {
				stunTimer --;
				if(stunTimer == 0) {
					stunned = false;
				}
			}
		}else {
			detect();
			collide();
			
			if(!frenzy) {
				if(waitTimer > 0){
					waitTimer--;
					if(waitTimer <= 0){
						vx = 8*dir;
						moveTimer = 22;
						Sprite = getImage("/imgs/Monsters/Crocman/crocmanwalk.gif");
					}
				} else if(moveTimer > 0){
					moveTimer--;
				} else{
					vx = 0;
					waitTimer = 20;
					Sprite = getImage("/imgs/Monsters/Crocman/crocmanStand.gif");
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