package object;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import Entities.Camera;
import Entities.Entity;
import General.Frame;

import java.net.URL;

public class Rock extends object{
	
    public Rock(int x, int y) {
    	super(x, y);
		fragile = false;
		w = 50;
		h = 50;
		Sprite = getImage("/imgs/Items/Objects/rock.png");
    }
    
    public void paint(Graphics g) {
		update();
		Graphics2D g2 = (Graphics2D) g;
		tx.setToTranslation((int)(x-Camera.x)-40, (int)(y-Camera.y)-40);
		g2.drawImage(Sprite, tx, null);
		g.drawRect((int)(x-Camera.x), (int)(y-Camera.y), w, h);
	}
}