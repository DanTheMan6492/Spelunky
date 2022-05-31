package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import Blocks.Block;
import Blocks.LevelBuilder;
import General.Frame;

public class giantSpider extends Entity
{
	public int jumpTimer, dropTimer;
	public boolean hanging;
	
	public giantSpider(int x, int y) {
		super(x, y, 256, 128, true, "/imgs/Monsters/GiantSpider/giantSpiderNeutral.png");
		jumpTimer = 10;
		hanging = true;
		Sprite = getImage("/imgs/Monsters/GiantSpider/giantSpiderNeutral.png");
		// TODO Auto-generated constructor stub
	}
	
	public void detect() {
		if(Frame.Ana.x + Frame.Ana.w > x + w/4
		&& Frame.Ana.x < x + 3 * w/4
		&& Math.abs(y - Frame.Ana.y) <= 5 * 128
		&& y < Frame.Ana.y) {
			hanging = false;
		}
	}
	
	public void jump() {
		jumpTimer = 60;
		vy = 30;
		if(Frame.Ana.x < x) {
			vx = -10;
		}else {
			vx = 10;
		}
	}
	
	public void update() {
		if(hanging) {
			tx.setToTranslation((int)(x - Camera.x), (int)(x - Camera.y));
		}else {
			tx.setToTranslation((int)(x - Camera.x), (int)(x - Camera.y));
		}
		
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
					vx = 0;
					grounded = true;
					flag = true;
					Sprite = getImage("/imgs/Monsters/GiantSpider/giantSpiderStand.gif");
				    break;
	
				case 4:
					vy = 0;
					break;
					
				case 0:
					if(flag == false && hanging == false) {
						grounded = false;
					}
					break;
				}
			}
		}
		
		if(hanging) {
			Sprite = getImage("/imgs/Monsters/Spider/giantSpider_neutral.gif");
			detect();
		}else {
			if(!grounded) {
				vy += 2;
				if(vy < 0) {
					Sprite = getImage("/imgs/Monsters/Spider/giatnSpider_jump.gif");
				}else {
					Sprite = getImage("/imgs/Monsters/Spider/giantSpider_fall.gif");
				}
				if(dropTimer > 0) {
					dropTimer --;
					Sprite = getImage("/imgs/Monsters/Spider/giantSpider_drop.gif");
				}
			}else {
				if(jumpTimer > 0) {
					jumpTimer --;
					if(jumpTimer == 0) {
						jump();
						jumpTimer = 20;
					}
				}
			}
		}
		
		x += vx;
		y += vy;
	}
	
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x - Camera.x), (int)(x - Camera.y), w,h);
	}
}
