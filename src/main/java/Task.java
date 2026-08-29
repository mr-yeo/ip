import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task {
    protected boolean done = false;
    protected String description = "";

    //constructors

    /**
     * constructs an undone to-do task.
     * @param description the description of the to-do task
     */
    public Task(String description) {
        this.done=false;
        this.description=description;
    }

    /**
     * constructs a done/undone to-do task (for internal use).
     * @param description the description of the to-do task
     * @param done indicates if the task is done/undone
     */
    public Task(String description, boolean done) {
        this.done=done;
        this.description=description;
    }

    //getters
    public boolean getCompletionStatus() {
        return this.done;
    }

    public String getDescription() {
        return this.description;
    }

    //setters
    public void setDone(boolean done) {
        this.done = done;
    }



    //booleans
    /**
     * checks if task is Done
     * @return True if task is done, false otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * gets the tasks signature. a signature is an internal
     * string representation by the chatbot, it is not to be confused
     * with the user level string representation given by toString().
     * @return signature of task(for internal use)
     */
    public String toSignature(){
        String s = "";
        if(this.done) {
            s += "T|1|" + this.description;
        } else {
            //not done
            s += "T|0|" + this.description;
        }

        return s;
    }

    /**
     * gets the tasks String representation. unlike toSignature(),
     * this string is the String representation of a task, at the user level.
     * @return user level String representation of task
     */
    //overrides
    @Override
    public String toString() {
        String s = "";
        if(this.done) {
            s += "[T][X] " + this.description;
        } else {
            //not done
            s += "[T][ ] " + this.description;
        }

        return s;
    }


}
