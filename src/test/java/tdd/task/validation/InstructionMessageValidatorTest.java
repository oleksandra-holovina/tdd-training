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

    private static final String INVALID_CODE = "ns";

    private static final int QUANTITY_BOUND = 0;
    private static final int UOM_LOWER_BOUND = 0;
    private static final int UOM_UPPER_BOUND = 256;
    private static LocalDateTime DATE_TIME_LOWER_BOUND = getUnixEpoch();
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
                INVALID_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenQuantityLessThanBound() {
        setException(INVALID_QUANTITY_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND - 1, UOM_LOWER_BOUND,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenQuantityAtBound() {
        setException(INVALID_QUANTITY_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND, UOM_LOWER_BOUND,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenQuantityGreaterThanBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenUomLessThanLowerBound() {
        setException(INVALID_UOM_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND - 1,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenUomAtLowerBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenUomGreaterThanLowerBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND + 1,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenUomLessThanUpperBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_UPPER_BOUND - 1,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenUomAtUpperBound() {
        setException(INVALID_UOM_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_UPPER_BOUND,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenUomGreaterThanUpperBound() {
        setException(INVALID_UOM_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_UPPER_BOUND + 1,
                DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenTimestampLessThanLowerBound() {
        setException(INVALID_TIMESTAMP_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_LOWER_BOUND.minusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenTimestampAtLowerBound() {
        setException(INVALID_TIMESTAMP_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_LOWER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenTimestampGreaterThanLowerBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_LOWER_BOUND.plusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenTimestampLessThanUpperBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND.minusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenTimestampAtUpperBound() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE, DEFAULT_CODE,
                QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenTimestampAfterUpperBound() {
        setException(INVALID_TIMESTAMP_MESSAGE);

        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND,
                DATE_TIME_UPPER_BOUND.plusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldNotThrowExceptionWhenCorrectMessage() {
        InstructionMessage message = createInstructionMessage(DEFAULT_TYPE,
                DEFAULT_CODE, QUANTITY_BOUND + 1, UOM_LOWER_BOUND, DATE_TIME_UPPER_BOUND);
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