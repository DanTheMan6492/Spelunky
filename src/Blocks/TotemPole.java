package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Entity;
import General.Frame;

import java.net.URL;

public class TotemPole extends Block{
	
	public int rightAttackTimer, rightActiveTimer, rightRecoilTimer, leftAttackTimer, leftActiveTimer, leftRecoilTimer;
	
    public TotemPole(int id, int x, int y) {
    	super(id,x,y);
    	rightAttackTimer = 0;
    	rightActiveTimer = 0;
    	rightRecoilTimer = 0;
    	leftAttackTimer = 0;
    	leftActiveTimer = 0;
    	leftRecoilTimer = 0;
    }
    
    public void detect() {
    	int spelunkerX = (int) (Frame.Ana.x/128);
    	int spelunkerY = (int) (Frame.Ana.y/128);
    	int trapX = x / 128;
    	int trapY = y / 128;
    	
    	if(trapY == spelunkerY && Math.abs(trapX - spelunkerX) <= 6) {
    		if(trapX > spelunkerX) {
    			rightAttackTimer = 60;
    		}else if(trapX < spelunkerX){
    			leftAttackTimer = 60;
    		}
    	}
    }
    
    public void update() {
    	detect();
    	
    	if(rightAttackTimer > 0) {
    		rightAttackTimer --;
    		if(rightAttackTimer < 10) {
    			if((int) (Frame.Ana.x/128) == x / 128 + 1) {
    				//hurt the spelunker
    			}
    		}
    	}
    	
    	if(leftAttackTimer > 0) {
    		leftAttackTimer --;
    		if(leftAttackTimer < 10) {
    			if((int) (Frame.Ana.x/128) == x / 128 - 1) {
    				//hurt the spelunker
    			}
    		}
    	}
    }
}
