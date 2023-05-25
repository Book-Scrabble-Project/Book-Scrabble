package Scrabble.Model.SearchLogic;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy { //Least recently used
    private class Word {
        String word;
        int useTimes;

        Word(String w) {
            this.word = w;
            this.useTimes = 0;
        }

        public String getWord() {
            return word;
        }

        public int getUseTimes() {
            return useTimes;
        }

        public void setUseTimes(int t) {
            this.useTimes = t;
        }


        @Override
        public int hashCode() {
            return Objects.hash(word, useTimes);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Word word1 = (Word) o;
            return word.equals(word1.word);
        }
    }

    PriorityQueue<Word> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.useTimes));
    HashMap<String, Integer> counter = new HashMap<>();

    public void add(String word) {
        Word temp = new Word(word);
        if (pq.contains(temp)) {
            int c = counter.get(word);
            counter.remove(word);
            counter.put(word, ++c);
            pq.remove(temp);
            temp.setUseTimes(++c);
            pq.add(temp);
        }
        temp.setUseTimes(1);
        pq.add(temp);
        counter.put(word, 1);
    }

    public String remove() {
        return pq.remove().word;
    }
}
