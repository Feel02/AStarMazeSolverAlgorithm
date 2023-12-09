/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.AbstractCollection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class AStarSolver extends Solver
{
	/*
	 * Constructor
	 * m: The maze to solve
	 */
	public AStarSolver(Maze m, Boolean manhattan)
	{
		this.maze = m;
		this.result = "";
		this.manhattan = manhattan; //manhattan distance or euclidean distance for the heuristic func
		this.frontier = new PriorityQueue<Node<Maze>>(new Comparator<Node<Maze>>()
		{
			public int compare(Node<Maze> s1, Node<Maze> s2) 
		    	{
		    		Double sf1 = s1.getContent().getCurrState().getF();
		    		Double sf2 = s2.getContent().getCurrState().getF();
		    		Double sh1 = s1.getContent().getCurrState().getH();
		    		Double sh2 = s2.getContent().getCurrState().getH();
		    	
		    		if(sf1 > sf2)
		    			return 1;
		    		else if(sf1 == sf2)
		    		{
		    			if(sh1 > sh2)
		    				return 1;
		    			else if(sh1 == sh2)
		    				return 0;
		    			else
		    				return -1;
		    		}
		    		else
		    			return -1;
		    	}
		});
		this.closedSquares = new PriorityQueue<Square>(new Comparator<Square>()
		{
			public int compare(Square s1, Square s2) 
		    	{
				Double sf1 = s1.getF();
		    		Double sf2 = s2.getF();
		    		Double sh1 = s1.getH();
		    		Double sh2 = s2.getH();
		    	
		    		if(sf1 > sf2)
		    			return 1;
		    		else if(sf1 == sf2)
		    		{
		    			if(sh1 > sh2)
		    				return 1;
		    			else if(sh1 == sh2)
		    				return 0;
		    			else
		    				return -1;
		    		}
		    		else
		    			return -1;
		    	}
		});
	}
	
	public String solve()
	{
		this.maze.initMaze(); //Init maze
		
		Boolean endfound = false;
		this.nodesCounter = 0;
		this.pathLength = 0;
		
		//Compute h (so f due to g=0) and f value of Starting square
		if(manhattan) //Check whether manhattan distance or euclidean distance for the heuristic func
			this.maze.getStart().calcManhattanH();
		else
			this.maze.getStart().calcEuclidH();
		
		this.maze.getStart().calcF();//calculate f for the starting square
		
		//Init data structures
		this.frontier.clear(); //Clear frontier Queue - frontier is fringe (priority queue)
		((PriorityQueue<Node<Maze>>) this.frontier).offer(new Node<Maze>(this.maze)); //Adding the first node (Start node) (g is at 0, [Start to Start = 0])
		this.closedSquares.clear(); //Clear closedSquares - stack for the visited ones aka explored set
		
		//Measure run time
		long startTime = System.currentTimeMillis();
		
		while(!endfound)
		{
                        //you should check if there exist a node to expand
			if(this.frontier.isEmpty())
				break;
			else
			{
                                //You should first remove it from the queue (you visit it)
				Node<Maze> current = ((PriorityQueue<Node<Maze>>) this.frontier).remove(); //Get first node from the frontier
				this.maze = (Maze) current.getContent(); //Get maze from the node
				Square currState = this.maze.getCurrState(); //Get current state from the maze
				
                                //checking if we have found the solution
				if(currState.getCol() == this.maze.getEnd().getCol() && currState.getLine() == this.maze.getEnd().getLine())
				{
					((PriorityQueue<Node<Maze>>) this.frontier).add(current);
					endfound = true;
				}
				else
				{
					LinkedList<Node<Maze>> nexts = this.getNextSquares(); 						

					Iterator<Node<Maze>> nextsIterator = nexts.iterator();						

					if(!(((PriorityQueue<Square>) this.closedSquares).contains(currState))){			
						while (nextsIterator.hasNext()){							
							Node<Maze> nextNode = nextsIterator.next();					
							nextNode.setFather(current); 							
							((PriorityQueue<Node<Maze>>) this.frontier).add(nextNode);			
							nodesCounter++;
						}
						((PriorityQueue<Square>)this.closedSquares).add(currState); 				
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();
		
		long time = endTime - startTime;
		
		if(this.manhattan)
			this.result = "    ___                    __  ___            __          __  __            \r\n" + 
					"   /   | __/|_            /  |/  /___ _____  / /_  ____ _/ /_/ /_____ _____ \r\n" + 
					"  / /| ||    /  ______   / /|_/ / __ `/ __ \\/ __ \\/ __ `/ __/ __/ __ `/ __ \\\r\n" + 
					" / ___ /_ __|  /_____/  / /  / / /_/ / / / / / / / /_/ / /_/ /_/ /_/ / / / /\r\n" + 
					"/_/  |_||/             /_/  /_/\\__,_/_/ /_/_/ /_/\\__,_/\\__/\\__/\\__,_/_/ /_/ \n";
		else
			this.result = "    ___                    ______           ___     __\r\n" + 
					"   /   | __/|_            / ____/_  _______/ (_)___/ /\r\n" + 
					"  / /| ||    /  ______   / __/ / / / / ___/ / / __  / \r\n" + 
					" / ___ /_ __|  /_____/  / /___/ /_/ / /__/ / / /_/ /  \r\n" + 
					"/_/  |_||/             /_____/\\__,_/\\___/_/_/\\__,_/   \n";
		//You should add the result to the String variable "result" 
                //which is going to be printed to a text file
		if(endfound)
		{
			this.maze.resetGrid();
			Node<Maze> revertedTree = ((PriorityQueue<Node<Maze>>) this.frontier).remove();
			
			revertedTree = revertedTree.getFather();
			this.result += "Path: " + this.maze.getEnd().toString() + "(End) <- ";
			this.pathLength++;
			
			while(revertedTree.hasFather())
			{
				Maze temp = revertedTree.getContent();
				Square state = temp.getCurrState();
				
				if(!state.equals(this.maze.getEnd()))
				{
					this.result += state.toString() + " <- ";
					this.maze.getGrid()[state.getLine()][state.getCol()].setAttribute("*");
					this.pathLength++;
				}
				revertedTree = revertedTree.getFather();
			}
			
			this.result += this.maze.getStart().toString() + "(Start) \n" + "Path length: " + this.pathLength + "\nNumber of nodes created: " + this.nodesCounter + "\nExecution time: " + time/1000d + " seconds\n";
			this.result += this.maze.printMaze();
		}
		else
		{
			this.result += "Failed : Unable to go further and/or end is unreachable.";
		}
		
		return this.result;
	}
	
	/*
	 *  Get the next ("walkables") squares from the given square
	 *  c: Square from where to get the nexts squares
	 */
	public LinkedList<Node<Maze>> getNextSquares()
	{
		LinkedList<Node<Maze>> res = new LinkedList<Node<Maze>>();
		
		//Get 4 next squares
		LinkedList<Maze> nexts = this.maze.getCurrState().getNexts();
		
		int gCurrent = this.maze.getCurrState().getG();
		
		for(int i = 0; i < nexts.size(); i++)
		{
			Square tempSq = nexts.get(i).getCurrState();
			if(!this.closedSquares.contains(tempSq))
			{
				if(this.manhattan)
					nexts.get(i).getCurrState().calcManhattanH();
				else
					nexts.get(i).getCurrState().calcEuclidH();
				
				nexts.get(i).getCurrState().incG(gCurrent);
				
				nexts.get(i).getCurrState().calcF();
				
				Node<Maze> tempNode = new Node<Maze>(nexts.get(i));
				res.add(tempNode);
			}
		}
		
		return res;
	}

	public String getResult() 
	{
		if(this.result == "")
			return "No resolution computed, use the solve method first";
		else
			return this.result;
	}
	
	public AbstractCollection<Node<Maze>> getFrontier() 
	{
		return this.frontier;
	}
}
