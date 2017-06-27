package training;

import training.exceptions.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidator {

    public void validate(List<InstructionMessage> messages){
        messages.forEach(this::validateSingleMessage);
    }

    private void validateSingleMessage(InstructionMessage message){
        try{
            validateInstructionType(message);
        }
        catch (ValidationException ignored){}
    }


    private void validateInstructionType(InstructionMessage message) {
        String type = message.getInstructionType();
        if (!"A".equals(type) && !"B".equals(type) && !"C".equals(type) && !"D".equals(type)){
            throw new ValidationException("The type "+type+" is not allowed! Available types: A,B,C,D");
        }
    }

    private void validateProductCode(InstructionMessage message) {
        String code = message.getProductCode();

        if(!code.matches("[A-Z]{2}\\d{2}")){
            throw new ValidationException("The product code "+code+" is not allowed! Available codes: 2 uppercase " +
                    "letters followed by 2 digits");
        }
    }

    private void validateQuantity(InstructionMessage message) {
        int quantity = message.getQuantity();

        if (quantity < 0){
            throw new ValidationException("The quantity should be a number greater than 0");
        }
    }

    private void validateUOM(InstructionMessage message) {
        int uom = message.getUOM();

        if (uom < 0 || uom > 256){
            throw new ValidationException("The UOM should be in range 0-256");
        }
    }
}
