package utility;

import model.Task;
import parsers.TaskParser;
import persistence.Jsonifier;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

// File input/output operations
public class JsonFileIO {
    public static final File jsonDataFile = new File("./resources/json/tasks.json");

    // EFFECTS: attempts to read jsonDataFile and parse it
    //           returns a list of tasks from the content of jsonDataFile
    public static List<Task> read() {
        TaskParser taskParser = new TaskParser();

        try {
            String content = new String(Files.readAllBytes(jsonDataFile.toPath()));
            return taskParser.parse(content);
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }

        return null;
    }

    // EFFECTS: saves the tasks to jsonDataFile
    public static void write(List<Task> tasks) {
        try {
            Files.write(jsonDataFile.toPath(), Jsonifier.taskListToJson(tasks).toString().getBytes());
        } catch (IOException e) {
            System.out.println("IOException thrown");
        }

    }
}
