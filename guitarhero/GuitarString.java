// Yiran Li
// TA: Connor Aksama
// This GuitarString class models a vibrating guitar string of a given frequency, 
// and constructs object that keep track of a ring buffer. 

import java.util.*;

public class GuitarString {
   public static final double ENERGY_DECAY_FACTOR = 0.996;
   
   private Queue<Double> ringBuffer;
   private double SAMPLE_RATE = StdAudio.SAMPLE_RATE;
   
   // Creates a ring buffer of a required capacity, and initializes it 
   // as a queue with zeros
   // @throws - IllegalArgumentException if the frequency is less than or 
   // equal to 0 or if the size of ring buffer is less than 2
   // @param frequency - frequency that determine the capacity of ring buffer
   public GuitarString(double frequency) { 
      ringBuffer = new LinkedList<Double>();
      int bufferCapacity = (int)(Math.round(SAMPLE_RATE / frequency));
      
      if (frequency <= 0 || bufferCapacity < 2) {
         throw new IllegalArgumentException();
      }    
      for (int i = 0; i < bufferCapacity; i++) {
         ringBuffer.add(0.0);
      }
   }
   
   // Constructs a GuitarString and set the content of the ring buffer to 
   // the values in the given array
   // @throws - IllegalArgumentException if the array has less than 2 elements
   // @param init - the array which values need to be put in the ring buffer
   public GuitarString(double[] init) {
      ringBuffer = new LinkedList<Double>();
      
      if (init.length < 2) {
         throw new IllegalArgumentException();
      }
      for (int i = 0; i < init.length; i++) {
         ringBuffer.add(init[i]);
      }
   }
   
   // Replaces the elements in the ring buffer with same number of random values
   // between -0.5 inclusive and +0.5 exclusive
   public void pluck() {
      for(int i = 0; i < ringBuffer.size(); i++) {
         ringBuffer.remove();
         ringBuffer.add(Math.random() - 0.5);
      }
   }
   
   // Applys the Karplus-Strong update once
   // Deteles the first element in the ring buffer and add to the end of the ring buffer
   // of the first and second elements, multiplied by 0.996
   public void tic() {
      double first = ringBuffer.remove();
      double second = ringBuffer.peek();
      
      ringBuffer.add((first + second) * 0.5 * ENERGY_DECAY_FACTOR);
   }
   
   // Returns the value that is the first element of the ring buffer
   // @return - returns the first element value of the ring buffer
   public double sample() {
      return ringBuffer.peek();
   }
}