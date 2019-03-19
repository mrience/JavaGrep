package Reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class TextFileReader implements Reader {
    private String path;
    private StringBuilder text = new StringBuilder();

    public TextFileReader(String path) {
       this.path = path;
    }

    @Override
    public String read()  {
        File file = new File(path);
        try  (
                Scanner fileInput = new Scanner(new BufferedReader(new FileReader(file)));
        ) {
            while (fileInput.hasNext()) {
                text.append(fileInput.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return text.toString();
    }
}
