package Scrabble.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class BookScrabbleHandler implements ClientHandler {
    Scanner in;
    PrintWriter out;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient) {
        in = new Scanner(inFromclient);
        out = new PrintWriter(outToClient);
        String input = in.next();
        DictionaryManager dm = DictionaryManager.get();
        if (input.startsWith("Q,")) {
            input = input.replaceFirst("Q,", "");
            boolean t = dm.query(input.split(","));
            out.println(t + "\n");
            out.flush();
        } else {
            input = input.replaceFirst("C,", "");
            boolean t = dm.challenge(input.split(","));
            out.println(t + "\n");
            out.flush();
        }
        close();
    }

    @Override
    public void close() {
        in.close();
        out.close();
    }
}
