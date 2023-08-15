// Name: J2-07-22
// Date: 10/17/22

import java.util.*;
import java.io.*;

public class MazeGrandMaster
{
   public static void main(String[] args)
   {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter the maze's filename (no .txt): ");
      Maze m = new Maze(sc.next()+".txt");  //append the .txt 
      m.display();      
      
      System.out.println("Options: ");
      System.out.println("1: Length of the shortest path\n\tIf no path exists, say so.");
      System.out.println("2: Length of the shortest path\n\tList the shortest path\n\tDisplay the shortest path\n\tIf no path exists, say so.");
      System.out.print("Please make a selection: ");
      
      m.solve(sc.nextInt());
   } 
}

class Maze
{
   //constants
   private final char WALL = 'W';
   private final char DOT = '.';
   private final char START = 'S';
   private final char EXIT = 'E';
   private final char TEMP = 'o';
   private final char PATH = '*';
   
   //instance fields
   private char[][] maze;
   private int startRow, startCol;
  
	
  /** 
	 * Constructor.
    * Creates a "deep copy" of the array.
    * We use this in Codepost. 
	 */
   public Maze(char[][] m)  
   {
      maze = m;
      for(int r = 0; r < maze.length; r++)
      {
         for(int c = 0; c < maze[0].length; c++)
         { 
            if(maze[r][c] == START)      //identify start
            {
               startRow = r;
               startCol = c;
            }
         }
      }
   } 
	
  /** 
	 * Write this one-arg constructor.
    * Use a try-catch block.
	 * Use next(), not nextLine() 
    * Search the maze to find the location of 'S' 
	 */
   public Maze(String filename)    
   {
      Scanner infile = null;
      try{
         infile = new Scanner(new File(filename));
      }
      catch(FileNotFoundException e){
         System.out.println("File not found");
         System.exit(0);
      }
      
      //read the file
      maze = new char[infile.nextInt()][infile.nextInt()]; // gets the size of the array based on the ints at the top of every file
      String rowString = null;
      char[] rowCharArray = null;
      int width = maze[0].length; // how many columns there are(the width of the matrix)
      
      for (int rows = 0; rows < maze.length; rows++){ // reads thru every row
         rowString = infile.next(); // puts the row into a string
         rowCharArray = rowString.toCharArray(); // splits the string into chars(into a char array)
         for (int columns = 0; columns < width; columns++){
            maze[rows][columns] = rowCharArray[columns];
            if (maze[rows][columns] == 'S'){
               startRow = rows;
               startCol = columns;
            }
         }
      }
   }
   
   public char[][] getMaze()
   {
      return maze;
   }
   
   public void display()
   {
      if(maze==null) 
         return;
      for(int a = 0; a<maze.length; a++)
      {
         for(int b = 0; b<maze[0].length; b++)
         {
            System.out.print(maze[a][b]);
         }
         System.out.println();
      }
      System.out.println();
   }
   
   public void solve(int n)
   {
      switch(n)
      {    
         case 1:  
            int shortestPath = findShortestLengthPath(startRow, startCol);
            if( shortestPath < 999 )
               System.out.println("Shortest path is " + shortestPath);
            else
               System.out.println("No path exists."); 
            display();
            break;
            
         case 2:
            String strShortestPath = findShortestPath(startRow, startCol);
            if( strShortestPath.length()!=0 )
            {
               System.out.println("Shortest length path is: " + getPathLength(strShortestPath));
               System.out.println("Shortest path is: " + strShortestPath);
               markPath(strShortestPath);
               display();  //display solved maze
            }
            else
               System.out.println("No path exists."); 
            break;
         default:
            System.out.println("File not found");   
      }
   }
   
   
 /*  MazeGrandMaster 1   
     recur until you find E, then return the shortest path
     returns 999 if it fails
     precondition: Start can't match with Exit
 */ 
   public int findShortestLengthPath(int r, int c)
   {
      int up;
      int down;
      int left;
      int right;
            
      if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length || maze[r][c] == WALL || maze[r][c] == TEMP)
         return 999;
      
      if (maze[r][c] == EXIT)
         return 0;
         
      if (maze[r][c] == DOT)
         maze[r][c] = TEMP;
      up = findShortestLengthPath(r - 1, c);
      down = findShortestLengthPath(r + 1, c);
      left = findShortestLengthPath(r, c - 1);
      right = findShortestLengthPath(r, c + 1);
      
