import java.util.*;

class Node {
    String key;
    ArrayList<Node> children = null;

    Node(String key){
        this.key = key;
        this.children = new ArrayList<Node>();
    }
}

public class Trie {

    private Node root = null; //one root per object

    public Trie () {
        if (root == null) {
            root = new Node("");
        }
    }

    private void insertHelper(Node nd, String wd) {
        if (wd.isEmpty()) {
            return;
        }

        boolean foundPrefix = false;

        if (!nd.children.isEmpty()) {
            for (Node child : nd.children) {
                if (wd.contains(child.key)) {
                    foundPrefix = true;
                    insertHelper(child, wd.substring(child.key.length()));
                }
            }
        }
        if (!foundPrefix) {
            Node wdNode = new Node(wd);
            nd.children.add(wdNode);
        }
    }

    public void insert(String word) {
        if (word.isEmpty()) {
            return;
        }
        insertHelper(root, word);
    }

    public boolean doesPrefixExist(String word) {
        if (word.isEmpty()) {
            return false;
        }
        for (Node nd : root.children) {
            System.out.println("Root children = " + nd.key);
            if (word.contains(nd.key) && !(nd.key).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<String> getOddLengthPrefixes() {
        ArrayList<String> oddLengthPrefixes = new ArrayList<String> ();
        for (Node nd : root.children) {
            if (nd.key.length() % 2 != 0) {
                oddLengthPrefixes.add(nd.key);
            }
        }
        return oddLengthPrefixes;
    }

    public ArrayList<String> getEvenLengthPrefixes() {
        ArrayList<String> evenLengthPrefixes = new ArrayList<String> ();
        for (Node nd : root.children) {
            if (nd.key.length() % 2 == 0) {
                evenLengthPrefixes.add(nd.key);
            }
        }
        return evenLengthPrefixes;
    }

    public String getLongestEvenLengthPrefix () {
        int wordLength = 0;
        String longestEvenLengthPrefix = null; 
        for (Node nd : root.children) {
            if (nd.key.length() > wordLength) {
                wordLength = nd.key.length();
                longestEvenLengthPrefix = nd.key;
            }
        }
        return longestEvenLengthPrefix;
    }

    public ArrayList<String> getPrefixes() {
        ArrayList<String> children = new ArrayList<String>();
        for (Node nd : root.children) {
            children.add(nd.key);
        }

        return children;
    }

    public int getNumOfPrefixes () {
        return root.children.size();
    }
}
