package tdd.task.parsing;

import tdd.task.entities.InstructionMessage;
import tdd.task.entities.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InstructionMessageParser {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String WORD_DELIMITER_REGEX = "\\s";

    private static final int TYPE_INDEX = 1;
    private static final int CODE_INDEX = 2;
    private static final int QUANTITY_INDEX  = 3;
    private static final int UOM_INDEX = 4;
    private static final int TIMESTAMP_INDEX = 5;

    private static final String MESSAGE_IS_NULL = "The message passed is equal to null";
    private static final String INCORRECT_STRUCTURE_MESSAGE = "The structure should be the following:" +
            "InstructionMessage type code quantity uom timestamp\\n";
    private static final String INCORRECT_TYPE_MESSAGE = "The type is incorrect. Available types: A,B,C,D";
    private static final String INCORRECT_NUMBER_MESSAGE = " is not a number";
    private static final String INCORRECT_TIMESTAMP_MESSAGE = "There is an error in timestamp";

    private static final String INSTRUCTION_MESSAGE_REGEX = "^InstructionMessage \\S \\S+ \\S+ \\S+ \\S+\n$";

    public InstructionMessage parse(String text){
        checkIfMessageIsNull(text);
        checkStructure(text);

        String[] messageParts = splitMessageByDelimiter(text);
        return createInstructionMessage(messageParts);
    }

    private void checkIfMessageIsNull(String text){
        if (text == null ){
            throw new ParsingException(MESSAGE_IS_NULL);
        }
    }

    private void checkStructure(String text){
        if (!text.matches(INSTRUCTION_MESSAGE_REGEX)){
            throw new ParsingException(INCORRECT_STRUCTURE_MESSAGE);
        }
    }

    private InstructionMessage createInstructionMessage(String [] messageParts){
        MessageType type = getMessageType(messageParts[TYPE_INDEX]);
        String code = messageParts[CODE_INDEX];
        int quantity = convertStringToInteger(messageParts[QUANTITY_INDEX]);
        int uom = convertStringToInteger(messageParts[UOM_INDEX]);
        LocalDateTime timestamp = getTimestamp(messageParts[TIMESTAMP_INDEX]);

        return new InstructionMessage(type, code, quantity, uom, timestamp);
    }

    private MessageType getMessageType(String type){
        try {
            return MessageType.valueOf(type);
        }
        catch (IllegalArgumentException e){
            throw new ParsingException(INCORRECT_TYPE_MESSAGE);
        }
    }

    private int convertStringToInteger(String number){
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ParsingException(number + INCORRECT_NUMBER_MESSAGE);
        }
    }

    private LocalDateTime getTimestamp(String timestamp) {
        try {
            return LocalDateTime.parse(timestamp, formatter);
        }
        catch (DateTimeParseException e) {
            throw new ParsingException(INCORRECT_TIMESTAMP_MESSAGE);
        }
    }

    private String[] splitMessageByDelimiter(String message) {
        return message.split(WORD_DELIMITER_REGEX);
    }
}
