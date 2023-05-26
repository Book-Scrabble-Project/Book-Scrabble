package Scrabble.Model.CommunicationServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Observable;
import java.util.Scanner;


public class GuestHandler extends Observable {
    private String guestID;
    private volatile boolean connectionAlive;
    private Scanner in;
    private PrintWriter out;

    public GuestHandler(String guestID, Socket socket) {
        this.guestID = guestID;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
            while (connectionAlive) {
                String message = getMessageFromHost();
                if (message != null) {
                    String answerToHost = handleHostMessage(message);
                    out.println(message);
                    out.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public String handleHostMessage(String message) {
        String[] elements = message.split(":");
        switch (elements[0]) {

            case ("queryResponse"):
                query(elements[1], elements[2]);
                break;

            case ("challengeResponse"):
                challenge(elements[1], elements[2]);
                break;

            case ("setScore"):
                setScore(elements[1]);
                break;

            case ("isWinner"):
                isWinner(elements[1]);
                break;

            case ("gameOver"):
                close();
                break;
        }
        return null;
    }

    public void query(String response, String word) {
        hasChanged();
        notifyObservers("query" + ":" + response + ":" + word);
    }

    public void challenge(String response, String word) {
        hasChanged();
        notifyObservers("challenge" + ":" + response + ":" + word);
    }

    public void setScore(String score) {
        hasChanged();
        notifyObservers("score" + ":" + score);
    }

    public void isWinner(String winner) {
        hasChanged();
        notifyObservers("winner" + ":" + winner);
        close();
    }

    public String getMessageFromHost() {
        String message = null;
        if (in.hasNext()) {
            message = in.next();
        }

        return message;
    }

    public void sendMessageToHost(String message) {
        out.println(message);
        out.flush();
    }

    public boolean isConnectionAlive() {
        return connectionAlive;
    }

    public void setConnectionAlive(boolean connectionAlive) {
        this.connectionAlive = connectionAlive;
    }

    public void close() {
        setConnectionAlive(false);
        in.close();
        out.close();
    }
}
