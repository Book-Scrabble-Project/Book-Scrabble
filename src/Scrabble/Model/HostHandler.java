package Scrabble.Model;

import Scrabble.server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class HostHandler implements ClientHandler {
    private final Socket client;
    private Scanner in;
    private PrintWriter out;

    public HostHandler(Socket client) {
        this.client = client;
    }

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient);

    }
    public void handleRequest(){

    }
    public void handleResponse(){

    }
    @Override
    public void close() {
        in.close();
        out.close();

    }



}
