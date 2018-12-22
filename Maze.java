import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;
/*
 * code is clean
 * 
 * as of right now, the fibonacci heap is not
 * implemented. I may or may not pursue using
 * it in the future, should i learn how
 * 
 * im done with this for now, time for quicksort
 */
public class Maze {
	
	JFrame frame = new JFrame();
	//true means long graphics, false means primitive
	boolean graphics = true;
	
	//not currently in use
    ArrayList<Integer> fibSet = new ArrayList<Integer>();
    int fibInt = 0;  
    
    boolean unicursal = false;
	boolean[][] maze;
	int[][] enumeration;
	int nodesCreated = 0;
	int nodesExplored = 0;
	NodeManager manager;
	int start;
	int end;
	BufferedImage mazeImage;
	MazeGraphics thePanel;
	ArrayList<Integer> visitedSet = new ArrayList<Integer>();
	
	Maze(BufferedImage image) {
		
		mazeImage = image;
		maze = new boolean[image.getHeight()][image.getWidth()];
		enumeration = new int[image.getHeight()][image.getWidth()];
		
		manager = new NodeManager(image.getHeight(),image.getWidth());
		
		thePanel = new MazeGraphics(resize(mazeImage));
		
		inputGraphics();
		
		do {
			try {
				manager.setHeuristic(Integer.parseInt(JOptionPane.showInputDialog("Enter 1 for A*, 0 for Dijkstra's, or something else if you're really clever :)")));
			}catch(java.lang.NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Input invalid.");
				continue;
			}
		}while(false);
		
		displayMaze();
		
		System.out.println("populating...");
		populate();
		System.out.println("setting nodes...");
		setNodes();
		
		
	//	System.out.println("setting fibonacci heap...");
		
	//	manager.setNodeFib(end, fibInt);
	//	fibInt++;
		
//		setFibonacci(end);   
		
		manager.setEnd(end);
	}
    
	//not currently in use
	int getFib(int n) {
        if(n==1)
            return 1;
        if(n==0)
            return 0;
        
        return getFib(n-1) + getFib(n-2);
    }
	
    /*
    * It seems the fibonacci heap mumbo
    * jumbo is a bit above my pay grade for now
    * so im removing all implementation
    * for now
    */
    void setFibonacci(int n) {
        
        ArrayList<Integer> touching = manager.getTouching(n);
        
        for(int i = 0; i < touching.size(); i++) {
        	
            if(fibSet.contains(touching.get(i))) {
            	
       //     	System.out.println(touching.get(i));
            	
            	//assign a fibonacci pointer to the fibHolder array
                manager.setNodeFib(touching.get(i), fibInt);
                
                //increment fibInt
                fibInt++;
                System.out.println(fibInt);
                
                //remove node from fibSet so it isnt used again
                fibSet.remove(fibSet.indexOf(touching.get(i)));
                
                //check neighbors and continue
                setFibonacci(touching.get(i));
            }
        }
    }
    
    public static BufferedImage resize(BufferedImage img) { 
    	
    Image tmp = img.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
    BufferedImage dimg = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

    Graphics2D g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();

    return dimg;
    } 
    
