package Scrabble.Model.SearchLogic;

public interface CacheReplacementPolicy {
    void add(String word);

    String remove();
}
