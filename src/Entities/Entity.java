package Entities;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;

public class Entity {
	
	public static ArrayList<Entity> entities = new ArrayList<Entity>();
	public double x, y;
	public double w, h;
	public boolean visible;
    public Image Sprite;
    public AffineTransform tx;
	
    
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
