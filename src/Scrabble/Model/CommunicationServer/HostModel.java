package Scrabble.Model.CommunicationServer;


import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;
import Scrabble.Model.Server.MyServer;
import java.io.IOException;
import java.util.*;

public class HostModel implements Model {
    private GameServer gameServer;
    private volatile boolean gameOver;
    public Board board;
    public Tile.Bag bag;
    public Player player;
    private Map<Integer, Player> TurnIndexToPlayerMap;
    private List<Player> players;
    private HostHandler hostHandler = new HostHandler();
    private MyServer myServer;
    private int currentPlayerTurnIndex;

    public HostModel(int port) throws IOException {
        board = Board.getBoard();
        bag = Tile.Bag.getBag();
        players = new ArrayList<>();
        gameOver = false;
        player = new Player();
        currentPlayerTurnIndex = 0;
        TurnIndexToPlayerMap = new HashMap<>();
        players.add(player);
        gameServer = new GameServer(port, hostHandler);
    }

    public void createNewGameGuestMode() {
        // Single Guest Game
        this.player.setTurnIndex(0);
        dealTilesToFullHand(this.player);
    }

    public void createNewGameHostMode() {
        players.sort(Comparator.comparing(Player::getDrawnTile)); //get the order of the players
        for(int i=0;i<players.size();i++){
            players.get(i).setTurnIndex(i);
            TurnIndexToPlayerMap.put(i,players.get(i));
            dealTilesToFullHand(players.get(i));
        }
    }
    public void dealTilesToFullHand(Player p) {
        //keep the player with 7 tiles.
        int bagSize = bag.size();
        int playerTileSize = p.getTiles().size();
        while (playerTileSize < 7 && bagSize > 0) {
            p.addTile(bag.getRand());
        }
    }
    public List<Player> getPlayers() {
        return players;
    }
    public int getCurrentPlayerID() {
        return currentPlayerTurnIndex;
    }

    public void setCurrentPlayerID(int currentPlayerID) {
        this.currentPlayerTurnIndex = currentPlayerID;
    }

    @Override
    public Board getBoard() {
        return board;
    }

    @Override
    public int getScore(Player player) {
        return player.getScore();
    }

    @Override
    public List<Tile> getHand(Player player) {
        return player.getTiles();
    }
}
