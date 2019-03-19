package Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

public class WebsiteReader implements Reader {
    private String path;
    private String text;

    public WebsiteReader (String path) {
        this.path = path;
    }

    public String read(){
        URL url = null;
        try {
            url = new URL(path);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        ) {
            text = reader.lines().collect(Collectors.joining());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
