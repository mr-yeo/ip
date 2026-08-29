import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventTask extends Task{
    //fields
    private DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private LocalDateTime startTime = LocalDateTime.now().plusHours(1);;
    private LocalDateTime endTime = LocalDateTime.now().plusHours(2);;

    //constructors

    /**
     * Constructs an undone event task.
     * @param description the description of the task
     * @param startTime the start time specified by the task
     * @param endTime the end time specified by the task
     */
    public EventTask(String description, String startTime, String endTime, String format){
        super(description);
        DateTimeFormatter tempFormat = DateTimeFormatter.ofPattern(format);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = LocalDateTime.parse(startTime,tempFormat);
        this.endTime = LocalDateTime.parse(endTime,tempFormat);
    }

    public EventTask(String description, String startTime, String endTime){
        super(description);

        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = LocalDateTime.parse(startTime,this.timeFormat);
        this.endTime = LocalDateTime.parse(endTime,this.timeFormat);
    }

    public EventTask(String description, LocalDateTime startTime, LocalDateTime endTime){
        super(description);

        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public EventTask(String description, boolean done, String startTime, String endTime, String format){
        super(description, done);
        DateTimeFormatter tempFormat = DateTimeFormatter.ofPattern(format);
        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = LocalDateTime.parse(startTime,tempFormat);
        this.endTime = LocalDateTime.parse(endTime,tempFormat);
    }

    public EventTask(String description, boolean done, String startTime, String endTime){
        super(description, done);

        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = LocalDateTime.parse(startTime,this.timeFormat);
        this.endTime = LocalDateTime.parse(endTime,this.timeFormat);
    }

    public EventTask(String description, boolean done, LocalDateTime startTime, LocalDateTime endTime){
        super(description, done);

        this.timeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //getters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    //setters
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }


    //boolean

    //overides
    @Override
    public String toSignature() {
        String s = "";
        if(this.done) {
            s += "E|1|" + this.description + "|" + this.startTime.format(timeFormat) + "|" + this.endTime.format(timeFormat);
        } else {
            //not done
            s += "E|0|" + this.description + "|" + this.startTime.format(timeFormat) + "|" + this.endTime.format(timeFormat);
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        if(this.done) {
            s += "[E][X] " + this.description + " (from: " + this.startTime.format(timeFormat) + " to: " + this.endTime.format(timeFormat)+ ")";
        } else {
            //not done
            s += "[E][ ] " + this.description + " (from: " + this.startTime.format(timeFormat) + " to: " + this.endTime.format(timeFormat)+ ")";
        }

        return s;
    }

}
