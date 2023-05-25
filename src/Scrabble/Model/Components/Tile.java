package Scrabble.Model.Components;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tile {

    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag {
        private static Bag b = null;
        private int tilesAmount[];
        private Tile tilesList[];

        private Bag() {
            int array[] = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            int tileScore[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
            this.tilesAmount = Arrays.copyOf(array, 26);
            int num = 'A';
            this.tilesList = new Tile[26];
            for (int i = 0; i < 26; i++, num++) {
                this.tilesList[i] = new Tile((char) num, tileScore[i]);
            }
        }

        public Tile getRand() {
            if (bagStatus()) {
                return null;
            }
            Random rand = new Random();
            int randomIndex = ThreadLocalRandom.current().nextInt(0, this.tilesList.length);
            if (this.tilesAmount[randomIndex] == 0) {
                randomIndex = 0;
            }
            while (randomIndex < this.tilesAmount.length && this.tilesAmount[randomIndex] == 0) {
                randomIndex++;
            }
            this.tilesAmount[randomIndex] -= 1;
            return this.tilesList[randomIndex];
        }

        public Tile getTile(char c) {
            if (bagStatus() || c < 'A' || c > 'Z') {
                return null;
            }
            if (this.tilesAmount[((int) c - 'A')] != 0) {
                this.tilesAmount[((int) c - 'A')] -= 1;
                return this.tilesList[(int) (c - 'A')];
            } else
                return null;
        }

        public void put(Tile t) {
            int array[] = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            if (this.tilesAmount[t.letter - 'A'] < array[t.letter - 'A']) {
                this.tilesAmount[t.letter - 'A'] += 1;
            }
        }

        public int size() {
            int sum = 0;
            for (int t : tilesAmount) {
                sum += t;
            }
            return sum;
        }

        public int[] getQuantities() {
            int temp[] = new int[26];
            System.arraycopy(this.tilesAmount, 0, temp, 0, this.tilesAmount.length);
            return temp;
        }

        public static Bag getBag() {
            if (b == null) {
                b = new Bag();
            }
            return b;
        }

        private boolean bagStatus() {
            for (int i = 0; i < this.tilesAmount.length; i++) {
                if (tilesAmount[i] != 0) {
                    return false;
                }
            }
            return true;
        }
    }

}
