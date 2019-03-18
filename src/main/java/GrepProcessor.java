import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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
    private String path;
    private URL url;
    BufferedReader reader;

    GrepProcessor() {
    }

    public void process() {
        System.out.println("Write path to Your file (starting file://)or webside address (starting http://) : ");
            path = sc.nextLine();
        try {
            readInputAndCheckExtention(path);
        } catch (UnsupportedTypeException | IncorrectPathException e) {
            e.printStackTrace();
        }
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

    private void readInputAndCheckExtention(String path) throws UnsupportedTypeException, IncorrectPathException {
        if (path.startsWith("file://")) {
            readFile();
        } else if (path.startsWith("http://") || path.startsWith("https://")) {
            readWebsite();
        } else throw new IncorrectPathException("Incorrect path!");
    }

    private void readFile() throws UnsupportedTypeException {
        file = new File(path.replace("file://", ""));
        if (!file.getName().endsWith(".txt"))
            throw new UnsupportedTypeException("Type of file is not txt");
        getTextFromFile();
    }

    private void readWebsite() {
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            text = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
