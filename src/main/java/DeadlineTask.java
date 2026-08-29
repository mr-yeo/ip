import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DeadlineTask extends Task{
    //fields
    DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    LocalDateTime deadline = LocalDateTime.now().plusHours(1);


    //constructors
    /**
     * constructs an undone deadline task.
     * @param description the description of the task
     * @param deadline the deadline specified by the task
     */
    public DeadlineTask(String description, LocalDateTime deadline){
        super(description);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline=deadline;
    }

    public DeadlineTask(String description, String deadline){
        super(description);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline = LocalDateTime.parse(deadline, this.timeFormat);
    }

    public DeadlineTask(String description, String deadline, String format){
        super(description);

        //dont let users break the default format
        DateTimeFormatter tempFormat = DateTimeFormatter.ofPattern(format);

        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline = LocalDateTime.parse(deadline, tempFormat);
    }

    /**
     * constructs a done/undone deadline task (for internal use).
     * @param description the description of the task
     * @param done indicates if task has been done
     * @param deadline the deadline specified by the task
     */
    public DeadlineTask(String description, boolean done, LocalDateTime deadline){
        super(description, done);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline = deadline;
    }

    public DeadlineTask(String description, boolean done, String deadline, String format){
        super(description, done);

        DateTimeFormatter tempFormat = DateTimeFormatter.ofPattern(format);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline = LocalDateTime.parse(deadline,tempFormat);
    }

    public DeadlineTask(String description, boolean done, String deadline){
        super(description, done);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.deadline = LocalDateTime.parse(deadline, this.timeFormat);
    }

    //getters

    public LocalDateTime getDeadline() {
        return deadline;
    }


    //setters

    //boolean

    //overides

    @Override
    public String toSignature(){
        String s = "";
        if(this.done) {
            s += "D|1|" + this.description + "|" + this.deadline.format(timeFormat) ;
        } else {
            //not done
            s += "D|0|" + this.description + "|" + this.deadline.format(timeFormat);
        }

        return s;
    }

    @Override
    public String toString() {
        String s = "";
        if(this.done) {
            s += "[D][X] " + this.description + " (by: " + this.deadline.format(timeFormat) + ")";
        } else {
            //not done
            s += "[D][ ] " + this.description + " (by: " + this.deadline.format(timeFormat) + ")";
        }

        return s;
    }

}
