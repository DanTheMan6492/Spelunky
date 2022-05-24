package General;

import Blocks.LevelBuilder;

public class Transition implements Runnable{
    public void run(){
        if(LevelBuilder.ready)
            LevelBuilder.nextLevel();
        else    
            LevelBuilder.skip();
        
    }
}
