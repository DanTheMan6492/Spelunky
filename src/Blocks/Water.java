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
    	return false;//return true if there are blocks/legal water blocks on left, right, and down
    }
    
	public void paint(Graphics g) {
		
	}
	
	public void update() {
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Water.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
}
