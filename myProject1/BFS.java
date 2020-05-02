package myProject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class BFS {
	
	private Grid curMap;
	private int N;
	private int M;
	private Node init;
	private int[]  steps;
	private int cost;
	
	
	public BFS(Grid curMap) {
		this.curMap=curMap;
		this.M = curMap.getNumOfColumns(); 
		this.N = curMap.getNumOfRows();
		this.init = new Node(null,N,M,curMap.getStart(),curMap);	
	}
	
	public int getBFScost() {
		return cost;
	}
	
	public int[] search() {
		ArrayList<int[]> visited = new ArrayList<int[]>();
		ArrayList<int[]> steps = new ArrayList<int[]>();
		LinkedList<Node> queue = new LinkedList<>();
	   // queue.add(init);                               //initialize the queue with the first node
	    //visited.add(init);
	    
		init.findNeighbours( visited , curMap );
		//visited = init.getChildren();
		queue.addAll(init.getChildren());			// then add all its children
		int i=0;
		while(!queue.isEmpty()) {					
			//System.out.println(Arrays.toString(queue.get(0).getNodePos()));
			//System.out.println(visited);
			//i++;
			Node ouras = queue.remove();
			//System.out.println(curMap.getTerminal()[0]);
			//System.out.println(curMap.getTerminal()[1]);
			//System.out.println(ouras.getNodePos()[0]);
			//System.out.println(ouras.getNodePos()[1]);
			if (ouras.getNodePos()[0]==curMap.getTerminal()[0] && ouras.getNodePos()[1]==curMap.getTerminal()[1]) {		//checking for terminal node
				
				while(ouras.getParent()!=null) {
					//total.cost getcellType...
					cost += ouras.getCost();
					steps.add(ouras.getNodePos());
					ouras=ouras.getParent();
				}
				int[] stepsPos = new int[steps.size()];
				for(int l=0;l<steps.size();l++) {
					stepsPos[l]=steps.get(l)[0]*M+steps.get(l)[1];
				}
				//System.out.println(Arrays.toString(ouras.getNodePos()));	
				return stepsPos;
			}
			else { // adding elements to the queue until we reach the goal node
				visited.add(ouras.getNodePos());
				ouras.findNeighbours(visited, curMap);
				queue.addAll(ouras.getChildren());
				//System.out.println("mesa");
				//System.out.println(Arrays.toString(ouras.getNodePos()));
				//for(int k=0;k<visited.size();k++) {
					//System.out.println("lista visited:");
					//System.out.println(Arrays.toString(visited.get(k)));
				//}
			}
			
		}
		return null;
	}

}
