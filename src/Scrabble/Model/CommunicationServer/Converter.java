package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;

public class Converter {
    /**
     * This Coverter will help us to turn tile to convert objects to string and convert strings to objects
     **/
    public static String tileToString(Tile t) {
        return String.valueOf(t.letter) + '-' + String.valueOf(t.score);
    }

    public static Tile stringToTile(String s) {
        String[] elements = s.split("-");
        return new Tile(elements[0].charAt(0), Integer.parseInt(elements[1]));
    }

    public static String wordToString(Word w) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(w.getTiles().length) + ".");
        for (int i = 0; i < w.getTiles().length; i++) {
            sb.append(tileToString(w.getTiles()[i])).append(".");
        }
        sb.append(String.valueOf(w.getRow()) + ".").append(String.valueOf(w.getCol()) + ".");
        if (w.getVertical())
            sb.append("t");
        else
            sb.append("f");
        return sb.toString();
    }

    public static Word stringToWord(String s) {
        String[] elements = s.split("\\.");
        int tilesSize = Integer.parseInt(elements[0]);
        Tile[] tiles = new Tile[tilesSize];
        for (int i = 0; i < tilesSize; i++) {
            tiles[i] = stringToTile(elements[i + 1]);
        }
        boolean bool;
        if (elements[tilesSize + 3].charAt(0) == 't') {
            bool = true;
        } else {
            bool = false;
        }
        return new Word(tiles, Integer.parseInt(elements[tilesSize + 1]), Integer.parseInt(elements[tilesSize + 2]), bool);
    }

    public static String TileArrayToString(Tile[][] tiles) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (tiles[i][j] != null) {
                    sb.append(tileToString(tiles[i][j]));
                } else {
                    sb.append("0-0");
                }
                sb.append(",");
            }
            sb.append(";");
        }
        return sb.toString();
    }

    public static Tile[][] stringToTileArray(String s) {
        String[] rows = s.split(";");
        Tile[][] tiles = new Tile[rows.length][];
        for (int i = 0; i < rows.length; i++) {
            String[] tileStrings = rows[i].split(",");
            tiles[i] = new Tile[tileStrings.length];
            for (int j = 0; j < tileStrings.length; j++) {
                Tile temp = stringToTile(tileStrings[j]);
                if (temp.score == 0 && temp.letter == '0')
                    tiles[i][j] = null;
                else
                    tiles[i][j] = temp;
            }
        }
        return tiles;
    }
}
