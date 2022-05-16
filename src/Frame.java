import java.awt.Color;
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
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import Blocks.Block;
import Blocks.KaliAltar;
import Blocks.LevelGen;
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
	static Block test;
	static XInputDevice[] devices;
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
			     	Ana.vx -= brakeDelta*10;
			} else {
			    // Controller is not connected; display a message
			}
		}
		super.paintComponent(g);
		Ana.paint(g);
	}
	
	public static void main(String[] arg) {
		try {
			devices = XInputDevice.getAllDevices();
		} catch (XInputNotLoadedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		Frame f = new Frame();
		int[][] levelTest = new int[4][4];
		LevelGen.generateSections(levelTest);
		for(int[] arr : levelTest) {
			System.out.println(Arrays.toString(arr));
		}
	}
	
	public Frame() {
		JFrame f = new JFrame("Spelunky Lite");
		
		Ana = new Player(0, 0, 128, 128, true, "");
		Ana.update();
		test = new Block(0, 0, 600, false);
		f.setSize(new Dimension(400, 1000));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(true);
		f.setLayout(new GridLayout(1,2));
		f.addMouseListener(this);
		f.addKeyListener(this);
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
		
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