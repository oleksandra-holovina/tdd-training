package training;

import training.enums.MESSAGE_TYPE;
import training.exceptions.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public List<InstructionMessage> parse(String text){
        String[] messages = splitTextIntoMessages(text);

        List<InstructionMessage> instructionMessages = new ArrayList<>();

        for (String message : messages) {

            InstructionMessage instructionMessage = processStringMessage(message);

            if (instructionMessage != null) {
                instructionMessages.add(instructionMessage);
            }
        }

        return instructionMessages;
    }

    private InstructionMessage processStringMessage(String message){
        String[] messageParts = splitMessageBySpaces(message);
        return createInstructionMessage(messageParts);
    }

    private InstructionMessage createInstructionMessage(String [] messageParts){
        validateNumberOfParameters(messageParts);

        String message = messageParts[MESSAGE_INDEX];
        MESSAGE_TYPE type = validateMessageType(messageParts[TYPE_INDEX]);
        String code = messageParts[CODE_INDEX];
        int quantity = strToInt(messageParts[QUANTITY_INDEX]);
        int uom = strToInt(messageParts[UOM_INDEX]);
        Date date = validateTimestamp(messageParts[TIMESTAMP_INDEX]);

        return new InstructionMessage(message, type, code, quantity, uom, date);
    }

    private void validateNumberOfParameters(String[] message) {
        if (message.length != NUMBER_OF_PARAMETERS) {
            throw new ValidationException("Number of arguments should be 6: message, type, code, quantity,uom, " +
                    "timestamp separated by space");
        }
    }

    private MESSAGE_TYPE validateMessageType(String typeStr){
        MESSAGE_TYPE type;

        switch (typeStr){
            case "A":
                type = MESSAGE_TYPE.A;
                break;
            case "B":
                type = MESSAGE_TYPE.B;
                break;
            case "C":
                type = MESSAGE_TYPE.C;
                break;
            case "D":
                type = MESSAGE_TYPE.D;
                break;
            default: throw new ValidationException("The type is invalid. Available types: A,B,C,D");
        }

        return type;
    }

    private int strToInt(String number){
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ValidationException(number + " is not a number");
        }
    }

    private Date validateTimestamp(String timestamp) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestamp);
        } catch (ParseException e) {
            throw new ValidationException("There is an error in timestamp");
        }
        return date;
    }

    private String[] splitTextIntoMessages(String text) {
        return text.split("\\r?\\n");
    }

    private String[] splitMessageBySpaces(String message) {
        return message.split("\\s");
    }
}
