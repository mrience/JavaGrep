import Reader.Reader;
import Reader.ReaderDecider;
import exceptions.IncorrectPathException;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrepProcessor {
    private final Scanner sc = new Scanner(System.in);

    public void process() {
        String text = "";
        System.out.println("Write path to your text file or website address (starting http:// or https://): ");
            String path = sc.nextLine();
        try {
            text = readInput(path);
        } catch (IncorrectPathException e) {
            e.printStackTrace();
            System.exit(1);
        }
        List<String> wordsFromText = matchTextToList(text);
        System.out.println("write space separated words to checkout (eg. lorem ipsum):");
        List<String> wordsToCheckout = matchTextToList(sc.nextLine());
        sc.close();
        for (String word : wordsToCheckout) {
            if (text.contains(word)) {
                int wordCounter = Collections.frequency(wordsFromText, word);
                System.out.format("Word '%s' does appears in text %d times!\n", word, wordCounter);
            } else System.out.format("Word '%s' does not appear in text!\n", word);
        }
    }

    private String readInput(String path) throws IncorrectPathException {
        Reader reader = new ReaderDecider().getReader(path);
        return reader.read();
    }

    private List <String> matchTextToList(String text) {
        return Stream.of(text.split("^?[.,?! \n\"]+?")).collect(Collectors.toList());
    }

}
