//This is a file that contains many useful methods that I use in a multitude of projects.
import java.util.*;
import java.io.*;


public class MyUtils
{
   /**
   * This main method is for testing purposes only.
   * Consider using it to make sure your getNumber method works correctly.
   * You can remove main or leave it when you turn in this file with Assignment 5 - either is fine
   *
   * @param args console input
   */
   public static void main(String[] args)
   {
   }
   
   /**
   * This method prompts the user for input until the input is a number between 1 and the max, and it is an integer.
   *
   * @param - Scanner console- used to prompt the user for input.
   * @param - String prompt- The question the program will ask before the user inputs an answer.
   * @param - int min- the minimum number that can be inputed by the user.
   * @param - int max- the maximum number that can be inputed by the user.
   * @return - returns user input.
   */
   public static int getNumber(Scanner console, String prompt, int min, int max)
   {
      while (true)
      {
         System.out.print(prompt);
         try
         {  
            int input = console.nextInt();
            console.nextLine();
            if (input < min || input > max)
            {
               System.out.println("Your number needs to be between " + min + " and " + max + ".");
            }
            
            else
            {
               return input;
            }
         }
         catch (Exception e)
         {
            System.out.println("You must enter an *integer* between " + min + " and " + max +".");
            console.nextLine();
         }
       }
   }
   
   /**
   * This method prompts the user for an input file.
   *
   * @param - Scanner console- used to prompt the user for input.
   * @param - String prompt- The question the program will ask before the user inputs an answer.
   * @return - returns fileScanner to the program
   */
   public static Scanner getInputScanner(Scanner console, String prompt)
   {
      String fileName = "";
      System.out.print(prompt);
      while (true)
      {
         fileName = console.nextLine();
         try
         {
            Scanner fileScanner = new Scanner(new File(fileName));
            return fileScanner;
         }
         catch (Exception e)
         {
            System.out.print("File not found. Try again: ");
         }
      }
   }
   
   /**
   * This method prompts the user for an output file and creates a PrintStream.
   *
   * @param - Scanner console- used to prompt the user for input.
   * @return - returns ps to the program
   */
   public static PrintStream getOutputStream(Scanner console, String outputName)
   {
      try
      {
         PrintStream ps = new PrintStream(new File(outputName));
         return ps;
      }
      catch (Exception e)
      {
         System.out.println("Error");
         return null;
      }
      
   }
}
   
   
   
   
   
   
   
  