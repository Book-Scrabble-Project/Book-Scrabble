package Scrabble.Model.Components;

public class Player {
    private String name;
    private int score;
    private Tile[] tiles;


    private int id;

    public Player() {
        this.name = "Default Player";
        this.score = 0;
        this.tiles = new Tile[7];
    }

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.tiles = new Tile[7];
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public void setId(int id) { this.id = id; }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addScore(int score) {
        this.score += score;
    }

    public void addTile(Tile tile) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == null) {
                tiles[i] = tile;
                break;
            }
        }
    }

    public void removeTile(Tile tile) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == tile) {
                tiles[i] = null;
                break;
            }
        }
    }

    public void completeTiles() {
        int bagSize = Tile.Bag.getBag().size();
        int currentTileSize = this.getTiles().length;
        if (bagSize == 0) {
            return;
        }
        while (currentTileSize < 7) {
            this.addTile(Tile.Bag.getBag().getRand());
            currentTileSize++;
        }
    }

    public void removeTile(int index) {
        tiles[index] = null;
    }

    public void removeTiles(Tile[] tiles) {
        for (Tile tile : tiles) {
            removeTile(tile);
        }
    }

    public void removeTiles(int[] indexes) {
        for (int index : indexes) {
            removeTile(index);
        }
    }

    public void addTiles(Tile[] tiles) {
        for (Tile tile : tiles) {
            addTile(tile);
        }
    }

    public void addTiles(int[] indexes, Tile[] tiles) {
        for (int i = 0; i < indexes.length; i++) {
            this.tiles[indexes[i]] = tiles[i];
        }
    }

    public void swapTiles(int[] indexes, Tile[] tiles) {
        for (int i = 0; i < indexes.length; i++) {
            Tile temp = this.tiles[indexes[i]];
            this.tiles[indexes[i]] = tiles[i];
            tiles[i] = temp;
        }
    }

}
