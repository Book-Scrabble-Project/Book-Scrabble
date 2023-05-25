package Scrabble.Model.CommunicationServer;


import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Server.MyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class HostModel implements Model{
    private final ServerSocket server;
    private volatile boolean gameOver;
    public Board board;
    private List<Player> players;
    private HostHandler hostHandler;
    private MyServer myServer;
    private int currentPlayerID;

    public HostModel(int port) throws IOException {
        board = Board.getBoard();
        server = new ServerSocket(port);
        gameOver = false;
        currentPlayerID = 0;
    }

    public void run() {
        while (!gameOver){
            try {
                Socket client = server.accept();
               // players.add();
            } catch (IOException e) {
                gameOver = true;
            }
        }
    }

}
