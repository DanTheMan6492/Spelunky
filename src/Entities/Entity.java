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
<<<<<<< HEAD
	public int dir;
	

	//check if two rectangles are clipping
	public boolean checkClipping(double x1, double y1, int w1, int h1,
								 int x2, int y2, int w2, int h2){

		//check side boundries
		if( (x1 > x2) == (x1 > x2 + w2) &&
		    (x1+w1 > x2) == (x1 + w1> x2 + w2)){
			if( (x2 > x1) == (x2 > x1 + w1) &&
		        (x2+w2 > x1) == (x2 + w2> x1 + w1)){
					return false;
			}	
		}

		//check top boundries
		if( (y1 > y2) == (y1 > y2 + h2) &&
		    (y1+h1 > y2) == (y1 + h1> y2 + h2)){
			if( (y2 > y1) == (y2 > y1 + h1) &&
		        (y2+h2 > y1) == (y2 + h2> y1 + h1)){
					return false;
			}	
		}

		return true;
	}

    public int correctClipping() {

		int result = 0;

		if(Math.abs(vx) > 2 && vy == 0){
			return result;
		}
		//What tile is the player in
		int X = ((int)x)/128;
		int Y = ((int)y)/128;

		//only check blocks close to the player
		Block[] toCheck = {LevelBuilder.level[Y][X]  , LevelBuilder.level[Y][X+1]  , LevelBuilder.level[Y][X-1]  ,
						   LevelBuilder.level[Y+1][X], LevelBuilder.level[Y+1][X+1], LevelBuilder.level[Y+1][X-1],
						   LevelBuilder.level[Y-1][X], LevelBuilder.level[Y-1][X+1], LevelBuilder.level[Y-1][X-1]};
		
		//check x collision
		if(Math.abs(vx) > 2){
			for(int i = 0; i < Math.abs(vx); i++){
				x += vx/(Math.abs(vx));
				for(Block block : Block.blocks){
					if(block != null && block.solid){
						if(checkClipping(x, y, w-2, h-2, block.x, block.y, block.width, block.height)){
							x -= vx/(Math.abs(vx));
							break;
						}
					}
				}
			}
		}

		//check y collision
		for(int i = 0; i < Math.abs(vy); i++){
			y += vy/(Math.abs(vy));
			for(Block block : toCheck){
				if(block != null && block.solid){
					if(checkClipping(x, y, w-2, h-2, block.x, block.y, block.width, block.height)){
						y -= vy/(Math.abs(vy));
						if(vy < 0){
							frame = 0;
							grounded = true;
							vy = 0;
						}
						break;
					}
				}
			}
		}
		
		boolean flag = true;
		for(Block block : toCheck){
			if(block != null && block.solid){
				if(checkClipping(x, y+1, w-2, h-2, block.x, block.y, block.width, block.height)){
					flag = false;
					grounded = true;
					break;
				}
				}
		}
		if(flag){
			grounded = false;
			vx = 0;
			frame = 0;
		} 

		return result;
    }
    
    public int checkClipping(Block block) {
    	//return 2 if entity will be crushed by block, 1 if they will be pushed by a block, and 0 if they are not touching the block
    	if(block != null) {
    		if(block.killX > x && block.killX < x + w && block.killY > y && block.killY < y + h) {
    			return 2;
    		}
    		
    		if(x + w > block.x && x < block.x + block.width && y + h > block.y && y < block.y + block.height) {
    			return 1;
    		}
    	}
    	return 0;
    }

    
    public void Destroy() {
    	
    }
    
    public Entity checkCollision() {
    	
    	for(Entity entity: entities) {
    		double x2 = x + w;
    		double y2 = y + h;
    		
    		double ex2 = entity.x + entity.w;
    		double ey2 = entity.y + entity.h;
    		
    		if((x>entity.x) != x2>entity.x)
    			if((y>entity.y) != (y2>entity.y))
    				return entity;
    		
    		if((entity.x>x) != (ex2>x))
    			if((entity.y>y) != (ey2>y))
    				return entity;
    		
    	}
    	return null;
    }
=======
	public static int dir;
	public int damage;
	public int health;
>>>>>>> 1db06e10b421c766ef8c1d555f94fb3b97b37740
    
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