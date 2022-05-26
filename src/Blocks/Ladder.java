package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;

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
