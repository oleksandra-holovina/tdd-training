package training.validation;

import training.entities.InstructionMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class InstructionMessageValidator {
    private static final int LOWER_BOUND = 0;
    private static final int UPPER_BOUND = 256;
    private static final String MESSAGE_START = "InstructionMessage";
    private static final String PRODUCT_CODE_REGEX = "[A-Z]{2}\\d{2}";

    private static final String INVALID_MESSAGE_START = "The message should start with InstructionMessage";
    private static final String INVALID_CODE_MESSAGE = "Invalid code. Available codes: 2 uppercase letters followed by 2 digits";
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private static final String INVALID_UOM_MESSAGE = "The UOM should be in range 0-256";
    private static final String INVALID_TIMESTAMP_MESSAGE = "The date should be in range [1 January 1970 - today]";

    private InstructionMessage message;

    public void validate(InstructionMessage message) {
        this.message = message;

        validateBeginningOfMessage();
        validateProductCode();
        validateQuantity();
        validateUOM();
        validateTimestamp();
    }

    private void validateBeginningOfMessage(){
        String messageStart = message.getInstructionMessage();

        if (!messageStart.startsWith(MESSAGE_START)) {
            throw new ValidationException(INVALID_MESSAGE_START);
        }
    }

    private void validateProductCode() {
        String code = message.getProductCode();

        if (code == null || !code.matches(PRODUCT_CODE_REGEX)) {
            throw new ValidationException(INVALID_CODE_MESSAGE);
        }
    }

    private void validateQuantity() {
        int quantity = message.getQuantity();

        if (quantity < LOWER_BOUND) {
            throw new ValidationException(INVALID_QUANTITY_MESSAGE);
        }
    }

    private void validateUOM() {
        int uom = message.getUOM();

        if (uom < LOWER_BOUND || uom > UPPER_BOUND) {
            throw new ValidationException(INVALID_UOM_MESSAGE);
        }
    }

    private void validateTimestamp() {
        LocalDateTime timestamp = message.getTimestamp();
        if (timestamp.isBefore(getUnixEpoch()) || timestamp.isAfter(getTodayDate())) {
            throw new ValidationException(INVALID_TIMESTAMP_MESSAGE);
        }
    }

    private LocalDateTime getUnixEpoch() {
        Instant unixEpoch = Instant.EPOCH;
        return LocalDateTime.ofInstant(unixEpoch, ZoneOffset.UTC);
    }

    private LocalDateTime getTodayDate() {
        return LocalDateTime.now();
    }
}
