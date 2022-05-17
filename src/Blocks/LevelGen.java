package Blocks;

public class LevelGen {
	
	public static int level = 1;

	public static void generateSections(int[][] levelSex) {
		
		int eIndex = (int) (Math.random() * 4);
		levelSex[0][eIndex] = 16;
		
		int dir = (int) (Math.random() * 3) - 1;
		int currlevel = 0;
		
		if(dir == 0) {
			currlevel++;
		} else {
			eIndex += dir;
			if(eIndex < 0) {
				eIndex -= dir;
				dir = 0;
				currlevel++;
			} else if(eIndex > 3) {
				eIndex -= dir;
				dir = 0;
				currlevel++;
			}
		}
		
		boolean shop = false;
		if(level == 1) {
			shop = true;
		}
		
		while(true) {
			
			System.out.println(dir);
			boolean[] index = {false, false, false, false};
			switch(dir) {
			case -1:
				index[2] = true;
				break;
			case 0:
				index[3] = true;
				break;
			case 1:
				index[0] = true;
				break;
			}
			
			dir = (int) (Math.random() * 3) - 1;
			
			
			int x = 0;
			int y = 0;
			
			
			if(dir != 0){
				x = dir;
				if(eIndex + x < 0) {
					dir = 0;
					x = 0;
				} else if(eIndex + x > 3) {
					dir = 0;
					x = 0;
				}
			}
			
			if(dir == 0) {
				y++;
				if(currlevel + y > 3) {
					levelSex[currlevel][eIndex] = 17;
					break;
				}
			}
			
			index[1+dir] = true;
			levelSex[currlevel][eIndex] = binToInt(index);
			
			eIndex += x;
			currlevel += y;

		}
		
	}
	
	public static int binToInt(boolean[] bin) {
		int result = 0;
		for(int i = 0; i < 4; i++) {
			if(bin[3-i]) {
				result += Math.pow(2, i);
			}
		}
		return result;
	}
}
