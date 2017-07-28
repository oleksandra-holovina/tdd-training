package tdd.task.parsing;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tdd.task.entities.InstructionMessage;
import tdd.task.entities.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;


public class InstructionMessageParserTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final MessageType DEFAULT_TYPE = MessageType.B;
    private static final String DEFAULT_CODE = "MZ89";
    private static final int DEFAULT_QUANTITY = 5678;
    private static final int DEFAULT_UOM = 50;
    private static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.parse("2015-03-05T10:04:56.012Z", formatter);

    private static final String DEFAULT_CORRECT_MESSAGE = "InstructionMessage B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";

    private static final String INCORRECT_STRUCTURE = "InstructionMessage A MZ89 5678 50\n";
    private static final String INCORRECT_TYPE = "InstructionMessage 5 MZ89 8 50 2015-03-05T10:04:56.012Z\n";
    private static final String INCORRECT_QUANTITY = "InstructionMessage A MZ89 a 50 2015-03-05T10:04:56.012Z\n";
    private static final String INCORRECT_UOM = "InstructionMessage A MZ89 5 a 2015-03-05T10:04:56.012Z\n";
    private static final String INCORRECT_TIMESTAMP = "InstructionMessage A MZ89 5678 50 2015-03-05\n";

    private static final String MESSAGE_IS_NULL = "The message passed is equal to null";
    private static final String INCORRECT_STRUCTURE_MESSAGE = "The structure should be the following:" +
            "InstructionMessage type code quantity uom timestamp\\n";
    private static final String INCORRECT_TYPE_MESSAGE = "The type is incorrect. Available types: A,B,C,D";
    private static final String INCORRECT_NUMBER_MESSAGE = "is not a number";
    private static final String INCORRECT_TIMESTAMP_MESSAGE = "There is an error in timestamp";

    private InstructionMessageParser parser = new InstructionMessageParser();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldNotThrowExceptionWhenParseCorrectType() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getInstructionType(), DEFAULT_TYPE);
    }

    @Test
    public void shouldNotThrowExceptionWhenParseCorrectCode() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getProductCode(), DEFAULT_CODE);
    }

    @Test
    public void shouldNotThrowExceptionWhenParseCorrectQuantity() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test
    public void shouldNotThrowExceptionWhenParseCorrectUom() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getUom(), DEFAULT_UOM);
    }

    @Test
    public void shouldNotThrowExceptionWhenParseCorrectTimestamp() {
        InstructionMessage message = parser.parse(DEFAULT_CORRECT_MESSAGE);
        assertEquals(message.getTimestamp(), DEFAULT_DATE_TIME);
    }

    @Test
    public void shouldThrowExceptionWhenMessageIsNull(){
        setException(MESSAGE_IS_NULL);
        parser.parse(null);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidMessageStructure(){
        setException(INCORRECT_STRUCTURE_MESSAGE);
        parser.parse(INCORRECT_STRUCTURE);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidType() {
        setException(INCORRECT_TYPE_MESSAGE);
        parser.parse(INCORRECT_TYPE);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidQuantity()  {
        setException(INCORRECT_NUMBER_MESSAGE);
        parser.parse(INCORRECT_QUANTITY);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidUom() {
        setException(INCORRECT_NUMBER_MESSAGE);
        parser.parse(INCORRECT_UOM);
    }

    @Test
    public void shouldThrowExceptionWhenInvalidTimestamp(){
        setException(INCORRECT_TIMESTAMP_MESSAGE);
        parser.parse(INCORRECT_TIMESTAMP);
    }

    private void setException(String message) {
        exception.expect(ParsingException.class);
        exception.expectMessage(message);
    }
}