package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.entities.InstructionMessage;
import training.entities.MessageType;
import training.validation.InstructionMessageValidator;
import training.validation.ValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidatorTest {
    private final String INVALID_CODE = "Available codes: 2 uppercase letters followed by 2 digits";
    private final String INVALID_QUANTITY = "The quantity should be a number greater than 0";
    private final String INVALID_UOM = "The UOM should be in range 0-256";
    private final String INVALID_TIMESTAMP = "The date should be in range [1 January 1970 - today]";

    private InstructionMessageValidator validator;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        validator = new InstructionMessageValidator();
    }

    @Test
    public void throwExceptionWhenInvalidMessageCode() throws Exception{
       setException(INVALID_CODE);

       InstructionMessage message = createInstructionMessage("msg", MessageType.A,"ns",5,5,
               "2015-03-05T10:04:56.012Z");
       validator.validate(message);
    }


    @Test
    public void throwExceptionWhenInvalidQuantity() throws Exception{
        setException(INVALID_QUANTITY);
        
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",-58,5,
                "2015-03-05T10:04:56.012Z");
        validator.validate(message);
    }

    @Test
    public void throwExceptionWhenInvalidUomLessThanLowerBound() throws Exception{
        setException(INVALID_UOM);
        
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",5,-5,
                "2015-03-05T10:04:56.012Z");
        validator.validate(message);
    }

    @Test
    public void throwExceptionWhenInvalidUomGreaterThanUpperBound() throws Exception{
        setException(INVALID_UOM);
        
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",5,1000,
                "2015-03-05T10:04:56.012Z");
        validator.validate(message);
    }

    @Test
    public void throwExceptionWhenInvalidTimestampBeforeUnixEpoch() throws Exception{
        setException(INVALID_TIMESTAMP);
        
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",5,10,
                "1000-03-05T10:04:56.012Z");
        validator.validate(message);
    }


    @Test
    public void throwExceptionWhenInvalidTimestampAfterToday() throws Exception{
        setException(INVALID_TIMESTAMP);
        
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",5,10,
                "3100-03-05T10:04:56.012Z");
        validator.validate(message);
    }

    @Test
    public void throwNoExceptionWhenCorrectMessage() throws Exception{
        InstructionMessage message = createInstructionMessage("msg", MessageType.A,"AA55",5,10,
                "2006-03-05T10:04:56.012Z");
        validator.validate(message);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }
    
    private InstructionMessage createInstructionMessage(String text, MessageType type, String code,
                                                        int quantity, int uom, String dateStr) throws ParseException {
        
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(dateStr);
        return new InstructionMessage(text, type, code, quantity, uom, date);
    }
}