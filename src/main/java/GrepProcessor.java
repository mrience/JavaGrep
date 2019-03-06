import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GrepProcessor {
    private Scanner sc = new Scanner(System.in);
    private String word;
    private List<String> words;
    private int wordCounter;
    private File file;
    private String text = "";

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
        words = textToListMatcher(text);
        System.out.println("write word to checkout:");
        word = sc.nextLine();
        if (text.contains(word)) {
            wordCounter = Collections.frequency(words, word);
            System.out.format("Word '%s' does appears in text %d times!", word, wordCounter);
        }
        else System.out.format("Word '%s' does not appear in text!", word);
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
