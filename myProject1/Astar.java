package myProject;
import java.util.*;

public class Astar {
	private Grid curMap;
	private int M,N;
	private int[] start;
	private Node init;
	private int cost;
	
	public Astar(Grid map) {
		this.curMap=map;
		this.M=map.getNumOfColumns();
		this.N=map.getNumOfRows();
		this.start=curMap.getStart();
		this.init=new Node(null,N,M,start,curMap);
	}
	
	public int getASTARcost() {
		return cost;
	}
	
	public int[] search() {
		ArrayList<int[]> visited = new ArrayList<>();

        PriorityQueue<Node> queue = new PriorityQueue<Node>(20, new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if (o1.getHeuristicScore()>o2.getHeuristicScore())
                    return 1;
                else if (o1.getHeuristicScore()<o2.getHeuristicScore())
                    return -1;
                else
                    return 0;
            }
        });

        ArrayList<int[]> steps = new ArrayList<>();
        init.findNeighbours(visited,curMap);
        queue.addAll(init.getChildren());
        
        while (!queue.isEmpty()){
            Node currentNode = queue.remove();
           // System.out.println(queue);
            //System.out.println(currentNode.getCost());
           // System.out.println(currentNode.getHeuristicScore());
           // System.out.println("Current node: " + Arrays.toString(currentNode.getNodePos()));
            if(currentNode.getNodePos()[0]==curMap.getTerminal()[0]&&currentNode.getNodePos()[1]==curMap.getTerminal()[1]){
                while (currentNode!=null){
                   // System.out.println("Path: " + Arrays.toString(current.getNodePos()));
                    steps.add(currentNode.getNodePos());
					cost += currentNode.getCost();
                    currentNode = currentNode.getParent();
                }
                int[] stepsInt = new int[steps.size()];
                for(int l=0;l<steps.size();l++)
                    stepsInt[l]=steps.get(l)[0]*M+steps.get(l)[1];
                return stepsInt;
            }
            else{
                visited.add(currentNode.getNodePos());  //add current node to the seen nodes
                currentNode.findNeighbours(visited,curMap); 
                queue.addAll(currentNode.getChildren());  
            }
        }
        return null;

    }
	
}
