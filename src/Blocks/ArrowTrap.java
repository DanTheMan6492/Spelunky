package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Entity;

import java.net.URL;

public class ArrowTrap extends Block{
	
	public boolean arrow;
	
    public ArrowTrap(int id, int x, int y) {
    	super(id, x, y, true);
    	arrow = true;
    }
    
    public void detect(){
    	
    }
    
    public void Break() {
    	if(arrow == true) {
    		//spawn arrow
    	}else {
    		
    	}
    }
    
    public void update(){
    	detect();
    }
	
}
