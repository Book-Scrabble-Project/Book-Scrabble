package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class GuestHandler implements ClientHandler {
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {

    }

    @Override
    public void close() {

    }
}
