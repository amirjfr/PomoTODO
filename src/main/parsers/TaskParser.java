package parsers;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Represents Task parser
public class TaskParser {

    // EFFECTS: iterates over every JSONObject in the JSONArray represented by the input
    // string and parses it as a task; each parsed task is added to the list of tasks.
    // Any task that cannot be parsed due to malformed JSON data is not added to the
    // list of tasks.
    // Note: input is a string representation of a JSONArray
    public List<Task> parse(String input) {
        List<Task> tasks = new ArrayList<>();
        JSONArray tasksArray = new JSONArray(input);

        for (Object object : tasksArray) {
            JSONObject taskJson = (JSONObject) object;

            if (allKeysPresent(taskJson) && allDueDateKeyPresent(taskJson)) {
                String description = taskJson.get("description").toString();
                Task task = new Task(description);
                parseStatus(task, taskJson);
                parseDueDate(task, taskJson);
                parsePriority(task, taskJson);
                parseTags(task, taskJson);
                tasks.add(task);

            }
        }

        return tasks;
    }

    //EFFECTS: sets the status of the given task
    private void parseStatus(Task task, JSONObject taskJson) {
        if (taskJson.get("status").toString().equals("TODO")) {
            task.setStatus(Status.TODO);
        } else if (taskJson.get("status").toString().equals("IN_PROGRESS")) {
            task.setStatus(Status.IN_PROGRESS);
        } else if (taskJson.get("status").toString().equals("DONE")) {
            task.setStatus(Status.DONE);
        } else {
            task.setStatus(Status.UP_NEXT);
        }

    }
    //EFFECTS: sets the duedate of the given task
    private void parseDueDate(Task task, JSONObject taskJson) {

        if (taskJson.get("due-date") != JSONObject.NULL) {
            JSONObject dueDateJson = (JSONObject) taskJson.get("due-date");
            Calendar calendar = Calendar.getInstance();
            int year = dueDateJson.getInt("year");
            int month = dueDateJson.getInt("month");
            int day = dueDateJson.getInt("day");
            int hour = dueDateJson.getInt("hour");
            int minute = dueDateJson.getInt("minute");
            DueDate parsedDueDate = new DueDate();
            calendar.set(year, month, day, hour, minute);
            parsedDueDate.setDueDate(calendar.getTime());
            task.setDueDate(parsedDueDate);
        }


    }
    //EFFECTS: sets the priority of the given task
    private void parsePriority(Task task, JSONObject taskJson) {
        JSONObject priorityJson = taskJson.getJSONObject("priority");
        Priority priority = new Priority();
        priority.setImportant(priorityJson.getBoolean("important"));
        priority.setUrgent(priorityJson.getBoolean("urgent"));
        task.setPriority(priority);

    }

    //EFFECTS: adds the tags to the given task
    private void parseTags(Task task, JSONObject taskJson) {
        JSONArray tagsJson = taskJson.getJSONArray("tags");
        for (Object object : tagsJson) {
            JSONObject tagJson = (JSONObject) object;
            Tag tag = new Tag(tagJson.get("name").toString());
            task.addTag(tag);
        }
    }
    //EFFECTS: returns true if the given JSONObject contains all the required keys for representing a task otherwise false
    private boolean allKeysPresent(JSONObject taskJson) {

        if (!taskJson.has("description")
                || !taskJson.has("status")
                || !taskJson.has("due-date")
                || !taskJson.has("tags")
                || !taskJson.has("priority")

        ) {
            return false;
        }
        JSONObject priorityJson = taskJson.getJSONObject("priority");
        if (!priorityJson.has("important")
                || !priorityJson.has("urgent")) {
            return false;
        }

        return true;
    }
    //EFFECTS: returns true if the given JSONObject contains all the required keys for due-date otherwise false
    private boolean allDueDateKeyPresent(JSONObject taskJson) {
        if (taskJson.get("due-date") != JSONObject.NULL) {

            JSONObject dueDateJson = (JSONObject) taskJson.get("due-date");
            return (dueDateJson.has("year")
                    && dueDateJson.has("month")
                    && dueDateJson.has("day")
                    && dueDateJson.has("hour")
                    && dueDateJson.has("minute"));
        }
        return true;

    }

}
