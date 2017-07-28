package tdd.task.validation;

import tdd.task.entities.InstructionMessage;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


public class InstructionMessageValidator {
    private static final int LOWER_QUANTITY_BOUND = 1;
    private static final int LOWER_UOM_BOUND = 0;
    private static final int UPPER_UOM_BOUND = 255;
    private static final String PRODUCT_CODE_REGEX = "[A-Z]{2}\\d{2}";
    private static final LocalDateTime greaterThanUnixEpoch = LocalDateTime.ofInstant(Instant.EPOCH, ZoneOffset.UTC).plusSeconds(1);

    private static final String INVALID_CODE_MESSAGE = "Invalid code. Available codes: 2 uppercase letters followed by 2 digits";
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private static final String INVALID_UOM_MESSAGE = "The UOM should be greater or equal to 0 and less than 256";
    private static final String INVALID_TIMESTAMP_MESSAGE = "The timestamp should be greater than 1 January 1970 and less " +
            "or equal to current time";

    public void validate(InstructionMessage message) {
        validateProductCode(message.getProductCode());
        validateQuantity(message.getQuantity());
        validateUom(message.getUom());
        validateTimestamp(message.getTimestamp());
    }

    private void validateProductCode(String code) {
        if (!code.matches(PRODUCT_CODE_REGEX)) {
            throw new ValidationException(INVALID_CODE_MESSAGE);
        }
    }

    private void validateQuantity(int quantity) {
        if (quantity < LOWER_QUANTITY_BOUND) {
            throw new ValidationException(INVALID_QUANTITY_MESSAGE);
        }
    }

    private void validateUom(int uom) {
        if (uom < LOWER_UOM_BOUND || uom > UPPER_UOM_BOUND) {
            throw new ValidationException(INVALID_UOM_MESSAGE);
        }
    }

    private void validateTimestamp( LocalDateTime timestamp) {
        if (isTimestampInvalid(timestamp)) {
            throw new ValidationException(INVALID_TIMESTAMP_MESSAGE);
        }
    }

    private boolean isTimestampInvalid(LocalDateTime timestamp){
        return timestamp.isBefore(greaterThanUnixEpoch) || timestamp.isAfter(LocalDateTime.now());
    }
}
