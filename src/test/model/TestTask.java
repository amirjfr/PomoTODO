package model;

import model.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import static java.util.Calendar.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTask {
    private Task task;
    private Set<Tag> tags;
    private Priority priority;
    private Status status;
    private DueDate deadline;
    Calendar calendar = Calendar.getInstance();

    @BeforeEach
    void setup() {
        try {
            task = new Task("important");
        } catch (EmptyStringException e) {
            fail("Caught an unexpected EmptyStringException");
        }
        tags = new HashSet<>();
        priority = new Priority(1);
        status = Status.IN_PROGRESS;
        deadline = new DueDate();


    }
    @Test
    void exceptionsTest() {
        try {
            throw new EmptyStringException();
        } catch (EmptyStringException e) {
            System.out.println(e.getMessage());
        }
        try {
            throw new InvalidPriorityLevelException();
        } catch (InvalidPriorityLevelException e) {
            System.out.println(e.getMessage());
        }
        try {
            throw new InvalidTimeException();
        } catch (InvalidTimeException e) {
            System.out.println(e.getMessage());
        }
        try {
            throw new NullArgumentException();
        } catch (NullArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            throw new  NegativeInputException();
        } catch (NegativeInputException e) {
            System.out.println(e.getMessage());
        }
        try {
            throw new InvalidProgressException();
        } catch (InvalidProgressException e) {
            System.out.println(e.getMessage());
        }

    }
    @Test
    void constructorTest() {
        assertEquals("important", task.getDescription());
        try {
            new Task("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }
        try {
            new Task(null);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }

        Task task1 = new Task("hi");
        assertEquals("hi", task1.getDescription());
        assertEquals("TODO", task1.getStatus().toString());
        assertEquals("DEFAULT",task1.getPriority().toString());

        assertEquals(0, task1.getEstimatedTimeToComplete());
        assertEquals(0, task1.getProgress());



        Task task2 = new Task("Hello## today; TOday; toDAY; tag1; tAg1; important; up next; ");
        assertEquals("Hello",task2.getDescription());
        assertEquals("Fri Mar 29 2019 11:59 PM",task2.getDueDate().toString());
        assertEquals("IMPORTANT",task2.getPriority().toString());
        assertEquals("UP NEXT", task2.getStatus().toString());


        Todo task3 = new Project("hi");
        task3.getDescription();

    }

    @Test
    void addTagTest() {
        assertEquals(0, task.getTags().size());
        try {
            task.addTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(1, task.getTags().size());
        assertEquals("[#first task]", task.getTags().toString());
        try {
            task.addTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(1, task.getTags().size());
        assertEquals("[#first task]", task.getTags().toString());
        try {
            task.addTag("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }



    }

    @Test
    void removeTagTest() {
        assertEquals(0, task.getTags().size());
        try {
            task.removeTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("second task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("third task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("fourth task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(4, task.getTags().size());
        try {
            task.removeTag("second task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(3, task.getTags().size());
        assertFalse(task.containsTag("second task"));
        try {
            task.removeTag("second task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(3, task.getTags().size());
        assertFalse(task.containsTag("second task"));
        try {
            task.removeTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.removeTag("fourth task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(1, task.getTags().size());
        assertFalse(task.containsTag("first task"));
        assertFalse(task.containsTag("fourth task"));
        assertTrue(task.containsTag("third task"));
        try {
            task.removeTag("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }


    }

    @Test
    void getTagsTest() {
        assertEquals(0, task.getTags().size());
        try {
            task.addTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("second task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals(2, task.getTags().size());

    }

    @Test
    void getPriorityTest() {
        assertEquals("DEFAULT", task.getPriority().toString());
        try {
            task.setPriority(priority);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected  NullArgumentException");
        }
        assertEquals("IMPORTANT & URGENT", task.getPriority().toString());
    }

    @Test
    void setPriorityTest() {
        assertEquals("DEFAULT", task.getPriority().toString());
        try {
            task.setPriority(priority);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected  NullArgumentException");
        }
        assertEquals("IMPORTANT & URGENT", task.getPriority().toString());
        try {
            task.setPriority(null);
            fail("failed to throw  NullArgumentException");
        } catch (NullArgumentException e) {

        }

        Task task1 = new Task("important");
        assertFalse(task.equals(task1));
    }

    @Test
    void getStatusTest() {
        assertEquals("TODO", task.getStatus().toString());
        try {
            task.setStatus(status);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected  NullArgumentException");
        }
        assertEquals("IN PROGRESS", task.getStatus().toString());

    }

    @Test
    void setStatusTest() {
        assertEquals("TODO", task.getStatus().toString());
        try {
            task.setStatus(status);
        } catch (NullArgumentException e) {
            fail("Caught an unexpected  NullArgumentException");
        }
        assertEquals("IN PROGRESS", task.getStatus().toString());
        try {
            task.setStatus(null);
            fail("failed to throw  NullArgumentException");
        } catch (NullArgumentException e) {
        }

        Task task1 = new Task("important");
        assertFalse(task.equals(task1));

    }

    @Test
    void getDescriptionTest() {
        assertEquals("important", task.getDescription());
        try {
            task.setDescription("very important");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals("very important", task.getDescription());
        task.setDescription("Register for the course. ## cpsc210; tomorrow; important; urgent; in progress");
        assertEquals("Register for the course. ",task.getDescription());

    }

    @Test
    void setDescriptionTest() {
        assertEquals("important", task.getDescription());
        try {
            task.setDescription("very important");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertEquals("very important", task.getDescription());
        try {
            task.setDescription("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }
        try {
            task.setDescription(null);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }




    }

    @Test
    void getDueDateTest() {
        calendar.set(YEAR, 2020);
        calendar.set(MONTH, 11);
        calendar.set(DAY_OF_MONTH, 12);
        calendar.set(HOUR_OF_DAY, 22);
        calendar.set(MINUTE, 59);
        try {
            deadline.setDueDate(calendar.getTime());
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");

        }
        task.setDueDate(deadline);
        assertEquals("Sat Dec 12 2020 10:59 PM", task.getDueDate().toString());
    }

    @Test
    void setDueDateTest() {
        calendar.set(YEAR, 2020);
        calendar.set(MONTH, 11);
        calendar.set(DAY_OF_MONTH, 12);
        calendar.set(HOUR_OF_DAY, 22);
        calendar.set(MINUTE, 59);
        try {
            deadline.setDueDate(calendar.getTime());
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");

        }
        task.setDueDate(deadline);
        assertEquals("Sat Dec 12 2020 10:59 PM", task.getDueDate().toString());
        Task task1 = new Task("important");
        assertFalse(task.equals(task1));

    }

    @Test
    void containsTagTest() {
        assertFalse(task.containsTag("first task"));
        try {
            task.addTag("first task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertTrue(task.containsTag("first task"));
        assertFalse(task.containsTag("second task"));
        try {
            task.addTag("second task");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        assertTrue(task.containsTag("first task"));
        assertTrue(task.containsTag("second task"));
        try {
            task.containsTag("");
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }

        Tag tag2 = null;
        try {
            task.containsTag(tag2);
            fail("failed to throw NullArgumentException");
        } catch (NullArgumentException e) {

        }

        String tag3 = null;
        try {
            task.containsTag(tag3);
            fail("failed to throw EmptyStringException");
        } catch (EmptyStringException e) {

        }


    }

    @Test
    void toStringTest() {
        calendar.set(YEAR, 2020);
        calendar.set(MONTH, 11);
        calendar.set(DAY_OF_MONTH, 12);
        calendar.set(HOUR_OF_DAY, 22);
        calendar.set(MINUTE, 59);
        try {
            deadline.setDueDate(calendar.getTime());
        } catch (NullArgumentException e) {
            fail("Caught an unexpected NullArgumentException");

        }
        task.setDueDate(deadline);
        try {
            task.addTag("science project");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        try {
            task.addTag("computer science");
        } catch (EmptyStringException e) {
            fail("Caught unexpected EmptyStringException");
        }
        System.out.println(task.toString());
        assertEquals("\n" +
                "{\n" +
                "\tDescription: important\n" +
                "\tDue date: Sat Dec 12 2020 10:59 PM\n" +
                "\tStatus: TODO\n" +
                "\tPriority: DEFAULT\n" +
                "\tTags: #computer science, #science project\n" +
                "}", task.toString());


        Task task1 = new Task("buy milk");
        task1.toString();




    }

    @Test
    void statusInsertUnderscoreTest() {
        task.setStatus(Status.TODO);
        assertEquals("TODO",task.statusInsertUnderscore());
        task.setStatus(Status.DONE);
        assertEquals("DONE",task.statusInsertUnderscore());
        task.setStatus(Status.IN_PROGRESS);
        assertEquals("IN_PROGRESS",task.statusInsertUnderscore());
        task.setStatus(Status.UP_NEXT);
        assertEquals("UP_NEXT",task.statusInsertUnderscore());
    }

    @Test
    void equalsTest() {

        assertEquals(task,new Task ("important"));
        assertTrue(task.equals(task));
        assertFalse(task.equals(new Task ("urgent")));
        assertFalse(task.equals(priority));


    }

    @Test
    void setProgressTest() {
        task.setProgress(50);
        assertEquals(50,task.getProgress());

        try {
            task.setProgress(110);
            fail("failed to throw InvalidProgressException");
        } catch (InvalidProgressException e) {

        }
        try {
            task.setProgress(-10);
            fail("failed to throw InvalidProgressException");
        } catch (InvalidProgressException e) {

        }
    }

    @Test
    void setEstimatedTimeToComplete() {

        task.setEstimatedTimeToComplete(5);
        assertEquals(5,task.getEstimatedTimeToComplete());
        try {
            task.setEstimatedTimeToComplete(-10);
            fail("failed to throw NegativeInputException");
        } catch (NegativeInputException e) {

        }

    }






}