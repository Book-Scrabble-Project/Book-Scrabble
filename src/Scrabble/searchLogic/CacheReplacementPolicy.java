package Scrabble.searchLogic;

public interface CacheReplacementPolicy {
    void add(String word);

    String remove();
}
