package model;

import model.exceptions.EmptyStringException;
import model.exceptions.InvalidProgressException;
import model.exceptions.NullArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class TestProject {
    private Project project;
    private Project project2;
    private Task task;
    private Task task2;
    private Task task3;
    private Task task4;
    private Status status;



    @BeforeEach
    void setup() {
        try {
            project = new Project("computer project");
        } catch (EmptyStringException e) {
            fail("Caught an unexpected EmptyStringException");
        }
        try {
            project2 = new Project("science project");
        } catch (EmptyStringException e) {
            fail("Caught an unexpected EmptyStringException");
        }
        try {
            task = new Task("important");
        } catch (EmptyStringException e) {
            fail("Caught an unexpected EmptyStringException");
        }
        try {
            task2 = new Task("very important");
        } catch (EmptyStringException e) {
            fail("Caught an unexpected EmptyStringException");
        }
        status = Status.DONE;
    }

    @Test
    void constructorTest() {
        assertEquals("computer project", project.getDescription());
        assertEquals("science project", project2.getDescription());
        try {
            new Project("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }
        try {
            new Project(null);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }

    }

    @Test
    void addTest() {
        assertFalse(project.contains(task));
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertTrue(project.contains(task));
        assertEquals(1, project.getNumberOfTasks());
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertTrue(project.contains(task));
        assertEquals(1, project.getNumberOfTasks());
        try {
            project.add(project);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }

        try {
            project.add(project2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        try {
            project.add(null);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }

    }

    @Test
    void removeTest() {
        assertFalse(project.contains(task));
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertTrue(project.contains(task));
        try {
            project.add(project2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        try {
            project.remove(project2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        try {
            project.remove(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertFalse(project.contains(task));
        try {
            project.remove(task);

        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }

        try {
            project.remove(null);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }

    }

    @Test
    void getDescriptionTest() {
        assertEquals("computer project", project.getDescription());
        assertEquals("science project", project2.getDescription());

    }

    @Test
    void getTasksTest() {

        try {
            project.getTasks();
            fail("failed to throw UnsupportedOperationException");
        } catch (UnsupportedOperationException e) {

        }

    }

    @Test
    void getProgressTest() {
        assertEquals(0, project.getProgress());
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        try {
            project.add(task2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertEquals(0, project.getProgress());
        task.setProgress(60);
        task2.setProgress(10);
        assertEquals(35,project.getProgress());
        task3 = new Task("hello");
        project2.add(task3);
        task3.setProgress(40);
        project.add(project2);
        assertEquals(36, project.getProgress());
    }

    @Test
    void getNumberOfTaskTest() {
        assertEquals(0, project.getNumberOfTasks());
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertEquals(1, project.getNumberOfTasks());
        try {
            project.add(task2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertEquals(2, project.getNumberOfTasks());

    }

    @Test
    void isCompletedTest() {
        assertFalse(project.isCompleted());
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        try {
            project.add(task2);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertFalse(project.isCompleted());

        try {
            task.setProgress(100);
        } catch (InvalidProgressException e) {
            fail("Caught an unexpected InvalidProgressException");
        }
        assertFalse(project.isCompleted());
        try {
            task2.setProgress(100);
        } catch (InvalidProgressException e) {
            fail("Caught an unexpected InvalidProgressException");
        }
        assertTrue(project.isCompleted());

    }

    @Test
    void containsTest() {
        assertFalse(project.contains(task));
        try {
            project.add(task);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");
        }
        assertFalse(project.contains(task2));
        assertTrue(project.contains(task));
        try {
            project.contains(null);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }
    }


    @Test
    void equalHashCodeTest() {

        assertEquals(project,new Project("computer project"));
        assertTrue(project.equals(project));
        assertFalse(project.equals(project2));
        assertFalse(project.equals(task));
        project.hashCode();
    }

    @Test
    void getEstimatedTimeToCompleteTest() {
        assertEquals(0,project.getEstimatedTimeToComplete());
        project.add(task);
        task.setEstimatedTimeToComplete(10);
        assertEquals(10,project.getEstimatedTimeToComplete());
        project2.add(task2);
        task2.setEstimatedTimeToComplete(5);
        project.add(project2);
        assertEquals(15,project.getEstimatedTimeToComplete());
    }

    @Test
    void iteratorTest() {
        Priority priority = new Priority(1);
        Priority priority2 = new Priority(2);
        Priority priority3 = new Priority(3);
        task3 = new Task("hi");
        task4 = new Task("ok");

        project.add(task2);
        project.add(task3);
        project.add(task);
        project.add(task4);
        project.add(project2);
        task.setPriority(priority);
        task2.setPriority(priority2);
        task3.setPriority(priority3);
        project2.setPriority(priority3);

        for (Todo task: project) {
            System.out.println(task.getPriority());
        }

        try {
            project2.iterator().next();
            fail("failed to throw NoSuchElementException");
        } catch (NoSuchElementException e) {

        }




        }

    }
