package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;

public class Section {
	
	ArrayList<Block> blocks;
	public int x, y;
    public Image Sprite;
    public AffineTransform tx;
	
    public Section(int x, int y, String entrance, String exit) {
    	
    }
    
	public void paint(Graphics g) {
		
	}
	
	public void update() {
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Section.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
}
