package training;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import training.parsing.InstructionMessageParser;
import training.parsing.ParsingException;


public class InstructionMessageParserTest {
    private static final String DEFAULT_CORRECT_MESSAGE = "InstructionMessage B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String INVALID_MESSAGE_STRUCTURE = "InstructionMessage A MZ89 5678 50\n";
    private static final String INVALID_MESSAGE_TYPE = "InstructionMessage 5 MZ89 8 50 2015-03-05T10:04:56.012Z\n";
    private static final String INVALID_MESSAGE_QUANTITY = "InstructionMessage A MZ89 a 50 2015-03-05T10:04:56.012Z\n";
    private static final String INVALID_MESSAGE_UOM = "InstructionMessage A MZ89 5 a 2015-03-05T10:04:56.012Z\n";;
    private static final String INVALID_MESSAGE_TIMESTAMP = "InstructionMessage A MZ89 5678 50 2015-03-05\n";

    private final String INVALID_MESSAGE_STRUCTURE_MESSAGE = "The structure should be the following:" +
            "InstructionMessage type, code, quantity,uom, timestamp separated by space";
    private final String INVALID_TYPE_MESSAGE = "The type is invalid. Available types: A,B,C,D";
    private final String INVALID_NUMBER_MESSAGE = "is not a number";
    private final String INVALID_TIMESTAMP_MESSAGE = "There is an error in timestamp";

    private InstructionMessageParser parser;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        parser = new InstructionMessageParser();
    }

    @Test
    public void shouldThrowExceptionWhenWhenMessageIsNull(){
        setException(INVALID_MESSAGE_STRUCTURE_MESSAGE);
        parser.parse(null);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMessageStructure(){
        setException(INVALID_MESSAGE_STRUCTURE_MESSAGE);
        parser.parse(INVALID_MESSAGE_STRUCTURE);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidType() {
        setException(INVALID_TYPE_MESSAGE);
        parser.parse(INVALID_MESSAGE_TYPE);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidQuantity()  {
        setException(INVALID_NUMBER_MESSAGE);
        parser.parse(INVALID_MESSAGE_QUANTITY);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUom() {
        setException(INVALID_NUMBER_MESSAGE);
        parser.parse(INVALID_MESSAGE_UOM);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimestamp(){
        setException(INVALID_TIMESTAMP_MESSAGE);
        parser.parse(INVALID_MESSAGE_TIMESTAMP);
    }

    @Test
    public void shouldThrowNoExceptionWhenValidMessage() {
        parser.parse(DEFAULT_CORRECT_MESSAGE);
    }

    private void setException(String message) {
        exception.expect(ParsingException.class);
        exception.expectMessage(message);
    }
}