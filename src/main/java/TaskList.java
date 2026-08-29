import packages.exception.list.ListEmptyException;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task> {

    //constructors
    /**
     * constructs a task list
     */
    public TaskList() {
        super();
    }

    //setters
    /**
     * marks a task as done, or not done
     * @param idx index of task to be marked
     * @param done if true, then task is marked as done. If false, then undone
     * @return string to inform users status of operation (e.g. task done, task undone, error)
     */
    public String setTask(int idx, boolean done) {
        try {
            if(idx < 0 || idx > this.size()) {
                throw new NullPointerException("task doesnt exist");
            } else {
                this.get(idx).setDone(done);
                int num = idx+1;
                if (done) {
                    //mark done
                    return "ok, marked Task" + num + " as done";
                } else {
                    //unmark done
                    return "ok, unmarked Task" + num;
                }

            }
        } catch (NullPointerException e) {
            //task doesnt exist
            return "Error: " + e.getMessage();
        }
    }


    /**
     * gets the tasklist signature. a signature is an internal
     * string representation by the chatbot, it is not to be confused
     * with the user level string representation given by toString().
     * @return signature of tasklist(for internal use)
     */
    public String toSignature() {

        if (this.isEmpty()) {
            return "";
        } else {
            //history not empty
            String out = "";

            for (int i = 0; i<= this.size() -2; i++) {
                int nextIdx = i+2;
                out = out + this.get(i).toSignature() + "\n";
            }

            out = out + this.get(this.size() -1).toSignature();
            return out;
        }

    }

    //overrides
    /**
     * gets the user level String representation of this tasklist. unlike toSignature(),
     * this string is the String representation of a tasklist, at the user level.
     * @return user level String representation of tasklist
     */
    @Override
    public String toString() {
        try {
            if (this.isEmpty()) {
                throw new ListEmptyException("History is empty");
            } else {
                //history not empty
                String out = "1. ";

                for (int i = 0; i<= this.size() -2; i++) {
                    int nextIdx = i+2;
                    out = out + this.get(i).toString() + "\n" + nextIdx + ". ";
                }

                out = out + this.get(this.size() -1).toString();
                return out;

            }
        } catch (ListEmptyException e) {
            return "Error: "+ e.getMessage();
        }

    }
}
