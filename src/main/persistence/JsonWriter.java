package persistence;

import model.User;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
    Writer class for Writing User to JSON file
*/
public class JsonWriter {
    private final String destination;

    // MODIFIES: this
    // EFFECTS: creates a new Json writer with given file path.
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // EFFECTS: writes the user in the file, throws FileNotFoundException if file is not found
    public void write(User user) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(destination);
        writer.print(user.toJsonString());
        writer.close();
    }

    // EFFECTS: returns the file path for the json writer
    public String getDestination() {
        return destination;
    }
}
