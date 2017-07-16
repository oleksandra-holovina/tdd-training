package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.entities.InstructionMessage;
import training.entities.MessageType;
import training.validation.InstructionMessageValidator;
import training.validation.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InstructionMessageValidatorTest {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final String DEFAULT_MESSAGE_START = "InstructionMessage";
    private static final MessageType DEFAULT_MESSAGE_TYPE = MessageType.A;
    private static final String DEFAULT_MESSAGE_CODE = "AA55";
    private static final int DEFAULT_MESSAGE_QUANTITY = 5;
    private static final int DEFAULT_MESSAGE_UOM = 5;
    private static final String DEFAULT_MESSAGE_TIMESTAMP = "2015-03-05T10:04:56.012Z";

    private static final String INVALID_MESSAGE_START = "msg";
    private static final String INVALID_MESSAGE_CODE = "ns";
    private static final int INVALID_MESSAGE_QUANTITY = -5;
    private static final int INVALID_MESSAGE_UOM_LESS_THAN_LOWER_BOUND = -5;
    private static final int INVALID_MESSAGE_UOM_GREATER_THAN_UPPER_BOUND = 1000;
    private static final String INVALID_MESSAGE_TIMESTAMP_LESS_THAN_LOWER_BOUND = "1000-03-05T10:04:56.012Z";
    private static final String INVALID_MESSAGE_TIMESTAMP_GREATER_THAN_UPPER_BOUND = "3100-03-05T10:04:56.012Z";

    private final String INVALID_MESSAGE_START_MESSAGE = "The message should start with InstructionMessage";
    private final String INVALID_CODE_MESSAGE = "Available codes: 2 uppercase letters followed by 2 digits";
    private final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private final String INVALID_UOM_MESSAGE = "The UOM should be in range 0-256";
    private final String INVALID_TIMESTAMP_MESSAGE = "The date should be in range [1 January 1970 - today]";

    private InstructionMessageValidator validator;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        validator = new InstructionMessageValidator();
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMessageStart() {
        setException(INVALID_MESSAGE_START_MESSAGE);

        InstructionMessage message = createInstructionMessage(INVALID_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE, DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM, DEFAULT_MESSAGE_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMessageCode() {
       setException(INVALID_CODE_MESSAGE);

       InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
               INVALID_MESSAGE_CODE, DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM, DEFAULT_MESSAGE_TIMESTAMP);
       validator.validate(message);
    }


    @Test
    public void shouldThrowExceptionWhenInvalidQuantity(){
        setException(INVALID_QUANTITY_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,INVALID_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM, DEFAULT_MESSAGE_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUomLessThanLowerBound(){
        setException(INVALID_UOM_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,INVALID_MESSAGE_UOM_LESS_THAN_LOWER_BOUND,
                DEFAULT_MESSAGE_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUomGreaterThanUpperBound(){
        setException(INVALID_UOM_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,INVALID_MESSAGE_UOM_GREATER_THAN_UPPER_BOUND,
                DEFAULT_MESSAGE_TIMESTAMP);
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimestampBeforeUnixEpoch() {
        setException(INVALID_TIMESTAMP_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM,
                INVALID_MESSAGE_TIMESTAMP_LESS_THAN_LOWER_BOUND);
        validator.validate(message);
    }


    @Test
    public void shouldThrowExceptionWhenInvalidTimestampAfterToday() {
        setException(INVALID_TIMESTAMP_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM,
                INVALID_MESSAGE_TIMESTAMP_GREATER_THAN_UPPER_BOUND);
        validator.validate(message);
    }

    @Test
    public void shouldThrowNoExceptionWhenCorrectMessage() {
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_START, DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM, DEFAULT_MESSAGE_TIMESTAMP);
        validator.validate(message);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }
    
    private InstructionMessage createInstructionMessage(String text, MessageType type, String code,
                                                        int quantity, int uom, String dateStr)  {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern((DATE_TIME_FORMAT));
        LocalDateTime date =  LocalDateTime.parse(dateStr, formatter);

        return new InstructionMessage(text, type, code, quantity, uom, date);
    }
}