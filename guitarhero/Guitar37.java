// Yiran Li
// TA: Connor Aksama
// This implementation of the Guitar interface models a guitar with 37 different strings

public class Guitar37 implements Guitar {
   public static final String KEYBOARD ="q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
   private GuitarString[] keyStrings;
   private int time = 0;
   
   // Creates an array of GuitarString object with length 37, and where 
   // each character of the string corresponds to the specific frequencies.
   public Guitar37() {
      keyStrings = new GuitarString[KEYBOARD.length()];
      for (int i = 0; i < KEYBOARD.length(); i++) {
         keyStrings[i] = new GuitarString(440.0*Math.pow(2,((i-24.0)/12.0)));
      }
   }
   
   // Plucks the string that has the same pitch
   // @param pitch - the pitch that used to find the corresponding string
   public void playNote(int pitch) {
      int index = pitch + 24;
      if (index >= 0 && index < KEYBOARD.length()) {
         keyStrings[index].pluck();
      }
   }
   
   // Determines whether the given string is in the guitar
   // @param string - the string that is going to be checked
   // @return - returns true if the given character has a corresponding string in the guitar
   public boolean hasString(char string) {
      return KEYBOARD.indexOf(string) != -1;
   }
   
   // Plucks the string that is corresponding to the key
   // @throws - IllegalArgumentException if key is not one of the 37 keys
   // @param string - the string that is going to be plucked
   public void pluck(char string) {
      if (!hasString(string)) {
         throw new IllegalArgumentException();
      } 
      keyStrings[KEYBOARD.indexOf(string)].pluck();
   }

   // Returns the sum of all samples from the strings of the guitar
   // @return - returns the sum of all samples from the strings
   public double sample() {
      double resultSample = 0.0;
      for (int i = 0; i < KEYBOARD.length(); i++) {
         resultSample += keyStrings[i].sample();
      }
      return resultSample;
   }
   
   // Strings are advanced forward by applying the Karplus-Strong algorithm.
   public void tic() {
      for (int i = 0; i < KEYBOARD.length(); i++) {
         keyStrings[i].tic();
      }
      time++;
   }
   
   // Returns the time that tic has been called
   // @return - return the number of time of tic has been called
   public int time() {
      return time;
   }
}
