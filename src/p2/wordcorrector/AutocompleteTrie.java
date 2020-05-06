package p2.wordcorrector;

import cse332.datastructures.containers.Item;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;

public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode current = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
            if (current.pointers.find(item) == null) {
                return null;
            }
            else {
                current = current.pointers.find(item);
            }
        }

        StringBuilder result = new StringBuilder(key);

        while (current.pointers.size() == 1) {
            if (current.value != null) {
                return null;
            }
            Item<Character, HashTrieNode> nextItem = current.pointers.iterator().next();
            result.append(nextItem.key);
            current = nextItem.value;
        }

        // Switch this to return null to only complete if we're at the end of
        // the word
        if (current.pointers.size() != 0) {
            return result.toString();
        }
        return result.toString();
    }
}
