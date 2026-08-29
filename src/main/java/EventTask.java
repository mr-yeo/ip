public class EventTask extends Task{
    //fields
    private String startTime = "";
    private String endTime = "";

    //constructors

    /**
     * Constructs an undone event task.
     * @param description the description of the task
     * @param startTime the start time specified by the task
     * @param endTime the end time specified by the task
     */
    public EventTask(String description, String startTime, String endTime){
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * constructs a done/undone event task
     * @param description the description of the task
     * @param done indicates if the task is done/undone
     * @param startTime the start time specified by the task
     * @param endTime the end time specified by the task
     *
     */
    public EventTask(String description, boolean done, String startTime, String endTime){
        super(description,done);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //getters
    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    //setters
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    //boolean

    //overides
    @Override
    public String toSignature() {
        String s = "";
        if(this.done) {
            s += "E|1|" + this.description + "|" + this.startTime + "|" + this.endTime;
        } else {
            //not done
            s += "E|0|" + this.description + "|" + this.startTime + "|" + this.endTime;
        }
        return s;
    }

    @Override
    public String toString() {
        String s = "";
        if(this.done) {
            s += "[E][X] " + this.description + " (from: " + this.startTime + " to: " + this.endTime+ ")";
        } else {
            //not done
            s += "[E][ ] " + this.description + " (from: " + this.startTime + " to: " + this.endTime+ ")";
        }

        return s;
    }

}
