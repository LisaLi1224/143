// Yiran Li
// TA: Connor Aksama
// This AnagramSolver class uses given dictionary to print all anagram phrases of a 
// given word or phrase. Anagram means a word or phrase made by rearranging the letters
// of another word or phrase.

import java.util.*;

public class AnagramSolver{
   private Map<String, LetterInventory> dictMap;
   private List<String> dictList;
   
   // Constructs a new AnagramSolver object that uses the given list as its dictionary.
   // The list does not change. And this method "preprocess" the dictionary to compute
   // all of the inventories in advance.
   // @param dictionary - the list that contains words which are going to be compute, and 
   //                     shown as inventories.
   public AnagramSolver(List<String> dictionary){
      dictMap = new HashMap<String, LetterInventory>();
      for (String word: dictionary){
         LetterInventory wordInventory = new LetterInventory(word);
         dictMap.put(word, wordInventory);
      }
      dictList = dictionary;
   }
   
   // prints all combinations of words from dictionary that are anagrams of text and 
   // includes at most max words.
   // @param text - the phrase that is input to be used to find anagrams of it
   // @param max - the maximum number of words that is going to be printed. If max is
   //              equal to zero, than means unlimited number of words to print.
   // @throws - IllegalArgumentException if max is less than 0.
   public void print(String text, int max){
      if (max < 0){
         throw new IllegalArgumentException();
      } 
      LetterInventory textLetters = new LetterInventory(text);
      Stack<String> anagrams = new Stack<String>();
      List<String> relevWords = relevantWords(textLetters);
      print(textLetters, relevWords, anagrams, max);
   }
   
   // prints all combination of words from the relevant words list that fits the remaining
   // inventory with the given maximum number
   // @param currentLetters - the new inventory to be checked after each subtraction of letters
   // @param releWords - a list of words that are able to be subtracted from the given phrase
   // @param anagram - the current anagrams of a given phrase
   // @param max - the maximum number of words that is going to be printed. If max is
   //              equal to zero, than means unlimited number of words to print.
   private void print(LetterInventory currentLetters, List<String> releWords, 
                                 Stack<String> anagram, int max){
      if (currentLetters.isEmpty()){
         System.out.println(anagram);
      } else {
         if (max == 0 || max > anagram.size()){
            for (String s: releWords){
               LetterInventory diffLetters = currentLetters.subtract(dictMap.get(s));
               if (diffLetters != null){
                  anagram.push(s);
                  print(diffLetters, releWords, anagram, max);
                  anagram.pop();
               }
            }
         }
      }
   }
   
   // returns a list of relevant words that are made up of letters of a given phrase
   // @param textLetters - the inventory of the given phrase
   // @return - return a list of relevant words that are made up of letters of a given phrase
   private List<String> relevantWords(LetterInventory textLetters){
      List<String> result = new ArrayList<String>();
      for (String s: dictList){
         LetterInventory diffLetters = textLetters.subtract(dictMap.get(s));
         if (diffLetters != null){
            result.add(s);
         }
      }
      return result;
   }
}