package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;

public class Converter {
    /** This Coverter will help us to turn tile to convert objects to string and convert strings to objects **/
    public static String tileToString(Tile t) {
        return String.valueOf(t.letter) + '-' + String.valueOf(t.score);
    }

    public static Tile stringToTile(String s) {
        String[] elements = s.split("-");
        return new Tile(elements[0].charAt(0), Integer.parseInt(elements[1]));
    }

    public static String wordToString(Word w) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(w.getTiles().length)+".");
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
        for(int i=0;i<tilesSize;i++){
            tiles[i]=stringToTile(elements[i+1]);
        }
        boolean bool;
        if(elements[tilesSize+3].charAt(0)=='t'){
            bool=true;
        } else {
            bool=false;
        }
        return new Word(tiles,Integer.parseInt(elements[tilesSize+1]),Integer.parseInt(elements[tilesSize+2]),bool);
    }

}
