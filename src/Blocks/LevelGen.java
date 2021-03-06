package Blocks;

public class LevelGen {
	
	public static int level = 1;

	public static void generateSections(int[][] indexes) {
		

		int col = (int) (Math.random() * 4);
		int row = 0;

		indexes[row][col] = 5;
		boolean prevDown = false;
		boolean shop = false;

		while(true){
			if(prevDown || indexes[row][col] == 3){
				indexes[row][col] = 3;
				if(col == 0)
					col++;
				else if(col == 3)
					col--;
				else{
					int dir = (int) (Math.random() * 2)*2-1;
					col += dir;
				}
				prevDown = false;
			} else{
				int dir = (int) (Math.random() * 3)-1;
				
				if(dir == 0){
					if(row == 3){
						indexes[row][col] = 4;
						break;
					} else{
						if(indexes[row][col] == 5 || indexes[row][col] ==6)
							indexes[row][col] = 6;
						else
							indexes[row][col] = 2;
						row++;
						prevDown = true;
					}
						
				} else{
					if(indexes[row][col] == 0)
						indexes[row][col] = 1;
					
					if(col == 0)
						dir = 1;
					else if(col == 3)
						dir = -1;
					
					col += dir;

					if(indexes[row][col] != 0){
						if(!shop){
							shop = true;
							if(dir == 1){
								indexes[row][col-1] = 8;
							} else{
								indexes[row][col+1] = 9;
							}
						}
					}
				}
			}
		}

		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				if(indexes[i][j] == 0){
					int rand = (int) (Math.random() * 12);
					System.out.println(rand);
					if(rand == 0){
						indexes[i][j] = 7;
						return;
					}
				} 
			}
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

	public static boolean[] intToBin(int num) {
		boolean[] result = {false, false, false, false};

		for(int i = 3; i >= 0; i--){
			if(num/Math.pow(2, i) != 0){
				result[3-i] = true;
			}
			num = (int) (num % Math.pow(2, i));
		}

		return result;
	}
}
