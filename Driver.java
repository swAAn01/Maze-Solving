import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/*
 * thats some clean code!
 * 
 * DOCUMENTATION:
 * currently implemented are Dijkstra's shortest
 * path, and the A* search algorithm. I'm using
 * only basic heuristics and simple heaps.
 * 
 * The biggest bottleneck is the find-min fuction
 * in the solve function. I reduced it with the discoveredNodes
 * system, but it is still slow
 * 
 * I don't understand how to implement the fibonacci heap for
 * efficient priority queues yet, so I'm taking a break
 * 
 * I feel like there are more bottlenecks i dont know 
 * i need someone else to proofread my code
 */
public class Driver {

	public static void main(String[] args){
			
		//creating a bufferedimage of the maze
		BufferedImage image = null;
		File f = null;
		
		try {
			f= new File("200x200spiral.bmp");
			image = ImageIO.read(f);
		}catch(IOException e) {
			System.out.println(e);
		}
	
		Maze solver = new Maze(image);
        solver.solve(); 
	}
}
