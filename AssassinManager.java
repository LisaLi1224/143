// Yiran Li
// TA: Connor Aksama
// This AssassinManager class simulates a game of "Assassin", where each person
// has a target to assassinate. The file keeps track of who is stalking whom and 
// the history of who killed whom in the game.
import java.util.*;

public class AssassinManager {
   private AssassinNode killRing;
   private AssassinNode graveyard;
   
   // Constructs a new list of people called killRing, which keeps track of all the target in the game
   // @throws - IllgealArgumentException if the given list is empty
   // @param names - a list of value(String) given by the user
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      } else {
         killRing = null;
         graveyard = null;
         for (int i = names.size() - 1; i >= 0; i--) {
            killRing = new AssassinNode(names.get(i), killRing);
         }
      }
   }
   
   // Prints the names and their targets in the killRing list in the format of 
   // "X is stalking Y". When the game is over, print "X is stalking X".
   public void printKillRing() {
      AssassinNode temp = killRing;
      while (temp.next != null) {
         System.out.println("    " + temp.name + " is stalking " + temp.next.name);
         temp = temp.next;
      }
      System.out.println("    " + temp.name + " is stalking " + killRing.name);
   }

   // Prints the names of the people in the graveyard(people who are killed) in the
   // form of "X was killed by Y". It has no output if the graveyard is empty.
   public void printGraveyard() {
      AssassinNode temp = graveyard;
      while (temp != null) {
         System.out.println("    " + temp.name + " was killed by " + temp.killer);
         temp = temp.next;
      }
   }
   
   // Returns the boolean value whether the name is in the killRing list, ignoring the case.
   // @param name - the name, passed by the user, that is going to be determined whether 
   // is in the killRing list
   // @return - returns the boolean value whether the name is in the killRing list.
   public boolean killRingContains(String name) {
      return contains(name, killRing);
   }
   
   // Returns the boolean value whether the name is in the graveyard list, ignoring the case.
   // @param name - the name, passed by the user, that is going to be determined whether 
   // is in the graveyard list
   // @return - returns the boolean value whether the name is in the graveyard list.   
   public boolean graveyardContains(String name) { 
      return contains(name, graveyard);
   }
   
   // Private method that used to reduce redundancy of the killRingContains and 
   // graveyardContains methods. Determines whether the input name by user is in
   // the given list, ignoring the case.
   // @param name - the name, passed by the user, that is going to be determined whether 
   // is in the given list
   // @param list - the list that is going to be checked
   // @return - return true if the name is in the given list, ignoring case; return false
   // if the name is not in the given list, ignoring case.
   private boolean contains(String name, AssassinNode list) {
      AssassinNode temp = list;
      while (temp != null) {
         if (name.equalsIgnoreCase(temp.name)) {
            return true;
         }        
         temp = temp.next;
      }
      return false;
   }
   
   // Returns true if the game is over, which means killRing only contains 
   // one person.
   // @return - return true if the game is over, return false otherwise.
   public boolean gameOver() {
      return (killRing.next == null);
   }
   
   // Returns the winner of the game, which is the last name in the killRing list.
   // @return - return null if the game is not over; return the name otherwise.
   public String winner() {
      if (!gameOver()) {
         return null;
      }
      return killRing.name;
   }
   
   // Records the assassination of the person, and transferring the person from killRing list
   // to the front of the graveyard list, without changing the relative order of killRing,
   // ignoring the case.
   // @throws - IllegalStateException if the game is over. This takes precedence if both throw
   // conditions are true.
   // @throws - IllegalArgumentException if the given name is not in the killRing.
   // @param name - the name that is going to be assassinated.
   public void kill(String name) {
      AssassinNode tempKillRing = killRing;
      AssassinNode tempGraveyard = graveyard;
      
      if (gameOver()) {
         throw new IllegalStateException();
      } else if (! killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      
      if (name.equalsIgnoreCase(killRing.name)) {
         while (tempKillRing.next != null) {
            tempKillRing = tempKillRing.next;
         }
         graveyard = killRing;
         killRing = killRing.next;
      } else {
         while (! name.equalsIgnoreCase(tempKillRing.next.name)) {
           tempKillRing = tempKillRing.next; 
         }
         graveyard = tempKillRing.next;
         tempKillRing.next = tempKillRing.next.next;  
      }
      graveyard.killer = tempKillRing.name;
      graveyard.next = tempGraveyard;
   } 
}
