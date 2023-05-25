package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Server.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    ExecutorService executorService;
    Map<String, Socket> guestsMapIDtoSocket = new HashMap<>();
    private int port;
    private ClientHandler clientHandler;
    private volatile boolean stopServer;

    public GameServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        this.stopServer = false;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void stopServer() {
        stopServer = true;
    }

    public void close() {
        stopServer();
    }

    private void runServer() throws Exception {
        ServerSocket server = new ServerSocket(port);
        server.setSoTimeout(1000);

        while (!stopServer) {
            try {
                Socket aClient = server.accept();
                String guestID = UUID.randomUUID().toString().substring(0, 6); //Generate an unique ID to the Guest.
                guestsMapIDtoSocket.put(guestID, aClient);

                try {
                    try {
                        clientHandler.handleClient((aClient.getInputStream()), (aClient.getOutputStream()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            }
        }
        if (guestsMapIDtoSocket.isEmpty()) {
            clientHandler.close();
            server.close();
        }
    }

    public void start() {
        new Thread(() -> {
            try {
                runServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void broadcast(String message) {
        //Send broadcast Message to all the connected players
        guestsMapIDtoSocket.forEach((guestID, socket) -> {
            PrintWriter out;
            try {
                out = new PrintWriter(socket.getOutputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.println(message);
            out.flush();
        });
    }

    public void directMessageToGuest(String guestID, String message) {
        //Send Direct message to specific guest.
        Socket socket = guestsMapIDtoSocket.get(guestID);
        PrintWriter out;
        try {
            if (socket != null) {
                out = new PrintWriter(socket.getOutputStream());
                out.println(message);
                out.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isServerRunning() {
        return !stopServer;
    }
}