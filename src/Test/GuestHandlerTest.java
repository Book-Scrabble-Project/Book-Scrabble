package Test;

import Scrabble.Model.CommunicationServer.Converter;
import Scrabble.Model.CommunicationServer.GuestHandler;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;
import org.junit.jupiter.api.*;

import java.io.*;
import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.*;

public class GuestHandlerTest {
    String test;
    String wordTest;
    private GuestHandler guestHandler;
    private InputStream inputStream;
    private OutputStream outputStream;

    @BeforeEach
    public void setup() throws IOException {
        Tile[] tiles = {new Tile('A',2),new Tile('B',5),new Tile('D',9)};
        Word w = new Word(tiles,4,3,true);
        wordTest = Converter.wordToString(w);
        test = "query:success:" + wordTest;
        inputStream = new ByteArrayInputStream(test.getBytes());
        outputStream = new ByteArrayOutputStream();
        guestHandler = new GuestHandler("guest1", inputStream,outputStream);
    }

    @AfterEach
    public void cleanup() throws IOException {
        inputStream.close();
        outputStream.close();
    }

    @Test
    public void testHandleHostMessage_QueryResponse() {
        TestObserver observer = new TestObserver();
        guestHandler.addObserver(observer);

        guestHandler.handleHostMessage(test);

        String expectedMessage = "query:success:" + wordTest;;
        assertEquals(expectedMessage, observer.getReceivedMessage(), "Observer should receive the expected message");
    }

    @Test
    public void testHandleHostMessage_ChallengeResponse() {
        TestObserver observer = new TestObserver();
        guestHandler.addObserver(observer);

        guestHandler.handleHostMessage("challenge:failed:"+wordTest);

        String expectedMessage = "challenge:failed:"+wordTest;
        assertEquals(expectedMessage, observer.getReceivedMessage(), "Observer should receive the expected message");
    }

    @Test
    public void testHandleHostMessage_SetScore() {
        TestObserver observer = new TestObserver();
        guestHandler.addObserver(observer);

        guestHandler.handleHostMessage("setScore:100");

        String expectedMessage = "score:100";
        assertEquals(expectedMessage, observer.getReceivedMessage(), "Observer should receive the expected message");
    }

    @Test
    public void testHandleHostMessage_IsWinner() {
        TestObserver observer = new TestObserver();
        guestHandler.addObserver(observer);

        guestHandler.handleHostMessage("isWinner:true");

        String expectedMessage = "winner:true";
        assertEquals(expectedMessage, observer.getReceivedMessage(), "Observer should receive the expected message");
        assertFalse(guestHandler.isConnectionAlive(), "Connection should be closed after receiving isWinner message");
    }

    @Test
    public void testGetMessageFromHost() {
        String message = guestHandler.getMessageFromHost();

        String expectedMessage = "query:success:"+wordTest;
        assertEquals(expectedMessage, message, "Received message should match the expected message");
    }

    @Test
    public void testSendMessageToHost() {
        guestHandler.sendMessageToHost("message");

        String expectedMessage = "message\n";
        String actualMessage = outputStream.toString().replaceAll("\\R", "\n"); // Normalize line separators
        assertEquals(expectedMessage, actualMessage, "Sent message should match the expected message");
    }

    private static class TestObserver implements Observer {
        private String receivedMessage;

        @Override
        public void update(Observable o, Object arg) {
            receivedMessage = arg.toString();
        }

        public String getReceivedMessage() {
            return receivedMessage;
        }
    }
}
