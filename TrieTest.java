public class TrieTest {
    public static void main (String[] args) {
        Trie t = new Trie();

        t.insert("zig");
        boolean prefixExist = t.doesPrefixExist("zigzag");
        
        System.out.println("Prefix exist = " + prefixExist);
    }
}
