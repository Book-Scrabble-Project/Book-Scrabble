package Test;

import Scrabble.Model.CommunicationServer.Converter;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class ConverterTest {

    @Test
    public void testTileToString() {
        Tile tile = new Tile('A', 1);
        String expected = "A-1";
        String actual = Converter.tileToString(tile);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testStringToTile() {
        String s = "B-3";
        Tile expected = new Tile('B', 3);
        Tile actual = Converter.stringToTile(s);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testWordToString() {
        Tile[] tiles = {new Tile('C', 2), new Tile('D', 3), new Tile('E', 1)};
        Word word = new Word(tiles, 2, 3, true);
        String expected = "3.C-2.D-3.E-1.2.3.t";
        String actual = Converter.wordToString(word);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testStringToWord() {
        String s = "3.F-2.G-3.H-1.3.4.f";
        Tile[] expectedTiles = {new Tile('F', 2), new Tile('G', 3), new Tile('H', 1)};
        Word expected = new Word(expectedTiles, 3, 4, false);
        Word actual = Converter.stringToWord(s);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEndToEndWord() {
        Tile[] expectedTiles = {new Tile('D', 4), new Tile('C', 0), new Tile('W', 9)};
        Word expected = new Word(expectedTiles, 14, 12, true);
        String test = Converter.wordToString(expected);
        Word actual = Converter.stringToWord(test);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void testEndToEndTileArrayToString() {
        char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O'};
        int MIN_SCORE = 1;
        int MAX_SCORE = 10;
        Tile[][] actualTiles = new Tile[15][15];
        Random random = new Random();

        for (int i = 0; i < actualTiles.length; i++) {
            for (int j = 0; j < actualTiles[i].length; j++) {
                char randomLetter = LETTERS[random.nextInt(LETTERS.length)];
                int randomScore = random.nextInt(MAX_SCORE - MIN_SCORE + 1) + MIN_SCORE;
                actualTiles[i][j] = new Tile(randomLetter, randomScore);
            }
        }
        String test = Converter.TileArrayToString(actualTiles);
        Tile[][] expected = Converter.stringToTileArray(test);
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (expected[i][j].score != actualTiles[i][j].score || expected[i][j].letter != actualTiles[i][j].letter) {
                    System.out.println("Test Failed!");
                }
            }
        }
        System.out.println("Test Success!");
    }

}

