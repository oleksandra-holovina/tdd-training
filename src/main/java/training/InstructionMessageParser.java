package training;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public interface InstructionMessageParser {
    String [] splitTextIntoMessages(String text);
    String [] splitMessageBySpaces(String message);
}
