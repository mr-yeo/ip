public class Task {
    private boolean done = false;
    private String description = "";

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
            s += "[X] " + this.description;
        } else {
            //not done
            s += "[ ] " + this.description;
        }

        return s;
    }


}
