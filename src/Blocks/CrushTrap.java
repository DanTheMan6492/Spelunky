package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Entity;

import java.net.URL;

public class CrushTrap extends Block{
	
	public int xv, yv;
	
    public CrushTrap(int id, int x, int y) {
    	super(id,x,y);
    }
    
    public void detect() {
    	//detect spelunker and make velocity variables something
    }
    
    public void update() {
    	detect();
    	super.x += xv;
    	super.y += yv;
    }
	
}
