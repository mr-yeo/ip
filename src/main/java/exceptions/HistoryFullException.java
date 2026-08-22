package exceptions;

public class HistoryFullException extends RuntimeException{
    public HistoryFullException(String s){
        super(s);
    }
}
