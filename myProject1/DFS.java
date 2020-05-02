package myProject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

public class DFS {

	private Grid curMap;
	private int M,N;
	private int[] start;
	private Node init;
	private int cost;
	
	public DFS(Grid map) {
		this.curMap=map;
		this.M=map.getNumOfColumns();
		this.N=map.getNumOfRows();
		this.start=curMap.getStart();
		this.init=new Node(null,N,M,start,curMap);
	}
	
	public int getDFScost() {
		return cost;
	}
	
	public int[] search() {
		Stack<Node> stack = new Stack();
		stack.push(init);
		ArrayList<Node> seenNodes = new ArrayList<Node>();	//keeps the node
		ArrayList<int[]> seen = new ArrayList<int[]>();		//keeps the node's position for steps
		ArrayList<int[]> steps = new ArrayList<int[]>();	
		
		while(!stack.isEmpty()) {
			//System.out.println(stack);
			Node current = stack.pop();
			//System.out.println(current);
			//System.out.println(Arrays.toString(current.getNodePos()));
			//System.out.println(stack);
			
			if(current.getNodePos()[0]==curMap.getTerminal()[0] && current.getNodePos()[1]==curMap.getTerminal()[1] ) {
				while(current.getParent()!=null) {
					steps.add(current.getNodePos());
					cost += current.getCost();
					//System.out.println(cost);
					current=current.getParent();
				}
				int[] stepsPos = new int[steps.size()];
				for(int l=0;l<steps.size();l++) {
					//System.out.println(Arrays.toString(steps.get(l)));
					stepsPos[l]=steps.get(l)[0]*M+steps.get(l)[1];
					//System.out.println(stepsPos[l]);
				}
				//System.out.println("TELOS");
				return stepsPos;
			}
			//if(!seenNodes.contains(current)) {
			if(!current.getVisited()) {
				current.setVisited();
				current.findNeighbours(seen, curMap);
				seenNodes.add(current);
				seen.add(current.getNodePos());
				//System.out.println(Arrays.toString(current.getNodePos()));
				//System.out.println(current.getChildren().size());
				//for(int i=0; i<current.getChildren().size();i++) {
					//if(!seenNodes.contains(current.getChildren().get(0))) {
				stack.addAll(current.getChildren());
					//seenNodes.add(current.getChildren().get(0));
					//}
				//}
			}
			
		}
		return null;
	}
	
}
