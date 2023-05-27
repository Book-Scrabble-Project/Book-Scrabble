package Test;

import Scrabble.Model.CommunicationServer.Converter;
import Scrabble.Model.CommunicationServer.HostModel;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HostModelTest {
    private HostModel hostModel;

    @BeforeEach
    public void setup() {
        hostModel = HostModel.getHostModel();
    }

    @Test
    public void testCreateNewGameGuestMode() {
        hostModel.createNewGameGuestMode();

        // Verify that the player's turn index is set to 0
        int expectedTurnIndex = 0;
        assertEquals(expectedTurnIndex, hostModel.player.getTurnIndex(), "Player's turn index should match the expected value");

        // Verify that the player's hand has 7 tiles
        int expectedHandSize = 7;
        assertEquals(expectedHandSize, hostModel.player.getTiles().size(), "Player's hand size should be 7");
    }

    @Test
    public void testCreateNewGameHostMode() {
        hostModel.createNewGameHostMode();

        // Verify that the players' turn indexes are set correctly and their hands have 7 tiles
        List<Player> players = hostModel.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);

            // Verify turn index
            assertEquals(i, player.getTurnIndex(), "Player's turn index should match the expected value");

            // Verify hand size
            int expectedHandSize = 7;
            assertEquals(expectedHandSize, player.getTiles().size(), "Player's hand size should be 7");
        }
    }

    @Test
    public void testDealTilesToFullHand() {
        Player player = new Player();
        hostModel.dealTilesToFullHand(player);

        // Verify that the player's hand has at least 7 tiles and no more than the bag size
        List<Tile> hand = player.getTiles();
        int expectedMinHandSize = 7;
        int expectedMaxHandSize = Math.min(expectedMinHandSize + hostModel.bag.size(), hostModel.bag.size());
        assertTrue(hand.size() >= expectedMinHandSize && hand.size() <= expectedMaxHandSize, "Player's hand size should be between 7 and bag size");
    }

    @Test
    public void testSetTurnIndexToNextPlayer() {
        int currentPlayerTurnIndex = 0;
        hostModel.setTurnIndexToNextPlayer(currentPlayerTurnIndex);

        // Verify that the current player's turn index is updated to the next player's turn index
        int expectedNextPlayerTurnIndex = (currentPlayerTurnIndex + 1) % hostModel.getPlayers().size();
        assertEquals(expectedNextPlayerTurnIndex, hostModel.getCurrentPlayerTurnIndex(), "Current player's turn index should match the expected value");
    }

    @Test
    public void testGetBoard() {
        Tile[][] board = hostModel.getBoard();

        // Verify that the returned board is the same as the actual board in the HostModel
        assertEquals(Converter.TileArrayToString(hostModel.board.getTiles()), Converter.TileArrayToString(board), "Returned board should match the actual board");
    }

    @Test
    public void testGetScore() {
        Player player = new Player();
        int score = 100;
        player.setScore(score);

        int retrievedScore = hostModel.getScore(player);

        // Verify that the retrieved score matches the player's score
        assertEquals(score, retrievedScore, "Retrieved score should match the player's score");
    }

    @Test
    public void testGetHand() {
        Player player = new Player();
        List<Tile> hand = player.getTiles();

        List<Tile> retrievedHand = hostModel.getHand(player);

        // Verify that the retrieved hand matches the player's hand
        assertEquals(hand, retrievedHand, "Retrieved hand should match the player's hand");
    }
}
