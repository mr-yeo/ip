package parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import app.DestroyerOfWorlds;
import command.Command;
import task.DeadlineTask;
import task.EventTask;
import task.Task;
import task.TaskList;
import util.Util;

/**
 * Converts user commands and persisted task signatures into the appropriate program objects.
 * Keeps parsing and command recognition separated from file I/O and task management.
 */
public class Parser {
    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Parses a task signature string and returns the corresponding Task object.
     * Supported formats:
     *   T|0/1|description
     *   D|0/1|description|dd/MM/yyyy HH:mm
     *   E|0/1|description|dd/MM/yyyy HH:mm|dd/MM/yyyy HH:mm
     * @param taskSignature the task signature string
     * @return the parsed Task object, or null if parsing fails
     */
    public static Task parseTaskSignature(String taskSignature) {
        try {
            ArrayList<String> words = Util.toArrayList(taskSignature, '|');

            if (words.isEmpty()) {
                return null;
            }

            String taskType = words.get(0);
            boolean done = words.get(1).equals("1");
            String description = words.get(2);

            if ("T".equals(taskType)) {
                return new Task(description, done);
            } else if ("D".equals(taskType)) {
                LocalDateTime byDate = LocalDateTime.parse(words.get(3).trim(), TIME_FORMAT);
                return new DeadlineTask(description, done, byDate);
            } else if ("E".equals(taskType)) {
                LocalDateTime fromDate = LocalDateTime.parse(words.get(3).trim(), TIME_FORMAT);
                LocalDateTime toDate = LocalDateTime.parse(words.get(4).trim(), TIME_FORMAT);
                return new EventTask(description, done, fromDate, toDate);
            }
        } catch (Exception e) {
            // Ignore malformed entries while loading persisted data
            return null;
        }
        return null;
    }

