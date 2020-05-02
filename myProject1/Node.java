package myProject;

import java.util.ArrayList;
import java.util.Arrays;

public class Node {
	
	private Node parent;
	private int[] nodePos;
	private boolean isVisited = false;
	private ArrayList<Node> NodeChildren;
	private int N,M;
	private Grid curMap;
	private int currentScore; //for LRTA*
	
	public Node(Node parent, int N , int M , int[] nodePos , Grid curMap) {
		this.parent=parent;
		this.isVisited=false;
		this.N=N;
		this.M=M;
		this.NodeChildren = new ArrayList<Node>();
		this.nodePos=nodePos;
		this.curMap=curMap;
	}
	/////// new
	public Node getParent() {
		return parent;
	}
	
	public ArrayList<Node> getChildren(){
		return NodeChildren;
	}
	
	//find all possible neighbours of current cell
	public void findNeighbours(ArrayList<int[]> visited , Grid curMap) {
		ArrayList<int[]> visitd = visited;

		//System.out.println("FROM FIND NEIGHBOURS cur pos:" +Arrays.toString(nodePos));
		//for(int j=0; j<visitd.size();j++) {
			//System.out.println("FROM FIND NEIGHBOURS" +Arrays.toString(visited.get(j)));
			//System.out.println("FROM FIND NEIGHBOURS getvstd" +Arrays.toString(visited.get(j)));
		//}
		//System.out.println("FROM FIND NEIGHBOURS" +visited);
		Cell left=curMap.getCell(0, 0);
		Cell right=curMap.getCell(0, 0);
		Cell upper=curMap.getCell(0, 0);
		Cell down=curMap.getCell(0, 0);
		//the getStart() method returns x,y coordinates representing the point in x,y axes
		//so if either of [0] or [1] coordinate is zero then we have one less neighbor
		if (nodePos[1]> 0) {
			upper=curMap.getCell(nodePos[0], nodePos[1]-1) ;          
		}
		if (nodePos[1] < M-1) {
			down=curMap.getCell(nodePos[0], nodePos[1]+1);
		}
		if (nodePos[0] > 0) {
			left=curMap.getCell(nodePos[0] - 1, nodePos[1]);
		}
		if (nodePos[0] < N-1) {
			right=curMap.getCell(nodePos[0]+1, nodePos[1]);
		}
		//for(int i=0;i<visitd.size();i++) {
			//System.out.println(Arrays.toString(visitd.get(i)));	
		//}
		
		//check if the neighbor is wall,if its not add to the list
		if(nodePos[1]>0 && !upper.isWall() && (!exists(visitd,new int[] {nodePos[0], nodePos[1] - 1}))) {
			//Node e=new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1},curMap);
			//if(e.getVisited()!=true) {
			NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1}, curMap)); // father of the new nodes is this node
			//}
		}
		if(nodePos[1]<M-1 &&!down.isWall() && (!exists(visitd,new int[] {nodePos[0], nodePos[1] + 1}))){
			NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] + 1}, curMap));
		}
		if(nodePos[0]>0 && !left.isWall() && (!exists(visitd,new int[] {nodePos[0] - 1, nodePos[1]}))) {
			NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] - 1, nodePos[1]}, curMap));
		}
		if(nodePos[0]<N-1 && !right.isWall() && (!exists(visitd,new int[] {nodePos[0] + 1, nodePos[1]}))) {
			NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] + 1, nodePos[1]}, curMap));
		}
		//for(int i=0; i<NodeChildren.size();i++) {
			//System.out.println("Paidia sth find:" + Arrays.toString(NodeChildren.get(i).getNodePos()));
		//}
						
	}
	

	public void findNeighbours2(ArrayList<int[]> visited , Grid curMap, Node previous) {
		NodeChildren.clear();
		ArrayList<int[]> visitd = visited;

		//System.out.println("FROM FIND NEIGHBOURS cur pos:" +Arrays.toString(nodePos));
		//for(int j=0; j<visitd.size();j++) {
			//System.out.println("FROM FIND NEIGHBOURS" +Arrays.toString(visited.get(j)));
			//System.out.println("FROM FIND NEIGHBOURS getvstd" +Arrays.toString(visited.get(j)));
		//}
		//System.out.println("FROM FIND NEIGHBOURS" +visited);
		Cell left=curMap.getCell(0, 0);
		Cell right=curMap.getCell(0, 0);
		Cell upper=curMap.getCell(0, 0);
		Cell down=curMap.getCell(0, 0);
		//the getStart() method returns x,y coordinates representing the point in x,y axes
		//so if either of [0] or [1] coordinate is zero then we have one less neighbor
		if (nodePos[1] > 0) {
			upper=curMap.getCell(nodePos[0], nodePos[1]-1) ;          
		}
		if (nodePos[1] < M-1) {
			down=curMap.getCell(nodePos[0], nodePos[1]+1);
		}
		if (nodePos[0] > 0) {
			left=curMap.getCell(nodePos[0] - 1, nodePos[1]);
		}
		if (nodePos[0] < N-1) {
			right=curMap.getCell(nodePos[0]+1, nodePos[1]);
		}
		//for(int i=0;i<visitd.size();i++) {
			//System.out.println(Arrays.toString(visitd.get(i)));	
		//}
		//bool check = false;
		//check if the neighbor is wall,if its not add to the list
		if(previous!=null) {
			if(nodePos[1]>0 && !upper.isWall() && nodePos[0]!=previous.getNodePos()[0] && nodePos[1] - 1!=previous.getNodePos()[1]) {
				//Node e=new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1},curMap);
				//if(e.getVisited()!=true) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1}, curMap)); // father of the new nodes is this node
				//}
			}
			if(nodePos[1]<M-1 &&!down.isWall()  && nodePos[0]!=previous.getNodePos()[0] && nodePos[1]+ 1!=previous.getNodePos()[1]){
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] + 1}, curMap));
			}
			if(nodePos[0]>0 && !left.isWall()  && nodePos[0] - 1!=previous.getNodePos()[0] && nodePos[1]!=previous.getNodePos()[1]) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] - 1, nodePos[1]}, curMap));
			}
			if(nodePos[0]<N-1 && !right.isWall() && nodePos[0] + 1!=previous.getNodePos()[0] && nodePos[1]!=previous.getNodePos()[1]) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] + 1, nodePos[1]}, curMap));
			}
			//for(int i=0; i<NodeChildren.size();i++) {
				//System.out.println("Paidia sth find:" + Arrays.toString(NodeChildren.get(i).getNodePos()));
			//}
		}
		else {
			if(nodePos[1]>0 && !upper.isWall()) {
				//Node e=new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1},curMap);
				//if(e.getVisited()!=true) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] - 1}, curMap)); // father of the new nodes is this node
				//}
			}
			if(nodePos[1]<M-1 &&!down.isWall()){
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0], nodePos[1] + 1}, curMap));
			}
			if(nodePos[0]>0 && !left.isWall()) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] - 1, nodePos[1]}, curMap));
			}
			if(nodePos[0]<N-1 && !right.isWall()) {
				NodeChildren.add(new Node(this , this.N , this.M , new int[] {nodePos[0] + 1, nodePos[1]}, curMap));
			}
			//for(int i=0; i<NodeChildren.size();i++) {
				//System.out.println("Paidia sth find:" + Arrays.toString(NodeChildren.get(i).getNodePos()));
			//}
		}
		
					
	}
	
	public boolean exists(ArrayList<int[]> list, int[] position) {
		for (int i=0; i < list.size();i++) {
			//if(position==list.get(i)) {
			if(Arrays.equals(list.get(i), position)) {	
				return true;
			}
		}
		return false;
	}
	
	public int getCost() {
		Cell cell=curMap.getCell(nodePos[0], nodePos[1]);
		int cost=cell.getCost();
		return cost;
	}
	
	public void setCurrentScore(int score) {
		this.currentScore=score;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	
	public void setVisited() {
		this.isVisited=true;
	}
	
	public boolean getVisited() {
		if (isVisited) {
			return true;
		}else
			return false;
	}
	
    public int[] getNodePos(){
        return nodePos;
    }
    
    public int getHeuristicScore() {	//called every time from current cell
    	Cell cell=curMap.getCell(nodePos[0], nodePos[1]);
    	int cost=cell.getCost();
    	int[] goal=curMap.getTerminal();
    	//System.out.println(cost);
    	int heuristic=Math.abs(nodePos[0] - goal[0]) + Math.abs(nodePos[1] - goal[1]);
    	return heuristic + cost;
    }
    public int getHeuristicScore2() {	//called every time from current cell
    	Cell cell=curMap.getCell(nodePos[0], nodePos[1]);
    	//int cost=cell.getCost();
    	int[] goal=curMap.getTerminal();
    	//System.out.println(cost);
    	int heuristic=Math.abs(nodePos[0] - goal[0]) + Math.abs(nodePos[1] - goal[1]);
    	return heuristic; //+ cost;
    }
	
}
