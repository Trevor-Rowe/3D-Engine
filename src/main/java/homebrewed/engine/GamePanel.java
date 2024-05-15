package homebrewed.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import homebrewed.engine.math.CartesianAxis;
import homebrewed.engine.math.Matrix;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int TARGET_FPS = 60; 
	
	private boolean running;
	private Thread displayThread; 
	private BufferedImage buffer;
	private Graphics bufferGraphics;
	private Camera camera; 

	public GamePanel(Camera camera) {
		
		this.setSize(Camera.WIDTH, Camera.HEIGHT); 
		this.setLocation(0, 0);
		this.running = true; 
		this.displayThread = new Thread(this);
		this.buffer = new BufferedImage(Camera.WIDTH, Camera.HEIGHT, BufferedImage.TYPE_INT_RGB);
		this.bufferGraphics = this.buffer.getGraphics();
		this.camera = camera;
		Controls controls = new Controls(); 
		this.addKeyListener(controls);
		this.displayThread.start(); 
			
	}
	
	
	@Override
	public void run() {
		
		while(running) {
			
			drawToBuffer();
			drawToPanel();
			
			try { Thread.sleep(1000 / TARGET_FPS); } catch(Exception e) { e.printStackTrace(); }
			
		}
		
	}
	
	@Override
	public void addNotify() {
		
		super.addNotify();
		this.requestFocus();
		
	}
	
	private void drawToBuffer() {
		
		bufferGraphics.setColor(Color.LIGHT_GRAY);	
		bufferGraphics.fillRect(0, 0, Camera.WIDTH, Camera.HEIGHT);
		this.camera.draw(buffer);
		
	}
	
	public void drawToPanel() {
		
		Graphics g = this.getGraphics();
		if(g != null) {
			
			g.drawImage(buffer, 0, 0, null);
			
		}
		
	}
	
	public class Controls implements KeyListener {
		

		@Override
		public void keyTyped(KeyEvent e) {
			
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
		
			
			switch(e.getKeyCode()) {
			
			case KeyEvent.VK_A: 
				
				camera.moveCamera(-1, CartesianAxis.X_AXIS);
				break;
				
			case KeyEvent.VK_D:
				
				camera.moveCamera(1, CartesianAxis.X_AXIS);
				break;
				
			case KeyEvent.VK_W: 
				 
				camera.moveCamera(1, CartesianAxis.Z_AXIS);
				break;
				
			case KeyEvent.VK_S:
				
				camera.moveCamera(-1, CartesianAxis.Z_AXIS);
				break;
				
			case KeyEvent.VK_SPACE:
				
				camera.moveCamera(1, CartesianAxis.Y_AXIS);
				break;
				
			case KeyEvent.VK_SHIFT:
				
				camera.moveCamera(-1, CartesianAxis.Y_AXIS);
				break;
			
			}
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
