import java.util.ArrayList;
/*
 * code is clean
 */
public class NodeManager {
	
	int height;
	int width;
	int[][] enumeration;
	int endPoint;
	int heuristic;
	//the node map is used in the getTouching() function
	//to help locate unvisitedSet in euclidian space
	char[][] nodeMap;
    
	/* not currently in use */
    int[] fibHolder;  
    
    //stores cost values in enumerative indexes
	int[] tentative;
    
    //unvisitedSet
	ArrayList<Integer> unvisitedSet = new ArrayList<Integer>();
    
    //holds nodes, which hold their parent and children enumerative values
	//and coordinates
    ArrayList<Node> nodeHolder = new ArrayList<Node>();
    
    /*holds all node enumerations with costs < infinity
    *removed if a currentNode
    *
    * why do this? its probably faster than evaluating the 
    * entire unvisitedSet every time, redundantly checking nodes
    * with costs of infinity
    * 
    * significantly reduces solving time
    */
    ArrayList<Integer> discoveredNodes = new ArrayList<Integer>();
	
    //from user input
    void setHeuristic(int h) {
    	heuristic = h;
    }
    
	NodeManager(int heightt, int widthh) {
		
        fibHolder = new int[heightt * widthh];
		enumeration = new int[heightt][widthh];
		
		//sets enumeration grid
		int num = 0;
		
		for(int i = 0; i < heightt; i++) {
			for(int j = 0; j < widthh; j++) {
				enumeration[i][j] = num;
				num++;
			}
		}
		
		tentative = new int[widthh*heightt];
		nodeMap = new char[heightt][widthh];
		width = widthh;
		height = heightt;
	}
	
    void discoverNode(int e) {
        discoveredNodes.add(e);        
    }
    
    int getDNodeAtIndex(int index) {
    	return discoveredNodes.get(index);
    }
    
	void setEnd(int end) {
		endPoint = end;
	}
	
	void setTentative(int e, int value) {
		tentative[e] = value;
	}
	
	void addNode(int e, int i, int j) {
        //adds enumeration to unvisitedSet
		unvisitedSet.add(e);
        nodeMap[i][j] = '#';
        

        while(nodeHolder.size() <= e) {
    		nodeHolder.add(new Node());
    	}
    	
        nodeHolder.get(e).setCoordinates(i , j);
	}
	
	//not currently in use
	void setNodeFib(int e, int val) {
        fibHolder[e] = val;
	}
	
	int getNodeX(int e) {
		return nodeHolder.get(e).getX();
	}
    
    int getNodeY(int e) {
        return nodeHolder.get(e).getY();    
    }
    
    //the heart and soul of this operation
	int getCost(int e) {
		
		if(tentative[e]==(int)Double.POSITIVE_INFINITY)
			return (int)Double.POSITIVE_INFINITY;
		
		//this is the heuristic function
		//add tentative value to 
		//euclidian distance for cost
		
		//get coordinates for endpoint
		int ex = nodeHolder.get(endPoint).getX();
		int ey = nodeHolder.get(endPoint).getY();
		
		//get coordinates for given point
		int x = nodeHolder.get(e).getX();
		int y = nodeHolder.get(e).getY();
		
		int dx = 0;
		int dy = 0;
		
		if(ex>x)
			dx = ex - x;
		
		else
			dx = x - ex;
		
		if(ey>y)
			dy = ey - y;
		
		else
			dy = y - ey;
		
		/*
		 * this is what makes dijkstra's / a* work
		 * the heuristic is input in the Maze class
		 */
		return  (((int) (Math.sqrt( (dy * dy) + (dx * dx) ) ) )* heuristic ) /* - enumeration[x][y] */ + tentative[e];//  + fibHolder[e] ;
	}
	
