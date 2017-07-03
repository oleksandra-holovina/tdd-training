package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.parsing.InstructionMessageParser;
import training.parsing.ParsingException;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageParserTest {
    private final String INVALID_PARAMS_NUMBER = "Number of arguments should be 6";
    private final String INVALID_TYPE = "The type is invalid. Available types: A,B,C,D";
    private final String INVALID_NUMBER = "is not a number";
    private final String INVALID_TIMESTAMP = "There is an error in timestamp";

    private InstructionMessageParser parser;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        parser = new InstructionMessageParser();
    }


    @Test
    public void throwExceptionWhenInvalidNumberOfArguments() throws Exception {
        setException(INVALID_PARAMS_NUMBER);

        String messages = "msg A MZ89 5678 50\n";
        parser.parse(messages);
    }

    @Test
    public void throwExceptionWhenInvalidType() throws Exception {
        setException(INVALID_TYPE);

        String messages = "msg 5 MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void throwExceptionWhenInvalidQuantity() throws Exception {
        setException(INVALID_NUMBER);

        String messages = "msg A MZ89 a 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void throwExceptionWhenInvalidUom() throws Exception {
        setException(INVALID_NUMBER);

        String messages = "msg A MZ89 5 a 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    @Test
    public void throwExceptionWhenInvalidTimestamp() throws Exception {
        setException(INVALID_TIMESTAMP);

        String messages = "msg A MZ89 5678 50 2015-03-05\n";
        parser.parse(messages);
    }

    @Test
    public void throwExceptionWhenValidMessage() throws Exception {
        String messages = "msg B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
        parser.parse(messages);
    }

    private void setException(String message) {
        exception.expect(ParsingException.class);
        exception.expectMessage(message);
    }
}