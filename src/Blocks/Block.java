package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Entity;

import java.net.URL;

public class Block {
	
	public int x, y, width, height;
	boolean breakable;
	public Entity item;
    public Image Sprite;
    public AffineTransform tx;
	
    public Block(int id, int x, int y, boolean breakable) {
    	if(this.getClass().equals("class Blocks.Block")) {
    		int random = (int)(Math.random() * 100);
    	}
    	width = 64;
    	height = 64;
    }
    
    public int[] getGrid() {
    	int[] result = {x/64, y/64};
    	return result;
    }
    
	public void paint(Graphics g) {
		
	}
	
	public void Break() {
		//something to spawn the item that it carries
	}
	
	public void update() {
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Block.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
}
