package training.parsing;

import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageParser {
    private final int NUMBER_OF_PARAMETERS = 6;
    private final int MESSAGE_INDEX = 0;
    private final int TYPE_INDEX = 1;
    private final int CODE_INDEX = 2;
    private final int QUANTITY_INDEX  = 3;
    private final int UOM_INDEX = 4;
    private final int TIMESTAMP_INDEX = 5;

    private final String INVALID_PARAMS_NUMBER = "Number of arguments should be 6: message, type, code, quantity,uom, " +
            "timestamp separated by space";
    private final String INVALID_TYPE = "The type is invalid. Available types: A,B,C,D";
    private final String INVALID_NUMBER = " is not a number";
    private final String INVALID_TIMESTAMP = "There is an error in timestamp";

    public InstructionMessage parse(String text){
        String message = getAllBeforeNewLine(text);

        String[] messageParts = splitMessageBySpaces(message);
        return createInstructionMessage(messageParts);
    }

    private InstructionMessage createInstructionMessage(String [] messageParts){
        validateNumberOfParameters(messageParts);

        String message = messageParts[MESSAGE_INDEX];
        MessageType type = validateMessageType(messageParts[TYPE_INDEX]);
        String code = messageParts[CODE_INDEX];
        int quantity = strToInt(messageParts[QUANTITY_INDEX]);
        int uom = strToInt(messageParts[UOM_INDEX]);
        Date date = validateTimestamp(messageParts[TIMESTAMP_INDEX]);

        return new InstructionMessage(message, type, code, quantity, uom, date);
    }

    private void validateNumberOfParameters(String[] message) {
        if (message.length != NUMBER_OF_PARAMETERS) {
            throw new ParsingException(INVALID_PARAMS_NUMBER);
        }
    }

    private MessageType validateMessageType(String typeStr){
        try {
            return MessageType.valueOf(typeStr);
        }
        catch (IllegalArgumentException e){
            throw new ParsingException(INVALID_TYPE);
        }
    }

    private int strToInt(String number){
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ParsingException(number + INVALID_NUMBER);
        }
    }

    private Date validateTimestamp(String timestamp) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestamp);
        } catch (ParseException e) {
            throw new ParsingException(INVALID_TIMESTAMP);
        }
    }

    private String[] splitMessageBySpaces(String message) {
        return message.split("\\s");
    }

    private String getAllBeforeNewLine(String message) {
        return message.split("\\r?\\n")[0];
    }
}
