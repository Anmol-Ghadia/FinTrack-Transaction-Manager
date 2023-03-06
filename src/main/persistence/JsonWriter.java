package persistence;

import model.User;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class JsonWriter {
    private String destination;

    // MODIFIES: this
    // EFFECTS: creates a new Json writer with given file path.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: creates new writer, writes user in JSON file and closes the file
    public void write(User user) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(new File(destination));
        writer.print(user.toJsonString());
        writer.close();
    }

    // EFFECTS: returns the destination of the json writer
    public String getDestination() {
        return destination;
    }
}
