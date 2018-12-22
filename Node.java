import java.util.ArrayList;
/*
 * clean enough
 */
public class Node {

    /*
    *This class is purposed entirely
    *with mapping heirarchial inheritance
    *for nodes. each node will store 1 parent and
    *potentially multiple children based on their 
    *respective enumeration
    *
    *each instance of this node will not hold its own enumeration
    *values, enumeration values are stored in the index of the List<> which
    *each instance of Node is to be stored
    */
    int fibVal = 0;
    int dad;
    int xvalue;
    int yvalue;
    ArrayList<Integer> kids = new ArrayList<Integer>();
    
    Node() {
        dad = -1;
    }
    
	Node(int parent) {
	   dad = parent;
	}
    
    int getX() {
        return xvalue;    
    }
    
    int getY() {
        return yvalue;    
    }
    
    void setCoordinates(int x, int y) {
        xvalue = x;
        yvalue = y;
    }
	
    int getParent() {
    	return dad;    
    }
    
    Object[] getChildren() {
        return kids.toArray();
    }
    
    void addChild(int child) {
        kids.add(child);     
    }
    
    void setParent(int parent) {
        dad = parent;    
    }
    
    boolean hasDad() {
    	if(dad!=-1)
    		return true;
    	
    	return false;
    }
    //fibonacci stuff not currently in use
    void setFib(int val) {
    	fibVal = val;
    }
    
    int getFib() {
    	return fibVal;
    }
}