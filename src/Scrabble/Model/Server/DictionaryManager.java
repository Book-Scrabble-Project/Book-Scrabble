package Scrabble.Model.Server;

import Scrabble.Model.SearchLogic.Dictionary;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager {
    private static DictionaryManager dm = null;
    int size;
    private final Map<String, Dictionary> dictionaryMap;

    private DictionaryManager() {
        dictionaryMap = new HashMap<>();
        this.size = 0;
    }

    public static DictionaryManager get() {
        if (dm == null) {
            dm = new DictionaryManager();
        }
        return dm;
    }

    public boolean query(String... args) {
        boolean check = false;
        String s = initiateStrings(args);
        for (Dictionary t : dictionaryMap.values()) {
            if (t.query(s)) {
                check = true;
            }
        }
        return check;
    }

    public boolean challenge(String... args) {
        boolean check = false;
        String s = initiateStrings(args);
        for (Dictionary t : dictionaryMap.values()) {
            if (t.challenge(s)) {
                check = true;
            }
        }
        return check;
    }

    private String initiateStrings(String[] args) {
        for (int i = 0; i < args.length - 1; i++) {
            if (!dictionaryMap.containsKey(args[i])) {
                dictionaryMap.put(args[i], new Dictionary(args[i]));
                size++;
            }
        }
        return args[args.length - 1];
    }

    public int getSize() {
        return size;
    }
}