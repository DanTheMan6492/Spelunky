package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class monkey extends Entity
{
	public int jumpTimer, robTimer, robCoolDownTimer;
	public boolean robbing;

	public monkey(int x, int y, int w, int h, boolean visible, String path) {
		super(x, y, w, h, visible, path);
		// TODO Auto-generated constructor stub
		dir = 1;
		jumpTimer = 10;
		Sprite = getImage("/imgs/Monsters/Monkey/monkeyStand.gif");
	}
	
	public void checkRobbing() {
		if(robbing || robCoolDownTimer > 0) {return;}
		
		if(x + w > Frame.Ana.x
		&& x < Frame.Ana.x + Frame.Ana.w
		&& y + h > Frame.Ana.y
		&& y < Frame.Ana.y + Frame.Ana.h) {
			robbing = true;
			robTimer = 60;
		}
	}

	public void jump() 
	{
		if(grounded) {
			grounded = false;
			vy = -30;
			if(Frame.Ana.x + Frame.Ana.w/2 < x + w/2) {
				vx = -10;
			}else {
				vx = 10;
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
					if(!grounded) {
						vx *= -1;
						dir *= -1;
					}
					break;
	
				case 2:
					if(!grounded) {
						vx *= -1;
						dir *= -1;
					}
					break;
	
				case 3:
					vy = 0;
					vx = 0;
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
		
		if(robbing) {
			x = Frame.Ana.x;
			y = Frame.Ana.y;
			vy = 0;
			vx = 0;
			Sprite = getImage("/imgs/Monsters/Monkey/monkeyRob.gif");
			if(robTimer > 0) {
				robTimer --;
				if(robTimer == 0) {
					robbing = false;
					robCoolDownTimer = 60;
				}
			}
		}else {
			if(!grounded) {
				vy += 2;
				checkRobbing();
				Sprite = getImage("/imgs/Monsters/Monkey/monkeyJump.gif");
			}else {
				if(jumpTimer > 0) {
					jumpTimer --;
					if(jumpTimer == 0) {
						jump();
						jumpTimer = 20;
					}
				}
				Sprite = getImage("/imgs/Monsters/Monkey/monkeyStand.gif");
			}
		}
		
		if(robCoolDownTimer > 0) {
			robCoolDownTimer --;
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
