package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Player;
import Scrabble.Model.Components.Tile;
import Scrabble.Model.Components.Word;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class GuestModel implements Model, Observer {
    //local variables
    Player player;
    Tile[][] currBoard;
    Socket socket;
    GuestHandler guestHandler;
    boolean challengeChoice;

    /**
     * The GuestModel function is a constructor that initializes the board and players variables.
     */
    private GuestModel(String ip, int port) {
        currBoard = new Tile[15][15];
        player = new Player();
        setConnectionToServer(ip, port);
        guestHandler = new GuestHandler(this.player.getGuestID(), socket);
        guestHandler.addObserver(this);
    }

    private void setConnectionToServer(String ip, int port) {
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public void passTurnToNextPlayer(int thisPlayerIndex) {
        String message = "passTurnToNextPlayer" + ":" + String.valueOf(thisPlayerIndex);
        guestHandler.sendMessageToHost(message);
    }

    public void tryPlaceWord(Word word) {
        String outputMessage = "tryPlaceWord" + ":" + this.player.getGuestID() + ":" + Converter.wordToString(word);
        guestHandler.sendMessageToHost(outputMessage);
    }

    public void query(Word word) {
        StringBuilder sb = new StringBuilder("query" + ":");
        sb.append(player.getGuestID()).append(":");
        sb.append(Converter.wordToString(word));
        guestHandler.sendMessageToHost(sb.toString());
    }

    public void challenge(Word word) {
        StringBuilder sb = new StringBuilder("challenge" + ":");
        sb.append(player.getGuestID()).append(":");
        sb.append(Converter.wordToString(word));
        guestHandler.sendMessageToHost(sb.toString());
    }

    public void myHandState() {
        StringBuilder sb = new StringBuilder("myHandState").append(":").append(this.player.getGuestID()).append(":");
        sb.append(String.valueOf(this.player.getTiles().size())).append(":");
        for (int i = 0; i < this.player.getTiles().size(); i++) {
            if (i == this.player.getTiles().size() - 1) {
                sb.append(this.player.getTiles().get(i));
            } else {
                sb.append(this.player.getTiles().get(i)).append(":");
            }
        }
        guestHandler.sendMessageToHost(sb.toString());
    }

    public void quitGame() {
        String message = "quitGame" + ":" + player.getName();
        guestHandler.sendMessageToHost(message);
    }

    public void displayWinner(String winner) {
        System.out.println("The winner of the game is " + winner);
        guestHandler.close();

    }

    public void placeWordOnBoard(Word word) {
        String message = "placeWordOnBoard" + ":" + Converter.wordToString(word);
        guestHandler.sendMessageToHost(message);
    }

    public void handleFailQuery(Word word) {
        if (challengeChoice) {
            challenge(word);
        } else {
            passTurnToNextPlayer(player.getTurnIndex());
        }
    }

    /**
     * The getPlayer function returns the current player.
     *
     * @return The current player
     */
    public Player getPlayer() {
        return player;
    }

    @Override
    public Tile[][] getBoard() {
        return currBoard;
    }

    @Override
    public int getScore(Player player) {
        return player.getScore();
    }

    public boolean isChallengeChoice() {
        return challengeChoice;
    }

    public void setChallengeChoice(boolean challengeChoice) {
        this.challengeChoice = challengeChoice;
    }

    @Override
    public List<Tile> getHand(Player player) {
        return player.getTiles();
    }

    @Override
    public void update(Observable o, Object arg) {
        GuestHandler gh = (GuestHandler) o;
        String message = (String) arg;
        String[] elements = message.split(":");
        switch (elements[0]) {
            case ("score"):
                this.player.setScore(Integer.parseInt(elements[1]));
                break;
            case ("winner"):
                displayWinner(elements[1]);
                break;
            case ("updateBoardState"):
                this.currBoard = Converter.stringToTileArray(elements[1]);
                break;
            case ("query"):
                boolean res = false;
                if (elements[1].equals("true")) {
                    placeWordOnBoard(Converter.stringToWord(elements[3]));
                } else {
                    handleFailQuery(Converter.stringToWord(elements[3]));
                }
                break;
            case ("challenge"):
                if (elements[1].equals("true")) {
                    placeWordOnBoard(Converter.stringToWord(elements[2]));
                } else {
                    passTurnToNextPlayer(this.player.getTurnIndex());
                }
        }
    }

}
