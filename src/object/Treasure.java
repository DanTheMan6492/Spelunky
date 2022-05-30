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

public class Treasure extends object{
	
	public int value;
	public boolean collected;
	
    public Treasure(int x, int y) {
    	super(x,y);
    	grounded = false;
    	collected = false;
    }
    
    public void checkCollision() {
    	if(Frame.Ana.x + Frame.Ana.w > x
    	&& Frame.Ana.x < x + w
    	&& Frame.Ana.y + Frame.Ana.h > y
    	&& Frame.Ana.y < y + h) {
    		collected = true;
    		Frame.Ana.money += value;
    	}
    }
    
	public void paint(Graphics g) {
		if(!collected) {
			update();
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(Sprite, tx, null);
			g.drawRect((int)(x-Camera.x), (int)(y-Camera.y), w, h);
		}
	}
	
	public void Break() {
		//something to spawn the item that it carries
	}
	
	public void update() {
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

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Treasure.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
}