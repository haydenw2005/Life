//Hayden White
//Section B (1st period)
//Assingment 7
//This is the simulation "Conway's Game of Life" 
//Life
//12/18/2020

import java.util.*;
import java.io.*;
import java.awt.*;

public class Life
{

   /**
   *Class constant that represents the size of each pixel
   */
   public static final int PIXEL_SIZE = 12;
   
   /**
   * The main methods purpose is to call methods and run the program, as well as collect user input
   *
   * @param args console input
   */
   public static void main(String[] args)
   {
      Scanner console = new Scanner(System.in);
      System.out.println("This program runs Conway's Game of Life");
      char[][] startArray = fileToArray(console);
      System.out.print("Output file name? ");
      String outputName = console.nextLine();
      int frames = MyUtils.getNumber(console, "Number of frames to run the simulation (0-5000): " , 0, 5000);
      int delay = MyUtils.getNumber(console, "Time between steps in ms (1-5000): " , 1, 5000);
      try
      {
         createPanel(startArray, frames, delay, console, outputName);
      }
      catch (Exception e) 
      {
         System.out.print("Error found in the input file. Halting simulation.");
      }
   }
   
   /**
   * This method reads the appropriate txt file and turns it into an array.
   *
   * @param - Scanner console- used to prompt the user for input.
   * @return - char[][] array- an array of the current cells.
   */ 
   public static char[][] fileToArray(Scanner console)
   {
      Scanner fileScanner = MyUtils.getInputScanner(console, "Input file name? ");
      String line = fileScanner.nextLine();
      Scanner lineScanner = new Scanner(line);
      int x = lineScanner.nextInt();
      int y = lineScanner.nextInt();
      char[][] array = new char[x][y];
      for (int i = 0; i < x; i++)
      {
         String token = fileScanner.next();
         for (int j = 0; j < y; j++)
         {
            array[i][j] = token.charAt(j);   
         }
      }
      return array;
   }
  
   /**
   * The purpose of this method is to loop through the array and call other methods, 
   * as well as loop through the while statment that corresponds to framerate.
   *
   * @param - char[][] array- the main array that represents the array made by fileToArray.
   * @param - int frames- the amount of frames that panel will make.
   * @param - int delay- the delay between frames.
   * @param - Scanner console- used to prompt the user for input.
   * @param - String outputName- the name of the output file.
   */
   public static void createPanel(char[][] array, int frames, int delay, Scanner console, String outputName)
   {
      int rowLength = array.length;
      int colLength = array[0].length;
      DrawingPanel panel = new DrawingPanel(PIXEL_SIZE * colLength + 1, PIXEL_SIZE * rowLength + 1);
      int counter = 0;
      newFrame(array, panel, colLength, rowLength);
      while (counter < frames - 1)
      {
         char[][] newArray = new char[rowLength][colLength];
         int currentCol = 0;
         int currentRow = 0;
         int total = 0;
         for (currentRow = 0; currentRow < rowLength; currentRow++)
         {
            for (currentCol = 0; currentCol < colLength; currentCol++)
            {
               total = getTotal(array, currentCol, currentRow);
               newArray[currentRow][currentCol] = cellStatus(array, currentCol, currentRow, total);
            }
         }
         panel.sleep(delay);
         array = newArray;
         newFrame(array, panel, currentCol, rowLength);
         counter++;
      }
      outputFile(console, array, outputName);
      System.out.println("Simulation successful!");
   }
   
   /**
   * This method reports how many live cells are around the current cell.
   *
   * @param - char[][] array- the array that represents the current status of all cells.
   * @param - int currentCol- the current column in which createPanel is reading.
   * @param - int currentRow- the current row in which createPanel is reading.
   * @return - grandTotal- the total amount of alive cells around the current cell. 
   */
   public static int getTotal(char[][] array, int currentCol, int currentRow) 
   {
      int grandTotal = 0;

      for (int xShift = -1; xShift < 2; xShift++)
      {
         for (int yShift = -1; yShift < 2; yShift++)
         {
            try
            {
               if (!(yShift == 0 && xShift == 0) && (array[currentRow + xShift][currentCol  + yShift] == 'x'))
               {
                  grandTotal++;
               }
            }
            catch (Exception e)
            {
            }
         }
      }
      return grandTotal;
   }

   /**
   * This method decides and updates whether a cell is dead or alive.
   *
   * @param - char[][] array- the array that represents the current status of all cells.
   * @param - int currentCol- the current column in which createPanel is reading.
   * @param - int currentRow- the current row in which createPanel is reading.
   * @param - grandTotal- the total amount of alive cells around the current cell.
   * @return - char cell- a cell, either x or .
   */
   public static char cellStatus(char[][] array, int currentCol, int currentRow, int grandTotal)
   {
      char cell = array[currentRow][currentCol];
      if (array[currentRow][currentCol] == 'x')
      {
         if (grandTotal < 2)
         {
            cell = '.';
         }
         else if (grandTotal == 2 || grandTotal == 3)
         {
           cell = 'x';
         }
         else if (grandTotal > 3)
         {
            cell = '.';
         }
      }
      else if (array[currentRow][currentCol] == '.')
      {
         if (grandTotal == 3)
         {
            cell = 'x';
         }
      }
      return cell;
   }
   
   /**
   * This method draws a new frame onto the panel.
   *
   * @param - char[][] array- the array that represents the current status of all cells.
   * @param - DrawingPanel panel- the DrawingPanel variable needed to create/update a window.
   * @param - int col- the column length of the array.
   * @param - int row- the row length of the array.
   */
   public static void newFrame(char[][] array, DrawingPanel panel, int col, int row)
   {
      Graphics g = panel.getGraphics();
      for (int currentRow = 0; currentRow < row; currentRow++)
      {
         for (int currentCol = 0; currentCol < col; currentCol++)
         {
            if (array[currentRow][currentCol] == 'x')
            {
               g.setColor(Color.BLACK);
               g.fillRect(PIXEL_SIZE * currentCol, PIXEL_SIZE * currentRow, PIXEL_SIZE, PIXEL_SIZE);
            }
            else if (array[currentRow][currentCol] == '.')
            {
               g.setColor(Color.WHITE);
               g.fillRect(PIXEL_SIZE * currentCol, PIXEL_SIZE * currentRow, PIXEL_SIZE, PIXEL_SIZE);
            }
         }
      }
   }
 
   /**
   * This method inputs the file name and outputs the final array on vto a txt file.
   *
   * @param - Scanner console- used to prompt the user for input.
   * @param - char[][] array- the array that represents the current status of all cells.
   * @param - String outputName- the name of the output file.
   */
   public static void outputFile(Scanner console, char[][] array, String outputName)
   { 
      PrintStream ps = MyUtils.getOutputStream(console, outputName);
      String changedArray = Arrays.deepToString(array);
      changedArray = changedArray.replace(",", "");
      changedArray = changedArray.replace("[", "");
      changedArray = changedArray.replace("]", " ");
      changedArray = changedArray.replace("  ", "\n");
      changedArray = changedArray.replace(" ", "");
      changedArray = changedArray.trim();
      ps.print(changedArray);
   }
}

//I apologize for any spelling errors in my javadocs, thanks