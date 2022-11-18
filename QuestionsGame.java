// Yiran Li
// TA: Connor Aksama
// This QuestionsGame program is playing games of 20 questions. User can think of
// an object, and the computer can guess what is the object. 

import java.util.*;
import java.io.*;

public class QuestionsGame {
   private QuestionNode overallRoot;
   private Scanner console;
   
   // initializes a new QuestionsGame object with a single leaf node representing
   // the object "computer"
   public QuestionsGame(){
      console = new Scanner(System.in);
      overallRoot = new QuestionNode("computer");
   }
   
   // uses the tree from a file to replace the current tree
   // @param input - uses Scanner input to read the file
   public void read(Scanner input){
      overallRoot = readHelper(input);
   }
   
   // helper method of read method that uses the tree from a file to replace the current tree
   // @param input - uses Scanner input to read the file
   // @return - returns the root where current tree is rooted
   private QuestionNode readHelper(Scanner input) {
      String question = input.nextLine();
      String answer = input.nextLine();
      QuestionNode root = new QuestionNode(answer);
      
      if (question.equals("Q:")){
         root.yes = readHelper(input);
         root.no = readHelper(input);
      }
      return root;
   }
   
   // stores the current tree to an output file
   // @param output - use PrintStream to output the tree
   public void write(PrintStream output){
      write(overallRoot, output);
   }
   
   // helper method of write method that stores the current tree to an output file
   // @param root - the root where the question tree that is going to be stored to the file is rooted
   // @param output - use PrintStream to output the tree
   private void write(QuestionNode root, PrintStream output){
      if (root.yes != null || root.no != null){
         output.println("Q:" + root.data);
         write(root.yes, output);
         write(root.no, output);
      } else {
         output.println("A:" + root.data);
      }
   }
   
   // uses the current tree to ask the user a series of yes/no questions until 
   // the game is finished.
   public void askQuestions() {
      overallRoot = askQuestions(overallRoot);
   }
   
   // helper method of the askQuestions method that uses the current tree to ask the user a series 
   // of yes/no questions until the game is finished.
   // @param root - the root where the current question tree to play is rooted
   // @return - returns QuestionNode root
   private QuestionNode askQuestions(QuestionNode root) {
      if (root.yes != null || root.no != null) {
         if (yesTo(root.data)){
            root.yes = askQuestions(root.yes);
         } else {
            root.no = askQuestions(root.no);
         }
      } else {
         if (yesTo("Would your object happen to be " + root.data)){
            System.out.println("Great, I got it right!");
         } else {
            System.out.print("What is the name of your object? ");
            String ans = console.nextLine();
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.print("and mine--> ");
            String question = console.nextLine();
            if (yesTo("And what is the answer for your object?")){
               root = new QuestionNode(question, new QuestionNode(ans), root);
            } else {
               root = new QuestionNode(question, root, new QuestionNode(ans));
            }
         }
      }
      return root;
   }
   
   // post: asks the user a question, forcing an answer of "y" or "n";
   //       returns true if the answer was yes, returns false otherwise
   private boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
   
   // this class represents the single node in the tree
   private static class QuestionNode{
      public String data;
      public QuestionNode yes;
      public QuestionNode no;
      
      // constructs a leaf node with the given data
      // @param data - the given data of the leaf node
      public QuestionNode(String data){
         this(data, null, null);
      }
      
      // constructs a branch node with the given data and links
      // @param data - the given data of the branch node
      // @param yes - the left subtree of the node
      // @param no - the right subtree of the node
      public QuestionNode(String data, QuestionNode yes, QuestionNode no){
         this.data = data;
         this.yes = yes;
         this.no = no;
      }
   }
}
