
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
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Blocks.Block;
import Blocks.KaliAltar;
import Blocks.LevelBuilder;
import Blocks.LevelGen;
import Entities.Camera;
import Entities.Player;

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
	
	
	static Player Ana;
	static XInputDevice[] devices;
	static Camera camera;

	public static int WIDTH = 1920;
	public static int HEIGHT = 1080;
	public long oldTime = 0;
	//CREATE THE OBJECT (STEP 1)
	Background 	bg 	= new Background(0, 0);
	
	public void paint(Graphics g) {
		for(int i =0 ; i < devices.length; i++) {

			XInputDevice device = devices[i];
			if (device.poll()) {
			    // Retrieve the delta
			    XInputComponentsDelta delta = device.getDelta();

			    XInputButtonsDelta buttons = delta.getButtons();
			    XInputAxesDelta axes = delta.getAxes();


			    // Retrieve button state change
			    if (buttons.isPressed(XInputButton.A)) {
			        Ana.jump();
			    } else if (buttons.isReleased(XInputButton.A)) {
			    }

			    // Retrieve axis state change.
			    // The class provides methods for each axis and a method for providing an XInputAxis
			    float accelerationDelta = axes.getRTDelta();
			    float brakeDelta = axes.getDelta(XInputAxis.LEFT_THUMBSTICK_X);
				float YDelta = axes.getDelta(XInputAxis.LEFT_THUMBSTICK_Y);
			    Ana.vx -= brakeDelta*10;
				if(Ana.debug){
					Ana.vy -= YDelta*10;
				}
			} else {
			    // Controller is not connected; display a message
			}
		}

		super.paintComponent(g);
		for(Block[] row : LevelBuilder.level){
			for(Block block : row){
				if(block != null){
					block.paint(g);
				}
			}
		}
		Ana.paint(g);


		//fps cap
		long newTime = System.nanoTime();
		long deltaT = newTime - oldTime;
		long fps =  (long) (Math.pow(10, 9) / deltaT);
		while(fps > 60){
			newTime = System.nanoTime();
			deltaT = newTime - oldTime;
			fps =  (long) (Math.pow(10, 9) / deltaT);
		}

		oldTime = newTime;
		//System.out.println(fps);
	}
	
	public static void main(String[] arg) {
		LevelBuilder.start();
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
		
		Ana = new Player(0, 0, 128, 128, true, "");
		camera = new Camera(Ana);
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
}