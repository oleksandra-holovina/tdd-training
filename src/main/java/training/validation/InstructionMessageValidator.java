package training.validation;

import training.entities.InstructionMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class InstructionMessageValidator {
    private static final int LOWER_QUANTITY_BOUND = 0;
    private static final int LOWER_UOM_BOUND = 0;
    private static final int UPPER_UOM_BOUND = 256;
    private static final String PRODUCT_CODE_REGEX = "[A-Z]{2}\\d{2}";

    private static final String INVALID_CODE_MESSAGE = "Invalid code. Available codes: 2 uppercase letters followed by 2 digits";
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private static final String INVALID_UOM_MESSAGE = "The UOM should be in range 0-256";
    private static final String INVALID_TIMESTAMP_MESSAGE = "The date should be in range [1 January 1970 - today]";

    public void validate(InstructionMessage message) {
        validateProductCode(message);
        validateQuantity(message);
        validateUOM(message);
        validateTimestamp(message);
    }


    private void validateProductCode(InstructionMessage message) {
        String code = message.getProductCode();

        if (code == null || !code.matches(PRODUCT_CODE_REGEX)) {
            throw new ValidationException(INVALID_CODE_MESSAGE);
        }
    }

    private void validateQuantity(InstructionMessage message) {
        int quantity = message.getQuantity();

        if (quantity <= LOWER_QUANTITY_BOUND) {
            throw new ValidationException(INVALID_QUANTITY_MESSAGE);
        }
    }

    private void validateUOM(InstructionMessage message) {
        int uom = message.getUOM();

        if (uom < LOWER_UOM_BOUND || uom >= UPPER_UOM_BOUND) {
            throw new ValidationException(INVALID_UOM_MESSAGE);
        }
    }

    private void validateTimestamp(InstructionMessage message) {
        LocalDateTime timestamp = message.getTimestamp();
        if (isTimestampInvalid(timestamp)) {
            throw new ValidationException(INVALID_TIMESTAMP_MESSAGE);
        }
    }

    private boolean isTimestampInvalid(LocalDateTime timestamp){
        LocalDateTime unixEpoch = getUnixEpoch();

        return timestamp.isBefore(unixEpoch)
                || timestamp.equals(unixEpoch)
                || timestamp.isAfter(getCurrentDateTime());
    }

    private LocalDateTime getUnixEpoch() {
        Instant unixEpoch = Instant.EPOCH;
        return LocalDateTime.ofInstant(unixEpoch, ZoneOffset.UTC);
    }

    private LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }
}
