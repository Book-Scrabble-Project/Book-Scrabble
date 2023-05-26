package Scrabble.Model.CommunicationServer;


import Scrabble.Model.Components.Board;
import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;
import Scrabble.Model.Server.MyServer;

import java.util.*;

public class HostModel implements Model {
    public static HostModel hostmodel;
    private GameServer gameServer;
    private volatile boolean gameOver;
    public Board board;
    public Tile.Bag bag;
    public Player player;
    private Map<Integer, Player> turnIndexToPlayerMap;
    private List<Player> players;
    private HostHandler hostHandler;
    private MyServer myServer;

    public int currentPlayerTurnIndex;
    private String bsIP;
    private int bsPort;
    private final int PORT_NUMBER = 5124;

    public HostModel() {
        hostHandler = new HostHandler();
        board = Board.getBoard();
        bag = Tile.Bag.getBag();
        players = new ArrayList<>();
        gameOver = false;
        player = new Player();
        currentPlayerTurnIndex = 0;
        turnIndexToPlayerMap = new HashMap<>();
        players.add(player);
        gameServer = new GameServer(PORT_NUMBER, hostHandler);
    }

    public static HostModel getHostModel() {
        if (hostmodel == null)
            hostmodel = new HostModel();
        return hostmodel;
    }

    public void createNewGameGuestMode() {
        // Single Guest Game
        this.player.setTurnIndex(0);
        dealTilesToFullHand(this.player);
    }

    public void createNewGameHostMode() {
        players.sort(Comparator.comparing(Player::getDrawnTile)); //get the order of the players
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setTurnIndex(i);
            turnIndexToPlayerMap.put(i, players.get(i));
            dealTilesToFullHand(players.get(i));
        }
    }

    public void dealTilesToFullHand(Player p) {
        //keep the player with 7 tiles.
        int bagSize = bag.size();
        while (p.getTiles().size() < 7 && bagSize > 0) {
            p.addTile(bag.getRand());
        }
    }

    public void updateBoardState() {
        String message = "updateBoardState" + ":" + Converter.TileArrayToString(board.getTiles());
        gameServer.broadcast(message);
    }

    public void tryPlaceWord(String message) {
        String[] elements = message.split(":");
        String guestID = elements[1];
        Word w = Converter.stringToWord(elements[2]);
        query(guestID, w);
    }

    public void query(String guestID, Word word) {
        boolean res = gameServer.communicateBSServer(bsIP, bsPort, "query", word.toString());
        String message = "query" + ":" + res + ":" + Converter.wordToString(word);
        gameServer.directMessageToGuest(guestID, message);
    }

    public void challenge(String guestID, Word word) {
        boolean res = gameServer.communicateBSServer(bsIP, bsPort, "challenge", word.toString());
        String message = "challenge" + ":" + res + ":" + Converter.wordToString(word);
        gameServer.directMessageToGuest(guestID, message);
    }

    public void placeWordOnBoard(String message) {
        String[] elements = message.split(":");
        int score = board.tryPlaceWord(Converter.stringToWord(elements[1]));
        turnIndexToPlayerMap.get(currentPlayerTurnIndex).setScore(score);
        updateBoardState();
        setTurnIndexToNextPlayer(currentPlayerTurnIndex);
    }

    public void placeWordOnBoard(Word word) {
        int score = board.tryPlaceWord(word);
        turnIndexToPlayerMap.get(currentPlayerTurnIndex).setScore(score);
        updateBoardState();
        setTurnIndexToNextPlayer(currentPlayerTurnIndex);
    }

    public void setScore(String guestID, int score) {
        String message = "setScore" + ":" + score;
        gameServer.directMessageToGuest(guestID, message);
    }

    public void isWinner(Player player) {
        String message = "isWinner" + ":" + player.getName();
        gameServer.broadcast(message);
    }

    public void gameOver() {
        String message = "gameOver" + ":";
        gameServer.broadcast(message);
        gameServer.close();
    }

    public void playerQuit(String guestName) {
        String message = "quitGame" + ":" + guestName;
        gameServer.broadcast(message);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getCurrentPlayerTurnIndex() {
        return currentPlayerTurnIndex;
    }

    public void setCurrentPlayerTurnIndex(int currentPlayerTurnIndex) {
        this.currentPlayerTurnIndex = currentPlayerTurnIndex;
    }

    public void setTurnIndexToNextPlayer(int currentPlayerTurnIndex) {
        this.currentPlayerTurnIndex = (currentPlayerTurnIndex + 1) % players.size();
    }


    @Override
    public Tile[][] getBoard() {
        return Board.getBoard().getTiles();
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
