import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
/*
 * code is clean
 */
public class MazeGraphics extends JPanel{

	private static final long serialVersionUID = 1L;
	private BufferedImage mazeImage;
	
	public MazeGraphics(BufferedImage img) {
		
		mazeImage = img;
		
	}
	
	public void paintNode(BufferedImage b) {
	
		mazeImage = b;
		paintComponent(mazeImage.getGraphics());
		
	}
	
	public void paintComponent(Graphics g) {
		
		g.drawImage(mazeImage, 0, 0, null);
		
	}
	
}
