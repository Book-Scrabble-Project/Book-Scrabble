package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class HostHandler implements ClientHandler {
    private Scanner in;
    private PrintWriter out;

    public HostHandler() {
    }
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
            in = new Scanner(inFromclient);
            out = new PrintWriter(outToClient);
            String inputMessage = in.next();

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
