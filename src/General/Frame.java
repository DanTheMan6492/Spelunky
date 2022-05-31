package General;

import java.awt.Image;

import java.awt.Color;
import java.lang.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.util.Arrays;
import java.awt.Toolkit;
import java.net.URL;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.text.DefaultStyledDocument.ElementSpec;

import Blocks.Block;
import Blocks.KaliAltar;
import Blocks.LevelBuilder;
import Blocks.LevelGen;
import Entities.Camera;
import Entities.Entity;
import Entities.Player;
import Entities.snake;
import object.Item;
import object.Rock;
import object.Whip;
import object.object;
import Entities.bat;
import Entities.bomb;

import com.github.strikerx3.jxinput.XInputAxes;
import com.github.strikerx3.jxinput.XInputAxesDelta;
import com.github.strikerx3.jxinput.XInputButtons;
import com.github.strikerx3.jxinput.XInputButtonsDelta;
import com.github.strikerx3.jxinput.XInputComponents;
import com.github.strikerx3.jxinput.XInputComponentsDelta;
import com.github.strikerx3.jxinput.XInputDevice;
import com.github.strikerx3.jxinput.enums.XInputAxis;
import com.github.strikerx3.jxinput.enums.XInputButton;
import com.github.strikerx3.jxinput.exceptions.XInputNotLoadedException;
import com.github.strikerx3.jxinput.listener.SimpleXInputDeviceListener;
import com.github.strikerx3.jxinput.listener.XInputDeviceListener;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static Font font;
	public static Font sfont;
	public static Font bfont;
	public static Player Ana;
	//whip placeholder, delete later
	public Whip whip = new Whip();
	static XInputDevice[] devices;
	static Camera camera;
	public static Music title;
	public static Music[][] Tracks;

	Entities.bomb bomb;
	static double controllerPos = 0;

	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	public long oldTime = 0;
	//CREATE THE OBJECT (STEP 1)
	Background 	bg 	= new Background(0, 0);
	TimerCount timer = new TimerCount();
	static boolean character_selected = false;
	

	public void paint(Graphics g) {

		super.paintComponent(g);

		g.setFont(font);
		if(!character_selected){
			
			bg.paint(g);
			g.setColor(Color.black);
			Graphics2D g2 = (Graphics2D) g;
			AffineTransform tx = AffineTransform.getTranslateInstance(6, 100);
			for(int i = 1; i <= 21; i++){
				tx = AffineTransform.getTranslateInstance(6 + ((i-1)%7)*(273), 80 + ((i-1)/7) * (327));
				tx.scale(0.545, 0.545);

				g2.drawImage(splice(i), tx, null);

			}
			g.setColor(Color.white);
			g.drawString("Click on Your Character", 770, 50);
			return;
		}
		
		for(int i =0 ; i < devices.length; i++) {
			XInputDevice device = devices[i];
			if (device.poll()) {
				// Retrieve the delta
				XInputComponentsDelta delta = device.getDelta();

				XInputButtonsDelta buttons = delta.getButtons();
				XInputAxesDelta axes = delta.getAxes();

				float accelerationDelta = axes.getRTDelta();
				float brakeDelta = axes.getDelta(XInputAxis.LEFT_THUMBSTICK_X);
				float YDelta = axes.getDelta(XInputAxis.LEFT_THUMBSTICK_Y);

				// Retrieve button state change
				if (buttons.isPressed(XInputButton.A)) {
					Ana.jump();
				} 

				if (buttons.isPressed(XInputButton.B)) {
					if(Math.abs(YDelta) > 0.1){
						if(YDelta > 0){
							BombLob();
						} else{
							BombDrop();
						}
					} else{
						BombThrow();
					}
				}

				if(Math.abs(YDelta) > 0.1){
	
					Ana.climb(YDelta);
				}
				if(buttons.isPressed(XInputButton.RIGHT_SHOULDER)){
					Ana.door();
				}
				
				if(buttons.isPressed(XInputButton.Y)) {
					if(!Frame.Ana.carrying) {
						for(int j = 0; j < LevelBuilder.objects.size(); j ++) {
								if(LevelBuilder.objects.get(j).checkPickUp()) {
								Frame.Ana.carrying = true;
								LevelBuilder.objects.get(j).carried = true;
								Frame.Ana.itemHeld = j;
								break;
							}
						}
					}else {
						Frame.Ana.carrying = false;
						LevelBuilder.objects.get(Frame.Ana.itemHeld).thrown();
						System.out.println("throwing item");
					}
				}
				
				if(buttons.isPressed(XInputButton.X)) {
					whip.whip();
				}

				// Retrieve axis state change.
				// The class provides methods for each axis and a method for providing an XInputAxis

				controllerPos -= brakeDelta*20;
				if(Math.abs(controllerPos) > 10){
					Ana.vxBuffer = 20 * Math.abs(controllerPos)/controllerPos;
				} else{
					Ana.vxBuffer = 0;
				}
				//Ana.vxBuffer -= brakeDelta*20;
				if(Ana.debug){
					Ana.vxBuffer = controllerPos;
					Ana.vy -= YDelta*20;
				}
			} else {
				// Controller is not connected; display a message
			}
		}

		
		Camera.update();

		bg.paint(g);

		for(Block[] row : LevelBuilder.level){
			for(Block block : row){
				if(block != null){
					block.paint(g);
				}
			}
		}
		
		for(Entity e : LevelBuilder.enemies) {
			e.collide();
		}
		
		for(Entity e : LevelBuilder.enemies) {
			if(e != null){
				e.paint(g);
			}
		}
		
		for(object o : LevelBuilder.objects) {
			o.paint(g);
		}

		whip.paint(g);
		Ana.paint(g);

		for(Block deco : LevelBuilder.decorations){
			deco.paint(g);
		}


		bomb.paint(g);
		//Hud
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(getImage("/imgs/Items/HUD/bar.png"),  50, 50, null);
		g2.drawImage(getImage("/imgs/Items/HUD/Heart.png"),   50, 27, null);
		g2.drawImage(getImage("/imgs/Items/HUD/bomb.png" ),  135, 40, null);
		g2.drawImage(getImage("/imgs/Items/HUD/rope.png" ),  197, 40, null);
		g2.drawImage(getImage("/imgs/Items/HUD/money.png"), 1200, 30, null);
		g2.drawImage(getImage("/imgs/Items/HUD/time.png" ), 1500, 30, null);
		g2.drawImage(getImage("/imgs/Items/HUD/level.png"), 1700, 30, null);

		AffineTransform tx = AffineTransform.getTranslateInstance(5, 40);
		tx.scale(0.9, 0.9);
		g2.drawImage(getImage("/imgs/Items/HUD/kapala/" + Ana.blood + ".png"), tx, null);


		for(int i = Ana.items.size()-1; i >= 0; i--){
			g2.drawImage(getImage("/imgs/Items/HUD/item_"  + Ana.items.get(i) + ".png"),  50+30*i, 80, null);
		}
		
		g.setColor(Color.white);
		g.drawString("" + Ana.health, 90, 72);
		g.setFont(sfont);
		g.drawString("" + Ana.bombs, 163, 70);
		g.drawString("" + Ana.ropes, 240, 70);
		g.drawString("" + Ana.money, 1240, 52);
		g.drawString(timer.minutes + ":" + timer.seconds, 1540, 52);
		g.drawString(LevelBuilder.levelNum/4+1 + "-" + (LevelBuilder.levelNum%4+1), 1740, 52);

		Fade.paint(g);


		//fps cap
		timer.update();
		long newTime = System.nanoTime();
		long deltaT = newTime - oldTime;
		long fps =  (long) (Math.pow(10, 9) / deltaT);
		while(fps > 30){
			newTime = System.nanoTime();
			deltaT = newTime - oldTime;
			fps =  (long) (Math.pow(10, 9) / deltaT);
		}

		oldTime = newTime;

		if(Ana.health <= 0)
		{
			g.setColor(Color.white);
			g.setFont(bfont);
			g.drawString("You Died!", 770, 300);
			g.setFont(font);
			g.drawString("Final Score:" + Ana.money, 720, 500);
		}
	}
	
	public static void main(String[] arg) {
		
		try {
			Tracks = new Music[][]{
				{new Music(".\\src\\Music\\1-1.wav", true), new Music(".\\src\\Music\\1-2.wav", true), new Music(".\\src\\Music\\1-3.wav", true)},
				{new Music(".\\src\\Music\\2-1.wav", true), new Music(".\\src\\Music\\2-2.wav", true), new Music(".\\src\\Music\\2-3.wav", true)},
				{new Music(".\\src\\Music\\3-1.wav", true), new Music(".\\src\\Music\\3-2.wav", true), new Music(".\\src\\Music\\3-3.wav", true)},
				{new Music(".\\src\\Music\\4-1.wav", true), new Music(".\\src\\Music\\4-2.wav", true), new Music(".\\src\\Music\\4-3.wav", true)}
			};
			title = new Music(".\\src\\Music\\title.wav", true);
			title.play();
		} catch (UnsupportedAudioFileException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 

		try {
			Font f = Font.createFont(Font.TRUETYPE_FONT, new File("Ubuntu-Bold.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(f);
			font = new Font("Ubuntu", Font.PLAIN, 32);
			sfont = new Font("Ubuntu", Font.PLAIN, 22);
			bfont = new Font("Ubuntu", Font.PLAIN, 155);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			devices = XInputDevice.getAllDevices();
		} catch (XInputNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		Frame f = new Frame();
	


	}
	
	public Frame() {
		
		JFrame f = new JFrame("Spelunky Lite");
		f.setSize(new Dimension(WIDTH, HEIGHT));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if(!character_selected){
			try {
				title.stop();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int x = arg0.getX();
			int y = arg0.getY();
			Ana = new Player(0, 0, 90, 120, true, "");
			camera = new Camera(Ana);
			Ana.character = 1 + (x/272) + (y/327)*7;
			
			LevelBuilder.start();

			while(!LevelBuilder.ready){
				System.out.print("");
			}
			bomb = new bomb(Ana.x, Ana.y);
			character_selected = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	protected Image getImage(String path) {

		Image tempImage = null;
		try {
			URL imageURL = Frame.class.getResource(path);
			tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {e.printStackTrace();}
		return tempImage;
	}

	public static BufferedImage splice(int ind) {
		BufferedImage sprite = new BufferedImage(500, 600, BufferedImage.TYPE_INT_ARGB);
		BufferedImage spriteSheet;
		String path = "src/imgs/Characters/Spliced/"+ ind + "-charArt.png";
		try {
			BufferedImage result = ImageIO.read(new File(path));
			return result;
		} catch(IOException e) {}
		try {
			spriteSheet = ImageIO.read(new File("src/imgs/Characters/"+ ind + ".png"));
			for(int y = 200; y < 800; y++){
				for(int x = 1540; x < 2040; x++){
					sprite.setRGB(x-1540, y-200, spriteSheet.getRGB(x, y));
				}
			}
		} catch (IOException e) {}
		
		
        File output = new File(path);
        
        try {
            ImageIO.write(sprite, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sprite;
		
	}


	public static void BombThrow(){

	}

	public static void BombLob(){

	}

	public static void BombDrop(){
		
	}
}