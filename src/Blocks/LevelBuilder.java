package Blocks;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.spi.CharsetProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Entities.Entity;
import Entities.Olmec;
import Entities.alien;
import Entities.bat;
import Entities.blueFrog;
import Entities.bomb;
import Entities.caveman;
import Entities.crocMan;
import Entities.ghost;
import Entities.giantSpider;
import Entities.mantrap;
import Entities.monkey;
import Entities.mummy;
import Entities.skeleton;
import Entities.snake;
import Entities.spider;
import Entities.yeti;
import General.Fade;
import General.Frame;
import object.Pot;
import object.Rock;
import object.object;
import object.shopkeeper;

public class LevelBuilder {
	public static Block[][] level;
	public static ArrayList<Entity> enemies = new ArrayList<Entity>();
	public static ArrayList<object> objects = new ArrayList<object>();
	public static ArrayList<Block> decorations = new ArrayList<Block>();
	public static int [][]sectionIDs;
	public static int levelNum;
	public static boolean TransistionRoom = false;
	public static boolean loading = false;
	public static boolean buildRoom = false;
	public static boolean ready = true;
	public static boolean damsel =  false;
	public static int track;
	public static int[][] SECTIONSTATS = {{6, 3, 4, 1, 3, 1, 1, 1, 1, 1}, {2, 5, 3, 1, 1, 2, 1, 1, 1, 1}, {3, 2, 2, 2, 2, 1, 1, 1, 1, 1}, {3, 3, 3, 3, 1, 1, 1, 1, 1, 1}};
	
	
	public static void start(){
		levelNum = -1;
		nextLevel();
	}
	public static void nextLevel(){
		enemies = new ArrayList<Entity>();
		objects = new ArrayList<object>();
		decorations = new ArrayList<Block>();
		if(levelNum != -1)
			transition();
		else
			ready = false;

		TransistionRoom = false;
		level = new Block[34][42];

	

		levelNum++;
		int world = levelNum/4+1;

		track = (int) (Math.random() * 3);
		Frame.Tracks[world-1][track].play();

		
		for(int i = 0; i < 34; i++){
			level[i][0] = new Block(0, 0, i*128);
			level[i][41] = new Block(0, 41*128, i*128);
		}
		for(int i = 0; i < 42; i++){
			level[0][i] = new Block(0, i*128, 0);
			if(world != 3)
				level[33][i] = new Block(0, i*128, 33*128);
		}
		sectionIDs = new int[4][4];
		LevelGen.generateSections(sectionIDs);
		for(int[] arr : sectionIDs) {
			System.out.println(Arrays.toString(arr));
		}

		int X = 0;
		int Y = 0;
		for(int r = 0; r < 4; r++){
			for(int c = 0; c < 4; c++){
				int ID = sectionIDs[r][c];
				String path = ".\\src\\Sections\\" + Integer.toString(world) + "\\" + Integer.toString(ID) + "-" + Integer.toString((int)(Math.random()*SECTIONSTATS[world-1][ID] + 1)) + ".room";
				File text = new File(path);
				try {
					try (Scanner scnnr = new Scanner(text)) {
						for(int i = 0; i < 8; i++){
							for(int j = 0; j < 10; j++){
								int blockID = scnnr.nextInt();
								int x = c*10 + j;
								int y = r*8  + i;
								X = (x)*128+128;
								Y = (y)*128+128;
								int rand;
								switch(blockID){

									//air
									case 0:
										level[y+1][x+1] = null;
									break;

									//entrance
									case 3:
										level[y+1][x+1] = null;
										Frame.Ana.x = X;
										Frame.Ana.y = Y;
									break;
									
									//shopkeep
									case 12:
										decorations.add(new Block(11, X, Y));
										enemies.add(new shopkeeper((x)*128+128, (y)*128+128, 128, 128, true, ""));
										level[y+1][x+1] = null;
										break;
									
									//shop sign
									case 14:
										decorations.add(new Block(blockID, X, Y));
										level[y+1][x+1] = new Block(1, X, Y);
										break;

									//caves regular enemy
									case 24:
										rand = (int) (Math.random() * 10);
										if(rand >= 7)
											enemies.add(new caveman(X, Y));
										else if(rand >= 3)
											enemies.add(new snake(X, Y));
									break;

									//caves hanging enemy
									case 25:
										rand = (int) (Math.random() * 20);
										if(rand == 20)
											enemies.add(new giantSpider(X, Y));
										else if(rand >= 13)
											enemies.add(new bat(X, Y));
										else if(rand >= 8){
											enemies.add(new spider(X, Y));
										}
									break;

									//jungle regular enemy
									case 26:
									rand = (int) (Math.random() * 20);
									if(rand >= 18)
										// orange frog
									if(rand >= 16)
										enemies.add(new blueFrog(X, Y));
									if(rand >= 11)
										enemies.add(new caveman(X, Y));
									else if(rand >= 7)
										enemies.add(new mantrap(X, Y));
									else if(rand >= 3)
										enemies.add(new monkey(X, Y));
									break;

									//ice caves yeti
									case 27:
									rand = (int) (Math.random() * 3);
									if(rand > 0)
										enemies.add(new yeti(X, Y));
									break;

									//temple regular enemy
									case 28:
										rand = (int) (Math.random() * 10);
										if(rand >= 7)
											enemies.add(new caveman(X, Y));
										else if(rand >= 3)
											enemies.add(new crocMan(X, Y));
									break;

									//temple mummy
									case 29:
									rand = (int) (Math.random() * 3);
									if(rand == 0)
										enemies.add(new mummy(X, Y));
									break;

									//olmec
									case 30:
									enemies.add(new Olmec(X, Y));
									break;

									//misc. blocks
									default:
										level[y+1][x+1] = new Block(blockID, x*128+128, y*128+128);
										break;
								}

							}
						}
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		ready = true;
	}
	private static void transition() {
		TransistionRoom = true;
		buildRoom = false;
		ready = false;
		Frame.Tracks[levelNum/4][track].pause();
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
			int world = (levelNum+1)/4+1;
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 16; j++){
					level[i][j] = new Block(1, j*128, i*128);
				}
			}



			for(int i = 2; i < 4; i++){
				level[i][00] =  new Block(1, 0, i*128);
				level[i][15] =  new Block(1, 15*128, i*128);
			}
			for(int j = 4; j < 12; j++){
				level[5][j] = new Block(1, j*128, 5*128);
			}


			for(int j = 0; j < 16; j++){
				level[8][j] = new Block(1, j*128, 8*128);
			}

			for(int i = 2; i < 4; i++){
				for(int j = 16-i; j < 16; j++){
					level[i][j] = new Block(1, j*128, i*128, world);
				}
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