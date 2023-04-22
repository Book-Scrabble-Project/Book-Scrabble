package Scrabble.Model;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy {

    LinkedList<String> wordQueue = new LinkedList<>();

    public void add(String word) {
        if (wordQueue.contains(word)) { // if the string inside the queue we will put him inside again to update his recently.
            wordQueue.remove(word);
            wordQueue.add(word);
        } else {
            wordQueue.add(word);
        }
    }

    public String remove() {
        return wordQueue.remove();
    }
}
