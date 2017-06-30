package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.InstructionMessageParser;
import training.exceptions.ValidationException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageParserTest {
    private InstructionMessageParser parser;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        parser = new InstructionMessageParser();
    }


    @Test
    public void checkForInvalidNumberOfArguments() throws Exception {
        setException("Number of arguments should be 6");

        String messages = "msg A MZ89 5678 50\n";
        parser.parse(messages);
    }

    @Test
    public void checkForInvalidType() throws Exception {
        setException("The type is invalid. Available types: A,B,C,D");

        String messages = "msg 5 MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void checkForInvalidQuantity() throws Exception {
        setException("is not a number");

        String messages = "msg A MZ89 a 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void checkForInvalidUom() throws Exception {
        setException("is not a number");

        String messages = "msg A MZ89 5 a 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void checkForInvalidTimestamp() throws Exception {
        setException("There is an error in timestamp");

        String messages = "msg A MZ89 5678 50 2015-03-05\n";
        parser.parse(messages);
    }

    @Test
    public void checkForValidMessage() throws Exception {
        String messages = "msg B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    private void setException(String message) {
        exception.expect(ValidationException.class);
        exception.expectMessage(message);
    }
}