/**
 * Represents a generic task with a completion state and description.
 */
public class Task {
    private boolean done;
    private String description;

    /**
     * Creates an undone to-do task.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.done = false;
    }

    /**
     * Creates a task with a specified completion state.
     *
     * @param description the description of the task
     * @param done whether the task is already complete
     */
    public Task(String description, boolean done) {
        this.description = description;
        this.done = done;
    }

    /**
     * Returns whether the task is complete.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean getCompletionStatus() {
        return this.done;
    }

    /**
     * Returns the task description.
     *
     * @return the description text of the task
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the task completion state.
     *
     * @param done whether the task is considered done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Checks whether the task is done.
     *
     * @return true if the task is done, false otherwise
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Produces the internal signature representation of the task.
     *
     * @return the task in internal storage format
     */
    public String toSignature() {
        if (this.done) {
            return "T|1|" + this.description;
        }
        return "T|0|" + this.description;
    }

    /**
     * Produces the user-facing text representation of the task.
     *
     * @return the display format of the task
     */
    @Override
    public String toString() {
        if (this.done) {
            return "[T][X] " + this.description;
        }
        return "[T][ ] " + this.description;
    }
}
