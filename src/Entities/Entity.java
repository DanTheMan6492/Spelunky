package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.imageio.stream.FileCacheImageOutputStream;

import java.net.URL;
import Blocks.Block;
import Blocks.LevelBuilder;
import Entities.Camera;
import General.Frame;

public class Entity {
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public int  x;
	public int  y;
	public double vx;
	public double vy;
	public int w, h;
	public boolean visible, stunned;
	public boolean interactable;
    public Image Sprite;
    public AffineTransform tx;
    public boolean grounded = false;
	public int frame;
	public int dir;
	public int damage;
	public int health;
    
    public int collide(Block b) {
        int result = 0;
       
        if(b == null || !b.solid) {
        return result;
        }
       
        if(x + w > b.x
        && x + w < b.x + b.width
        && y + h > b.y
        && y < b.y + b.height
        && y + h - 20 > b.y
        && y + 20 < b.y + b.height) {
        	if(vx > 0) {
        		x -= vx;
        	}
        	result = 1;
        }
        
        if(x < b.x + b.width
        && x > b.x
        && y + h > b.y
        && y < b.y + b.height
        && y + h - 20 > b.y
        && y + 20 < b.y + b.height) {
        	if(vx < 0) {
        		x -= vx;
        	}
        	result = 2;
        }
       
        if(y < b.y + b.height
        && y > b.y
        && x + w > x
        && x < b.x + b.width
        && x + w - 20 > b.x
        && x + 20 < b.x + b.width) {
        	y = b.y + b.height;
        	result = 4;
        	vy = 0;
        }
        
        if(y + h > b.y
        && y + h < b.y + b.height
        && x + w > b.x
        && x < b.x + b.width
        && x + w - 20 > b.x
        && x + 20 < x + b.width) {
        	y = b.y - h + 1;
        	result = 3;
        }
        
        return result;
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
        	Frame.Ana.takeDamage(damage);
        }
        
        //spelunker is to the right of entity
        if(Frame.Ana.x < x + w
        && Frame.Ana.x > x
        && Frame.Ana.y + Frame.Ana.h > y
        && Frame.Ana.y < y + h
        && Frame.Ana.y + Frame.Ana.h - 20 > y
        && Frame.Ana.y + 20 < y + h) {
        	Frame.Ana.takeDamage(damage);
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
        	Frame.Ana.takeDamage(damage);
        }
    }
    
    
	public Entity(int x, int y, 
				  int w, int h, 
				  boolean visible,
				  String path){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.visible = visible;
		Sprite = getImage(path);
		tx = AffineTransform.getTranslateInstance(x, y);
		entities.add(this);
	}
	
	public void takeDamage(int damage) {
		if(health == 0) {return;}
		
		if(health - damage > 0) {
			health -= damage;
		}else {
			die();
		}
	}
	
	public void die() {
		health = 0;
	}
	
	public void paint(Graphics g) {
		update();
		if(!visible)
			return;
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(Sprite, (int) (x-Camera.x), (int) (y-Camera.y), dir * (int) w, (int) h, null);
	}
	
	public void update() {
		tx.setToTranslation(x, y);
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