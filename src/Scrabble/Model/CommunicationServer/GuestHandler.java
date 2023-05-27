package Scrabble.Model.CommunicationServer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;


public class GuestHandler extends Observable {
    private String guestID;
    private volatile boolean connectionAlive;
    private Scanner in;
    private PrintWriter out;

    public GuestHandler(String guestID, InputStream inFromclient, OutputStream outToClient) {
        this.guestID = guestID;
        try {
            in = new Scanner(inFromclient);
            out = new PrintWriter(outToClient);
            while (connectionAlive) {
                String message = getMessageFromHost();
                if (message != null) {
                    String answerToHost = handleHostMessage(message);
                    out.println(message);
                    out.flush();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public String handleHostMessage(String message) {
        String[] elements = message.split(":");
        switch (elements[0]) {

            case ("query"):
                query(elements[1], elements[2]);
                break;

            case ("challenge"):
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
        setChanged();
        notifyObservers("query" + ":" + response + ":" + word);
    }

    public void challenge(String response, String word) {
        setChanged();
        notifyObservers("challenge" + ":" + response + ":" + word);
    }

    public void setScore(String score) {
        setChanged();
        notifyObservers("score" + ":" + score);
    }

    public void isWinner(String winner) {
        setChanged();
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
