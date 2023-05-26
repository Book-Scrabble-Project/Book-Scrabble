package Test;

import Scrabble.Model.CommunicationServer.GuestModel;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestModelTest {
    private GuestModel guestModel;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setup() {
        // Create a mock socket and set up the input/output streams
        Socket mockSocket = new Socket();
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        outputStream = new ByteArrayOutputStream();

        // Create the GuestModel instance with the mock socket
        guestModel = new GuestModel("localhost", 1234);
        guestModel.outputStream = outputStream;
        guestModel.inputStream = inputStream;
    }

    @Test
    public void testPassTurnToNextPlayer() {
        guestModel.passTurnToNextPlayer(2);

        String expectedMessage = "passTurnToNextPlayer:2\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

    @Test
    public void testTryPlaceWord() {
        Tile[] tiles = {new Tile('A', 2), new Tile('B', 5), new Tile('D', 9)};
        Word word = new Word(tiles, 4, 3, true);

        guestModel.tryPlaceWord(word);

        String expectedMessage = "tryPlaceWord:" + guestModel.player.getGuestID() + ":word\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

    @Test
    public void testQuery() {
        Tile[] tiles = {new Tile('A', 2), new Tile('B', 5), new Tile('D', 9)};
        Word word = new Word(tiles, 4, 3, true);

        guestModel.query(word);

        String expectedMessage = "query:" + guestModel.player.getGuestID() + ":word\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

    @Test
    public void testChallenge() {
        Tile[] tiles = {new Tile('A', 2), new Tile('B', 5), new Tile('D', 9)};
        Word word = new Word(tiles, 4, 3, true);

        guestModel.challenge(word);

        String expectedMessage = "challenge:" + guestModel.player.getGuestID() + ":word\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

    @Test
    public void testMyHandState() {
        guestModel.myHandState();

        StringBuilder expectedMessageBuilder = new StringBuilder("myHandState:")
                .append(guestModel.player.getGuestID()).append(":")
                .append(guestModel.player.getTiles().size()).append(":");
        List<Tile> tiles = guestModel.player.getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (i == tiles.size() - 1) {
                expectedMessageBuilder.append(tiles.get(i));
            } else {
                expectedMessageBuilder.append(tiles.get(i)).append(":");
            }
        }

        String expectedMessage = expectedMessageBuilder.toString() + "\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

    @Test
    public void testQuitGame() {
        guestModel.quitGame();

        String expectedMessage = "quitGame:" + guestModel.player.getName() + "\n";
        assertEquals(expectedMessage, outputStream.toString(), "Sent message should match the expected message");
    }

}