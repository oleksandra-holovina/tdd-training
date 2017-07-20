package tdd.task;

import tdd.task.entities.InstructionMessage;
import tdd.task.parsing.InstructionMessageParser;
import org.junit.Before;
import org.junit.Test;
import tdd.task.entities.MessageType;
import tdd.task.queue.InstructionQueue;
import tdd.task.parsing.ParsingException;
import tdd.task.validation.InstructionMessageValidator;
import tdd.task.validation.ValidationException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class MessageReceiverImplTest {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private static final MessageType DEFAULT_TYPE = MessageType.B;
    private static final String DEFAULT_CODE = "MZ89";
    private static final int DEFAULT_QUANTITY = 5678;
    private static final int DEFAULT_UOM = 50;
    private static final LocalDateTime DEFAULT_DATE_TIME = getDateTimeFromString("2015-03-05T10:04:56.012Z");

    private static final String CORRECT_MESSAGE = "InstructionMessage B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_PARSE_EXCEPTION = "InstructionMessage a MZ89 5678 50\n";
    private static final String MESSAGE_WITH_VALIDATION_EXCEPTION = "InstructionMessage A M89 5678 50 " +
            "2015-03-05T10:04:56.012Z\n";

    private MessageReceiver receiver;
    private InstructionQueue queue;

    @Before
    public void setUp() {
        InstructionMessageParser parser = new InstructionMessageParser();
        InstructionMessageValidator validator = new InstructionMessageValidator();

        queue = new InstructionQueue();
        receiver = new MessageReceiverImpl(parser, validator, queue);
    }
    @Test
    public void shouldEnqueueCorrectMessage() {
        assertEquals(queue.count(), 0);

        receiver.receive(CORRECT_MESSAGE);
        assertEquals(queue.count(), 1);
    }

    @Test
    public void shouldReceiveCorrectMessageType() {
        InstructionMessage message = receiveInstructionMessageAndReturn();
        assertEquals(message.getInstructionType(), DEFAULT_TYPE);
    }

    @Test
    public void shouldReceiveCorrectMessageCode() {
        InstructionMessage message = receiveInstructionMessageAndReturn();
        assertEquals(message.getProductCode(), DEFAULT_CODE);
    }

    @Test
    public void shouldReceiveCorrectMessageQuantity() {
        InstructionMessage message = receiveInstructionMessageAndReturn();
        assertEquals(message.getQuantity(), DEFAULT_QUANTITY);
    }

    @Test
    public void shouldReceiveCorrectMessageUom() {
        InstructionMessage message = receiveInstructionMessageAndReturn();
        assertEquals(message.getUOM(), DEFAULT_UOM);
    }

    @Test
    public void shouldReceiveCorrectMessageTimestamp() {
        InstructionMessage message = receiveInstructionMessageAndReturn();
        assertEquals(message.getTimestamp(), DEFAULT_DATE_TIME);
    }

    @Test(expected = ParsingException.class)
    public void shouldReceiveMessageWithParsingException() {
        receiver.receive(MESSAGE_WITH_PARSE_EXCEPTION);
    }

    @Test(expected = ValidationException.class)
    public void shouldReceiveMessageWithValidationException() {
        receiver.receive(MESSAGE_WITH_VALIDATION_EXCEPTION);
    }

    private static LocalDateTime getDateTimeFromString(String dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        return LocalDateTime.parse(dateTime, formatter);
    }

    private InstructionMessage receiveInstructionMessageAndReturn(){
        receiver.receive(CORRECT_MESSAGE);
        return queue.peek();
    }
}