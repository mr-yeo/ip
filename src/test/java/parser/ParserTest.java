package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import task.TaskList;

/**
 * Tests for parsing and executing commands.
 */
public class ParserTest {
    @Test
    public void testCommandParse() {
        assertEquals(
                new ArrayList<>(List.of("Added: [T][ ] hello")),
                Parser.parseCommand("todo hello", new TaskList())
        );
    }

    @Test
    public void testDuplicateTodoIsRejected() {
        TaskList tasks = new TaskList();
        Parser.parseCommand("todo hello", tasks);

        assertEquals(
                new ArrayList<>(List.of("Error: task already exists")),
                Parser.parseCommand("todo hello", tasks)
        );
        assertEquals(1, tasks.size());
    }

    @Test
    public void testDuplicateDeadlineIsRejected() {
        TaskList tasks = new TaskList();
        Parser.parseCommand("deadline report /by 09/09/2026 10:00", tasks);

        assertEquals(
                new ArrayList<>(List.of("Error: task already exists")),
                Parser.parseCommand("deadline report /by 09/09/2026 10:00", tasks)
        );
        assertEquals(1, tasks.size());
    }

    @Test
    public void testDuplicateEventIsRejected() {
        TaskList tasks = new TaskList();
        Parser.parseCommand(
                "event meeting /from 09/09/2026 10:00 /to 09/09/2026 11:00", tasks);

        assertEquals(
                new ArrayList<>(List.of("Error: task already exists")),
                Parser.parseCommand(
                        "event meeting /from 09/09/2026 10:00 /to 09/09/2026 11:00", tasks)
        );
        assertEquals(1, tasks.size());
    }
}
