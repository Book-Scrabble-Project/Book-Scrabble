package Scrabble.Model.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private String name;
    private int score;
    private List<Tile> tiles;
    private int id;

    public Player() {
        this.name = "Default";
        this.score = 0;
        this.tiles = new ArrayList<>();
        this.id = 0;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public void completeTiles() {
        int bagSize = Tile.Bag.getBag().size();
        int currentTileSize = tiles.size();
        if (bagSize == 0) {
            return;
        }
        while (currentTileSize < 7) {
            tiles.add(Tile.Bag.getBag().getRand());
            currentTileSize++;
        }
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
        return score == player.score && id == player.id && Objects.equals(name, player.name) && Objects.equals(tiles, player.tiles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, score, tiles, id);
    }
}

