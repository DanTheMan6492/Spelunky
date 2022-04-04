package Block;
import java.util.ArrayList;

public class Block {
	public double x, y;
	public double width, height;
	public static ArrayList<Block> Blocks = new ArrayList<Block>();
	public Block(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		Blocks.add(this);
	}
}
