public class Task {
    protected boolean done = false;
    protected String description = "";

    //constructors
    public Task(String description) {
        this.done=false;
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
