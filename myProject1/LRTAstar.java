package myProject;
import java.util.*;

public class LRTAstar {
		
		private Grid curMap;
		private int N;
		private int M;
		private Node init;
		private int cost;
		private int[]  steps;
		
		public LRTAstar(Grid curMap) {
			this.curMap=curMap;
			this.M = curMap.getNumOfColumns(); 
			this.N = curMap.getNumOfRows();
			this.init = new Node(null,N,M,curMap.getStart(),curMap);	
		}
		
		public int getLRTASTARcost() {
			return cost;
		}
		
		public Node getCheaperNeighbour(ArrayList<Node> list,ArrayList<int[]> seen,Node previous) {
			Node e = list.get(0);
			boolean c =false;
			for(int j=1;j<list.size();j++) {
				c=false;
				if(previous!=null && list.get(j).getNodePos()[0]==previous.getNodePos()[0]&&list.get(j).getNodePos()[1]==previous.getNodePos()[1]) {continue;}
				for(int i=0;i<seen.size();i++) {
					if(seen.get(i)[0]==(list.get(j).getNodePos()[0]) && seen.get(i)[1]==(list.get(j).getNodePos()[1])) {
						c=true;
						break;
					}
				}
				if(c) {continue;}
				if(list.get(j).getCurrentScore() < e.getCurrentScore()) {
					e = list.get(j);
				}
			}
			return e;
		}

		public int[] search() {
			
			ArrayList<Node> seenNodes = new ArrayList<Node>();	//keeps the node
			ArrayList<int[]> seen = new ArrayList<int[]>();	
			ArrayList<Node> lista = new ArrayList<>();
			
			Node current= init;
			Node ptr=null;
			seenNodes.add(init);
			init.setVisited();
			int numChilds = current.getChildren().size();
			//System.out.println(seenNodes);
			seen.add(init.getNodePos());
			boolean x =false;
			boolean a=false;
			int cnt =1;
			Node previous=null;
			int[] kostoi;
			while(current.getNodePos()[0]!=curMap.getTerminal()[0] || current.getNodePos()[1]!=curMap.getTerminal()[1]) {
				lista.clear();
				x=false;
				current.findNeighbours(seen, curMap);
				lista.addAll(current.getChildren());
				
				numChilds = current.getChildren().size();
				kostoi= new int[numChilds];

				 if(numChilds>0) {	
					for (int i=0;i<numChilds;i++) {
						//System.out.println(Arrays.toString(lista.get(i).getNodePos()));
						kostoi[i]=lista.get(i).getHeuristicScore2();
						lista.get(i).setCurrentScore(kostoi[i]);
					}
					//System.out.println();
					Node e=getCheaperNeighbour(lista,seen,previous);
					Arrays.sort(kostoi);
					//System.out.println(Arrays.toString(kostoi));
					//System.out.println(Arrays.toString(e.getNodePos()));
					seenNodes.add(current);
					current.setVisited();
					cost += current.getCost();
					seen.add(current.getNodePos());
					previous=current;
					current=e; x=true;
					//System.out.println("NEW CURRENT:" + Arrays.toString(current.getNodePos()));
				}
				 else {
					 lista.clear();
						
						while(numChilds==0 && !current.equals(init) ) {
							a=true;
							lista.clear();
							previous=current;
							
							seenNodes.add(current);
							seen.add(current.getNodePos());
							current =current.getParent();// seenNodes.get(seenNodes.size()-cnt);
							current.findNeighbours2(seen, curMap,previous);
							
							cnt++;

							cost += current.getCost();
							
							lista.addAll(current.getChildren());
							int p=0;
							numChilds=0;
							for(int i=0;i<lista.size();i++) {
								if(!lista.get(i).getVisited()) {
									numChilds++;
								}
							}
							int[] stepsPos = new int[seen.size()];
							for(int l=0;l<seen.size();l++) {
								stepsPos[l]=seen.get(l)[0]*M+seen.get(l)[1];
							}
							lista.clear();						
						}
				 }
				
				//System.out.println("CURRENT OUTSIDE:"+Arrays.toString(current.getNodePos()));
				ptr = current;			
			}

			cost += current.getCost();
			seen.add(ptr.getNodePos());
			if(current.getNodePos()[0] == curMap.getTerminal()[0] && current.getNodePos()[1] == curMap.getTerminal()[1]) {
				//System.out.println("telos");
				int[] stepsPos = new int[seen.size()];
				for(int l=0;l<seen.size();l++) {
					stepsPos[l]=seen.get(l)[0]*M+seen.get(l)[1];
				}
				//System.out.println(Arrays.toString(ouras.getNodePos()));	
				return stepsPos;
			}
			return null;
			
		}
}
