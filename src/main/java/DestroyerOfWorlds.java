import java.util.Scanner;
import exceptions.HistoryEmptyException;

public class DestroyerOfWorlds {

    //fields*****************************************************************
    private static boolean exitStatus = false;
    private static ChatHistory chatHistory = new ChatHistory(100);

    //getters****************************************************************
    public static String getConsoleInput() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
    }

    public static ChatHistory getHistory() {
        return chatHistory;
    }

    //setters****************************************************************
    /**
     * exits the chatbot by setting exitStatus to true
     */
    public static void exit() {
        exitStatus = true;
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
        String echo = styleString(text);
        System.out.println(echo);
        return echo;
    }

    /**
     * pushes to chat history a new string
     * @param s string to be pushed
     */
    public static void pushChatHistory(String s) {
        chatHistory.push(s);
    }

    //MAIN CODE***************************************************************
    public static void main(String[] args) {
        String banner = "\nHello I'm DESTROYEROFWORLDS\nPrepare to meet you DOOM!\n____________________________________";
        System.out.println(banner);

        System.out.println("What do you want from me Nerd!?!?!, Can't you see I'm busy:");

        //begin the chatting session
        while (exitStatus==false) {
            String consoleInput = getConsoleInput();
            /*
            if (Objects.equals(consoleInput, "bye")) {
                //bye bye
                echo("seeya cutie ;)");
                exit();
                break;
            } else {
                //talk back
                echo(consoleInput);
            }*/

            switch(consoleInput) {
                case "bye":
                    echo("seeya cutie ;)");
                    exit();
                    break;

                case "list":
                    echo(getHistory().toString());
                    break;

                default:
                    echo(consoleInput);
                    pushChatHistory(consoleInput);

            }

        }

    }
}
