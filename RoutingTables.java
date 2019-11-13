//-----------------------------------------------------------
// Paige Weber, L22812475 Networking Principles
//-----------------------------------------------------------
// This program assumes that data is being read from a file
// called "data.txt" and that the data is in the following
// format: X, X 10 with the two nodes and their distance
// between each other respectively. The data must contain
// 10 or less nodes. This program runs Dijkstra's 
// algorithm on the data and returns the routing table
// for the node of interest.
//-----------------------------------------------------------

import java.io.*;
import java.util.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class RoutingTables
{
 	static int min;
 	static int [] visited;
 	static int[][] adjacencyMatrix;
 	
    	public static int shortestPath(int node)
		{	
			min = 1000000000;
			int temp = min;
				for(int k = 0; k < 10; k++) // go through row
				{
					if(min > adjacencyMatrix[node][k] && adjacencyMatrix[node][k] > 0)
					{
						min = adjacencyMatrix[node][k]; // make the min new
						temp = k;
					}
				}
			if(min == 1000000000) // if the row is empty, meaning it isnt in the graph
			{
				System.out.println("    " + (char)(node + 65) + " | -");
			}
			else
			{	
				System.out.println("    " + (char)(node + 65) + " | " + (char)(temp + 65));
			}
			return temp;
			
		}
 	
 	
    public static void main(String[] args) throws FileNotFoundException
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the node of interest: ");
        char OGnode = sc.next().toLowerCase().charAt(0);
        
        File file = new File("data.txt"); 

        Scanner filesc = new Scanner(file);
        
        String line;
        int node1;
        int node2;
        int val;
        adjacencyMatrix = new int[10][10];
        int[] unvisited = new int[10];
        
        for(int i = 0; i < 10; i++) // initialize all distances to 0
        { 
        	unvisited[i] = i;
        	for(int j = 0; j < 10; j++)
        	{
        		adjacencyMatrix[i][j] = 0;
        	}
        
        }
        
      
      	// get input and initialize the adjacency matrix
        while(filesc.hasNextLine())
        {
            line = filesc.nextLine().toUpperCase();
            char[] temp = line.toCharArray();
            
            node1 = (int)temp[0] - 65; // make ASCII values, subtract 97 to get index in array
            node2 = (int)temp[2] - 65;
        
            val = Character.getNumericValue(temp[4]);
        	
        	adjacencyMatrix[node1][node2] = val; // change array value from 0 to distance
        	adjacencyMatrix[node2][node1] = val;
           	
        }
        
		
		boolean bool = true;
		int unvisitedIndex = 0;
		int min = 1000000000;
		int interestNode = OGnode - 97;
		int counter = 0;
		int lastInterest;
		
		System.out.println("-------------");
		System.out.println("Routing Table");
		System.out.println("-------------");
		
		// check to make sure there are nodes left to be visited
		while(counter < 10)
		{
				// remove from unvisited
				unvisited[interestNode] = -1; 
				counter++;
				
				lastInterest = interestNode;
				interestNode = shortestPath(interestNode);
				
				int counter2 = 0;
				
				while(counter2 < 10)
				{
					if(interestNode == 1000000000) // if your interestNode is not attached 
												   // go to last interest node
					{
						interestNode = lastInterest;
					}
					// if the node has been visited, go to next node
					if(unvisited[interestNode] == -1)
					{
						if(interestNode == 9)
						{
							// wrap to 0
							lastInterest = interestNode;
							interestNode = 0;
						}
						else
						{
							lastInterest = interestNode;
							interestNode++;
						}
					}
					// if it hasnt been visited break
					else
					{
						break;
						
					}
					counter2++;
					
				}
				
		} 
		
    }   

}

