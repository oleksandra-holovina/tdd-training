package tdd.task.validation;

import tdd.task.entities.InstructionMessage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tdd.task.entities.MessageType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class InstructionMessageValidatorTest {
    private static final MessageType DEFAULT_TYPE = MessageType.A;
    private static final String DEFAULT_CODE = "AA55";
    private static final int DEFAULT_QUANTITY = 5;
    private static final int DEFAULT_UOM = 5;
    private static final LocalDateTime DEFAULT_TIMESTAMP = getCurrentDateTime();

    private static final String INVALID_CODE = "ns";

    private static final int QUANTITY_BOUND = 1;
    private static final int UOM_LOWER_BOUND = 0;
    private static final int UOM_UPPER_BOUND = 255;
    private static LocalDateTime DATE_TIME_LOWER_BOUND = getUnixEpoch().plusDays(1);
    private static final LocalDateTime DATE_TIME_UPPER_BOUND = getCurrentDateTime();

    private static final String INVALID_CODE_MESSAGE = "Available codes: 2 uppercase letters followed by 2 digits";
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private static final String INVALID_UOM_MESSAGE = "The UOM should be greater or equal to 0 and less than 256";
    private static final String INVALID_TIMESTAMP_MESSAGE = "The date should be greater than 1 January 1970 and less " +
            "or equal to current time";

    private InstructionMessageValidator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        validator = new InstructionMessageValidator();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMessageCode() {
        setException(INVALID_CODE_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                INVALID_CODE, DEFAULT_QUANTITY, DEFAULT_UOM, DEFAULT_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenQuantityLessThanMinValue() {
        setException(INVALID_QUANTITY_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND - 1, DEFAULT_UOM, DEFAULT_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenValidQuantity() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                DEFAULT_UOM, DEFAULT_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenUomLessThanMinValue() {
        setException(INVALID_UOM_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, DEFAULT_QUANTITY,
                UOM_LOWER_BOUND - 1, DEFAULT_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenMinValidUom() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, DEFAULT_QUANTITY,
                UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenUomGreaterThanMinValue() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, DEFAULT_QUANTITY,
                UOM_LOWER_BOUND + 1, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenMaxValidUom() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, DEFAULT_QUANTITY,
                UOM_UPPER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenUomGreaterThanMaxValue() {
        setException(INVALID_UOM_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_UPPER_BOUND + 1, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }


    @Test
    public void shouldThrowExceptionWhenTimestampLessThanMinValue() {
        setException(INVALID_TIMESTAMP_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_LOWER_BOUND, DATE_TIME_LOWER_BOUND.minusDays(1));
        validator.validate(message);
    }


    @Test
    public void shouldNotThrowExceptionWhenMinValidTimestamp() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_LOWER_BOUND, DATE_TIME_LOWER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenTimestampLessThanMaxValue() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND.minusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenMaxValidTimestamp() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenTimestampGreaterThanMaxValue() {
        setException(INVALID_TIMESTAMP_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE, QUANTITY_BOUND,
                UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND.plusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenCorrectMessage() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND, UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }

    private InstructionMessage createInstructionMessage(MessageType type, String code,
                                                        int quantity, int uom, LocalDateTime date) {
        return new InstructionMessage(type, code, quantity, uom, date);
    }

    private static LocalDateTime getUnixEpoch() {
        Instant unixEpoch = Instant.EPOCH;
        return LocalDateTime.ofInstant(unixEpoch, ZoneOffset.UTC);
    }

    private static LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

}