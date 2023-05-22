package Scrabble.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Board {
    private static Board t = null;
    private final Tile[][] gameBoard;
    private ArrayList<Word> createdWordsList;

    private static final HashMap<String, String> scoreList = new HashMap<>();

    private Board() {
        this.gameBoard = new Tile[15][15];
        this.createdWordsList = new ArrayList<>();
        initScoreList();
    }

    private void initScoreList() {
        //The Star Tile
        scoreList.put("0707", "2W");

        //Multiply 3xWord
        scoreList.put("0000", "3W");
        scoreList.put("0700", "3W");
        scoreList.put("0007", "3W");
        scoreList.put("0014", "3W");
        scoreList.put("1400", "3W");
        scoreList.put("1407", "3W");
        scoreList.put("0714", "3W");
        scoreList.put("1414", "3W");

        //Multiply 2xWord
        scoreList.put("0101", "2W");
        scoreList.put("0202", "2W");
        scoreList.put("0303", "2W");
        scoreList.put("0404", "2W");
        scoreList.put("0113", "2W");
        scoreList.put("0212", "2W");
        scoreList.put("0311", "2W");
        scoreList.put("0410", "2W");
        scoreList.put("1301", "2W");
        scoreList.put("1202", "2W");
        scoreList.put("1103", "2W");
        scoreList.put("1004", "2W");
        scoreList.put("1010", "2W");
        scoreList.put("1111", "2W");
        scoreList.put("1212", "2W");
        scoreList.put("1313", "2W");

        //Multiply 3xTile
        scoreList.put("0501", "3L");
        scoreList.put("0901", "3L");
        scoreList.put("0105", "3L");
        scoreList.put("0505", "3L");
        scoreList.put("0905", "3L");
        scoreList.put("1305", "3L");
        scoreList.put("0109", "3L");
        scoreList.put("0509", "3L");
        scoreList.put("0909", "3L");
        scoreList.put("1309", "3L");
        scoreList.put("0513", "3L");
        scoreList.put("0913", "3L");

        //Multiply 2xTile
        scoreList.put("0300", "2L");
        scoreList.put("1100", "2L");
        scoreList.put("0602", "2L");
        scoreList.put("0802", "2L");
        scoreList.put("0003", "2L");
        scoreList.put("0703", "2L");
        scoreList.put("1403", "2L");
        scoreList.put("0206", "2L");
        scoreList.put("0606", "2L");
        scoreList.put("0806", "2L");
        scoreList.put("1206", "2L");
        scoreList.put("0307", "2L");
        scoreList.put("1107", "2L");
        scoreList.put("0208", "2L");
        scoreList.put("0608", "2L");
        scoreList.put("0808", "2L");
        scoreList.put("1208", "2L");
        scoreList.put("0011", "2L");
        scoreList.put("0711", "2L");
        scoreList.put("1411", "2L");
        scoreList.put("0612", "2L");
        scoreList.put("0812", "2L");
        scoreList.put("0314", "2L");
        scoreList.put("1114", "2L");
    }

    public static Board getBoard() {
        if (t == null) {
            t = new Board();
        }
        return t;
    }

    public Tile[][] getTiles() {
        Tile[][] temp = new Tile[15][15];
        for (int i = 0; i < gameBoard.length; i++) {
            System.arraycopy(this.gameBoard[i], 0, temp[i], 0, this.gameBoard[i].length);
        }
        return temp;
    }

    public boolean boardLegal(Word w) {
        if ((w.getRow() < 0) || (w.getCol() < 0) || (w.getRow() > 14) || (w.getCol() > 14)) { // There word isn't fit the board.
            return false;
        }
        if (!w.getVertical()) {
            if ((w.getCol() + w.getTiles().length >= 15)) {
                return false;
            }
        } else if ((w.getRow() + w.getTiles().length >= 15)) {
            return false;
        }

        if (isBoardEmpty() && firstWord(w)) {
            return true;
        }
        if (wordFitToBoard(w)) {
            return true;
        }
        return false;
    }

    private boolean firstWord(Word w) { // Checking if the first word starting on the star tile.
        if (w.getVertical()) { // True = up to down , false= left to right
            if (w.getCol() == 7 && (w.getRow() == 7 || (w.getRow() < 7 && w.getRow() + w.getTiles().length >= 7)))
                return true;
            else
                return false;
        } else {
            if (w.getRow() == 7 && (w.getCol() == 7 || (w.getCol() < 7 && w.getCol() + w.getTiles().length >= 7)))
                return true;
            else
                return false;
        }
    }

    private boolean wordFitToBoard(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        int length = w.getTiles().length;

        if (w.getVertical()) {

            if (row - 1 >= 0 && gameBoard[row - 1][col] != null) // there is a tile upper
                return true;
            if (row + length <= 14 && gameBoard[row + length][col] != null) //there is a tile in the bottom
                return true;

            for (int i = 0; i < length; i++) {
                if (col == 0) { //the tile is at the begging of the col
                    if (gameBoard[row + i][col + 1] != null)
                        return true;
                }
                if (col == 14) { //the tile is at the end of the col
                    if (gameBoard[row + i][col - 1] != null)
                        return true;
                }
                if (col > 0 && col < 14) {
                    if ((gameBoard[row + i][col + 1] != null) || (gameBoard[row + i][col - 1] != null))
                        return true;
                }
            }
        } else {

            if (col - 1 >= 0 && gameBoard[row][col - 1] != null) // there is a tile from left
                return true;
            if (col + length <= 14 && gameBoard[row][col + length] != null) //there is a tile from right
                return true;

            for (int i = 0; i < length; i++) {
                if (row == 0) { //the tile is at the begging of the row
                    if (gameBoard[row + 1][col + i] != null)
                        return true;
                }
                if (row == 14) { //the tile is at the end of the col
                    if (gameBoard[row - 1][col + i] != null)
                        return true;
                }
                if (row > 0 && row < 14) {
                    if ((gameBoard[row + 1][col + i] != null) || (gameBoard[row - 1][col + i] != null))
                        return true;
                }
            }
        }
        return false;
    }


    public boolean dictionaryLegal(Word w) {
        return true;
    }

    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> temp = new ArrayList<Word>();
        ArrayList<Word> wordsFound = new ArrayList<>();
        if (isBoardEmpty()) {
            temp.add(w);
            return temp;
        }
        temp.addAll(findNewWords(w));
        wordsFound.addAll(temp);
        for (Word t : wordsFound) {
            if (wordFound(t)) {
                temp.remove(t);
            }
        }
        return temp;
    }

    private boolean wordFound(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        int wordLength = w.getTiles().length;

        for (Word t : createdWordsList) {
            if (t.getRow() == row && t.getCol() == col && t.getTiles().length == wordLength) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Word> findNewWords(Word w) {
        ArrayList<Word> newWords = new ArrayList<>();
        Tile[][] boardCopy = getTiles();
        addWordToBoard(w, boardCopy);
        newWords.add(w);

        int row = w.getRow();
        int rowStart = w.getRow();
        int rowEnd = w.getRow() + w.getTiles().length;

        int col = w.getCol();
        int colStart = w.getCol();
        int colEnd = w.getCol() + w.getTiles().length;
        int length = w.getTiles().length;

        if (w.getVertical()) {
            while (rowStart >= 0 && boardCopy[rowStart - 1][col] != null) { //check the left side
                rowStart--;
            }
            while (rowEnd <= 14 && boardCopy[rowEnd + 1][col] != null) { //check the right side
                rowEnd++;
            }
            if (rowEnd - rowStart != w.getTiles().length) { //found new word
                Tile[] tempTiles = new Tile[rowEnd - rowStart + 1];
                for (int i = 0; i < rowEnd - rowStart + 1; i++) { //array of the new words tiles
                    tempTiles[i] = boardCopy[rowStart + i][col];
                }
                newWords.add(new Word(tempTiles, rowStart, col, true)); // the new word added to the list.
            }
        } else {
            while (colStart >= 0 && boardCopy[row][colStart - 1] != null) { //check the left side
                colStart--;
            }
            while (colEnd <= 14 && boardCopy[row][colEnd + 1] != null) { //check the right side
                colEnd++;
            }
            if (colEnd - colStart != w.getTiles().length) { //found new word
                Tile[] tempTiles = new Tile[colEnd - colStart + 1];
                for (int i = 0; i < colEnd - colStart + 1; i++) { //array of the new words tiles
                    tempTiles[i] = boardCopy[row][colStart + i];
                }
                newWords.add(new Word(tempTiles, row, colStart, false)); // the new word added to the list.
            }
        }
        if (w.getVertical()) {
            for (int j = 0; j < length; j++) {
                colStart = w.getCol();
                colEnd = w.getCol();
                while (colStart > 0 && boardCopy[row + j][colStart - 1] != null) { //check the upper side
                    colStart--;
                }
                while (colEnd < 14 && boardCopy[row + j][colEnd + 1] != null) { //check the buttom side
                    colEnd++;
                }
                if (colEnd - colStart >= 1) { // found new word
                    Tile[] tempTiles = new Tile[colEnd - colStart + 1];
                    for (int i = 0; i < colEnd - colStart + 1; i++) { //array of the new words tiles
                        tempTiles[i] = boardCopy[row + j][colStart + i];
                    }
                    newWords.add(new Word(tempTiles, row + j, colStart, false)); // the new word added to the list.
                }
            }
        } else {
            for (int j = 0; j < length; j++) {
                rowStart = w.getRow();
                rowEnd = w.getRow();
                while (rowStart > 0 && boardCopy[rowStart - 1][col + j] != null) { //check the upper side
                    rowStart--;
                }
                while (rowEnd < 14 && boardCopy[rowEnd + 1][col + j] != null) { //check the buttom side
                    rowEnd++;
                }
                if (rowEnd - rowStart >= 1) { // found new word
                    Tile[] tempTiles = new Tile[rowEnd - rowStart + 1];
                    for (int i = 0; i < rowEnd - rowStart + 1; i++) { //array of the new words tiles
                        tempTiles[i] = boardCopy[rowStart + i][col + j];
                    }
                    newWords.add(new Word(tempTiles, rowStart, col + j, true)); // the new word added to the list.
                }
            }
        }
        return newWords;
    }


    private int getScore(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        int wordLength = w.getTiles().length;
        Tile[][] copyBoard = getTiles();
        int score = 0;
        int wordMul = 1;
        int tileMul = 1;

        for (int i = 0; i < wordLength; i++) {
            if (w.getVertical()) {
                String currIndex = tileToString(row + i, col);
                if (Objects.equals(scoreList.get(currIndex), "2W")) {
                    wordMul = wordMul * 2;
                }
                if (Objects.equals(scoreList.get(currIndex), "3W")) {
                    wordMul = wordMul * 3;
                }
                if (Objects.equals(scoreList.get(currIndex), "2L")) {
                    tileMul = 2;
                }
                if (Objects.equals(scoreList.get(currIndex), "3L")) {
                    tileMul = 3;
                }
                if (w.getTiles()[i] == null) {
                    score += copyBoard[row + i][col].score * tileMul;
                } else {
                    score += w.getTiles()[i].score * tileMul;
                }
                if (currIndex.equals("0707")) {
                    scoreList.remove("0707");
                }
            }
            if (!w.getVertical()) {
                String currIndex = tileToString(row, col + i);
                if (Objects.equals(scoreList.get(currIndex), "2W")) {
                    wordMul = wordMul * 2;
                }
                if (Objects.equals(scoreList.get(currIndex), "3W")) {
                    wordMul = wordMul * 3;
                }
                if (Objects.equals(scoreList.get(currIndex), "2L")) {
                    tileMul = 2;
                }
                if (Objects.equals(scoreList.get(currIndex), "3L")) {
                    tileMul = 3;
                }
                if (w.getTiles()[i] == null) {
                    score += copyBoard[row][col + i].score * tileMul;
                } else {
                    score += w.getTiles()[i].score * tileMul;
                }
                if (currIndex.equals("0707")) {
                    scoreList.remove("0707");
                }
            }
            tileMul = 1;
        }
        score = score * wordMul;
        return score;
    }

    private String tileToString(int r, int c) {
        String row, col;
        if (r < 10)
            row = String.format("%02d", r);
        else
            row = Integer.toString(r);
        if (c < 10)
            col = String.format("%02d", c);
        else
            col = Integer.toString(c);
        return row + col;
    }

    public int tryPlaceWord(Word w) {
        if (!boardLegal(w)) {
            return 0;
        }
        ArrayList<Word> temp = getWords(w);
        for (Word t : temp) {
            if (!dictionaryLegal(t)) {
                return 0;
            }
        }
        createdWordsList.addAll(temp);
        addWordToBoard(w, this.gameBoard);
        int score = 0;
        for (Word t : temp) {
            score += getScore(t);
        }
        return score;
    }

    private void addWordToBoard(Word w, Tile[][] b) {
        if (w.getVertical()) {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (w.getTiles()[i] == null)
                    continue;
                b[w.getRow() + i][w.getCol()] = w.getTiles()[i];
            }
        } else {
            for (int i = 0; i < w.getTiles().length; i++) {
                if (w.getTiles()[i] == null)
                    continue;
                b[w.getRow()][w.getCol() + i] = w.getTiles()[i];
            }
        }
    }

    private boolean isBoardEmpty() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (this.gameBoard[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }
}
