package Scrabble.Model;

import Scrabble.server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;

public class GuestHandler implements ClientHandler {
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {

    }
    @Override
    public void close() {
        in.close();
        out.close();

    }



}
