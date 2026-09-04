package storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import parser.Parser;
import task.Task;
import task.TaskList;

/**
 * Handles loading tasks from a file and saving tasks back to the file.
 */
public class Storage {
    private final File taskFile;

    /**
     * Constructs a Storage object with the given file path.
     *
     * @param filePath the path to the task data file
     */
    public Storage(String filePath) {
        this.taskFile = new File(filePath);
    }

    /**
     * Loads tasks from the task file into a TaskList.
     *
     * @return a TaskList containing tasks loaded from the file
     * @throws IOException when the task file cannot be read
     */
    public TaskList load() throws IOException {
        TaskList tasks = new TaskList();
        try {
            Scanner scanner = new Scanner(taskFile);
            while (scanner.hasNextLine()) {
                String taskSignature = scanner.nextLine();
                Task task = Parser.parseTaskSignature(taskSignature);
                if (task != null) {
                    tasks.add(task);
                }
            }
            scanner.close();
            return tasks;
        } catch (FileNotFoundException e) {
            return tasks;
        }
    }

    /**
     * Saves the given TaskList to the task file, overwriting any existing content.
     *
     * @param tasks the TaskList to be saved
     * @throws IOException when the file cannot be written to
     */
    public void save(TaskList tasks) throws IOException {
        FileWriter writer = new FileWriter(taskFile);
        writer.write(tasks.toSignature());
        writer.close();
    }
}
