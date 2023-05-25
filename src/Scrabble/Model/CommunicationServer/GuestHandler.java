package Scrabble.Model.CommunicationServer;

import Scrabble.Model.Components.Player;
import Scrabble.Model.Server.ClientHandler;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class GuestHandler implements ClientHandler {
    private Scanner in;
    private PrintWriter out;
    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient);
        if(in.hasNext()){
            String inputMessage = in.next();

        }
    }
    public void responseToClient(String inputMessage) {
        String functionName = inputMessage.split(":")[0];
        switch (functionName){
            case(""):

                break;

            case ("tryPlaceWord"):
              //  tryPlaceWord();
                break;
        }
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}
