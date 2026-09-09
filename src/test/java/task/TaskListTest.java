package task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

/**
 * Tests duplicate-task comparison in TaskList.
 */
public class TaskListTest {
    @Test
    public void containsDuplicate_ignoresCompletionState() {
        TaskList tasks = new TaskList();
        tasks.add(new Task("buy milk", true));

        assertTrue(tasks.containsDuplicate(new Task("buy milk", false)));
    }

    @Test
    public void containsDuplicate_distinguishesTaskTypes() {
        TaskList tasks = new TaskList();
        tasks.add(new Task("submit report"));

        assertFalse(tasks.containsDuplicate(new DeadlineTask(
                "submit report", LocalDateTime.of(2026, 9, 9, 10, 0))));
    }

    @Test
    public void containsDuplicate_comparesDeadlineTime() {
        TaskList tasks = new TaskList();
        tasks.add(new DeadlineTask(
                "submit report", LocalDateTime.of(2026, 9, 9, 10, 0)));

        assertFalse(tasks.containsDuplicate(new DeadlineTask(
                "submit report", LocalDateTime.of(2026, 9, 9, 11, 0))));
    }

    @Test
    public void containsDuplicate_comparesEventTimes() {
        TaskList tasks = new TaskList();
        tasks.add(new EventTask(
                "team meeting",
                LocalDateTime.of(2026, 9, 9, 10, 0),
                LocalDateTime.of(2026, 9, 9, 11, 0)));

        assertTrue(tasks.containsDuplicate(new EventTask(
                "team meeting",
                false,
                LocalDateTime.of(2026, 9, 9, 10, 0),
                LocalDateTime.of(2026, 9, 9, 11, 0))));
    }
}
