package Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import Entities.Player;

public class LevelBuilder {
	public static Block[][] level;
	public static int [][]sectionIDs;
	public static int levelNum;
	public static int[][] SECTIONSTATS = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

	public static void start(){
		levelNum = -1;
		level = new Block[32][40];
		nextLevel();
	}
	public static void nextLevel(){
		File f = new File(".\\src\\Sections\\1");
		levelNum++;
		sectionIDs = new int[4][4];
		LevelGen.generateSections(sectionIDs);
		for(int[] arr : sectionIDs) {
			System.out.println(Arrays.toString(arr));
		}
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < 4; c++){
				int ID = sectionIDs[r][c];
				int world = levelNum/4+1;
				String path = ".\\src\\Sections\\" + Integer.toString(world) + "\\" + Integer.toString(ID) + "-" + Integer.toString((int)(Math.random()*SECTIONSTATS[world-1][ID] + 1)) + ".room";
				File text = new File(path);
				try {
					Scanner scnnr = new Scanner(text);
					for(int i = 0; i < 8; i++){
						for(int j = 0; j < 10; j++){
							int blockID = scnnr.nextInt();
							int x = c*10 + j;
							int y = r*8  + i;
							switch(blockID){
								case 0:
								level[y][x] = null;
								break;
								case 2:
								level[y][x] = null;
								Player.x = x*128;
								Player.y = y*128-2;
								break;
								default:
								level[y][x] = new Block(blockID, x*128, y*128);
								break;
							}
	
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
}