	int getDistance(int e1, int e2) {
		
		/*
		 * first, find the coordinates of the two points
		 * the y's are left
		 */
		
		int x1 = nodeHolder.get(e1).getX();
		int x2 = nodeHolder.get(e2).getX();
		int y1 = nodeHolder.get(e1).getY();
		int y2 = nodeHolder.get(e2).getY();
		
		int distance = 0;
		
		//which axis do we have in common?
		if(x1==x2) {
			if(y1>y2) 
				distance = y1 - y2;
			
			else
				distance = y2 - y1;
		}
		
		if(y1==y2) {
			if(x1>x2)
				distance = x1 - x2;
			
			else
				distance = x2 - x1;
		}
		
		return distance;
	}

	boolean getContaining(int e) {
		if(unvisitedSet.contains(e))
			return true;
		
		return false;
	}
    
    //works
    int getFib(int n) {
        if(n==1)
            return 1;
        if(n==0)
            return 0;
        
        return getFib(n-1) + getFib(n-2);
    }
    
	//works now
	ArrayList<Integer> getTouching(int e) {
		
		ArrayList<Integer> touching = new ArrayList<Integer>();
		
		int x = (nodeHolder.get(e)).getX();
		int y = (nodeHolder.get(e)).getY();
		
		for(int xi = x + 1; xi < height; xi++) {
			if(nodeMap[xi][y]=='x')
				break;
			
			if(nodeMap[xi][y]=='#') {
				touching.add(enumeration[xi][y]);
			//	System.out.println("bottom");
				break;
			}
		}
		
		for(int xi = x - 1; xi >= 0; xi--) {
			if(nodeMap[xi][y]=='x')
				break;
			
			if(nodeMap[xi][y]=='#') {
				touching.add(enumeration[xi][y]);
			//	System.out.println("top");
				break;
			}
		}
		
		for(int yi = y + 1; yi < width; yi++) {
			if(nodeMap[x][yi]=='x')
				break;
			
			if(nodeMap[x][yi]=='#') {
				touching.add(enumeration[x][yi]);
			//	System.out.println("right");
				break;
			}
		}
		
		for(int yi = y - 1; yi >= 0; yi--) {
			if(nodeMap[x][yi]=='x')
				break;
			
			if(nodeMap[x][yi]=='#') {
				touching.add(enumeration[x][yi]);
			//	System.out.println("left");
				break;
			}
		}
		
		return touching;
	}
	
	void setVisited(int e) {
		//not necessary to make a new variable here
		//but too lazy to change it
		int tempNode = unvisitedSet.indexOf(e);
		if(tempNode!=-1)
			unvisitedSet.remove(tempNode);
        
        if(discoveredNodes.contains(e))
            discoveredNodes.remove(discoveredNodes.indexOf(e));
        
    /*     dont know why i ever had this at all
        Collections.sort(discoveredNodes , Collections.reverseOrder()); */
	}
	
	void addWall(int i, int j) {
        nodeMap[i][j] = 'x';
	}
	
	//dev tool
	void printNodes() {
			System.out.println(unvisitedSet);
	}

	//dev tool
	void printCost() {
		for(int i = 0; i < height * width; i++) {
			System.out.print(i + " " + getCost(i) + "\n");
		}
	}
	
	//dev tool
	void printMap() {
		for(int i = 0; i < height; i++) {
			
			for(int j = 0; j < width; j++) {
				
				System.out.print(nodeMap[i][j]);
				
			}
			System.out.println();
		}
	}
	
	int getTentative(int e) {
		return tentative[e];
	}

    void setInheritance(int parent, int child) {
    	
    	//creates a new node at the index of the child's enumeration,
        //should no node at of this value already exist
    	//nodeHolder.set(child , new Node(parent));
    	
        (nodeHolder.get(child)).setParent(parent);
    }
    
    boolean nodeHasDad(int e) {
    	try {
    		nodeHolder.get(e);
    	}catch(java.lang.IndexOutOfBoundsException e1) {
    		return false;
    	}
    	
    	return nodeHolder.get(e).hasDad();
    }
    
    int getParentNode(int e) {
    	return (nodeHolder.get(e)).getParent();
    }
}