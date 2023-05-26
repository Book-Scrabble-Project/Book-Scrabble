package Test;

import static org.junit.jupiter.api.Assertions.*;

import Scrabble.Model.CommunicationServer.GameServer;
import Scrabble.Model.CommunicationServer.HostHandler;
import org.junit.jupiter.api.*;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class GameServerTest {

    private static final int PORT = 8080;
    private static final String HOST = "localhost";

    private GameServer gameServer;
    private Socket clientSocket;

    @BeforeEach
    public void setup() {
        gameServer = new GameServer(PORT, new HostHandler());
        gameServer.start();
    }

    @AfterEach
    public void cleanup() {
        gameServer.close();
        closeSocket();
    }

    @Test
    public void testStartStopServer() {
        assertTrue(gameServer.isServerRunning(), "Server should be running");
        gameServer.stopServer();
        assertFalse(gameServer.isServerRunning(), "Server should be stopped");
    }

    @Test
    public void testBroadcast() throws IOException {
        // Mock a connected client
        connectClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        gameServer.broadcast("Hello, world!");

        String expectedOutput = "Hello, world!\n";
        assertEquals(expectedOutput, outputStream.toString(), "Output should match broadcast message");
    }

    @Test
    public void testDirectMessageToGuest() throws IOException {
        // Mock a connected client
        connectClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String guestID = gameServer.guestsMapIDtoSocket.keySet().iterator().next();
        gameServer.directMessageToGuest(guestID, "Hello, guest!");

        String expectedOutput = "Hello, guest!\n";
        assertEquals(expectedOutput, outputStream.toString(), "Output should match direct message to guest");
    }

    private void connectClient() throws IOException {
        clientSocket = new Socket(HOST, PORT);
    }

    private void closeSocket() {
        if (clientSocket != null && !clientSocket.isClosed()) {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

