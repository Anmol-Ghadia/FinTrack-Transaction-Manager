package persistence;

import model.User;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
    Reader class for reading user from JSON file
*/
public class JsonReader {
    private String destination;

    // REQUIRES: a valid file path
    // MODIFIES: this
    // EFFECTS: creates a new json reader with given file path
    public JsonReader(String destination) {
        this.destination = destination;
    }

    // EFFECTS: returns the user as saved in file, if file reading causes error then IOException is thrown
    public User read() throws IOException {
        JSONObject jsonObject = new JSONObject(readFile());
        return new User(jsonObject);
    }

    // EFFECTS: returns the file contents, if file reading causes error then IOException is thrown
    private String readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(destination), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }
        return contentBuilder.toString();
    }

    // EFFECTS: returns the file path for the reader
    public String getDestination() {
        return destination;
    }
}
