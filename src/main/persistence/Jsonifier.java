package persistence;


import model.DueDate;
import model.Priority;
import model.Tag;
import model.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

// Converts model elements to JSON objects
public class Jsonifier {

    // EFFECTS: returns JSON representation of tag
    public static JSONObject tagToJson(Tag tag) {
        JSONObject tagJson = new JSONObject();

        tagJson.put("name", tag.getName());

        return tagJson;
    }

    // EFFECTS: returns JSONArray representation of tags
    public static JSONArray tagsToJson(Set<Tag> tags) {
        JSONArray tagsJson = new JSONArray();

        for (Tag tag : tags) {
            tagsJson.put(tagToJson(tag));
        }
        return tagsJson;
    }

    // EFFECTS: returns JSON representation of priority
    public static JSONObject priorityToJson(Priority priority) {
        JSONObject priorityJson = new JSONObject();

        if (priority.isImportant()) {
            priorityJson.put("important", true);
        }
        if (priority.isUrgent()) {
            priorityJson.put("urgent", true);
        }
        if (!priority.isImportant()) {
            priorityJson.put("important", false);
        }
        if (!priority.isUrgent()) {
            priorityJson.put("urgent", false);
        }

        return priorityJson;
    }

    // EFFECTS: returns JSON respresentation of dueDate
    public static JSONObject dueDateToJson(DueDate dueDate) {
        JSONObject dueDateJson = new JSONObject();

        if (dueDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dueDate.getDate());

            dueDateJson.put("year", calendar.get(Calendar.YEAR));
            dueDateJson.put("month", calendar.get(Calendar.MONTH));
            dueDateJson.put("day", calendar.get(Calendar.DAY_OF_MONTH));
            dueDateJson.put("hour", calendar.get(Calendar.HOUR_OF_DAY));
            dueDateJson.put("minute", calendar.get(Calendar.MINUTE));

            return dueDateJson;

        }
        return null;
    }

    // EFFECTS: returns JSON representation of task
    public static JSONObject taskToJson(Task task) {
        JSONObject taskJson = new JSONObject();

        taskJson.put("description", task.getDescription());
        taskJson.put("tags", tagsToJson(task.getTags()));
        taskJson.put("priority", priorityToJson(task.getPriority()));
        taskJson.put("status", task.statusInsertUnderscore());


        if (task.getDueDate() != null) {
            taskJson.put("due-date", dueDateToJson(task.getDueDate()));

        } else {
            taskJson.put("due-date", JSONObject.NULL);

        }


        return taskJson;
    }


    // EFFECTS: returns JSON array representing list of tasks
    public static JSONArray taskListToJson(List<Task> tasks) {
        JSONArray tasksJson = new JSONArray();

        for (Task task : tasks) {
            tasksJson.put(taskToJson(task));
        }
        return tasksJson;
    }
}

