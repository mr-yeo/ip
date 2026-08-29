public class DeadlineTask extends Task{
    //fields
    private String deadline = "";

    //constructors

    /**
     * constructs an undone deadline task.
     * @param description the description of the task
     * @param deadline the deadline specified by the task
     */
    public DeadlineTask(String description, String deadline){
        super(description);
        this.deadline=deadline;
    }

    /**
     * constructs a done/undone deadline task (for internal use).
     * @param description the description of the task
     * @param done indicates if task has been done
     * @param deadline the deadline specified by the task
     */
    public DeadlineTask(String description,boolean done, String deadline){
        super(description,done);
        this.deadline=deadline;
    }

    //getters

    //setters

    //boolean

    //overides

    @Override
    public String toSignature(){
        String s = "";
        if(this.done) {
            s += "D|1|" + this.description + "|" + this.deadline ;
        } else {
            //not done
            s += "D|0|" + this.description + "|" + this.deadline;
        }

        return s;
    }

    @Override
    public String toString() {
        String s = "";
        if(this.done) {
            s += "[D][X] " + this.description + " (by: " + this.deadline + ")";
        } else {
            //not done
            s += "[D][ ] " + this.description + " (by: " + this.deadline + ")";
        }

        return s;
    }

}
