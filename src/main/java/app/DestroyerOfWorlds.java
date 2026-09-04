package app;

import java.io.IOException;
import java.util.ArrayList;

import parser.Parser;
import storage.Storage;
import task.TaskList;
import ui.Ui;

/**
 * Entry point and coordinator for the task manager application.
 */
public class DestroyerOfWorlds {
    private static boolean exit = false;
    private static TaskList tasks = new TaskList();
    private static final Storage STORAGE = new Storage("src/main/java/data/tasks.txt");
    private static final Ui UI = new Ui();

    /**
     * Returns the current task list.
     *
     * @return the active task list
     */
    public static TaskList getTasks() {
        return tasks;
    }

    /**
     * Loads tasks from the saved data file.
     *
     * @return status string for the operation
     */
    public static String loadTasks() {
        try {
            tasks = STORAGE.load();
            return "Tasks successfully loaded";
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    /**
     * Exits the application and returns the farewell message.
     *
     * @return the exit message shown to the user
     */
    public static String exit() {
        exit = true;
        return "seeya cutie ;)";
    }

    /**
     * Starts the application and loads persisted data.
     *
     * @return the welcome banner shown to the user
     */
    public static String start() {
        exit = false;
        String banner = "\nHello I'm DESTROYEROFWORLDS\nPrepare to meet you DOOM!\n"
                + "____________________________________"
                + "\nWhat do you want from me Nerd!?!?!, Can't you see I'm busy:";

        loadTasks();
        return banner;
    }

    /**
     * Saves the current task list to the data file.
     *
     * @return success status for the operation
     */
    public static String updateTaskFile() {
        try {
            STORAGE.save(tasks);
            return "task file successfully updated";
        } catch (IOException e) {
            throw new RuntimeException("Failed to update task file: " + e.getMessage(), e);
        }
    }

    /**
     * Main execution point for the chatbot.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println(start());

        while (!exit) {
            String consoleInput = UI.readCommand();
            ArrayList<Object> qualifiedOut = Parser.parseCommand(consoleInput, tasks);
            String statusString = (String) qualifiedOut.get(0);
            updateTaskFile();
            UI.echo(statusString);
        }
    }
}
