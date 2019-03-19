package Reader;

import exceptions.IncorrectPathException;

public class ReaderDecider {
    public Reader getReader(String path) throws IncorrectPathException {
        if (path.endsWith(".txt")) {
            return new TextFileReader(path);
        } else if (path.startsWith("http://") || path.startsWith("https://")) {
            return new WebsiteReader(path);
        } else throw new IncorrectPathException("Incorrect path!");
    }
}
