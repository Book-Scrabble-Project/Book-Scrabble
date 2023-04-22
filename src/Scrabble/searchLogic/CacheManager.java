package Scrabble.searchLogic;

import java.util.HashSet;

public class CacheManager {

    CacheReplacementPolicy crp;
    int capacity;
    HashSet<String> cacheSet = new HashSet<>();

    CacheManager(int t, CacheReplacementPolicy crp) {
        this.capacity = t;
        this.crp = crp;
    }

    public boolean query(String s) {
        return cacheSet.contains(s);
    }

    public void add(String s) {
        crp.add(s);
        cacheSet.add(s);
        if (cacheSet.size() > capacity) {
            cacheSet.remove(crp.remove());
        }
    }
}
