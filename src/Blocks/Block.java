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
	
	public int x, y, width, height, killX, killY;
	boolean breakable;
	public Entity item;
    public Image Sprite;
    public AffineTransform tx;
    public static ArrayList<Block> blocks = new ArrayList<Block>();
	
    public Block(int id, int x, int y, boolean breakable) {
    	if(this.getClass().equals("class Blocks.Block")) {
    		int random = (int)(Math.random() * 100);
    	}
    	this.x = x;
    	this.y = y;
    	width = 128;
    	height = 128;
    	killX = x + width/2;
    	killY = y + height/2;
    	blocks.add(this);
    }
    
    public int[] getGrid() {
    	int[] result = {x/64, y/64};
    	return result;
    }
    
	public void paint(Graphics g) {
		g.drawRect(x, y, width, height);
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
