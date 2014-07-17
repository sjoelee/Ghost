import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
import java.lang.*;

public class Solution {
    /*
     * wordList: an internal ArrayList that the computer will use to update its own list
     * of word based on the player and its character choices throughout every turn.
     *
     * constructWord: the word being constructed throughout the game. Used to see if the 
     * result exists in the wordList.
     */
    private static StringBuffer constructWord = new StringBuffer();
    private static Trie wordList = new Trie();

    /*
     * Go through list of words (in the filename) and extract only words that match the
     * firstChar. Ignore words that are words of length less than or equal to 4.
     * While going through the list, if the first character of the word in the list is
     * greater than the firstChar, then don't continue looking. We found all the
     * relevant words that start with firstChar character.
     */
    private static Trie getWordsOfFirstChar(char firstChar, String filename) {
        Trie wordsOfFirstChar;
        try {
            Scanner s = new Scanner(new File(filename));
            wordsOfFirstChar = new Trie();
            while (s.hasNext()) {
                String word = s.next();
                if (word.length() <= 3) {
                    continue;
                }

                char ch = word.charAt(0);
                if (ch > firstChar) {
                    break;
                } else if (ch == firstChar) {
                    wordsOfFirstChar.insert(word);
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            wordsOfFirstChar = null;
        }

        return wordsOfFirstChar;
    }

    /* 
     * Go through the wordList and store words that are of odd length in local
     * ArrayList bestWordsToChooseFrom. Then randomly select from that.
     * 
     * If none exists, then we know that the computer has lost the game. 
     * In this case, choose the word with the largest length.
     *
     * NOTE: Be careful that some words can be a prefix for others (eg zigzag and zigzags)
     */
    private static char chooseNextLetter(int idx) throws StringIndexOutOfBoundsException {
        /* 
         * Keep track of the word length and word length. If there are no words
         * of odd length, then pick the word with the longest length as part of
         * the strategy to delay its loss.
         */      
        ArrayList<String> bestWordsToChooseFrom = wordList.getOddLengthPrefixes();
        Random rand = new Random();

        if (bestWordsToChooseFrom.isEmpty()) {
            String wd = wordList.getLongestEvenLengthPrefix();
            return wd.charAt(idx+1);
        }

        try {
            int index = rand.nextInt(bestWordsToChooseFrom.size());
            return bestWordsToChooseFrom.get(index).charAt(idx+1);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    /*
     * Based on the character that the user chose, update the wordList accordingly
     */
    private static void updateWordList(char playerChar, int idx) {
        Trie wordListUpdated = new Trie ();

        System.out.println("char: " + playerChar + " idx: " + idx);
        for (String word : wordList.getPrefixes()) {
            if (word.length() > idx) {
                if (word.charAt(idx) == playerChar) {
                    wordListUpdated.insert(word);
                }
            }
        }

        wordList = wordListUpdated;
    }

    private static void takeNextTurn(int idx) {
        char playerChar; 

        try {
            if (idx % 2 == 0) {
                playerChar = chooseNextLetter(idx);                
                System.out.println("Computer chooses " + playerChar + " as next letter");
            } else {
                Console console = System.console();
                playerChar = (console.readLine("Enter input ")).charAt(0); 
            }
            constructWord.append(playerChar);
            updateWordList(playerChar, idx+1);
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
            throw new IllegalStateException("No cigar!");
        }
    }

    public static void main(String[] args) {
        char playerChar, compChar; 
        String filename = "WORD.LST.txt2";
        boolean winnerExist = false;
        boolean isCompWinner = false;
        int idx = 0; // index tracking

        /*
         * Read user input for first character
         */
        Console console = System.console();
        playerChar = (console.readLine("Enter input ")).charAt(0);
        constructWord.append(playerChar);
        wordList = getWordsOfFirstChar(playerChar, filename);
        if (wordList == null) {
            System.out.println("Either player gave an invalid character " +
                               "or the list used it empty!");
        }

        while (winnerExist == false) {
            try {
                takeNextTurn(idx);
                idx++;
                if (wordList.getPrefixes().contains(constructWord.toString())) {
                    winnerExist = true;
                    continue;
                }
                if (wordList.getPrefixes().isEmpty()) {
                    System.out.print("You can't fool me! ");
                    System.out.println("No words exist with those characters!");
                    winnerExist = true;
                    isCompWinner = true;
                }
            } catch (IllegalStateException e) {
                System.out.println(e.getMessage());
                System.out.println("You lose!");
                return;
            }
        }

        /*
         * Check if the word length is odd or even. If it's odd, then the 
         * computer wins. If it's even, human wins.
         */
        if (isCompWinner) {
            System.out.println("Computer supriority!");
            return;
        }
        if (constructWord.length() % 2 != 0) {
            System.out.println("Haha, that's a word! Computer superiority!");
        } else {
            System.out.println("You've won! There's a flaw in the system...");
        }
        System.out.println("The word is: " + constructWord.toString());
    }
}
