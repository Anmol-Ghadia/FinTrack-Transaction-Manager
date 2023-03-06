package persistence;

import model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class JsonReader {
    private String destination;

    public JsonReader(String destination) {
        this.destination = destination;
    }

    public User read() throws IOException {
        JSONObject jsonObject = new JSONObject(readFile());
        return new User(jsonObject);
    }

    private String readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(destination), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    public String getDestination() {
        return destination;
    }
}
