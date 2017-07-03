package training.validation;

import training.entities.InstructionMessage;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidator {
    private final String INVALID_CODE = "Invalid code. Available codes: 2 uppercase letters followed by 2 digits";
    private final String INVALID_QUANTITY = "The quantity should be a number greater than 0";
    private final String INVALID_UOM = "The UOM should be in range 0-256";
    private final String INVALID_TIMESTAMP = "The date should be in range [1 January 1970 - today]";

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
            throw new ValidationException(INVALID_CODE);
        }
    }

    private void validateQuantity() {
        int quantity = message.getQuantity();

        if (quantity < 0) {
            throw new ValidationException(INVALID_QUANTITY);
        }
    }

    private void validateUOM() {
        int uom = message.getUOM();

        if (uom < 0 || uom > 256) {
            throw new ValidationException(INVALID_UOM);
        }
    }

    private void validateTimestamp() {
        Date timestamp = message.getTimestamp();
        if (timestamp.before(getUnixEpoch()) || timestamp.after(getTodayDate())) {
            throw new ValidationException(INVALID_TIMESTAMP);
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
