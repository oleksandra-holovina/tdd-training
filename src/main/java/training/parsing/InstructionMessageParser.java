package training.parsing;

import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InstructionMessageParser {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String WORD_DELIMITER_REGEX = "\\s";

    private static final int MESSAGE_INDEX = 0;
    private static final int TYPE_INDEX = 1;
    private static final int CODE_INDEX = 2;
    private static final int QUANTITY_INDEX  = 3;
    private static final int UOM_INDEX = 4;
    private static final int TIMESTAMP_INDEX = 5;

    private static final String INVALID_STRUCTURE_MESSAGE = "The structure should be the following:" +
            "InstructionMessage type, code, quantity,uom, timestamp separated by space";
    private static final String INVALID_TYPE_MESSAGE = "The type is invalid. Available types: A,B,C,D";
    private static final String INVALID_NUMBER_MESSAGE = " is not a number";
    private static final String INVALID_TIMESTAMP_MESSAGE = "There is an error in timestamp";

    private static final String INSTRUCTION_MESSAGE_REGEX = "^InstructionMessage\\s\\S\\s\\S+\\s\\S+\\s\\S+\\s\\S+\\n$";

    public InstructionMessage parse(String text){
        parseStructure(text);

        String[] messageParts = splitMessageBySpaces(text);
        return createInstructionMessage(messageParts);
    }

    private void parseStructure(String text){
        if (text == null || !text.matches(INSTRUCTION_MESSAGE_REGEX)){
            throw new ParsingException(INVALID_STRUCTURE_MESSAGE);
        }
    }

    private InstructionMessage createInstructionMessage(String [] messageParts){
        String message = messageParts[MESSAGE_INDEX];
        MessageType type = getMessageType(messageParts[TYPE_INDEX]);
        String code = messageParts[CODE_INDEX];
        int quantity = strToInt(messageParts[QUANTITY_INDEX]);
        int uom = strToInt(messageParts[UOM_INDEX]);
        LocalDateTime date = getTimestamp(messageParts[TIMESTAMP_INDEX]);

        return new InstructionMessage(message, type, code, quantity, uom, date);
    }

    private MessageType getMessageType(String typeStr){
        try {
            return MessageType.valueOf(typeStr);
        }
        catch (IllegalArgumentException e){
            throw new ParsingException(INVALID_TYPE_MESSAGE);
        }
    }

    private int strToInt(String number){
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ParsingException(number + INVALID_NUMBER_MESSAGE);
        }
    }

    private LocalDateTime getTimestamp(String timestamp) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern((DATE_TIME_FORMAT));
            return LocalDateTime.parse(timestamp, formatter);
        } catch (DateTimeParseException e) {
            throw new ParsingException(INVALID_TIMESTAMP_MESSAGE);
        }
    }

    private String[] splitMessageBySpaces(String message) {
        return message.split(WORD_DELIMITER_REGEX);
    }
}
