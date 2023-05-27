package Test;

import Scrabble.Model.CommunicationServer.GameServer;
import Scrabble.Model.CommunicationServer.HostHandler;
import Scrabble.Model.CommunicationServer.HostModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class GameServerTest {

    private static final int PORT = 9999;
    private static final String HOST = "localhost";
    private HostHandler hostHandler;
    private GameServer gameServer;
    private Socket clientSocket;

    @BeforeEach
    public void setup() {
        hostHandler = new HostHandler();
        gameServer = new GameServer(PORT, hostHandler);
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
        String actualOutput;
        // Mock a connected client
        connectClient();

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
        Scanner in = new Scanner(clientSocket.getInputStream());
        out.println("welcomeMessage:");

        gameServer.broadcast("quitGame:mockPlayer");
        if (in.hasNext()) {
            actualOutput = in.next();
        } else {
            actualOutput = "Server didn't responded!";
        }
        String expectedOutput = "quitGame:mockPlayer\n";

        assertEquals(expectedOutput, actualOutput, "Output should match broadcast message");
    }

    @Test
    public void testDirectMessageToGuest() throws IOException {
        // Mock a connected client
        connectClient();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        String guestID = (String) HostModel.getHostModel().getGuestsMapIDtoSocket().keySet().toArray()[0];
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

