package Scrabble.Model.CommunicationServer;


import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Server.MyServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class HostModel implements Model{
    private GameServer gameServer;
    private volatile boolean gameOver;
    public Board board;
    public Tile.Bag bag;
    public Player player;
    private List<Player> players;
    private HostHandler hostHandler = new HostHandler();
    private MyServer myServer;
    private int currentPlayerID;




    public HostModel(int port) throws IOException {
        board = Board.getBoard();
        bag = Tile.Bag.getBag();
        players = new ArrayList<>();
        gameOver = false;
        player = new Player();
        currentPlayerID = 0;
        gameServer = new GameServer(port,hostHandler);
    }


}
