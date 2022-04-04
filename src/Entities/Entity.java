package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;
import Block.Block;

public class Entity {
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public double  x,  y;
	public double vx, vy;
	public double w, h;
	public boolean visible;
    public Image Sprite;
    public AffineTransform tx;
    public boolean grounded = false;
	
    public int checkClipping() {
  
    	for(Block block: Block.Blocks) {
    		double x2 = x + w;
    		double y2 = y + h;
    		
    		double ex2 = block.x + block.width;
    		double ey2 = block.y + block.height;
    		
    		if((x>block.x) != (x2>block.x))
    			if((y>block.y) != (y2>block.y))
    				return 1;
    		
    		if((block.x>x) != (ex2>x))
    			if((block.y>y) != (ey2>y))
    				return 1;
    		
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
    
    
	public Entity(double x, double y, 
				  double w, double h, 
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
		g2.drawImage(Sprite, tx, null);
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
