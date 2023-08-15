// Name: J2-07-22
// Date: 10/03/22

import javax.swing.JFrame;

public class NQueensDriver
{
   public static void main(String[] args)
   {
      JFrame frame = new JFrame("N-Queens");
      frame.setSize(400, 400);
      frame.setLocation(200, 100);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      int boardSize = 8;
      
      NQueens nqueens = new NQueens(boardSize);
      frame.setContentPane(nqueens);
      frame.setVisible(true);
                  
      if(nqueens.solve())
         System.out.println("solved");
      else
         System.out.println("Solution not found");
   }
}