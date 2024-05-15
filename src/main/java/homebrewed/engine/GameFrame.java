package homebrewed.engine;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public GameFrame(Camera camera) {
			
		this.setSize(Camera.WIDTH, Camera.HEIGHT);
		this.setLocation(0, 0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(new GamePanel(camera)); 
		this.setResizable(false);
		this.setVisible(true);
		
	}

}
