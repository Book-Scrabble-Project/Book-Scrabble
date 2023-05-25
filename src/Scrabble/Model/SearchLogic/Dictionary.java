package Scrabble.Model.SearchLogic;

import java.io.BufferedReader;
import java.io.FileReader;


public class Dictionary {
    String fileNames[];
    CacheManager exist;
    CacheManager notExist;
    BloomFilter bloomFilter;

    public Dictionary(String... fileNames) {
        this.fileNames = fileNames;
        exist = new CacheManager(400, new LRU());
        notExist = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5", "SHA1");
        for (String fileName : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                while ((line = reader.readLine()) != null) {
                    String words[] = line.split(" ");
                    for (String s : words) {
                        bloomFilter.add(s);
                        exist.add(s);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean query(String s) {
        if (exist.query(s)) {
            return true;
        }
        if (notExist.query(s)) {
            return false;
        }
        if (bloomFilter.contains(s)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean challenge(String s) {
        if (IOSearcher.search(s, fileNames)) {
            exist.add(s);
            return true;
        } else {
            notExist.add(s);
            return false;
        }
    }
}
