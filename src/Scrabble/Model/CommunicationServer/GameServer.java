package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Server.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameServer {
    private int port;
    private ClientHandler clientHandler;
    private volatile boolean stopServer;
    List<Socket> socketList;
    ServerSocket server;
    ExecutorService threadPool = Executors.newFixedThreadPool(3);

    public GameServer(int port, ClientHandler clientHandler) {
        this.port = port;
        this.clientHandler = clientHandler;
        socketList = new ArrayList<>();
        this.stopServer = false;
    }

    private void runServer() throws Exception {
        server = new ServerSocket(port);
        server.setSoTimeout(5000);
        try {
            while (!stopServer) {
                try {
                    Socket aClient = server.accept();
                    String guestID = UUID.randomUUID().toString().substring(0, 6); //Generate an unique ID to the Guest.
                    HostModel.getHostModel().addNewGuestToMap(guestID, aClient);
                    socketList.add(aClient);
                    threadPool.execute(() -> {
                        try {
                            clientHandler.handleClient((aClient.getInputStream()), (aClient.getOutputStream()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                } catch (SocketTimeoutException e) {
                    throw new RuntimeException();
                }
            }
            server.close();
        } catch (Exception e) {

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
        socketList.forEach((socket) -> {
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
        Socket socket = HostModel.getHostModel().getGuestsMapIDtoSocket().get(guestID);
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

    public boolean communicateBSServer(String ip, int port, String queryOrChallenge, String... words) {
        String template;
        if (queryOrChallenge.equals("query")) {
            template = "Q,";
        } else {
            template = "C,";
        }
        StringBuilder message = new StringBuilder(template).append(",");
        for (int i = 0; i < words.length; i++) {
            if (i == words.length - 1) {
                message.append(words[i]);
            } else {
                message.append(words[i]).append(",");
            }
        }
        try {
            Socket socket = new Socket(ip, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            Scanner in = new Scanner(socket.getInputStream());
            out.println(message.toString());
            out.flush();
            String res = in.next();
            if (res.equals("true"))
                return true;
            else
                return false;
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void stopServer() {
        stopServer = true;
    }

    public void close() {
        stopServer();
        clientHandler.close();
        for (Socket socket : socketList) {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        threadPool.shutdown();
        try {
            server.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public boolean isServerRunning() {
        return !stopServer;
    }
}