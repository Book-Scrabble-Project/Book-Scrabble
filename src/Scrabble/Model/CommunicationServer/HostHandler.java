package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Player;
import Scrabble.Model.Server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Scanner;

public class HostHandler extends Observable implements ClientHandler {
    public Scanner in;
    public PrintWriter out;
    private boolean gameStop;

    public HostHandler() {
        gameStop = false;
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        while (!gameStop) {
            in = new Scanner(inFromclient);
            out = new PrintWriter(outToClient);
            if (in.hasNext()) {
                String inputMessage = in.next();
                handleGuestRequest(inputMessage);
            }
        }
    }

    public void handleGuestRequest(String message) {
        String[] elements = message.split(":");
        switch (elements[0]) {
            case ("welcomeMessage:"):
                HostModel.getHostModel().addPlayer(new Player());
                break;
            case ("passTurnToNextPlayer"):
                HostModel.getHostModel().setTurnIndexToNextPlayer(Integer.parseInt(elements[1]));
                break;
            case ("tryPlaceWord"):
                HostModel.getHostModel().tryPlaceWord(elements[1]);
                break;
            case ("quitGame"):
                HostModel.getHostModel().playerQuit(elements[1]);
                break;
            case ("query"):
                HostModel.getHostModel().query(elements[1], Converter.stringToWord(elements[2]));
                break;
            case ("challenge"):
                HostModel.getHostModel().challenge(elements[1], Converter.stringToWord(elements[2]));
                break;
            case ("placeWordOnBoard"):
                HostModel.getHostModel().placeWordOnBoard(Converter.stringToWord(elements[1]));
                break;
        }
    }

    public void close() {
        in.close();
        out.close();
    }

}
