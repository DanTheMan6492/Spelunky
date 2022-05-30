package Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;

import General.Frame;

public class Whip extends Entity{
	
	public int whipTimer;
	
	
	public Whip() {
		super(0,0, 40, 140, true, "");
	}
	
	public void whip() {
		if(whipTimer == 0) {
			whipTimer = 15;
			Player.whip();
		}
	}
	
	public boolean checkCollision(Entity e) {
		if(x + w > e.x
		&& x < e.x + e.w
		&& y + h > e.y
		&& y < e.y + e.h) {
			return true;
		}
		return false;
	}
	
	public void update() {
		if(whipTimer > 0) {
			whipTimer --;
			if(whipTimer <= 15 && whipTimer > 10) {
				//backwhip
				w = 70;
				h = 140;
				if(Frame.Ana.dir == 1) {
					x = Frame.Ana.x - w + 10;
					y = Frame.Ana.y - 20;
				}else {
					x = Frame.Ana.x + Frame.Ana.w - 10;
					y = Frame.Ana.y - 20;
				}
				if(whipTimer > 13){
					Sprite = Player.splice(12, 10);
				} else if (whipTimer > 11){
					Sprite = Player.splice(12, 11);
				} else{
					Sprite = Player.splice(12, 12);
				}
			}else if(whipTimer <= 15){
				//frontwhip
				w = 80;
				h = 60;
				if(Frame.Ana.dir == 1) {
					x = Frame.Ana.x + Frame.Ana.w - 10;
					y = Frame.Ana.y + 40;
				}else {
					x = Frame.Ana.x - w + 10;
					y = Frame.Ana.y + 40;
				}
				if(whipTimer > 12){
					Sprite = Player.splice(12, 13);
				} else if(whipTimer > 10){
					Sprite = Player.splice(12, 14);
				} else{
					Sprite = Player.splice(12, 15);
				}
			}
		}
	}
	
	public void paint(Graphics g) {
		if(whipTimer > 0) {
			update();
			Graphics2D g2 = (Graphics2D) g;
			if(Player.dir == -1)
				tx.setToTranslation(x-Camera.x+102, y-Camera.y);
			else
				tx.setToTranslation(x-Camera.x, y-Camera.y);
			tx.scale(Player.dir * 0.8, 0.8);
			g2.drawImage(Sprite, tx, null);
		}
	}
}
