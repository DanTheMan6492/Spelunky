package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;

public class Water extends Block{
	
	public int x, y, width, height;
	public boolean legal;
    public Image Sprite;
    public AffineTransform tx; 
	
    public Water(int id, int x, int y) {
    	super(id, x, y);
    }
    
    public boolean isLegal() {
    	int mapX = x / 128;
    	int mapY = y / 128;
    	
    	if(LevelBuilder.level[mapX - 1][mapY] != null
    	&& LevelBuilder.level[mapX + 1][mapY] != null
    	&& LevelBuilder.level[mapX][mapY + 1] != null) {
    		return true;
    	}
    	return false;
    }
    
    public void update() {
    	if(isLegal() == false) {
    		int mapX = x / 128;
        	int mapY = y / 128;
        	LevelBuilder.level[mapX][mapY] = null;
    	}
	}
    
	public void paint(Graphics g) {
		
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Water.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
	public String toString() {
		return "water";
	}
}
