import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.util.regex.Pattern;

public class DestroyerOfWorlds {

    //fields*****************************************************************
    private static boolean exit = false;
    private static TaskList tasks = new TaskList();
    private static File taskFile = new File("src/main/java/data/tasks.txt");
    private static DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    //getters****************************************************************
    public static String getConsoleInput() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();

    }

    public static TaskList getChatHistory() {
        return tasks;
    }

    /**
     * loads tasks from a file into the chatbot's task list
     * @param taskFile file to be loaded from
     * @return status String to indicate if operation succeeded
     * @throws IOException when task file has incorrect task format
     */
    public static String loadTasks(File taskFile) throws IOException{
        try {
            Scanner scanner = new Scanner(taskFile);
            while (scanner.hasNext()) {
                String taskSignature = scanner.nextLine();
                ArrayList<Object> out = parseCommand(taskSignature);
                if(out.get(1) instanceof Task) {
                    Task t = (Task) out.get(1);
                    tasks.add(t);
                } else {
                    throw new IOException("task file has incorrect task format");
                }
            }
            return "Tasks successfully loaded from " + taskFile.getName();
        } catch (FileNotFoundException e) {
            return e.getMessage();
        }

    }

    /**
     * loads tasks from the default task file specified within the chatbots implementation
     * into the chatbot's task list
     * @return status String to indicate if operation succeeded
     * @throws IOException when task file has incorrect task format
     */
    public static String loadTasks() throws IOException{
        return loadTasks(taskFile);
    }



    //setters (return status string after)***********************************************
    /**
     * exits the chatbot by setting exit to true
     * @return string to say bye to users
     */
    public static String exit() {
        exit = true;
        return "seeya cutie ;)";
    }

    /**
     * starts the chatbot, loads tasks, and does other precomputations.
     * @return string to greet the user
     */
    public static String start() {
        exit = false;
        String Banner = "\nHello I'm DESTROYEROFWORLDS\nPrepare to meet you DOOM!\n" +
                "____________________________________" +
                "\nWhat do you want from me Nerd!?!?!, Can't you see I'm busy:";

        try {
            loadTasks();
            return Banner;
        } catch (IOException e) {
            //error your task file is incorrect
            return e.getMessage();
        }
    }

    /**
     * sets a task in the chatbots task list as done/undone
     * @param idx index of task to be set
     * @param done if true, then set as done. if false, then undone
     * @return string to inform users status of operation (e.g. task done, task undone, error)
     */
    public static String setTask(int idx, boolean done) {
        return tasks.setTask(idx,done);
    }

    /**
     * adds a new task to the chatbots task list
     * @param task task to be added
     * @return status string to indicate successful operation
     */
    public static String addToTasks(Task task) {
        tasks.add(task);
        return "Added: " + task.toString();
    }

    /**
     * deletes a task from the chatbot's task list
     * @param idx index of task to be removed. note that this
     *            is the internal idx of the task list, while the
     *            user level task list is 1 indexed, task list at the
     *            internal level is 0-indexed
     * @return status string to indicate successful operation
     */
    public static String removeFromTasks(int idx) {
        try {
            tasks.remove(idx);
            return "Removed: " + tasks.get(idx).toString();
        } catch (IndexOutOfBoundsException e) {
            return "Error: task doesnt exist";
        }
    }

    /**
     * updates the tasks text file whose path is specified in DestroyerOfWorlds' attributes
     * @return success status as a string
     */
    public static String updateTaskFile() {
        try {
            //System.out.println(new File(".").getAbsolutePath());
            FileWriter writer = new FileWriter(taskFile);
            writer.write(tasks.toSignature());
            writer.close();
            return "task file successfully updated";
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

    //booleans****************************************************************
    //misc********************************************************************
    /**
     * puts a text within the chatbots speech bubble
     * @param text string to be put in speech bubble
     * @return a string that looks like it is within the chatbot's speech bubble
     */
    public static String styleString(String text) {

        String styledString = "";

        //add top boundary
        styledString+="\t______________________________________________\n\t";

        //Scan chars in string, whenever find \n char, add \t after it
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                //newline started, auto indent with a tab
                styledString += c + "\t";
            } else {
                styledString += c;
            }
        }

        //add bottom boundary
        styledString += "\n\t______________________________________________";
        return styledString;

    }

    /**
     * takes in a text input, echos that input
     * @param text input by user into console
     * @return consoleInput
     */
    public static String echo(String text) {
        String s = styleString(text);
        System.out.println(s);
        return s;
    }

    //COMMAND LOGIC
    /** checks if a text is a recognised chatbot command. if it is, execute it.
     * @param text command in the form of a string
     * @return array list of objects, the first item is the status string of the command to
     * indicate if operation is successful. Subsequent objects are the intended
     * outputs of the command, depending on what the command does.
     */
    public static ArrayList<Object> parseCommand(String text) {
        //notes:    * commands should not be subsets of each other
        //          * '|' is an illegal char for all text, as it is reserved as an argument delimiter
        //          * valid text are strings that are non blank, and do not contain illegal chars.
        //          * example of valid regex block: \\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*

        //COMMONLY USED REGEXS
        String timeRegex = ""; //dd/MM/yyyy hh:mm
        //timeRegex.matches(timeRegex);

        //COMMAND FORMATS************************************************************************
        //to-do formats:
        // <start> T | <0 or 1> | <valid desc text> <end>
        String todo1Regex = "\\AT\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" +
                "\\z";
        Command todo1Command = new Command(todo1Regex);
        boolean todo1Found = todo1Command.find(0,text);
        //Pattern.compile(todo1Regex);


        // <start> to-do <space> <valid> <end>
        String todo2Regex = "\\Atodo\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" +
                "\\z";
        Command todo2Command = new Command(todo2Regex);
        boolean todo2Found = todo2Command.find(0,text);
        //Pattern.compile(todo2Regex);

        //deadline formats:
        // <start> D | <0 or 1> | <valid desc text> | <valid by-date text> <end>
        String deadline1Regex = "\\AD\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command deadline1Command = new Command(deadline1Regex);
        boolean deadline1Found = deadline1Command.find(0,text);
        //Pattern.compile(deadline1Regex);

        // <start> deadline <space><valid> <space></by><space> <valid by-date> <space><end>
        String deadline2Regex = "\\Adeadline\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\s/by\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command deadline2Command = new Command(deadline2Regex);
        boolean deadline2Found = deadline2Command.find(0,text);
        //Pattern.compile(deadline2Regex);


        //event formats:
        // <start> E | <0 or 1> | <valid desc text> | <V from-date> | <V to-date> <end>
        String event1Regex = "\\AE\\|" + "[01]\\|" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" + "\\s*\\|" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command event1Command = new Command(event1Regex);
        boolean event1Found = event1Command.find(0,text);
        //Pattern.compile(event1Regex);

        // <start>event<space> <valid> <space></from><space> <valid> <space></to><space> <valid> <end>
        String event2Regex = "\\Aevent\\s" +
                "\\s*[\\S&&[^\\|]][\\s\\S&&[^\\|]]*" + "\\s/from\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" + "\\s*\\s/to\\s" +
                "\\s*\\d{2}/\\d{2}/\\d{4}\\s\\d{2}:\\d{2}" +
                "\\s*\\z";
        Command event2Command = new Command(event2Regex);
        boolean event2Found = event2Command.find(0,text);
        //Pattern.compile(event2Regex);

        // <start><exit><optional space><end>
        String exitRegex = "\\Abye\\s*\\z";
        Command exitCommand = new Command(exitRegex);
        boolean exitFound = exitCommand.find(0,text);
        //Pattern.compile(exitRegex);

        // <start><list><optional space><end>
        String listRegex = "\\Alist\\s*\\z";
        Command listCommand = new Command(listRegex);
        boolean listFound = listCommand.find(0,text);
        //Pattern.compile(listRegex);

        // <start><mark> <spaces><number><optional space> <end>
        String markRegex = "\\Amark"+ "\\s+" + "-?\\d+\\s*" +"\\z";
        Command markCommand = new Command(markRegex);
        boolean markFound = markCommand.find(0,text);
        //Pattern.compile(markRegex);

        // <start><unmark> <spaces><number><optional space> <end>
        String unmarkRegex = "\\Aunmark"+ "\\s+" + "-?\\d+\\s*" +"\\z";
        Command unmarkCommand = new Command(unmarkRegex);
        boolean unmarkFound = unmarkCommand.find(0,text);
        //Pattern.compile(unmarkRegex);

        // <start><delete> <spaces><number><optional space> <end>
        String deleteRegex = "\\Adelete"+ "\\s+" + "-?\\d+\\s*" +"\\z";
        Command deleteCommand = new Command(deleteRegex);
        boolean deleteFound = deleteCommand.find(0,text);
        //Pattern.compile(deleteRegex);


        //FIND COMMAND FORMAT*********************************************************************
        if (todo1Found) {//**********************************************************************
            ArrayList<String> words = Util.toArrayList(text,'|');

            boolean done = words.get(1).equals("1");
            String description = words.get(2);

            Task out = new Task(description, done);
            return new ArrayList<>(List.of("Added: " + out.toString(),out));
        } else if (todo2Found) {//**********************************************************************
            ArrayList<String> words = Util.toArrayList(text,new ArrayList<>(List.of("todo ")));

            boolean done = false;
            String description = words.get(1);

            Task out = new Task(description, done);
            return new ArrayList<>(List.of(addToTasks(out)));
        } else if (deadline1Found) {//**********************************************************************
            try {
                ArrayList<String> words = Util.toArrayList(text, '|');

                boolean done = words.get(1).equals("1");
                String description = words.get(2);
                LocalDateTime byDate = LocalDateTime.parse(words.get(3).trim(), timeFormat);

                Task out = new DeadlineTask(description, done, byDate);
                return new ArrayList<>(List.of("Added: " + out.toString(), out));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }

        } else if (deadline2Found) {//**********************************************************************
            try {
                ArrayList<String> words = Util.toArrayList(text,new ArrayList<>(List.of("deadline "," /by ")));
                boolean done = false;
                String description = words.get(1);
                LocalDateTime byDate = LocalDateTime.parse(words.get(2).trim(), timeFormat);

                Task out = new DeadlineTask(description, done, byDate);
                return new ArrayList<>(List.of(addToTasks(out)));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }

        } else if (event1Found) {//**********************************************************************
            try {
                ArrayList<String> words = Util.toArrayList(text, '|');

                boolean done = words.get(1).equals("1");
                String description = words.get(2);
                LocalDateTime fromDate = LocalDateTime.parse(words.get(3).trim(),timeFormat);
                LocalDateTime toDate = LocalDateTime.parse(words.get(4).trim(),timeFormat);

                Task out = new EventTask(description, done, fromDate, toDate);
                return new ArrayList<>(List.of("Added: " + out.toString(), out));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }
        } else if (event2Found) {//**********************************************************************
            try {
                ArrayList<String> words = Util.toArrayList(text, new ArrayList<>(List.of("event ", " /from ", " /to ")));

                boolean done = false;
                String description = words.get(1);
                LocalDateTime fromDate = LocalDateTime.parse(words.get(2).trim(),timeFormat);
                LocalDateTime toDate = LocalDateTime.parse(words.get(3).trim(),timeFormat);

                Task out = new EventTask(description, done, fromDate, toDate);
                return new ArrayList<>(List.of(addToTasks(out)));
            } catch (DateTimeParseException e) {
                return new ArrayList<>(List.of("Error: Wrong time format"));
            }

        } else if (exitFound) {//**********************************************************************
            return new ArrayList<>(List.of(exit()));
        } else if (listFound) {//**********************************************************************
            return new ArrayList<>(List.of(tasks.toString()));
        } else if (markFound) {//**********************************************************************
            //check that idx is within range
            try {
                int num = Util.findInteger(text,4);
                return new ArrayList<>(List.of(setTask(num-1,true) ));
            } catch (NumberFormatException e) {
                //IMPOSSIBLE: exception caught, idx is not a num
                return new ArrayList<>(List.of(e.getMessage()));
            }
        } else if (unmarkFound) {//**********************************************************************
            //check that idx is within range
            try {
                int num = Util.findInteger(text,6);
                return new ArrayList<>(List.of(setTask(num-1,false) ));
            } catch (NumberFormatException e) {
                //IMPOSSIBLE: exception caught, idx is not a num
                return new ArrayList<>(List.of(e.getMessage()));
            }

        } else if (deleteFound) {//**********************************************************************
            //check that idx is within range
            try {
                int num = Util.findInteger(text,6);
                return new ArrayList<>(List.of(removeFromTasks(num-1) ));
            } catch (NumberFormatException e) {
                //IMPOSSIBLE: exception caught, idx is not a num
                return new ArrayList<>(List.of(e.getMessage()));
            }

        } else{//**********************************************************************
            //ERROR wrong format
            return new ArrayList<>(List.of("i dont know what you are saying"));
        }
    }



    //MAIN CODE***************************************************************
    /**
     * execution point for the chatbot program to start
     * @param args commandline arguments
     */
    public static void main(String[] args) {
        System.out.println(start());
        while(!exit) {
            String consoleInput = getConsoleInput();
            ArrayList<Object> qualifiedOut = parseCommand(consoleInput);

            //always works as interface forces first item to be status string
            String statusString = (String) qualifiedOut.get(0);
            updateTaskFile();
            echo(statusString);
        }
    }
}
