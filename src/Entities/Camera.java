package Entities;
import Blocks.LevelBuilder;
import Constants.General;
public class Camera {
	
	
	public static Entity following;
	public static double x;
	public static double y;
	
	public Camera(Entity entity) {
		following = entity;
		x = entity.x-(General.WIDTH/2)+(following.w/2);
		y = entity.y-(General.HEIGHT/2)+(following.h/2);
		if(x < 0) {
			x = 0;
		}
		if(y < 0) {
			y = 0;
		}
		if(x > 3200){
			x = 3200;
		}
		if(y > 3016){
			y = 3016;
		}
		
	}

	public static void update(){
		if(LevelBuilder.buildRoom){
			x = 64;
			y = 64;
		} else{
			x = following.x-(General.WIDTH/2)+(following.w/2);
			y = following.y-(General.HEIGHT/2)-(following.h/2);

			if(x < 0) {
				x = 0;
			}
			if(y < 0) {
				y = 0;
			}
			if(x > 3200+256){
				x = 3200+256;
			}
			if(y > 3016+256){
				y = 3016+256;
			}
		}
	}
}
