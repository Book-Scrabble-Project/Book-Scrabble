package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;

import java.util.List;

public class GuestModel implements Model{
    //local variables
    Player player;
    int score;
    // game board
    Board board;

    GuestHandler guestHandler;

    /**
     * The GuestModel function is a constructor that initializes the board and players variables.
     */
    private GuestModel() {
        this.board = Board.getBoard();
        player = new Player();
        score = 0;
    }

    public String tryPlaceWord(Word word){
        String outputMessage = "tryPlaceWord" + ":" + player.getId() + Converter.wordToString(word);
        return outputMessage;
    }

    /**
     * The getPlayer function returns the current player.
     * @return The current player
     */
    public Player getPlayer() {
        return player;
    }
    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public int getScore(Player player) {
        return player.getScore();
    }

    @Override
    public List<Tile> getHand(Player player) {
        return player.getTiles();
    }
}
