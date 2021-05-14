package model;

import model.exceptions.EmptyStringException;
import model.exceptions.NullArgumentException;

import java.util.*;

// Represents a Project, a collection of zero or more Tasks
// Class Invariant: no duplicated task; order of tasks is preserved
public class Project extends Todo implements Iterable<Todo> {
    private String description;
    private List<Todo> tasks;

    // MODIFIES: this
    // EFFECTS: constructs a project with the given description
    //     the constructed project shall have no tasks.
    //  throws EmptyStringException if description is null or empty
    public Project(String description) {
        super(description);
        if (description.length() == 0) {
            throw new EmptyStringException("Cannot construct a project with no description");
        }
        this.description = description;
        this.tasks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: task is added to this project (if it was not already part of it)
    //   throws NullArgumentException when task is null
    public void add(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task cannot be null");
        }
        if (!contains(task) && !this.equals(task)) {
            tasks.add(task);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes task from this project
    //   throws NullArgumentException when task is null
    public void remove(Todo task) {
        if (task == null) {
            throw new NullArgumentException("task cannot be null");
        }
        if (contains(task)) {
            tasks.remove(task);
        }
    }

    // EFFECTS: returns the description of this project
    public String getDescription() {
        return description;
    }

    @Override
    public int getEstimatedTimeToComplete() {
        int allTasksETc = 0;
        if (getNumberOfTasks() == 0) {
            return allTasksETc;
        } else {
            for (Todo task : tasks) {
                allTasksETc += task.getEstimatedTimeToComplete();
            }
            return allTasksETc;
        }
    }


    // EFFECTS: returns an unmodifiable list of tasks in this project.
    @Deprecated
    public List<Task> getTasks() {
        throw new UnsupportedOperationException();
    }

    // EFFECTS: returns an integer between 0 and 100 which represents
//     the percentage of completion (rounded down to the nearest integer).
//     the value returned is the average of the percentage of completion of
//     all the tasks and sub-projects in this project.
    public int getProgress() {
        int numberOfTasks = getNumberOfTasks();
        if (numberOfTasks == 0) {
            return 0;
        } else {

            int allTasksProgress = 0;

            for (Todo task : tasks) {
                allTasksProgress += task.getProgress();
            }
            int totalProgress = allTasksProgress / numberOfTasks;
            return totalProgress;
        }
    }


    // EFFECTS: returns the number of tasks (and sub-projects) in this project
    public int getNumberOfTasks() {
        return tasks.size();
    }

    // EFFECTS: returns true if every task (and sub-project) in this project is completed, and false otherwise
//     If this project has no tasks (or sub-projects), return false.
    public boolean isCompleted() {
        return getNumberOfTasks() != 0 && getProgress() == 100;
    }

    // EFFECTS: returns true if this project contains the task
    //   throws NullArgumentException when task is null
    public boolean contains(Todo task) {
        if (task == null) {
            throw new NullArgumentException("Illegal argument: task is null");
        }
        return tasks.contains(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(description, project.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    public Iterator<Todo> iterator() {
        return new TodoIterator();
    }

    //MODIFIES: this
    //EFFECTS: constructs TodoIterator with currentCounter and totalCounter initialized at zero
    // importantUrgentExhausted, importantExhausted and urgentExhausted initialized with false.

    private class TodoIterator implements Iterator<Todo> {

        private boolean importantUrgentExhausted = false;
        private boolean importantExhausted = false;
        private boolean urgentExhausted = false;
        private int currentCounter = 0;
        private int totalCounter = 0;

        @Override
        public boolean hasNext() {
            return totalCounter <= tasks.size() - 1;
        }

        @Override
        public Todo next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            for (int i = currentCounter; i < tasks.size(); i++) {
                if (checkAll(i)) {
                    return tasks.get(i);
                }
            }

            resetCounter();

            return next();
        }

        //EFFECTS:
        // if importantUrgentExhausted is false returns checkImportantUrgent otherwise
        // if importantExhausted is false returns checkImportant otherwise
        // if urgentExhausted is false returns checkUrgent otherwise
        // returns checkRest
        private boolean checkAll(int i) {
            if (!importantUrgentExhausted) {
                return checkImportantUrgent(i);
            } else if (!importantExhausted) {
                return checkImportant(i);
            } else if (!urgentExhausted) {
                return checkUrgent(i);
            }
            return checkRest(i);
        }

        //EFFECTS: resets current counter to zero
        // if importantUrgentExhausted is false updates its value to true otherwise
        // if importantExhausted is false updates its value to true otherwise
        // updates urgentExhausted value to true
        private void resetCounter() {
            currentCounter = 0;
            if (!importantUrgentExhausted) {
                importantUrgentExhausted = true;
            } else if (!importantExhausted) {
                importantExhausted = true;
            } else {
                urgentExhausted = true;
            }
        }

        //EFFECTS: checks if the task or sub-project at given index is neither important nor urgent
        // if that's the case then adds 1 to the current counter and total counter and returns true,
        // otherwise returns false
        private boolean checkRest(int i) {
            if (!tasks.get(i).getPriority().isImportant() && !tasks.get(i).getPriority().isUrgent()) {
                currentCounter = i + 1;
                totalCounter++;
                return true;
            }
            return false;
        }

        //EFFECTS: checks if the task or sub-project at given index is urgent
        // if that's the case then adds 1 to the current counter and total counter and returns true,
        // otherwise returns false
        private boolean checkUrgent(int i) {
            if (!tasks.get(i).getPriority().isImportant() && tasks.get(i).getPriority().isUrgent()) {
                currentCounter = i + 1;
                totalCounter++;
                return true;
            }
            return false;
        }

        //EFFECTS: checks if the task or sub-project at given index is important
        // if that's the case then adds 1 to the current counter and total counter and returns true,
        // otherwise returns false
        private boolean checkImportant(int i) {
            if (tasks.get(i).getPriority().isImportant() && !tasks.get(i).getPriority().isUrgent()) {
                currentCounter = i + 1;
                totalCounter++;
                return true;
            }
            return false;
        }

        //EFFECTS: checks if the task or sub-project at given index is both important and urgent
        // if that's the case then adds 1 to the current counter and total counter and returns true,
        // otherwise returns false
        private boolean checkImportantUrgent(int i) {
            if (tasks.get(i).getPriority().isImportant() && tasks.get(i).getPriority().isUrgent()) {
                currentCounter = i + 1;
                totalCounter++;
                return true;
            }
            return false;
        }

    }
}



