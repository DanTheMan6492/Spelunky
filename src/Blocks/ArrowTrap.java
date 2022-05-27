package Blocks;


public class ArrowTrap extends Block{
	
	public boolean arrow;
	
    public ArrowTrap(int id, int x, int y) {
    	super(id, x, y);
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
