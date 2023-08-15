//Name: J2-07-23
//Date: 05/16/2023

/* Resource classes and interfaces
 * for use with Graph0 AdjMat_0_Driver,
 *              Graph1 WarshallDriver,
 *          and Graph2 FloydDriver
 */

import java.util.*;
import java.io.*;

interface AdjacencyMatrix
{
   public int[][] getGrid();
   public int[][] readGrid(String fileName);
   public boolean isNeighbor(int from, int to);
   public int countEdges();
   public List<Integer> getNeighbors(int source);
   public String showAllNeighbors();
   public String toString();  //returns the grid as a String
}

interface WithNames
{
   public void readNames(String fileName);
   public Map<String, Integer> getNamesAndNumbers();
   public String toStringNamesAndNumbers();  // each line contains number-name, ex: 0-Pendleton
   public boolean isNeighbor(String from, String to);
}
  
interface Warshall
{    
   public int countReachables();
   public boolean isReachable(String from, String to);  
   public List<String> getReachables(String from);
   public String toStringReachability(); //displays the reachability matrix with 2 spaces in front of each value
   public void allPairsReachability();   // Warshall's Algorithm. fills the reachability matrix                                  
}

interface Floyd
{
   public int getCost(int from, int to);
   public int getCost(String from, String to);
   public void allPairsWeighted();  //Floyd's Algorithm
}

/***********************  the graph  ******************/
public class AdjMat implements AdjacencyMatrix, WithNames, Warshall, Floyd
{
   private int[][] grid;   //adjacency matrix representation
   private int[][] reachability = null; //reachability matrix for Warshall, cost matrix for Floyd
   private Map<String, Integer> namesAndNumbers = null;    // maps name to number
   private  ArrayList<String> nameList = null;  //reverses the map, index-->name
   private static final int maxCost = 9999;
 
 /*  write constructors, accessor methods, and instance methods   */  
   public AdjMat(String filename) {
      grid = readGrid(filename);
   }
   
   public AdjMat(int size){
      grid = new int[size][size];
      allPairsReachability();
   }
   
   public AdjMat(String fileMatrix, String fileNames) 
   {
      grid = readGrid(fileMatrix);
      reachability = grid;
      namesAndNumbers = new TreeMap<String, Integer>();
      nameList = new ArrayList<String>(); //Warshall Extension
      readNames(fileNames);
   }
   
   public int[][] getGrid(){
      return grid;
   }
   
   public int[][] readGrid(String fileName){
      try{
         Scanner scanner = new Scanner(new File(fileName));
         int size = scanner.nextInt();
         grid = new int[size][size];
         for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
               grid[i][j] = scanner.nextInt();
         scanner.close();
      } 
      catch(FileNotFoundException e){
         e.printStackTrace(System.out);
      }
      return grid;
   }
   
   public boolean isNeighbor(int from, int to){
      return grid[from][to] == 1;
   }
   
   public int countEdges(){
      int count = 0;
      for (int i = 0; i < grid.length; i++)
         for (int j = 0; j < grid[i].length; j++)
            count += grid[i][j];
      return count;
   }
   
   public List<Integer> getNeighbors(int source){
      List<Integer> neighbors = new ArrayList<Integer>();
      for(int i = 0; i < grid.length; i++)
         if(grid[source][i] == 1)
            neighbors.add(i);
      return neighbors;
   }
   
   public String showAllNeighbors(){
      String s = "";
      for(int i = 0; i < grid.length; i++){
         s += i + ": ";
         List<Integer> list = getNeighbors(i);
         for(int n : list){
            if(n == list.get(0))
               s += "[";
            s += n;
            if(n != list.get(list.size() - 1))
               s += ", ";
         }
         s += "]\n";
      }
      return s;
   }
   
   public String toString(){  //returns the grid as a String
      String s = "";
      for(int i = 0; i < grid.length; i++){
         for(int j = 0; j < grid[0].length; j++)
            s += grid[i][j] + " ";
         s += "\n";
      }
      return s;
   }
   
   /**************  implement the WithNames interface **************/
   
