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
	
	public int waitTimer, moveTimer, throwTimer;

	public yeti(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		dir = 1;
		// TODO Auto-generated constructor stub
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
	
	public void update() {
		if(dir == 1) {
			tx.setToTranslation((int)(x - Camera.x), (int)(y - Camera.y));
		}else {
			tx.setToTranslation((int)(x - Camera.x + 128), (int)(y - Camera.y));
		}
		tx.scale(dir, 1);
		
		
		boolean flag = false;
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 1:
					dir *= -1;
					break;
	
				case 2:
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
		
		if(stunned) {
			if(vx > 0) {
				vx --;
			}else if(vx < 0) {
				vx ++;
			}
			
			if(dir == 1) {
				if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStunForward.png");
				}else if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStunBackward.png");
				}else {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStun.png");
				}
			}else {
				if(vx < 0) {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStunForward.png");
				}else if(vx > 0) {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStunBackward.png");
				}else {
					Sprite = getImage("/imgs/Monsters/Yeti/yetiStun.png");
				}
			}
			
			if(vy < -20) {
				Sprite = getImage("/imgs/Monsters/Yeti/yetiStunUpward.png");
			}else if(vy > 20) {
				Sprite = getImage("/imgs/Monsters/Yeti/yetiStunDownward.png");
			}
		}else {
			collide();
			checkGround();
			
			if(waitTimer > 0){
				waitTimer--;
				if(waitTimer <= 0){
					vx = 8*dir;
					moveTimer = 22;
					Sprite = getImage("/imgs/Monsters/Yeti/yetiWalk.gif");
				}
			} else if(moveTimer > 0){
				moveTimer--;
			} else{
				vx = 0;
				waitTimer = 20;
				Sprite = getImage("/imgs/Monsters/Yeti/yetiStand.gif");
			}
			
			if(vx == 0) {
				Sprite = getImage("/imgs/Monsters/Yeti/yetiStand.gif");
			}else {
				Sprite = getImage("/imgs/Monsters/Yeti/yetiWalk.gif");
			}
			
			if(throwTimer > 0) {
				throwTimer --;
				Sprite = getImage("/imgs/Monsters/Yeti/yetiThrow.gif");
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
