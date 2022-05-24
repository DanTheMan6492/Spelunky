package Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import Entities.Player;
import General.Fade;

public class LevelBuilder {
	public static Block[][] level;
	public static int [][]sectionIDs;
	public static int levelNum;
	public static boolean loading = false;
	public static boolean buildRoom = false;
	public static boolean ready = true;
	public static int[][] SECTIONSTATS = {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};

	public static void start(){
		levelNum = -1;
		level = new Block[32][40];
		nextLevel();
	}
	public static void nextLevel(){
		if(levelNum != 0)
			transition();
		level = new Block[32][40];
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
								case 8:
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
	private static void transition() {
		buildRoom = false;
		ready = false;
		Fade.fade();
		while(!ready){
			System.out.print("");
		}
		buildRoom = false;
		loading = false;
	}
	
	public static void loadTransitionRoom() {
		if(buildRoom){
			ready = true;
		} else {
			level = new Block[9][16];
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 16; j++){
					level[i][j] = new Block(1, j*128, i*128);
				}
			}

			for(int i = 5; i < 9; i++){
				level[i][00] =  new Block(1, 0, i*128);
				level[i][15] =  new Block(1, 15*128, i*128);
			}
			for(int j = 4; j < 12; j++){
				level[5][j] = new Block(1, j*128, 5*128);
			}


			for(int j = 0; j < 16; j++){
				level[8][j] = new Block(1, j*128, 8*128);
			}

			buildRoom = true;
		}



	}
	public static void skip() {
		if(buildRoom && !loading){
			loading = true;
			Fade.fade();
		}
	}

	
}

