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

public class Entity {
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public static double  x;
	public static double y;
	public double vx, vy;
	public int w, h;
	public boolean visible;
    public Image Sprite;
    public AffineTransform tx;
    public boolean grounded = false;
	public int frame;
	int dir;
	

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

		if(vx == 0 && vy == 0){
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

		if(Math.abs(vx) > 2 || vy != 0){
			if(Math.abs(vx) > 2){
				x += vx;
			}
			y += vy;

			double ratioX;
			double ratioY;

			if(Math.abs(vx) > 2){
				ratioX = -vx/(Math.abs(vx)+Math.abs(vy));
				ratioY = -vy/(Math.abs(vx)+Math.abs(vy));
			} else{
				ratioX = 0;
				ratioY = -1 * vy/Math.abs(vy);
			}

			for(Block block : toCheck){
				if(block != null){
					while(checkClipping(x, y, w-2, h-2, block.x, block.y, block.width, block.height)){
						x += ratioX;
						y += ratioY;
					}
				}
			}

			boolean flag = true;
			for(Block block : toCheck){
				if(block != null){
					if(checkClipping(x, y+1, w-2, h-2, block.x, block.y, block.width, block.height)){
						frame = 0;
						flag = false;
						vy = 0;
						grounded = true;
						break;
					}

				}
			}
			if(flag){
				grounded = false;
				frame = 0;
			} 


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
