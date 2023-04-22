package Scrabble.Model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IOSearcher {

    public static boolean search(String word, String... fileNames) {
        for (String s : fileNames) {
            try {
                Stream<String> searcher = Files.lines(Paths.get(s));
                if (searcher.anyMatch(t -> t.contains(word))) {
                    return true;
                }
                searcher.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
