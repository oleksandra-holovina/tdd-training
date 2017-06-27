package training.impl;

import training.InstructionMessageValidator;
import training.exceptions.ValidationException;
import training.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidatorImpl implements InstructionMessageValidator {
    private Constants constants = new Constants();

    private void validateNumberOfParameters(String[] message) {
        if (message.length != constants.NUMBER_OF_PARAMETERS) {
            throw new ValidationException("Number of arguments should be 6: message, type, code, quantity,uom, " +
                    "timestamp separated by space");
        }
    }

    private String validateInstructionType(String[] message) {
        String type = message[constants.TYPE_INDEX];
        if (!"A".equals(type) && !"B".equals(type) && !"C".equals(type) && !"D".equals(type)){
            throw new ValidationException("The type "+type+" is not allowed! Available types: A,B,C,D");
        }
        return type;
    }

    private String validateProductCode(String[] message) {
        String code = message[constants.CODE_INDEX];
        if(!code.matches("[A-Z]{2}\\d{2}")){
            throw new ValidationException("The product code "+code+" is not allowed! Available codes: 2 uppercase " +
                    "letters followed by 2 digits");
        }
        return code;
    }

    private int strToInt(String number){
        try{
            return Integer.parseInt(number);
        }
        catch (NumberFormatException e){
            throw new ValidationException(number + " is not a number");
        }
    }

    private int validateQuantity(String[] message) {
        String quantityStr = message[constants.QUANTITY_INDEX];
        int quantity = strToInt(quantityStr);
        if (quantity < 0){
            throw new ValidationException("The quantity should be a number greater than 0");
        }

        return quantity;
    }

    private int validateUOM(String[] message) {
        String uomStr = message[constants.UOM_INDEX];
        int uom = strToInt(uomStr);
        if (uom < 0 || uom > 256){
            throw new ValidationException("The UOM should be in range 0-256");
        }

        return uom;
    }

    private Date validateTimestamp(String[] message) {
        String timestamp = message[constants.TIMESTAMP_INDEX];
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(timestamp);
        } catch (ParseException e) {
            throw new ValidationException("There is an error in timestamp");
        }
        return date;
    }

    @Override
    public InstructionMessage getInstructionMessage(String[] messageParts) {
        validateNumberOfParameters(messageParts);
        String type = validateInstructionType(messageParts);
        String code = validateProductCode(messageParts);
        int quantity = validateQuantity(messageParts);
        int uom = validateUOM(messageParts);
        Date date = validateTimestamp(messageParts);

        String message = messageParts[constants.MESSAGE_INDEX];
        return new InstructionMessage(message, type, code, quantity, uom, date);
    }
}
