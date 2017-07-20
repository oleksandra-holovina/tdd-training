package tdd.task.parsing;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tdd.task.entities.InstructionMessage;
import tdd.task.entities.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;


public class InstructionMessageParserTest {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final MessageType DEFAULT_MESSAGE_TYPE = MessageType.B;
    private static final String DEFAULT_MESSAGE_CODE = "MZ89";
    private static final int DEFAULT_MESSAGE_QUANTITY = 5678;
    private static final int DEFAULT_MESSAGE_UOM = 50;
    private static final LocalDateTime DEFAULT_DATE_TIME = getLocalDateTimeFromString("2015-03-05T10:04:56.012Z");

    private static final String DEFAULT_CORRECT_MESSAGE = "InstructionMessage B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String INVALID_MESSAGE_STRUCTURE = "InstructionMessage A MZ89 5678 50\n";
    private static final String INVALID_MESSAGE_TYPE = "InstructionMessage 5 MZ89 8 50 2015-03-05T10:04:56.012Z\n";
    private static final String INVALID_MESSAGE_QUANTITY = "InstructionMessage A MZ89 a 50 2015-03-05T10:04:56.012Z\n";
    private static final String INVALID_MESSAGE_UOM = "InstructionMessage A MZ89 5 a 2015-03-05T10:04:56.012Z\n";
    private static final String INVALID_MESSAGE_TIMESTAMP = "InstructionMessage A MZ89 5678 50 2015-03-05\n";

    private static final String MESSAGE_IS_NULL = "The message passed is equal to null";
    private static final String INVALID_MESSAGE_STRUCTURE_MESSAGE = "The structure should be the following:" +
            "InstructionMessage type, code, quantity,uom, timestamp separated by space";
    private static final String INVALID_TYPE_MESSAGE = "The type is invalid. Available types: A,B,C,D";
    private static final String INVALID_NUMBER_MESSAGE = "is not a number";
    private static final String INVALID_TIMESTAMP_MESSAGE = "There is an error in timestamp";

    private InstructionMessageParser parser;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() throws Exception {
        parser = new InstructionMessageParser();
    }

    @Test
    public void shouldThrowExceptionWhenMessageIsNull(){
        setException(MESSAGE_IS_NULL);
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
    public void shouldParseCorrectType() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getInstructionType(), DEFAULT_MESSAGE_TYPE);
    }

    @Test
    public void shouldParseCorrectCode() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getProductCode(), DEFAULT_MESSAGE_CODE );
    }

    @Test
    public void shouldParseCorrectQuantity() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getQuantity(), DEFAULT_MESSAGE_QUANTITY);
    }

    @Test
    public void shouldParseCorrectUOM() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getUOM(), DEFAULT_MESSAGE_UOM);
    }

    @Test
    public void shouldParseCorrectDateTime() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getTimestamp(), DEFAULT_DATE_TIME);
    }

    private void setException(String message) {
        exception.expect(ParsingException.class);
        exception.expectMessage(message);
    }

    private static LocalDateTime getLocalDateTimeFromString(String timestamp){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(timestamp, formatter);
    }
}