import java.util.Scanner;

public class DestroyerOfWorlds {

    //fields*****************************************************************
    private static boolean exit = false;
    private static TaskList taskList = new TaskList(100);

    //getters****************************************************************
    public static String getConsoleInput() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public static TaskList getChatHistory() {
        return taskList;
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
     * sets a task in the chatbots task list as done/undone
     * @param idx index of task to be set
     * @param done if true, then set as done. if false, then undone
     * @return string to inform users status of operation (e.g. task done, task undone, error)
     */
    public static String setTask(int idx, boolean done) {
        return taskList.setTask(idx,done);
    }

    /**
     * pushes a new to-do task to the chatbots task list
     * @param description description of to-do task
     * @return string to inform users if operation succeeded
     */
    public static String pushChatHistory(String description) {
        Task t = new Task(description);
        return taskList.push(t);
    }

    /**
     * pushes a new deadline task to the chatbots task list
     * @param description description of deadline task
     * @param deadline deadline of deadline task
     * @return string to inform users if operation succeeded
     */
    public static String pushChatHistory(String description, String deadline) {
        Task t = new DeadlineTask(description, deadline);
        return taskList.push(t);
    }

    /**
     * pushes a new event task to the chatbots task list
     * @param description description of event task
     * @param startTime start time of event task
     * @param endTime end time of event task
     * @return string to inform users if operation succeeded
     */
    public static String pushChatHistory(String description, String startTime, String endTime) {
        Task t = new EventTask(description, startTime, endTime);
        return taskList.push(t);
    }

    //booleans****************************************************************
    //<EMPTY>

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



    //MAIN CODE***************************************************************
    public static void main(String[] args) {
        String banner = "\nHello I'm DESTROYEROFWORLDS\nPrepare to meet you DOOM!\n____________________________________";
        System.out.println(banner);
        System.out.println("What do you want from me Nerd!?!?!, Can't you see I'm busy:");

        //begin the chatting session
        while (!exit) {
            String consoleInput = getConsoleInput();

            switch(consoleInput) {
                case "bye":
                    echo(exit());
                    break;

                case "list":
                    echo(getChatHistory().toString());
                    break;

                default:
                    if (consoleInput.startsWith("mark ")) {
                        try {
                            int num = Integer.parseInt(
                                        consoleInput.substring(5));
                            echo(setTask(num-1, true)); //passed check, mark task

                        } catch (NumberFormatException e) {
                            //exception caught, not a num, fall through.
                        }

                    } else if (consoleInput.startsWith("unmark ")) {
                        try {
                            int num = Integer.parseInt(
                                    consoleInput.substring(7));
                            echo(setTask(num-1,false));

                        } catch (NumberFormatException e) {
                            //exception caught, not a num, fall through.
                        }

                    } else if (consoleInput.startsWith("todo ")) {
                        String description = consoleInput.substring(5);
                        echo(pushChatHistory(description));

                    } else if (consoleInput.startsWith("deadline ") && consoleInput.contains(" /by ")) {
                        int idx = consoleInput.indexOf(" /by ");

                        String description = consoleInput.substring(9,idx);
                        String deadline = consoleInput.substring(idx+5);
                        echo(pushChatHistory(description, deadline));

                    } else if (consoleInput.startsWith("event ") && consoleInput.contains(" /from ")
                                && consoleInput.contains(" /to ")
                                        && consoleInput.indexOf(" /from ") < consoleInput.indexOf(" /to ")) {
                        int startIdx = consoleInput.indexOf(" /from ");
                        int endIdx = consoleInput.indexOf(" /to ");
                        String description = consoleInput.substring(6,startIdx);
                        String startTime = consoleInput.substring(startIdx+7,endIdx);
                        String endTime = consoleInput.substring(endIdx+5);
                        echo(pushChatHistory(description,startTime,endTime));

                    } else {
                        echo("what are you saying!?!?!");
                    }

            }

        }

    }
}
