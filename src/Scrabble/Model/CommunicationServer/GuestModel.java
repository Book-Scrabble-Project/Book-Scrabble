package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;

import java.util.List;

public class GuestModel implements Model{
    //local variables
    Player player;
    int score;
    // game board
    Board board;
    GuestHandler guestHandler;
    private static  GuestModel guestModelInstance = null;

    /**
     * The GuestModel function is a constructor that initializes the board and players variables.
     */
    private GuestModel() {
        this.board = Board.getBoard();
        player = new Player();
        score = 0;
    }

    /**
     * The getModel function is a static function that returns the singleton instance of the GuestModel class.
     * This allows for only one instance of this model to be created, and it can be accessed from anywhere in the program.
     * @return The singleton instance of the GuestModel class
     */
    public static GuestModel getModel() {
        if (guestModelInstance == null) {
            guestModelInstance = new GuestModel();
        }
        return guestModelInstance;
    }


    //get Player

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
    public void setPlayer(String name) {
        player.setName(name);
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
