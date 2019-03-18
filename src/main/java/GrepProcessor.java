import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrepProcessor {
    private Scanner sc = new Scanner(System.in);
    private String inputWords;
    private List<String> wordsFromText;
    private int wordCounter;
    private File file;
    private String text = "";
    private List <String> wordsToCheckout;

    public GrepProcessor() {
    }

    void process() {
        System.out.println("Write path to Your file: ");
        try {
            readFileAndCheckExtention(sc.nextLine());
        } catch (UnsupportedTypeException e) {
            e.printStackTrace();
        }
        getTextFromFile();
        wordsFromText = textToListMatcher(text);
        System.out.println("write space separated wordsFromText to checkout (eg. lorem ipsum):");
        inputWords = sc.nextLine();
        wordsToCheckout = Arrays.asList(inputWords.split(" "));
        for (String word : wordsToCheckout) {
            if (text.contains(word)) {
                wordCounter = Collections.frequency(wordsFromText, word);
                System.out.format("Word '%s' does appears in text %d times!\n", word, wordCounter);
            } else System.out.format("Word '%s' does not appear in text!\n", word);
        }
    }

    private List <String> textToListMatcher(String text) {
        return Stream.of(text.split("^?[.,?! \n\"]+?")).map(elem -> new String(elem)).collect(Collectors.toList());
    }

    private void getTextFromFile() {
        try  (
                Scanner fileInput = new Scanner(new BufferedReader(new FileReader(file)));
        ) {
            while (fileInput.hasNext()) {
                text = text.concat(fileInput.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readFileAndCheckExtention(String path) throws UnsupportedTypeException {
        file = new File(path);
        if (!file.getName().endsWith(".txt"))
            throw new UnsupportedTypeException("Type of file is not txt");
    }
}
