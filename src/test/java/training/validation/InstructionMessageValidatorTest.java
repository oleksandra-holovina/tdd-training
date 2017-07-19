package training.validation;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class InstructionMessageValidatorTest {
    private static final MessageType DEFAULT_MESSAGE_TYPE = MessageType.A;
    private static final String DEFAULT_MESSAGE_CODE = "AA55";
    private static final int DEFAULT_MESSAGE_QUANTITY = 1;
    private static final int DEFAULT_MESSAGE_UOM_LOWER_BOUND = 0;
    private static final int DEFAULT_MESSAGE_UOM_UPPER_BOUND = 255;

    private static final String INVALID_MESSAGE_CODE = "ns";
    private static final int INVALID_MESSAGE_QUANTITY = 0;
    private static final int INVALID_MESSAGE_UOM_LESS_THAN_LOWER_BOUND = -1;
    private static final int INVALID_MESSAGE_UOM_GREATER_THAN_UPPER_BOUND = 256;

    private static final String INVALID_CODE_MESSAGE = "Available codes: 2 uppercase letters followed by 2 digits";
    private static final String INVALID_QUANTITY_MESSAGE = "The quantity should be a number greater than 0";
    private static final String INVALID_UOM_MESSAGE = "The UOM should be in range 0-256";
    private static final String INVALID_TIMESTAMP_MESSAGE = "The date should be in range [1 January 1970 - today]";

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

       InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
               INVALID_MESSAGE_CODE, DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM_LOWER_BOUND, LocalDateTime.now());
       validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidQuantity(){
        setException(INVALID_QUANTITY_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,INVALID_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM_UPPER_BOUND,
                getUnixEpoch().plusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUomLessThanLowerBound(){
        setException(INVALID_UOM_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,INVALID_MESSAGE_UOM_LESS_THAN_LOWER_BOUND,
                LocalDateTime.now());
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUomGreaterThanUpperBound(){
        setException(INVALID_UOM_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,INVALID_MESSAGE_UOM_GREATER_THAN_UPPER_BOUND,
                LocalDateTime.now());
        validator.validate(message);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimestampLessThanLowerBound() {
        setException(INVALID_TIMESTAMP_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM_LOWER_BOUND, getUnixEpoch());
        validator.validate(message);
    }


    @Test
    public void shouldThrowExceptionWhenInvalidTimestampAfterUpperBound() {
        setException(INVALID_TIMESTAMP_MESSAGE);
        
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM_LOWER_BOUND,
                LocalDateTime.now().plusDays(1));
        validator.validate(message);
    }

    @Test
    public void shouldThrowNoExceptionWhenCorrectMessage() {
        InstructionMessage message = createInstructionMessage(DEFAULT_MESSAGE_TYPE,
                DEFAULT_MESSAGE_CODE,DEFAULT_MESSAGE_QUANTITY,DEFAULT_MESSAGE_UOM_LOWER_BOUND, LocalDateTime.now());
        validator.validate(message);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }
    
    private InstructionMessage createInstructionMessage(MessageType type, String code,
                                                        int quantity, int uom, LocalDateTime date)  {
        return new InstructionMessage(type, code, quantity, uom, date);
    }

    private LocalDateTime getUnixEpoch() {
        Instant unixEpoch = Instant.EPOCH;
        return LocalDateTime.ofInstant(unixEpoch, ZoneOffset.UTC);
    }

}