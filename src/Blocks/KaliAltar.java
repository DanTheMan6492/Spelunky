package Blocks;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.net.URL;

public class KaliAltar extends Block{
	
	public int x, y, width, height;
	public static boolean[] rewards = {true, true};
    public Image Sprite;
    public AffineTransform tx;
	
    public KaliAltar(int id, int x, int y) {
    	super(id, x, y);
    }
    
    public void detect() {
    	//something to detect bodies/corpses
    }
    
    public void gift(int favor) {
    	//add favor to spelunker's favor
    	switch(favor) {
    	case 8:
    		if(rewards[0] == true) {
    			rewards[0] = false;
    		}
    		break;
    		
    	case 16:
    		if(rewards[1] == true) {
    			//spawn kapala harris
    			rewards[1] = false;
    		}
    		break;
    	}
    	if(favor >= 24 && favor % 8 == 0) {
    		//spawn royal jelly
    	}
    }
    
    public void punish() {
    	
    }
    
	public void paint(Graphics g) {
		
	}
	
	public void update() {
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = KaliAltar.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}
	
}
