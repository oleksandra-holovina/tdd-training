package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.InstructionMessageValidator;
import training.enums.MESSAGE_TYPE;
import training.exceptions.ValidationException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidatorTest {
    private InstructionMessageValidator validator;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        validator = new InstructionMessageValidator();
    }

    @Test
    public void checkForInvalidMessageCode() throws Exception{
       setException("Available codes: 2 uppercase letters followed by 2 digits");

       Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z");
       InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"ns",5,5, date);
       validator.validate(message);
    }


    @Test
    public void checkForInvalidQuantity() throws Exception{
        setException("The quantity should be a number greater than 0");

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",-58,5, date);
        validator.validate(message);
    }

    @Test
    public void checkForInvalidUomLessThanZero() throws Exception{
        setException("The UOM should be in range 0-256");

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",5,-5, date);
        validator.validate(message);
    }

    @Test
    public void checkForInvalidUomGreaterThan256() throws Exception{
        setException("The UOM should be in range 0-256");

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",5,1000, date);
        validator.validate(message);
    }

    @Test
    public void validateInvalidTimestampBeforeUnixEpoch() throws Exception{
        setException("The date should be in range [1 January 1970 - today]");

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("1000-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",5,10, date);
        validator.validate(message);
    }


    @Test
    public void validateInvalidTimestampAfterToday() throws Exception{
        setException("The date should be in range [1 January 1970 - today]");

        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("3100-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",5,10, date);
        validator.validate(message);
    }

    @Test
    public void validateCorrectMessage() throws Exception{
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2006-03-05T10:04:56.012Z");
        InstructionMessage message = new InstructionMessage("msg", MESSAGE_TYPE.A,"AA55",5,10, date);
        validator.validate(message);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }
}