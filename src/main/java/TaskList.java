import exception.list.ListEmptyException;
import exception.list.ListFullException;

public class TaskList {
    private int maxCount = 100;
    private int count = 0;
    private Task[] list = new Task[maxCount]; //history of tasks


    //constructor
    public TaskList(int maxCount) {
        this.maxCount = maxCount;
    }

    //getters
    public int getCount() {
        return count;
    }

    public Task[] getList() {
        return list;
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
            if(list[idx] == null) {
                throw new NullPointerException("task doesnt exist");
            } else {
                list[idx].setDone(done);
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
     * pushes a new task onto the task list
     * @param t Task to be pushed
     * @return string to inform users status of operation (e.g. successful, unsuccessful)
     */
    public String push(Task t) {
        try {
            if (this.isFull()) {
                throw new ListFullException("Sorry, history is full");
            } else {
                list[count]=t;
                count +=1;
                return "Pushed: " + t.toString();
            }
        } catch (ListFullException e) {
            return "Error: "+e.getMessage();
        }
    }

    //boolean
    /**
     * checks if task list is empty
     * @return true when list is empty, else returns false
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * checks if task list is full
     * @return true when list is full, else returns false
     */
    public boolean isFull() {
        return count == maxCount;
    }

    //misc
    /**
     * shows the (idx+1)th task from task list (i.e. taskList[idx])
     * @param idx the idx of task to be returned
     * @return the (idx+1)th task in task list
     */
    public Task peek(int idx) {
        return  list[idx];
    }

    /**
     * returns the task list as a string of tasks in pointer-list form
     * @return string of tasks in pointer-list form
     */
    @Override
    public String toString() {
        try {
            if (this.isEmpty()) {
                throw new ListEmptyException("History is empty");
            } else {
                //history not empty
                String out = "1. ";

                for (int i = 0; i<= count -2; i++) {
                    int nextIdx = i+2;
                    out = out + list[i].toString() + "\n" + nextIdx + ". ";
                }

                out = out + list[count -1].toString();
                return out;

            }
        } catch (ListEmptyException e) {
            return "Error: "+ e.getMessage();
        }

    }
}
