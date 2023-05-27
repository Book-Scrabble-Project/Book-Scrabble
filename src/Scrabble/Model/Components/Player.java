package Scrabble.Model.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Player {

    private String guestID;
    private String name;
    private int score;
    private List<Tile> tiles;
    private char drawnTile;
    private int turnIndex;

    public Player() {
        setName("Guest");
        setScore(0);
        setTiles(new ArrayList<>());
        setDrawnTile();
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public void setTurnIndex(int turnIndex) {
        this.turnIndex = turnIndex;
    }

    public char getDrawnTile() {
        // The DrawnTile char is a draw before the game to decide the order of the play.
        return drawnTile;
    }

    public void setDrawnTile() {
        // Taking a random Tile
        Random rand = new Random();
        int value = rand.nextInt(26) + 'A';
        this.drawnTile = (char) value;
    }

    public String getGuestID() {
        return guestID;
    }

    public void setGuestID(String guestID) {
        this.guestID = guestID;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return score;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addTile(Tile tile) {
        tiles.add(tile);
    }

    public void removeTile(Tile tile) {
        tiles.remove(tile);
    }

    public void removeTile(int index) {
        tiles.remove(index);
    }

    public void removeTiles(List<Tile> tiles) {
        this.tiles.removeAll(tiles);
    }

    public void removeTiles(int[] indexes) {
        for (int i = indexes.length - 1; i >= 0; i--) {
            removeTile(indexes[i]);
        }
    }

    public void addTiles(List<Tile> tiles) {
        this.tiles.addAll(tiles);
    }

    public void addTiles(int[] indexes, List<Tile> tiles) {
        for (int i = 0; i < indexes.length; i++) {
            this.tiles.set(indexes[i], tiles.get(i));
        }
    }

    public void swapTiles(int[] indexes, List<Tile> tiles) {
        for (int i = 0; i < indexes.length; i++) {
            Tile temp = this.tiles.get(indexes[i]);
            this.tiles.set(indexes[i], tiles.get(i));
            tiles.set(i, temp);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Player player = (Player) obj;
        return score == player.score && guestID == player.guestID && Objects.equals(name, player.name) && Objects.equals(tiles, player.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, tiles, guestID);
    }
}

