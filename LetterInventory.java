// Yiran Li
// TA: Connor Aksama
// The LetterInventory class represents an inventory of number 
// of each 26 letters in a given String


public class LetterInventory {
   // Number of letters in the English alphabet
   public static final int LETTERS_NUM = 26;
   
   // Count of each letter indexed by its position in the English alphabet
   private int[] letterCount;
   // Count of all letters
   private int totalCount;
   
   // Constructs an inventory that counts the letter in a given string
   // @param data - the string that is created into a inventory
   public LetterInventory(String data) {
      letterCount = new int[LETTERS_NUM];
      
      for (int i = 0; i < data.length(); i++) {
         if (Character.isLetter(data.charAt(i))) {
            letterCount[Character.toLowerCase(data.charAt(i)) - 'a']++;
            totalCount++;
         }
      }
   }
   
   // Returns the total number of letters in this LetterInventory
   // @return - returns the sum of all counts in the inventory
   public int size() {
      return totalCount;
   }
   
   // Returns whether there is no letter counted in this LetterInventory
   // @return - returns true if the inventory is empty and vice versa
   public boolean isEmpty() {
      return totalCount == 0;
   }
   
   // Counts the number of specific character
   // @throws - IllegalArgumentException if character is nonalphabetic
   // @param letter - the character that is counted
   // @return - returns the count of a specific character in the inventory
   public int get(char letter) {
      if (!Character.isLetter(letter)) {
         throw new IllegalArgumentException();
      }
      return letterCount[Character.toLowerCase(letter) - 'a'];
   }
   
   // Converts the inventory into a String with letters in lowercase 
   // and in sorted order and surrounded by square brackets
   // @return - returns a string representation of the inventory
   public String toString() {
      String result = "[";
      for (int i = 0; i < LETTERS_NUM; i++) {
         for (int j = 1; j <= letterCount[i]; j++) {
            result += (char) ('a' + i);
         }
      }
      return result + "]";
   }
   
   // Modifies the count of a specific letter to a new value
   // throws - IllegalArgumentException if character is nonalphabetic or value is negative
   // param letter - the letter which value will be changed
   // param value - the value that change on the given letter
   public void set(char letter, int value) {
      if (!Character.isLetter(letter) || value < 0) {
         throw new IllegalArgumentException();
      }
      int index = Character.toLowerCase(letter) - 'a';
      totalCount += value - letterCount[index];
      letterCount[index] = value;
   }
   
   // Constructs a letter inventory and add this to the other inventory
   // @param other - the inventory that added to the new constructed inventory
   // @return - returns the sum of two inventories
   public LetterInventory add(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      for (int i = 0; i < LETTERS_NUM; i++) {
         result.letterCount[i] = this.letterCount[i] + other.letterCount[i];
      }
      result.totalCount = this.totalCount + other.totalCount;
      return result;
   }
   
   // Constructs a letter inventory and subtract other inventory from this inventory
   // @param other - the inventory that subtracted from the new constructed inventory
   // @return - returns the difference between two letter inventories.
   public LetterInventory subtract(LetterInventory other) {
      LetterInventory result = new LetterInventory("");
      for (int i = 0; i < LETTERS_NUM; i++) {
         result.letterCount[i] = this.letterCount[i] - other.letterCount[i];
         if (result.letterCount[i] < 0) {
            return null;
         }
         result.totalCount = this.totalCount - other.totalCount;
      }
      return result;
   }
   
}
