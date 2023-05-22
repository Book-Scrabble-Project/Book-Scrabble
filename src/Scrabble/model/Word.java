package Scrabble.model;

import java.util.Arrays;
import java.util.Objects;

public class Word {
    private Tile tiles[];
    private int row;
    private int col;
    private boolean vertical;

    public Word(Tile t[], int r, int c, boolean v) {
        this.tiles = new Tile[t.length];
        System.arraycopy(t, 0, this.tiles, 0, t.length);
        this.row = r;
        this.col = c;
        this.vertical = v;
    }


    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public boolean getVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return row == word.row && col == word.col && vertical == word.vertical && Arrays.equals(tiles, word.tiles);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(row, col, vertical);
        result = 31 * result + Arrays.hashCode(tiles);
        return result;
    }
}