    public void paintNode(int e) {
    	
    	if(e==start) {
    		mazeImage.setRGB(manager.getNodeY(e) , manager.getNodeX(e) , Color.BLUE.getRGB());
    		return;
    	}
    	
    	/*
    	 * dont reallly need to make a new varaible
    	 * here but i copy pasted so much code its
    	 * just easier this way
    	 */
    	int currentNode = e;
    	int x1 = manager.getNodeX(manager.getParentNode(currentNode));
    	int x2 = manager.getNodeX(currentNode);
        int y1 = manager.getNodeY(manager.getParentNode(currentNode));
        int y2 = manager.getNodeY(currentNode);

   	    int distance = manager.getDistance(currentNode, manager.getParentNode(currentNode));
    	            
        boolean up = false;
        boolean down = false;
        boolean left = false;
        boolean right = false;
    	            
        //which axis do we have in common?
        if(x1==x2) {
        	if(y1>y2) 
              	left = true;
    	            			
            else
              	right = true;
   	    }
    	            		
        else if(y1==y2) {
            if(x1>x2)
            	up = true;
    	            			
   	        else
               	down = true;
        }
    	            
        int xi = x1;
        int yi = y1;
            
   	    for(int i = 0; i <= distance; i++) {
   	    	mazeImage.setRGB(yi, xi, Color.BLUE.getRGB());
            if(graphics) {
              	thePanel.paintNode(resize(mazeImage));
               	thePanel.repaint();
               	frame.repaint();
            }
            if(up)
              	xi--;
    	            	
            else if(down)
              	xi++;
    	            	
            else if(left)
               	yi--;
    	            	
            else if(right)
              	yi++;
  	    }
   	        
   	    ArrayList<Integer> touching = manager.getTouching(e);
   	    
   	    for(int i = 0; i < touching.size(); i++) {
   	    	
   	    	if(visitedSet.contains(touching.get(i)) && touching.get(i) != manager.getParentNode(e)) {
   	    		
   	    		int a1 = manager.getNodeX(e);
   	    	    int a2 = manager.getNodeX(touching.get(i));
   	            int b1 = manager.getNodeY(e);
   	            int b2 = manager.getNodeY(touching.get(i));

   	   	        int dist = manager.getDistance(e, touching.get(i));
   	    	            
   	            boolean u = false;
   	            boolean d = false;
   	            boolean l = false;
   	            boolean r = false;
   	    	            
   	            //which axis do we have in common?
   	            if(a1==a2) {
   	            	if(b1>b2) 
   	               		l = true;
   	    	            			
   	               	else
   	              		r = true;
   	   	        }
   	    	            		
   	            else if(b1==b2) {
   	                if(a1>a2)
   	                	u = true;
   	    	            			
   	   	            else
   	               		d = true;
   	            }
   	    	            
   	            int ai = a1;
   	            int bi = b1;
   	            
   	   	        for(int j = 0; j <= dist; j++) {
   	                mazeImage.setRGB(bi, ai, Color.BLUE.getRGB());
   	                if(graphics) {
   	                	thePanel.paintNode(resize(mazeImage));
   	                	thePanel.repaint();
   	   	            	frame.repaint();
   	                }
   	                if(u)
   	                	ai--;
   	    	            	
   	                else if(d)
   	                	ai++;
   	    	            	
   	                else if(l)
   	                	bi--;
   	    	            	
   	                else if(r)
   	                	bi++;
   	   	        }
   	    		
   	    	}
   	    	
   	    }
   	    
    	thePanel.repaint();
    	frame.repaint();
    }
	
	//100% works
	void populate() {
		
		//this just sets the enumeration grid
		//works
		int num = 0;
		
		for(int i = 0; i < mazeImage.getHeight(); i++) {
			for(int j = 0; j < mazeImage.getWidth(); j++) {
				enumeration[i][j]=num;
				num++;
			}
		}
		
		//sets maze to 1's and 0's
		//works
		for(int i = 0; i < mazeImage.getHeight(); i++) {
			for(int j = 0; j < mazeImage.getWidth(); j++) {
				
				//finding rgb for given pixel
				int p = mazeImage.getRGB(i, j);
				int r = (p>>16) & 0xff;
				int g = (p>>16) & 0xff;
				int b = (p>>16) & 0xff;
				/*
				 * uh so this is dumb
				 * Buffered image goes [0][0],[1][0]
				 * and arrays go [0][0],[0][1]
				 * so thats fun
				 */
				//if black, maze value = 1 ( wall )
				if(r==0&&g==0&&b==0) {
					maze[j][i]=true;
					manager.addWall(j , i);
				}
				
				
				//else, its a pathway
				else 
					maze[j][i]=false;
			}
		}
	}
	
	void printEndCost() {
		System.out.println(manager.getCost(end));
	}
	
	void inputGraphics() {
		
		char ans = '0';
		
		while(true) {
			
			String temp = JOptionPane.showInputDialog("Would you like advanced graphics? y/n");
			
			if(temp.length() != 1) {
				JOptionPane.showMessageDialog(null, "Input invalid.");
				continue;
			}
				
			ans = temp.charAt(0);
			
			if(ans != 'y' && ans != 'n') {
				JOptionPane.showMessageDialog(null, "Input invalid.");
				continue;
			}
			
			break;
			
		}
		
		if(ans == 'y')
			graphics = true;
		
		else
			graphics = false;
	}
	
