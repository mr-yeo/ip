import exceptions.HistoryEmptyException;
import exceptions.HistoryFullException;

public class ChatHistory {
    private int maxCount = 100;
    private int historyCount = 0;
    private String[] history = new String[maxCount]; //history of user inputs

    //constructor
    public ChatHistory(int maxCount) {
        this.maxCount = maxCount;
    }

    //getter
    public int getHistoryCount() {
        return historyCount;
    }

    //setter
    //<EMPTY>
    //boolean
    /**
     * checks if history is empty
     * @return true when history is empty, else returns false
     */
    public boolean isEmpty() {
        return historyCount==0;
    }

    /**
     * checks if history is full
     * @return true when history is full, else returns false
     */
    public boolean isFull() {
        return historyCount == maxCount;
    }

    //misc
    /**
     * shows the (idx+1)th item from history (i.e. history[idx])
     * @param idx the idx of item to be returned
     * @return the (idx+1)th item in history
     */
    public String peek(int idx) {
        return  history[idx];
    }

    /**
     * pushes a new string onto the history
     * @param s string to be pushed
     */
    public void push(String s) {
        try {
            if (this.isFull()) {
                throw new HistoryFullException("Sorry, history is full");
            } else {
                history[historyCount]=s;
                historyCount+=1;
            }
        } catch (HistoryFullException e) {
            System.out.print("Error: "+e.getMessage());
        }
    }

    /**
     * returns the chat history as a string of items in pointer form
     * @return string of items in pointer form
     */
    @Override
    public String toString() {
        try {
            if (this.isEmpty()) {
                throw new HistoryEmptyException("History is empty");
            } else {
                //history not empty
                String out = "1. ";

                for (int i = 0; i<= historyCount-2;i++) {
                    int nextIdx = i+2;
                    out = out + history[i] + "\n" + nextIdx + ". ";
                }

                out = out + history[historyCount-1];
                return out;

            }
        } catch (HistoryEmptyException e) {
            return "Error: "+ e.getMessage();
        }

    }
}
