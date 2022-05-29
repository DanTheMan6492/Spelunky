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

public class object {
	
	public int x, y, vx, vy, w, h;
	public boolean fragile, grounded, carried;
    public Image Sprite;
    public AffineTransform tx;
	
    public object(int x, int y) {
    	this.x = x;
    	this.y = y;
		tx = AffineTransform.getTranslateInstance(x, y);
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
    
    public void collide(Block b) {
       
        if(b == null || !b.solid) {
        return;
        }
       /*
        if(x + w > b.x
        && x + w < b.x + b.width
        && y + h > b.y
        && y < b.y + b.height
        && y + h - 20 > b.y
        && y + 20 < b.y + b.height) {
        	if(fragile) {
        		Break();
        	}else {
        		vx *= -1;
        	}
        }
        
        if(x < b.x + b.width
        && x > b.x
        && y + h > b.y
        && y < b.y + b.height
        && y + h - 20 > b.y
        && y + 20 < b.y + b.height) {
        	if(fragile) {
        		Break();
        	}else {
        		vx *= -1;
        	}
        }*/
       
        if(y < b.y + b.height
        && y > b.y
        && x + w > x
        && x < b.x + b.width
        && x + w - 30 > b.x
        && x + 30 < b.x + b.width) {
        	vy = 0;
        }
        
        if(y + h > b.y
        && y + h < b.y + b.height
        && x + w > b.x
        && x < b.x + b.width
        && x + w - 30 > b.x
        && x + 30 < x + b.width) {
        	if(vy > 0) {
        		y -= vy + 1;
        	}
        	if(Math.abs(vx) < 20) {
        		vx = 0;
        	}
        	grounded = true;
        }
        
    	
    	if(vx > 0) {
    		for(int i = x + w; i < x + w + vx; i ++) {
    			if(LevelBuilder.level[y/128][i/128] != null) {
	    			if(LevelBuilder.level[y/128][i/128].solid) {
	    				vx *= -1;
	    				break;
	    			}
    			}
    		}
    	}else {
    		for(int i = x; i > x + vx; i --) {
    			if(LevelBuilder.level[y/128][i/128] != null) {
	    			if(LevelBuilder.level[y/128][i/128].solid) {
	    				vx *= -1;
	    				break;
	    			}
    			}
    		}
    	}
    }
    
    public void carried() {
    	x = (int) Frame.Ana.x;
    	y = (int) Frame.Ana.y;
    	grounded = false;
    	vx = 0;
    	vy = 0;
    }
    
    public void thrown() {
    	carried = false;
    	vx = (int) (Math.signum(Frame.Ana.dir) * 50);
    	vy = -20;
    }
    
	public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		//g2.drawImage(Sprite, tx, null);
		g.fillRect((int)(x-Camera.x), (int)(y-Camera.y), w, h);
	}
	
	public void Break() {
		//something to spawn the item that it carries
	}
	
	public void update() {
		tx.setToTranslation((int)(x-Camera.x), (int)(y-Camera.y));
		
		for(Block[] blockArray : LevelBuilder.level) {
			for(Block b : blockArray) {
				collide(b);
			}
		}
		
		if(!grounded) {
			vy += 2;
		}
		
		if(Math.abs(vx)> 0) {
			vx -= 2 * Math.signum(vx);
		}
		
		x += vx;
		y += vy;
		
		if(carried) {
			carried();
		}
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = object.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
}
