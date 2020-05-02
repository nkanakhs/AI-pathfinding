/**
			INTELLIGENCE LAB
	course		: 	COMP 417 - Artificial Intelligence
	authors		:	A. Vogiatzis, N. Trigkas
	excercise	:	1st Programming
	term 		: 	Spring 2019-2020
	date 		:   March 2020
*/
package myProject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.awt.Canvas;

class GridGenerator{
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass,start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	public static void VisualizeGrid(String frame_name, int N, int M, int [] walls, int [] grass, int [] steps ,int start_idx, int terminal_idx ){
		JFrame frame = new JFrame(frame_name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Drawing(N,M,walls,grass, steps, start_idx,terminal_idx);
		canvas.setSize(M*30,N*30);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		String frame = "Random World";
		Grid mygrid;
		if (args.length<1)
			mygrid = new Grid();
		else if (args[0].equals("-i")){
			mygrid = new Grid(args[1]);
			frame = args[1].split("/")[1];
		}else if (args[0].equals("-d")){
			mygrid = new Grid(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		}else{
			mygrid = new Grid("world_examples/default.world");
			frame = "default.world";
		}
		int N = mygrid.getNumOfRows();
		int M = mygrid.getNumOfColumns();
		
		///////////////////////////////////////////////////////////////////
		
		int[] start= mygrid.getStart();
		int[] terminal=mygrid.getTerminal();
		
		//System.out.println(N);
		//System.out.println(M);
		//System.out.println(Arrays.toString(start));
		//System.out.println(Arrays.toString(terminal));
		//System.out.println(start[0]);
		//System.out.println(start[1]);

		///////////////////////////////////////////////////////////////////
		
		Scanner input = new Scanner(System.in);	
		int grass;
		
		//System.out.println("Give grass cost: ");
		//grass= input.nextInt();
		//mygrid.setGrassCost(grass);
		VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),mygrid.getStartidx(),mygrid.getTerminalidx());
		//mygrid = new Grid(grass);
		
		System.out.println("1.BFS");		
		System.out.println("2.DFS");		
		System.out.println("3.A*");		
		System.out.println("4.LRTA*");
		System.out.println("5.Exit");
		
		int option=0;
		
		while ( option!=5) {
			System.out.println("Choose an algorithm from the above,or 5 for exit!");
			option = input.nextInt();
			// option 1
			if(option == 1 ) {
				int cost=0;
				BFS bfs = new BFS(mygrid);
				int[] listara = bfs.search();
				cost = bfs.getBFScost();
				System.out.println("Total cost of BFS search :"+ cost);
				VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),listara,mygrid.getStartidx(),mygrid.getTerminalidx());
			}
			else if(option == 2) {
				int cost=0;
				DFS dfs= new DFS(mygrid);
				int[] vhmata = dfs.search();
				cost = dfs.getDFScost();
				System.out.println("Total cost of DFS search :" +cost);
				VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),vhmata,mygrid.getStartidx(),mygrid.getTerminalidx());	
			}	
			else if(option == 3) {
				int cost=0;
				Astar ASTAR= new Astar(mygrid);
				int[] vhmata = ASTAR.search();
				cost = ASTAR.getASTARcost();
				System.out.println("Total cost of Astar search :" +cost);
				VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),vhmata,mygrid.getStartidx(),mygrid.getTerminalidx());	
			}	
			else if(option == 4) {
				int cost=0;
				LRTAstar lrtastar= new LRTAstar(mygrid);
				int [] steps = lrtastar.search();
				cost = lrtastar.getLRTASTARcost();
				System.out.println("Total cost of LRTAstar search :" +cost);
				VisualizeGrid(frame,N,M,mygrid.getWalls(),mygrid.getGrass(),steps,mygrid.getStartidx(),mygrid.getTerminalidx());	
			}
		}
		input.close();
		return;

	}
}