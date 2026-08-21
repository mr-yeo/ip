import java.util.Objects;
import java.util.Scanner;

public class DestroyerOfWorlds {

    //fields*****************************************************************
    private static boolean exitStatus = false;

    //getters****************************************************************
    public static String getConsoleInput() {
        Scanner s = new Scanner(System.in);
        return s.nextLine();
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
     * takes in a text input, echos that input
     * @param text input by user into console
     * @return consoleInput
     */
    public static String echo(String text) {
        String echo = "\t______________________________________________\n\t"
                + text
                + "\n\t______________________________________________";

        System.out.println(echo);

        return echo;
    }

    //MAIN CODE***************************************************************
    public static void main(String[] args) {
        String banner = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n"
                + "\nHello I'm DESTROYEROFWORLDS\nPrepare to meet you DOOM!\n____________________________________";
        System.out.println(banner);

        System.out.println("What do you want from me Nerd!?!?!, Can't you see I'm busy:");

        //begin the chatting session
        while (exitStatus==false) {
            String consoleInput = getConsoleInput();
            if (Objects.equals(consoleInput, "bye")) {
                echo("seeya cutie ;)");
                exit();
                break;
            } else {
                echo(consoleInput);
            }

        }

    }
}
