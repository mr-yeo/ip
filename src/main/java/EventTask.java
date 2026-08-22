public class EventTask extends Task{
    //fields
    private String startTime = "";
    private String endTime = "";

    //constructors
    public EventTask(String description, String startTime, String endTime){
        super(description);
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