	void solve() {
		
		System.out.println("solving...");
		int currentNode = start;
		long startTime = System.currentTimeMillis();
		
		while(currentNode!=end) {
			
			nodesExplored++;
			
			ArrayList<Integer> touching = manager.getTouching(currentNode);
			
			for(int i = 0; i < touching.size(); i++) {
				
				//just realized: we only want to update tentative values that don't already have a value
				//this code is soooooo dirty lol
				if((manager.getCost(touching.get(i))==(int)Double.POSITIVE_INFINITY) || manager.getTentative(currentNode) + manager.getDistance(currentNode, touching.get(i)) < manager.getTentative(touching.get(i)))
					manager.setTentative(touching.get(i), manager.getTentative(currentNode) + manager.getDistance(currentNode, touching.get(i)));
				
				/*
				 * why the if statement?
				 * i dont want to add a value to the 
				 * discoveredNodes list if it isnt part of
				 * the unvisited set
				 */
                if(manager.getContaining(touching.get(i)))
                    manager.discoverNode(touching.get(i));
                    
                //if it doesn't have a dad, give it one
				if(!manager.nodeHasDad(touching.get(i))) 
					manager.setInheritance(currentNode,touching.get(i));
                
                //tentative/cost doesn't really matter, tentative just felt cleaner
				//works maybe idk how to test
                else if(manager.getTentative(touching.get(i)) > manager.getTentative(currentNode) + manager.getDistance(currentNode , touching.get(i)))
                    manager.setInheritance(currentNode,touching.get(i)); 
			}
			
			manager.setVisited(currentNode);	
			visitedSet.add(currentNode);
			paintNode(currentNode);
            
			int smallest = (int)Double.POSITIVE_INFINITY;
			int smallestNode = 0;
			
			for(int i = 0; i < manager.discoveredNodes.size(); i++) {
				
				if((manager.getCost(manager.getDNodeAtIndex(i)) < smallest)&&(manager.getContaining(manager.getDNodeAtIndex(i)))) {
					
					smallest = manager.getCost(manager.getDNodeAtIndex(i));
					
					smallestNode = manager.getDNodeAtIndex(i);
					
				}
				
			}
			
			if(!manager.getContaining(end))
				break; 
			
			if(smallest==(int)Double.POSITIVE_INFINITY) {
				System.out.println("unsolvable.");
				return;
			}
			
			currentNode = smallestNode;
			
		}
		
		long endTime = System.currentTimeMillis();
		
		paintPath();
		updateGraphics();
		
		long duration = (endTime - startTime) / 1000;
		
		System.out.println();
		System.out.println("time elapsed: " + duration + " seconds");
		System.out.println("distance to end: " + manager.getTentative(end));
		System.out.println("nodes created: " + nodesCreated);
		System.out.println("nodes explored: " + nodesExplored);
	}
	
	//legacy code
	void updateImage() {
		Component[] comp = frame.getContentPane().getComponents();
		for(Component i : comp) {
			if(i instanceof JLabel) {
			((JLabel) i).setIcon(null);
			((JLabel) i).setIcon(new ImageIcon(resize(mazeImage)));
			}
		}
	}
	
	void updateGraphics() {
		thePanel.paintNode(resize(mazeImage));
		thePanel.repaint();
		frame.repaint();
	}
	
	//legacy code
	void paintNodes() {
		for(int i : visitedSet) {
			mazeImage.setRGB(manager.getNodeY(i), manager.getNodeX(i), Color.BLUE.getRGB());
		}
	}
	
	//dev tool
	void printEndPath() {
		
		System.out.println();
		System.out.println("path: ");
		
		int currentNode = end;
		
		while(currentNode!=start) {
			System.out.println(currentNode + " -> " + manager.getParentNode(currentNode));
			currentNode = manager.getParentNode(currentNode);
		}
		
		
	}
	
	//not currently in use, i dont plan on changing that
	void addButton() {
		JButton button = new JButton("solve");
		button.setSize(500,26);
		button.setLocation(0,525);
		button.addActionListener(new ActionListener() {

			boolean flag = false;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//solve only once you bug hungry cowards
				
				if(!flag) {
					solve();
					flag = true;
				}
			}
			
		});
		
