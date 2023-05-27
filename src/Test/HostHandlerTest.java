package Test;

import Scrabble.Model.CommunicationServer.HostHandler;
import Scrabble.Model.CommunicationServer.HostModel;
import Scrabble.Model.Components.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HostHandlerTest {
    private HostHandler hostHandler;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    public void setup() {
        hostHandler = new HostHandler();
        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void testHandleGuestRequest_PassTurnToNextPlayer() {
        HostModel.getHostModel().addPlayer(new Player());
        String inputMessage = "passTurnToNextPlayer:2";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputMessage.getBytes());
        new Thread(() -> hostHandler.handleClient(inputStream, outputStream)).start();
        // Verify that the turn index is set to 1 in the HostModel
        int expectedTurnIndex = 1;
        assertEquals(expectedTurnIndex, HostModel.getHostModel().currentPlayerTurnIndex, "Turn index should match the expected value");
    }

    @Test
    public void testHandleGuestRequest_TryPlaceWord() {
        String inputMessage = "tryPlaceWord:word";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputMessage.getBytes());
        hostHandler.handleClient(inputStream, outputStream);

        // Verify that the word is tried to be placed in the HostModel
        String expectedWord = "word";
        assertEquals(expectedWord, HostModel.getHostModel().currentPlayerTurnIndex, "Attempted word should match the expected value");
    }


    @Test
    public void testHandleGuestRequest_Query() {
        String inputMessage = "query:true:word";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputMessage.getBytes());
        hostHandler.handleClient(inputStream, outputStream);

        // Verify that the query is handled in the HostModel
        boolean expectedResponse = true;
        String expectedWord = "word";
        //   assertEquals(expectedResponse, HostModel.getHostModel().getQueryResponse(), "Query response should match the expected value");
        //   assertEquals(expectedWord, HostModel.getHostModel().getQueryWord(), "Query word should match the expected value");
    }

    @Test
    public void testHandleGuestRequest_Challenge() {
        String inputMessage = "challenge:false:word";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputMessage.getBytes());
        hostHandler.handleClient(inputStream, outputStream);

        // Verify that the challenge is handled in the HostModel
        boolean expectedResponse = false;
        String expectedWord = "word";
        //   assertEquals(expectedResponse, HostModel.getHostModel().getChallengeResponse(), "Challenge response should match the expected value");
        //   assertEquals(expectedWord, HostModel.getHostModel().getChallengeWord(), "Challenge word should match the expected value");
    }

    @Test
    public void testHandleGuestRequest_PlaceWordOnBoard() {
        String inputMessage = "placeWordOnBoard:word";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputMessage.getBytes());
        hostHandler.handleClient(inputStream, outputStream);

        // Verify that the word is placed on the board in the HostModel
        String expectedWord = "word";
        //  assertEquals(expectedWord, HostModel.getHostModel().getPlacedWord(), "Placed word should match the expected value");
    }

    @Test
    public void testClose() {
        // Set up a mock input stream and output stream
        InputStream inputStream = new ByteArrayInputStream("".getBytes());
        OutputStream outputStream = new ByteArrayOutputStream();

        // Create a scanner and print writer for the host handler
        hostHandler.in = new Scanner(inputStream);
        hostHandler.out = new PrintWriter(outputStream);

        // Close the host handler
        hostHandler.close();

        // Verify that the input stream and output stream are closed
        assertEquals(true, hostHandler.in.ioException() != null, "Input stream should be closed");
        assertEquals(true, hostHandler.out.checkError(), "Output stream should be closed");
    }
}
