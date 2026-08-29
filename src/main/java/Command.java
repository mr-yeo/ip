import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    //to-do formats: <start> T | <0 or 1> | <valid desc text> <end>
    private String regex = "";
    Pattern pattern = Pattern.compile(regex);

    //constructors

    /**
     * creates an instance of a Command.
     * @param regex a regular expression which matches the format of the command
     */
    public Command(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    //getters

    //setters

    //booleans

    /**
     * searches the given text checking whether it contains this command
     * @param start the idx to start the search
     * @param text the text to be searched
     * @return true if command is present in text. false otherwise.
     */
    public boolean find(int start, String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find(0);
    }

    //overrides
}
