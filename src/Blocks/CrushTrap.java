package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Entity;
import General.Frame;

import java.net.URL;

public class CrushTrap extends Block{
	
	public int vx, vy, timer;
	public boolean active;
	
    public CrushTrap(int id, int x, int y) {
    	super(id,x,y);
    	active = false;
    	vx = 0;
    	vy = 0;
    }
    
    public int collide(Block b) {
        int result = 0;
       
        if(b == null || !b.solid) {
        	return result;
        }
       
        if(x + width > b.x
        && x + width < b.x + b.width
        && y + height > b.y
        && y < b.y + b.height
        && y + height - 20 > b.y
        && y + 20 < b.y + b.height) {
        	x = b.x - width - 1;
        	result = 1;
        }
        if(x < b.x + b.width
        && x > b.x
        && y + height > b.y
        && y < b.y + b.height
        && y + height - 20 > b.y
        && y + 20 < b.y + b.height) {
        	x = b.x + b.width + 1;
        	result = 2;
        }
       
        if(y < b.y + b.height
        && y > b.y
        && x + width > x
        && x < b.x + b.width
        && x + width - 20 > b.x
        && x + 20 < b.x + b.width) {
        	y = b.y + b.height;
        	result = 4;
        }
        
        if(y + height > b.y
        && y + height < b.y + b.height
        && x + width > b.x
        && x < b.x + b.width
        && x + width - 20 > b.x
        && x + 20 < x + b.width) {
        	y = b.y - height + 1;
        	result = 3;
        }
        
        return result;
    }
    
    public void detect() {
    	if(active == true) {return;}
    	
    	int spelunkerX = (int) (Frame.Ana.x/128);
    	int spelunkerY = (int) (Frame.Ana.y/128);
    	int trapX = x / 128;
    	int trapY = y / 128;
    	
    	if(trapY == spelunkerY && Math.abs(trapX - spelunkerX) <= 6) {
    		if(trapX > spelunkerX) {
    			vx = -10;
    		}else if(trapX < spelunkerX){
    			vx = 10;
    		}
    		active = true;
    	}
    }
    
    public void update() {
    	for(Block[] blockArray : LevelBuilder.level) {
			for(Block block : blockArray) {
				switch(collide(block)) {
				case 0:
					break;
					
					default:
						vx = 0;
						vy = 0;
						break;
				}
			}
    	}
    	
    	if(timer > 0) {
    		timer --;
    		if(timer == 0) {
    			active = false;
    		}
    	}
    	
    	detect();
    	super.x += vx;
    	super.y += vy;
    }
	
}
