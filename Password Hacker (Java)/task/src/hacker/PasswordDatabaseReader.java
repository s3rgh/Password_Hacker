package hacker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class PasswordDatabaseReader {
    public static Stream<String> getStringStream() throws IOException {
        URL url = new URL("https://stepik.org/media/attachments/lesson/255258/passwords.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
        return reader.lines();
    }
}