      if (maze[r][c] == TEMP)
         maze[r][c] = DOT;
      
      return 1 + Math.min(up, Math.min(down, Math.min(left, right)));
   }  
   
/*  MazeGrandMaster 2   
    recur until you find E, then build the path with (r,c) locations
    and the number of steps, e.g. ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
    ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)

    as you build, choose the shortest path at each step
    returns empty String if there is no path
    precondition: Start can't match with Exit
 */
   public String findShortestPath(int r, int c)
   {
      int up;
      int down;
      int left;
      int right;
      String locations = "";
            
      if (r < 0 || c < 0 || r >= maze.length || c >= maze[0].length || maze[r][c] == WALL || maze[r][c] == TEMP)
         return "";
      
      if (maze[r][c] == EXIT)
         return "";
         
      if (maze[r][c] == DOT)
         maze[r][c] = TEMP;
      up = findShortestLengthPath(r - 1, c);
      down = findShortestLengthPath(r + 1, c);
      left = findShortestLengthPath(r, c - 1);
      right = findShortestLengthPath(r, c + 1);
      
      int count = 1 + Math.min(up, Math.min(down, Math.min(left, right)));
      
      for(int a = 0; a < maze.length; a++){
         for(int b = 0; b < maze[0].length; b++){
            if (maze[a][b] == TEMP){
               locations += "((" + a + "," + b + ")," + count + "),";
               count -= 1;
               maze[a][b] = DOT;
            }
         }
      }
      
      if (locations.length() > 1)
         return locations.substring(0, locations.length() - 1);
      else
         return locations.substring(0, locations.length());
   }	

	/** MazeGrandMaster 2 
       returns the length, i.e., third number when the format of the strPath is 
            "((3,4),10),((3,5),9),..."
       returns 999 if the string is empty
       precondition: strPath is either empty or follows the format above
    */
   public int getPathLength(String strPath)
   {
      System.out.println(String.valueOf(strPath.substring(6,8)));
      return Integer.parseInt(String.valueOf(strPath.substring(6,8)));
   } 

  /** MazeGrandMaster 2 
      recursive method that takes a String created by findShortestPath(r, c)     
      in the form of ((5,0),10),((5,1),9),((6,1),8),((6,2),7),
              ((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),
              ((4,7),0) and marks the actual path in the maze 
      precondition: the String is either an empty String or one that    
                    has the format shown above
                    the (r,c) must be correct for this method to work 
   */
   public void markPath(String strPath)
   {
      if(strPath.equals(""))
         return;
      /*  enter your code  below  */   
      
      int r = 0;
      int c = 0;
      int length = Integer.parseInt(String.valueOf(strPath.substring(6,8)));
      
      strPath = strPath.substring(2, strPath.length());
      
      while (strPath.length() >= 3){
         System.out.println(strPath.charAt(0));
         r = Integer.parseInt(String.valueOf(strPath.charAt(0)));
         System.out.println(strPath.charAt(2));
         c = Integer.parseInt(String.valueOf(strPath.charAt(2)));
         System.out.println(strPath.charAt(11));
         strPath = strPath.substring(11, strPath.length());
         maze[r][c] = PATH;
      }
   }
}


   /*************************************************************
 ----jGRASP exec: java MazeGrandMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 Options: 
 1: Length of the shortest path
 	If no path exists, say so.
 2: Length of the shortest path
 	List the shortest path
 	Display the shortest path
 	If no path exists, say so.
 Please make a selection: 1
 Shortest path is 10
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.

 ******************************************************************/
 /**************************************************************
      ----jGRASP exec: java MazeGrandMaster_teacher
 Enter the maze's filename (no .txt): maze1
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW.E
 S.W.WW.W
 W......W
 WWWWWWWW
 
 Options: 
 1: Length of the shortest path
 	If no path exists, say so.
 2: Length of the shortest path
 	List the shortest path
 	Display the shortest path
 	If no path exists, say so.
 Please make a selection: 2
 Shortest length path is: 10
 Shortest path is: ((5,0),10),((5,1),9),((6,1),8),((6,2),7),((6,3),6),((6,4),5),((6,5),4),((6,6),3),((5,6),2),((4,6),1),((4,7),0)
 WWWWWWWW
 W....W.W
 WW.W...W
 W....W.W
 W.W.WW*E
 S*W.WW*W
 W******W
 WWWWWWWW
 
 
  ----jGRASP: operation complete.
  
  ******************************************/