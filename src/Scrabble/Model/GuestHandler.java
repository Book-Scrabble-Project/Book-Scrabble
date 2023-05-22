package Scrabble.Model;

import Scrabble.server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class GuestHandler implements ClientHandler {
    Scanner in;
    PrintWriter out;
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {

    }
    @Override
    public void close() {
        in.close();
        out.close();

    }



}