   public void readNames(String fileName){
      try{
         Scanner scanner = new Scanner(new File(fileName));
         namesAndNumbers = new TreeMap<String, Integer>();
         nameList = new ArrayList<String>();
         int count = -1;
         while(scanner.hasNextLine()) {
            if(count == -1){
               String trash = scanner.nextLine();
               count++;
            }
            String line = scanner.nextLine().trim();
            if(!line.isEmpty()) {
               namesAndNumbers.put(line, count);
               nameList.add(line);
               count++;
            }
         }
         scanner.close();
      }
      catch (FileNotFoundException e) {
         e.printStackTrace(System.out);
      }
   }  
   
   public Map<String, Integer> getNamesAndNumbers(){
      return namesAndNumbers;
   }
   
   public String toStringNamesAndNumbers(){  // each line contains number-name, ex: 0-Pendleton
      String s = "";
      for(Map.Entry<String, Integer> entry : namesAndNumbers.entrySet())
         s += entry.getValue() + "-" + entry.getKey() + "\n";  
      return s;
   }
   
   public boolean isNeighbor(String from, String to){
      if(namesAndNumbers.containsKey(from) && namesAndNumbers.containsKey(to))
         return grid[namesAndNumbers.get(from)][namesAndNumbers.get(to)] == 1;
      return false;
   }

   /************  implement the Warshall interface  ************/
   public int countReachables(){
      int count = 0;
      for(int i = 0; i < reachability.length; i++)
         for(int j = 0; j < reachability[i].length; j++)
            if(reachability[i][j] == 1)
               count++;
      return count;
   }
   
   public boolean isReachable(String from, String to){
      if(namesAndNumbers.containsKey(from) && namesAndNumbers.containsKey(to))
         return reachability[namesAndNumbers.get(from)][namesAndNumbers.get(to)] == 1;
      return false;
   } 
   
   public List<String> getReachables(String from){
      List<String> reachables = new ArrayList<String>();
      if(namesAndNumbers.containsKey(from))
         for(int i = 0; i < reachability[namesAndNumbers.get(from)].length; i++)
            if(reachability[namesAndNumbers.get(from)][i] == 1)
               reachables.add(nameList.get(i));
      return reachables;
   }
   
   public String toStringReachability(){ //displays the reachability matrix with 2 spaces in front of each value
      String s = "";
      for(int i = 0; i < reachability.length; i++){
         for(int j = 0; j < reachability[i].length; j++)
            s += reachability[i][j] + "  ";
         s += "\n";
      }
      return s.trim();
   }
   
   public void allPairsReachability(){   // Warshall's Algorithm. fills the reachability matrix
      reachability = new int[grid.length][grid.length];
      
      for(int i = 0; i < grid.length; i++) // copy the values oveer | deep copy
         for(int j = 0; j < grid.length; j++)
            reachability[i][j] = grid[i][j];
      
      for(int v = 0; v < grid.length; v++) // calculating the reachability
         for(int r = 0; r < grid.length; r++)
            for(int c = 0; c < grid.length; c++){
               boolean rv = reachability[r][v] == 1;
               boolean vc = reachability[v][c] == 1;
               if(rv && vc)
                  reachability[r][c] = 1;
            }
   }
   
   /************  implement the Floyd interface  ************/
   public int getCost(int from, int to){
      return reachability[from][to];
   }
   
   public int getCost(String from, String to){
      if(namesAndNumbers.containsKey(from) && namesAndNumbers.containsKey(to))
         return reachability[namesAndNumbers.get(from)][namesAndNumbers.get(to)];
      return maxCost;
   }
   
   public void allPairsWeighted(){  //Floyd's Algorithm
      int size = grid.length;
      reachability = new int[size][size];
      for(int i = 0; i < size; i++)
         for(int j = 0; j < size; j++)
            reachability[i][j] = grid[i][j];
      
      for(int k = 0; k < size; k++)
         for(int i = 0; i < size; i++)
            for(int j = 0; j < size; j++)
               if(reachability[i][k] != maxCost && reachability[k][j] != maxCost && reachability[i][j] > reachability[i][k] + reachability[k][j])
                  reachability[i][j] = reachability[i][k] + reachability[k][j];
      for (int i = 0; i < size; i++)
         reachability[i][i] = 0;
   }
}