		Component[] comp = frame.getContentPane().getComponents();
		for(Component i : comp) {
			if(i instanceof JLabel) {
			((JLabel) i).add(button);
			}
		}
	}
	
	//works
    void paintPath() {
    	
        int currentNode = end;
        
        while(currentNode!=start) {
            
             /*
             * first, find the coordinates of the two points
             * the y's are left
             */
        	
            int x1 = manager.getNodeX(currentNode);
            int x2 = manager.getNodeX(manager.getParentNode(currentNode));
            int y1 = manager.getNodeY(currentNode);
            int y2 = manager.getNodeY(manager.getParentNode(currentNode));

            int distance = manager.getDistance(currentNode, manager.getParentNode(currentNode));
            
            boolean up = false;
            boolean down = false;
            boolean left = false;
            boolean right = false;
            
            //which axis do we have in common?
            if(x1==x2) {
            	if(y1>y2) 
            		left = true;
            			
            	else
           			right = true;
           	}
            		
            else if(y1==y2) {
            	if(x1>x2)
            		up = true;
            			
            	else
           			down = true;
           	}
            
            int xi = x1;
            int yi = y1;
            
            for(int i = 0; i < distance; i++) {
            	mazeImage.setRGB(yi, xi, Color.RED.getRGB());
            	if(graphics) {
            		thePanel.paintNode(resize(mazeImage));
            		thePanel.repaint();
            		frame.repaint();
            	}
            	if(up)
            		xi--;
            	
            	else if(down)
            		xi++;
            	
            	else if(left)
            		yi--;
            	
            	else if(right)
            		yi++;
            }
            currentNode = manager.getParentNode(currentNode);
            
        }   
        mazeImage.setRGB(manager.getNodeY(start), manager.getNodeX(start), Color.RED.getRGB());
    }
    
    //works
    void displayMaze() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setSize(516,540);
        
        frame.add(thePanel);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        frame.revalidate();
    	frame.repaint();
        
    }
	
	void setNodes() {
		
		/*
		 * check the top wall for the entrance
		 * and the bottom wall for the exit
		 * 
		 * set variables start and end to 
		 * their enumeration grid values
		 */
        
		
		//sets start node
		for(int i = 0; i < mazeImage.getWidth(); i++) {
			if(maze[0][i]==false) {
				manager.addNode(enumeration[0][i] , 0 , i);
//				fibSet.add(enumeration[0][i]);
				start = enumeration[0][i];
				manager.setTentative(start, 0);
				nodesCreated++;
				break;
			}
		}
		
		//used for uni mazes
		if(unicursal) {
			for(int i = 0; i < mazeImage.getWidth(); i++) {
				if(maze[0][i]==false && enumeration[0][i]!=start) {
					manager.addNode(enumeration[0][i] , 0 , i);
					end = enumeration[0][i];
					manager.setTentative(end, (int)Double.POSITIVE_INFINITY);
					nodesCreated++;
					break;
				}
			}   
		}
		
		
		//sets end node
		else {
			for(int i = 0; i < mazeImage.getWidth(); i++) {
				if(maze[mazeImage.getHeight()-1][i]==false) {
					manager.addNode(enumeration[mazeImage.getHeight()-1][i] , mazeImage.getHeight()-1 , i);
	//				fibSet.add(enumeration[mazeImage.getHeight()-1][i]);
					end = enumeration[mazeImage.getHeight()-1][i];
					manager.setTentative(end, (int)Double.POSITIVE_INFINITY);
					nodesCreated++;
					break;
				}
			}    
		}
		
		/*
		 * we check all non-wall cells to see if they are nodes
		 * there are 2 node situations
		 * 1) dead ends, so wallsTouching == 3
		 * 2) turns, so up&&(left||right)||down&&(left||right)
		 * 3) intersections so wallsTouching = 0 or 1
		 */
		
		int wallsTouching = 0;
		boolean top = false;
		boolean bottom = false;
		boolean left = false;
		boolean right = false;
		
		for(int i = 1; i < mazeImage.getHeight()-1; i++) {
			for(int j = 1; j < mazeImage.getWidth()-1; j++) {
                
                if(maze[i][j]==false) {
                    
					if(maze[i][j-1]==true) {
						wallsTouching++;
						left = true;
					}
					
					if(maze[i][j+1]==true) {
						wallsTouching++;
						right = true;
					}
					
					if(maze[i-1][j]==true) {
						wallsTouching++;
						top = true;
					}
					
					if(maze[i+1][j]==true) {
						wallsTouching++;
						bottom = true;
					}
					
					if(wallsTouching == 1 || wallsTouching == 0) {
						manager.addNode(enumeration[i][j] , i , j);
						fibSet.add(enumeration[i][j]);
						manager.setTentative(enumeration[i][j], (int)Double.POSITIVE_INFINITY);
						nodesCreated++;
					}
					
					else if((top&&(left||right))||(bottom&&(left||right))) {
						manager.addNode(enumeration[i][j] , i , j);
						fibSet.add(enumeration[i][j]);
						manager.setTentative(enumeration[i][j], (int)Double.POSITIVE_INFINITY);
						nodesCreated++;
					}
					
					wallsTouching = 0;
					top = false;
					bottom = false;
					left = false;
					right = false;
                }
			}
		}
	}
	
	
	//dev tool
	void printGrid() {
		for(int i = 0; i < mazeImage.getHeight(); i++) {
			for(int j = 0; j < mazeImage.getWidth(); j++) {
				System.out.print(enumeration[i][j]);
			}
			System.out.println();
		}
	}
}
