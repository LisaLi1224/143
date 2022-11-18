// Yiran Li
// TA: Connor Aksama
// This class is used to store a HuffmanCode that can compress and decompress
// data and keep track of a binary tree constructed by using the huffman algorithm.

import java.util.*;
import java.io.*;

public class HuffmanCode {
   private HuffmanNode overallRoot;
   private Queue<HuffmanNode> freqOrder;
    
   // Initializes a new HuffmanCode object using the algorithm from an array of frequencies
   // @param frequencies - array of frequencies where frequencies[i] is count
   // of the character with ASCII value i
   public HuffmanCode(int[] frequencies) {
      freqOrder = new PriorityQueue<HuffmanNode>();
      
      for (int i = 0; i < frequencies.length; i++) {
         if(frequencies[i] > 0) {
            freqOrder.add(new HuffmanNode(frequencies[i], i));
         }
      }
      
      while (freqOrder.size() != 1) {
         HuffmanNode first = freqOrder.remove();
         HuffmanNode second = freqOrder.remove();
         HuffmanNode current = new HuffmanNode(first.frequency + second.frequency,
                                               -1, first, second);
         freqOrder.add(current);
      }
         this.overallRoot = freqOrder.remove();
   }
   
   // Initializes a new HuffmanCode object by reading a previously constructed
   // code from a ".code" file
   // @param input - used to read file
   public HuffmanCode(Scanner input) {
      this.overallRoot = null;
      while (input.hasNextLine()) {
         int asciiValue = Integer.parseInt(input.nextLine());
         String code = input.nextLine();
         int index = 0;
         if (index == code.length()) {
            overallRoot = new HuffmanNode(0, asciiValue);
         } else {
            if (overallRoot == null) {
               overallRoot = new HuffmanNode(0, -1);
            }
            if (code.charAt(index) == '0') {
               overallRoot.left = generateTree(overallRoot.left, code, index + 1, asciiValue);
            } else {
               overallRoot.right = generateTree(overallRoot.right, code, index + 1, asciiValue);
            }
         }
      }
   }
   
   // this is a helper method of the above method, used to Initializes a new HuffmanCode 
   // object by reading a previously constructed code from a ".code" file
   // @param root - the HuffmanNode
   // @param code - the code representing letter
   // @param index - the index of the code
   // @param value - the ASCII value of the letter
   // @return - return HuffmanNode
   private HuffmanNode generateTree(HuffmanNode root, String code, int index, int value) {
      if (index == code.length()) {
         root = new HuffmanNode(0, value);
      } else {
         if (root == null) {
            root = new HuffmanNode(0, -1);
         }
         if (code.charAt(index) == '0') {
            root.left = generateTree(root.left, code, index + 1, value);
         } else {
            root.right = generateTree(root.right, code, index + 1, value);
         }
      }
      return root;
   }
   
   // Stores the current huffman codes to the output stream in specific format
   // @param output - used to output the huffman codes
   public void save(PrintStream output) {
      if (overallRoot != null){
         save(output, overallRoot, "");
      }
   }
   
   // this is a helper method of the above method which is used to store the current 
   // huffman codes to the output stream in specific format
   // @param output - used to output the huffman codes
   // @param root - the HuffmanNode
   // @param code - the code representing letter
   private void save(PrintStream output, HuffmanNode root, String code) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.println(root.character);
            output.println(code);
         } else {
            save(output, root.left, code + "0");
            save(output, root.right, code + "1");
         }
      }
   }
   
   // translates the compressed back into the original contents.
   // @param input - used to read the file
   // @param output - used to write the output
   public void translate(BitInputStream input, PrintStream output) {
      while (input.hasNextBit()) {
         translate(overallRoot, input, output);
      }
   } 
   
   // this is the helper method of the above method, which is used to translates the 
   // compressed back into the original contents.
   // @param root - the HuffmanNode
   // @param input - used to read the file
   // @param output - used to write the output
   private void translate(HuffmanNode root, BitInputStream input, PrintStream output) {
      if (root != null) {
         if (root.left == null && root.right == null) {
            output.write(root.character);
         } else if (input.nextBit() == 0) {
            translate(root.left, input, output);
         } else {
            translate(root.right, input, output);
         }
      }
   }
   
   // this class represents the single HuffmanNode in the tree
   private static	class	HuffmanNode	implements Comparable<HuffmanNode> {
   	public HuffmanNode left;
   	public HuffmanNode right;
   	public int frequency;
   	public int character;
   	
      // Constructs a new HuffmanNode with given frequency and given ASCII value
      // @param f - the given frequency 
      // @param c - the given ASCII value
   	public HuffmanNode(int f, int	c)	{
   		this.frequency	= f;
   		this.character	= c;
   		this.left =	null;
   		this.right = null;
   	}
	   
      // Constructs a new HuffmanNode with given frequency, given ASCII value, and two HuffmanNodes
      // @param f - the given frequency 
      // @param c - the given ASCII value
      // @param left - the left HuffmanNode
      // @param right - the right HuffmanNode
      public HuffmanNode(int f, int c, HuffmanNode left, HuffmanNode right) {
         this.frequency = f;
         this.character = c;
         this.left = left;
         this.right = right;
      }
      
      // compares two HuffmanNode using their frequency
      // @param node - the second HuffmanNode
      // @return - returns an int representing the relationship between two HuffmanNode
   	public int compareTo(HuffmanNode	node)	{
   		return this.frequency -	node.frequency;
   	}
   }
}