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
}
