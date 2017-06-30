package training;

import training.enums.MESSAGE_TYPE;
import training.exceptions.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidator {
    private InstructionMessage message;

    public void validate(InstructionMessage message) {
        this.message = message;

        validateProductCode();
        validateQuantity();
        validateUOM();
        validateTimestamp();
    }


    private void validateProductCode() {
        String code = message.getProductCode();

        if (code == null || !code.matches("[A-Z]{2}\\d{2}")) {
            throw new ValidationException("The product code " + code + " is not allowed! Available codes: 2 uppercase " +
                    "letters followed by 2 digits");
        }
    }

    private void validateQuantity() {
        int quantity = message.getQuantity();

        if (quantity < 0) {
            throw new ValidationException("The quantity should be a number greater than 0");
        }
    }

    private void validateUOM() {
        int uom = message.getUOM();

        if (uom < 0 || uom > 256) {
            throw new ValidationException("The UOM should be in range 0-256");
        }
    }

    private void validateTimestamp() {
        Date timestamp = message.getTimestamp();
        if (timestamp.before(getUnixEpoch()) || timestamp.after(getTodayDate())) {
            throw new ValidationException("The date should be in range [1 January 1970 - today]");
        }
    }

    private Date getUnixEpoch() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.JANUARY, 1);

        return calendar.getTime();
    }

    private Date getTodayDate() {
        return Calendar.getInstance().getTime();
    }
}
