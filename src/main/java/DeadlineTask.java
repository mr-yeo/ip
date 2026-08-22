public class DeadlineTask extends Task{
    //fields
    private String deadline = "";

    //constructors
    public DeadlineTask(String description, String deadline){
        super(description);
        this.deadline=deadline;
    }

    //getters

    //setters

    //boolean

    //overides
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
