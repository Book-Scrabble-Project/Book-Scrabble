package Scrabble.Model;

public interface CacheReplacementPolicy {
    void add(String word);

    String remove();
}