    /**
     * Interprets a user command and returns the corresponding status/output payload.
     * @param text raw user input
     * @param tasks the current task list being mutated by commands
     * @return command result payload, with the status string as the first element
     */
    public static ArrayList<Object> parseCommand(String text, TaskList tasks) {
        String todo1Regex = "\\AT\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" +
                "\\z";
        Command todo1Command = new Command(todo1Regex);
        boolean todo1Found = todo1Command.find(0, text);

        String todo2Regex = "\\Atodo\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" +
                "\\z";
        Command todo2Command = new Command(todo2Regex);
        boolean todo2Found = todo2Command.find(0, text);

        String deadline1Regex = "\\AD\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command deadline1Command = new Command(deadline1Regex);
        boolean deadline1Found = deadline1Command.find(0, text);

        String deadline2Regex = "\\Adeadline\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\s/by\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command deadline2Command = new Command(deadline2Regex);
        boolean deadline2Found = deadline2Command.find(0, text);

        String event1Regex = "\\AE\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" + "\\s*\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command event1Command = new Command(event1Regex);
        boolean event1Found = event1Command.find(0, text);

        String event2Regex = "\\Aevent\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\s/from\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" + "\\s*\\s/to\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command event2Command = new Command(event2Regex);
        boolean event2Found = event2Command.find(0, text);

        String exitRegex = "\\Abye\\s*\\z";
        Command exitCommand = new Command(exitRegex);
        boolean exitFound = exitCommand.find(0, text);

        String listRegex = "\\Alist\\s*\\z";
        Command listCommand = new Command(listRegex);
        boolean listFound = listCommand.find(0, text);

        String markRegex = "\\Amark" + "\\s+" + "-?\\d+\\s*" + "\\z";
        Command markCommand = new Command(markRegex);
        boolean markFound = markCommand.find(0, text);

        String unmarkRegex = "\\Aunmark" + "\\s+" + "-?\\d+\\s*" + "\\z";
        Command unmarkCommand = new Command(unmarkRegex);
        boolean unmarkFound = unmarkCommand.find(0, text);

        String deleteRegex = "\\Adelete" + "\\s+" + "-?\\d+\\s*" + "\\z";
        Command deleteCommand = new Command(deleteRegex);
        boolean deleteFound = deleteCommand.find(0, text);

        if (todo1Found) {
            ArrayList<String> words = Util.toArrayList(text, '|');
            boolean done = words.get(1).equals("1");
            String description = words.get(2);
            Task out = new Task(description, done);
            return new ArrayList<>(List.of("Added: " + out.toString(), out));
        } else if (todo2Found) {
            ArrayList<String> words = Util.toArrayList(text, new ArrayList<>(List.of("todo ")));
            boolean done = false;
            String description = words.get(1);
            Task out = new Task(description, done);
            return new ArrayList<>(List.of(tasks.addTask(out)));
        } else if (deadline1Found) {
            try {
                ArrayList<String> words = Util.toArrayList(text, '|');
                boolean done = words.get(1).equals("1");
                String description = words.get(2);
                LocalDateTime byDate = LocalDateTime.parse(words.get(3).trim(), TIME_FORMAT);
                Task out = new DeadlineTask(description, done, byDate);
                return new ArrayList<>(List.of("Added: " + out.toString(), out));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }
        } else if (deadline2Found) {
            try {
                ArrayList<String> words = Util.toArrayList(text, new ArrayList<>(List.of("deadline ", " /by ")));
                boolean done = false;
                String description = words.get(1);
                LocalDateTime byDate = LocalDateTime.parse(words.get(2).trim(), TIME_FORMAT);
                Task out = new DeadlineTask(description, done, byDate);
                return new ArrayList<>(List.of(tasks.addTask(out)));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }
        } else if (event1Found) {
            try {
                ArrayList<String> words = Util.toArrayList(text, '|');
                boolean done = words.get(1).equals("1");
                String description = words.get(2);
                LocalDateTime fromDate = LocalDateTime.parse(words.get(3).trim(), TIME_FORMAT);
                LocalDateTime toDate = LocalDateTime.parse(words.get(4).trim(), TIME_FORMAT);
                Task out = new EventTask(description, done, fromDate, toDate);
                return new ArrayList<>(List.of("Added: " + out.toString(), out));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }
        } else if (event2Found) {
            try {
                ArrayList<String> words = Util.toArrayList(text, new ArrayList<>(List.of("event ", " /from ", " /to ")));
                boolean done = false;
                String description = words.get(1);
                LocalDateTime fromDate = LocalDateTime.parse(words.get(2).trim(), TIME_FORMAT);
                LocalDateTime toDate = LocalDateTime.parse(words.get(3).trim(), TIME_FORMAT);
                Task out = new EventTask(description, done, fromDate, toDate);
                return new ArrayList<>(List.of(tasks.addTask(out)));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }
        } else if (exitFound) {
            return new ArrayList<>(List.of(DestroyerOfWorlds.exit()));
        } else if (listFound) {
            return new ArrayList<>(List.of(tasks.toString()));
        } else if (markFound) {
            try {
                int num = Util.extractIntegerFromIndex(text, 4);
                return new ArrayList<>(List.of(tasks.setTask(num - 1, true)));
            } catch (NumberFormatException e) {
                return new ArrayList<>(List.of(e.getMessage()));
            }
        } else if (unmarkFound) {
            try {
                int num = Util.extractIntegerFromIndex(text, 6);
                return new ArrayList<>(List.of(tasks.setTask(num - 1, false)));
            } catch (NumberFormatException e) {
                return new ArrayList<>(List.of(e.getMessage()));
            }
        } else if (deleteFound) {
            try {
                int num = Util.extractIntegerFromIndex(text, 6);
                return new ArrayList<>(List.of(tasks.removeTask(num - 1)));
            } catch (NumberFormatException e) {
                return new ArrayList<>(List.of(e.getMessage()));
            }
        } else {
            return new ArrayList<>(List.of("i dont know what you are saying"));
        }
    }
}
