package Blocks;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;

public class Ladder extends Block{
	
	public int x, y, width, height;
    public Image Sprite;
    public AffineTransform tx; 
	
    public Ladder(int id, int x, int y) {
    	super(id, x, y);
    }
    
	public void paint(Graphics g) {
		
	}
	
	public String toString() {
		return "ladder";
	}
}
