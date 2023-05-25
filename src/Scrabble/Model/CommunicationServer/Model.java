package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;

import java.util.List;

public interface Model {
    /**
     * The getBoard function returns the current state of the game board.
     * @return A 2d array of tile objects
     */
    Board getBoard();

    /**
     * The getScore function gets the score for a player.
     * @param player A player object
     * @return The score of the player
     */
    int getScore(Player player);


    /**
     * The getHand function gets the hand for a player.
     * @param player A player object
     * @return The hand of the player
     */
    List<Tile> getHand(Player player);

}
