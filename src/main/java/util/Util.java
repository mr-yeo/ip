package util;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Util {

    /**
     * converts a delimited text into an array list of stored string values
     * @param delimitedValues text containing delimited values
     *                        (e.g. a comma separated value(csv) file)
     * @param delimiter the char symbol used to separate the values
     * @return array list of the string values in the text
     */
    public static ArrayList<String> toArrayList(String delimitedValues, char delimiter) {
        ArrayList<String> words = new ArrayList<>();
        int prevIdx = -1;

        //scan input,
        for (int i = 0; i < delimitedValues.length(); i++) {
            char symbol = delimitedValues.charAt(i);
            if(symbol==delimiter) {
                //delimiter, push previous
                words.add(delimitedValues.substring(prevIdx+1,i));
                prevIdx=i;
            } else {
                //continue
            }
        }

        //add last word, since it wasnt added
        words.add(delimitedValues.substring(prevIdx+1));

        return words;
    }

    /**
     * converts a delimited text with custom delimiter
     * strings into an array list of the stored string values
     * @param delimitedValues text containing delimited values
     *                        (e.g. "value1%%%value2##value3@value4")
     * @param delimiters an array list of delimiter strings in the sequence that they appear in the text
     *                   (e.g. ["%%%","##","@"])
     * @return array list of the string values in the text
     */
    public static ArrayList<String> toArrayList(String delimitedValues, ArrayList<String> delimiters) {
        //command format e.g. "word1@@@word2%%word3&word4", [@@@, %%,&] ... -> arraylist<>([word1,word2,...])
        ArrayList<String> words = new ArrayList<>();
        int nextDelimiterIdx = 0; //from list of delimiters

        //pointers, scan from prev to next
        int prevIdx = 0; //start position of current word
        int currIdx = 0; //current position of scan
        int nextIdx = delimitedValues.indexOf( //end position of the current word
                delimiters.get(nextDelimiterIdx),
                        prevIdx);


        //scan input,
        while (currIdx < delimitedValues.length()){
            if(currIdx==nextIdx) {
                //next delimiter encountered, push word, update pointers
                words.add(delimitedValues.substring(prevIdx,nextIdx));

                //update pointers
                int dSize = delimiters.get(nextDelimiterIdx).length();
                prevIdx = nextIdx + dSize;
                currIdx = nextIdx + dSize;

                if(nextDelimiterIdx < delimiters.size() - 1) {
                    //still have a next delimiter
                    nextDelimiterIdx += 1;
                    nextIdx = delimitedValues.indexOf(
                            delimiters.get(nextDelimiterIdx),
                            prevIdx);
                } else {
                    //no more delimiters to read
                    nextIdx=delimitedValues.length();
                }




            } else {
                //continue scan normally
                currIdx+=1;

            }
        }

        //add last word, as it was never added in loop
        words.add(delimitedValues.substring(prevIdx));


        return words;
    }

    /**
     * Extracts an integer value from a substring consisting of a single integer value surrounded by optional whitespace.
     * The substring (from start to end index) must contain only an integer and optional whitespace.
     * Whitespace is trimmed before parsing.
     * @param text the text containing the integer substring
     * @param start the start index of the substring (inclusive)
     * @param end the end index of the substring (exclusive)
     * @return the integer value parsed from text.substring(start, end)
     * @throws NumberFormatException if the substring cannot be parsed as a single integer
     */
    public static int extractIntegerInRange(String text, int start, int end) {
        int num = Integer.parseInt(
                text.substring(start,end).trim());
        return num;
    }

    /**
     * Extracts an integer value from a substring consisting of a single integer value surrounded by optional whitespace.
     * The substring (from start to end of text) must contain only an integer and optional whitespace.
     * Whitespace is trimmed before parsing.
     * @param text the text containing the integer substring
     * @param start the start index from which to extract the integer (inclusive)
     * @return the integer value parsed from text.substring(start)
     * @throws NumberFormatException if the substring cannot be parsed as a single integer
     */
    public static int extractIntegerFromIndex(String text, int start) {
        int num = Integer.parseInt(
                text.substring(start).trim());
        return num;
    }








}
