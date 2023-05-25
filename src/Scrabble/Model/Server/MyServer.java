package Scrabble.Model.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class MyServer {
    private final int port;
    private final ClientHandler ch;
    private volatile boolean stop;

    public MyServer(int port, ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        stop = false;
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

    public void close() {
        stop = true;
    }

    private void runServer() throws Exception {
        ServerSocket server = new ServerSocket(port); //Create new instance of server and specify the port number on which the server should listen.
        server.setSoTimeout(1000); // 1 Second timeout
        while (!stop) {          //infinite loop which waits for a client to connect to the server.
            try {
                Socket aClient = server.accept();       // blocking call that's mean the program will stop until a client connects to the server.
                try { // Once a client connects, the server creates a new instance of the Socket class to represent the connection to the client.
                    ch.handleClient(aClient.getInputStream(), aClient.getOutputStream()); // Once the connection is established,the handleClient()
                    // method is called on the "ch" object,passing the input and output streams of the client socket as arguments.
                    aClient.getInputStream().close(); // Once communication with the client is finished,
                    aClient.getOutputStream().close();  // the input and output streams of the client socket are closed,
                    aClient.close();                     // then the client socket itself is closed
                } catch (IOException e) {
                }
            } catch (SocketTimeoutException e) {
            }
        }   //  The loop continues and waits for the next client connection.
        server.close();
    }
}
