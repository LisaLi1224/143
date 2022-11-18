// Yiran Li
// TA: Connor Aksama
// This HangmanManager class manages an Evil Hangman game, and it narrows
// down the set of possible words after each guess from user. The program 
// always choose the set with the largest number of words in order to keep 
// user guess incorrectly.

import java.util.*;

public class HangmanManager {

   private Set<String> wordsFamily;
   private int remainGuess;
   private String pattern;
   private Set<Character> letterGuess; 
   
   // Constructs a hangman manager to initialize the state of the game by
   // adding words into a set without duplication, and initializing the pattern
   // @throws - IllegalArgumentException if the given length is less than 1 or 
   // max is less than 0
   // @param dictionary - the set of words that is going to be added into the set as "family"
   // @param length - the length of the target word
   // @param max - the maximum number of wrong guesses the user is allowed to make
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      
      wordsFamily = new TreeSet<>();       
      letterGuess = new TreeSet<>();
      remainGuess = max;
      pattern = "";
      
      for(int i = 1; i <= length; i++) {
         pattern += "- ";
      }
      
      for (String word: dictionary) {
         if(word.length() == length && ! wordsFamily.contains(word)) {
            wordsFamily.add(word);
         }
      } 
   }
   
   // This method returns the current set of words considered by the HangmanManager
   // @return - returns the current set of words being considered by the hangman manager
   public Set<String> words() {
      return wordsFamily;
   }
   
   // This method returns the number of guesses that the user left
   // @return - returns the integer that represents how many guesses the user has left
   public int guessesLeft() {
      return remainGuess;
   }

   // This method returns the set of letters that have been guessed by the user
   // @return - returns a set of leeters that have been guessed by the user
   public Set<Character> guesses() {
      return letterGuess;
   }
   
   // Updates the pattern after each guess by the user, and put the new pattern into the key of the treeMap, and 
   // puts the words to their corresponding pattern, and finally chooses the largest set to continue the game.
   // @throws - IllegalStateException if the set of words is empty
   // @return - returns the current pattern to be displayed for the game
   public String pattern() {
      if (wordsFamily.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   // Updates the pattern after each guess by the user, and put the new pattern into the key of the treeMap, and 
   // puts the words to their corresponding pattern, and finally chooses the largest set to continue the game.
   // @param patternMap - map that stores the pattern as key, and stores set of possible words as value
   // @param guess - the letter that the user guessed
   private void patternCurrent(Map<String, Set<String>> patternMap, char guess) {
      for (String word: wordsFamily) {
         String newPattern = pattern;
         for (int i = 0; i < word.length() * 2; i = i + 2) {
            if (word.charAt(i / 2) == guess) {
               newPattern = newPattern.substring(0, i) + guess + newPattern.substring(i + 1);
            }
         }
         
         if (!patternMap.containsKey(newPattern)) {
            patternMap.put(newPattern, new TreeSet<String>());
         }
            patternMap.get(newPattern).add(word);
      }
      
      int largestFamily = 0;
      for (String pattern: patternMap.keySet()) {
         Set<String> family = patternMap.get(pattern);
         if (family.size() > largestFamily) {
            largestFamily = family.size();
            wordsFamily = family;
            this.pattern = pattern;
         }
      } 
   }
   
   // This method records after the user made a guess. It decides what set to use 
   // going forward, returns the number of the guessed letter in the new pattern, and 
   // updates the number of guesses left.
   // @throws - IllegalStateException if the number of guesses left is less than 1 or if 
   // the set of words is empty
   // @throws - IllegalArgumentException if the letter being guessed was guessed before
   // @param guess - the letter that guessed by the user
   // @return - returns the number of the guessed letter in the new pattern
   public int record(char guess) {
      if (remainGuess < 1 || wordsFamily.isEmpty()) {
         throw new IllegalStateException();
      } else if (! wordsFamily.isEmpty() && letterGuess.contains(guess)) {
         throw new IllegalArgumentException();
      }
      
      letterGuess.add(guess);
      Map<String,Set<String>> patternMap = new TreeMap<String,Set<String>>();
      patternCurrent(patternMap, guess);
      
      int count = 0;
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            count++;
         }
      }
      if (count == 0) {
         remainGuess--;
      }
      return count;
   }
}
