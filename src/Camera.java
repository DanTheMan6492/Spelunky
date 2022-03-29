import Entities.Entity;

public class Camera {
	
	public Entity following;
	public double x;
	public double y;
	
	public Camera(Entity entity) {
		following = entity;
		x = entity.x;
		y = entity.y;
	}
}
