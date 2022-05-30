package object;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Blocks.Block;
import Blocks.LevelBuilder;
import Entities.Camera;
import Entities.Entity;
import General.Frame;

import java.net.URL;

public class Idol extends object{
	
	public int value;
	public boolean collected;
	
    public Idol(int x, int y) {
    	super(x,y);
    	grounded = false;
    	collected = false;
    	value = 5000 + LevelBuilder.levelNum * 5000;
    	Sprite = getImage("/imgs/Items/Objects/gold_idol.png");
    }
    
    public boolean checkPickUp() {
    	if(Frame.Ana.x + Frame.Ana.w > x
    	&& Frame.Ana.x < x + w
    	&& Frame.Ana.y + Frame.Ana.h > y
    	&& Frame.Ana.y < y + h) {
    		return true;
    	}
    	return false;
    }
    
    public void carried() {
    	if(Frame.Ana.dir == 1) {
    		x = Frame.Ana.x + Frame.Ana.w - 20;
    	}else {
    		x = Frame.Ana.x - w + 20;
    	}
    	y = (int) Frame.Ana.y + 80;
    	grounded = false;
    	vx = 0;
    	vy = 0;
    }
    
    public void thrown() {
    	carried = false;
    	vx = (int) (Math.signum(Frame.Ana.dir) * 50);
    	vy = -20;
    }
    
    public void checkCollision() {
    	int X = (int) x / 128;
		int Y = (int) y / 128;
		
    	if(LevelBuilder.level[Y][X].id == 2) {
    		collected = true;
    		Frame.Ana.money += value;
    	}
    }
    
    public void update() {
		if(carried) {
			carried();
		}else {
			grounded = false;
		}
		
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block b : blockArray) {
				collide(b);
			}
		}
		
		checkCollision();
		
		if(Math.abs(vx)> 0) {
			vx -= 2 * Math.signum(vx);
		}
		
		if(!grounded) {
			vy += 2;
		}
		
		x += vx;
		y += vy;
		tx.setToTranslation((int)(x-Camera.x), (int)(y-Camera.y));
	}
}