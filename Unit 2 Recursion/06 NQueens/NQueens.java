// Name: J2-07-22
// Date: 10/03/22

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Scanner;

public class NQueens extends JPanel
{
   // Instance Variables: Encapsulated data for EACH NQueens problem
   private JButton[][] board;
   private int N;
   JSlider speedSlider;
   private int timerDelay;
   Scanner size = new Scanner(System.in);

   public NQueens(int n)
   {
      System.out.print("Enter Side Length of the Chess Board: ");
      int boardSize = Integer.parseInt(size.nextLine());
      
      N = boardSize;
      this.setLayout(new BorderLayout());
   
      JPanel north = new JPanel();
      north.setLayout(new FlowLayout());
      add(north, BorderLayout.NORTH);
      JLabel label = new JLabel( N + "Queens solution");
      north.add(label);
      
      JPanel center = new JPanel();
      center.setLayout(new GridLayout(N,N));
      add(center, BorderLayout.CENTER);
      board = new JButton[N][N];
      for(int r = 0; r < N; r++)
         for(int c = 0; c < N; c++)
         {
            board[r][c] = new JButton();
            board[r][c].setBackground(Color.blue);
            center.add(board[r][c]);
         }
   
      speedSlider = new JSlider();   
      speedSlider.setInverted(true);   
      add(speedSlider, BorderLayout.SOUTH);   
   }

   /** Returns the number of queens to be placed on the board. **/
   public int numQueens()
   {
      return N;   
   }

    /** Solves (or attempts to solve) the N Queens Problem. **/
   public boolean solve()
   {
      return isPlaced(0, 0);
   }

 /**
  * Iteratively try to place the queen in each column.
  * Recursively try the next row.
  **/       
   private boolean isPlaced(int row, int col)
   {
      if (row >= N) //matrix is filled
         return true;
         
      /*  enter code here  */
      /* EXTENSION:
      for (int i = 0; i < N; i++){
         for (int j = 0; j < N; j++){
            if (locationIsOK(j, i)){
               addQueen(j, i);
               if (isPlaced(row + 1, col) == true){
                  return true;
               }
               removeQueen(j, i);
            }
         }
      }
      */
      for (int i = 0; i < N; i++){
         if (locationIsOK(row, i)){
            addQueen(row, i);
            if (isPlaced(row + 1, col) == true){
               return true;
            }
            removeQueen(row, i);
         }
      }
      
      return false;
   } 
   
  /** Verify that another queen can't attack this location.
    * You only need to check the locations above this row.
    * Iteration is fine here.
    **/
   private boolean locationIsOK(int r, int c)
   {
      /*  enter code here  */
      for (int i = 0; i < r; i++) // checking the column
         if (board[i][c].getBackground().equals(Color.RED))
            return false;
      
      for (int i = 0; i < N; i++){ // checking left diagonal
         for (int j = 0; j < N; j++){
            if (board[i][j].getBackground().equals(Color.RED)){
               if ((i - j) == (r - c))
                  return false;
            }
         }
      }
      
      for (int i = 0; i < N; i++){ // checking right diagonal
         for (int j = 0; j < N; j++){
            if (board[i][j].getBackground().equals(Color.RED)){
               if ((i + j) == (r + c))
                  return false;
            }
         }
      }
      
      return true;
   }

    /** Adds a queen to the specified location.
       **/
   private void addQueen(int r, int c)
   {
      board[r][c].setBackground(Color.RED);
      pause();
   }

    /** Removes a queen from the specified location.
     **/
   private void removeQueen(int r, int c)
   {
      board[r][c].setBackground(Color.BLUE);
      pause();
   }
   private void pause()
   {
      int timerDelay = speedSlider.getValue(); 
      for(int i=1; i<=timerDelay*1E7; i++)  {}
   }
}