/* 
 * What are proper tests for a prefix tree?
 *
 * 1. 
 */
import java.util.*;
import java.io.*;

public class TrieTest {
    public static void main (String[] args) {
        try {
            Scanner s = new Scanner(new File("WORD.LST.txt"));
            Trie prefixTrie = new Trie();
            while (s.hasNext()) {
                prefixTrie.insert(s.next());
            }
            s.close();
            boolean prefixExist = prefixTrie.doesPrefixExist("zigzag");
        
            System.out.println("Prefix exist = " + prefixExist);
            boolean exist = prefixTrie.doesWordExist("zigzag");
            System.out.println(exist);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
