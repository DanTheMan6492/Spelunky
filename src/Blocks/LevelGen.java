package Blocks;

public class LevelGen {

	public static void generateSections(int[][] levelSex) {
		int eIndex = (int) (Math.random() * 4);
		levelSex[0][eIndex] = 18;
		
		int dir = (int) (Math.random() * 3) - 1;
		int currlevel = 0;
		boolean flag = false;
		
		while(true) {
			boolean[] index = {false, false, false, false};
			index[1+dir] = true;
			if(flag)
				index[3] = true;
			if(dir == 0) {
				currlevel++;
				if(currlevel > 3) {
					levelSex[currlevel][eIndex] = 19;
					break;
				}
			} else {
				eIndex += dir;
			}
			if(eIndex < 0) {
				eIndex++;
				currlevel++;
				index[1+dir] = false;
				index[1] = true;
			} else if(eIndex > 3) {
				eIndex--;
				currlevel++;
				index[1+dir] = false;
				index[1] = true;
			}
			
			levelSex[currlevel][eIndex] = binToInt(index);
			dir = (int) (Math.random() * 3) - 1;
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
