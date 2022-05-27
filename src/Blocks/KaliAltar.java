package Blocks;


public class KaliAltar extends Block{

	public static boolean[] rewards = {true, true};
	
    public KaliAltar(int id, int x, int y) {
    	super(id, x, y);
    	super.width = 128;
    }
    
    public void detect() {
    	//something to detect bodies/corpses, if there is one remove it and call the gift method
    }
    
    public void gift(int favor) 
    {
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
}
