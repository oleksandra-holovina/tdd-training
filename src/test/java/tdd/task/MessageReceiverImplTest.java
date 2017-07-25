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
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private static final MessageType DEFAULT_TYPE = MessageType.B;
    private static final String DEFAULT_CODE = "MZ89";
    private static final int DEFAULT_QUANTITY = 5678;
    private static final int DEFAULT_UOM = 50;
    private static final LocalDateTime DEFAULT_DATE_TIME = LocalDateTime.parse("2015-03-05T10:04:56.012Z", formatter);

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
        InstructionMessage message = receiveInstructionMessageAndReturn();
        checkInstructionMessage(message);
    }

    @Test(expected = ParsingException.class)
    public void shouldThrowParsingExceptionWhenMessageCanNotBeParsed() {
        receiver.receive(MESSAGE_WITH_PARSE_EXCEPTION);
    }

    @Test(expected = ValidationException.class)
    public void shouldThrowValidationExceptionWhenMessageIsInvalid() {
        receiver.receive(MESSAGE_WITH_VALIDATION_EXCEPTION);
    }

    private InstructionMessage receiveInstructionMessageAndReturn(){
        receiver.receive(CORRECT_MESSAGE);
        return queue.peek();
    }

    private void checkInstructionMessage(InstructionMessage message) {
        assertEquals(message.getInstructionType(), DEFAULT_TYPE);
        assertEquals(message.getProductCode(), DEFAULT_CODE);
        assertEquals(message.getQuantity(), DEFAULT_QUANTITY);
        assertEquals(message.getUom(), DEFAULT_UOM);
        assertEquals(message.getTimestamp(), DEFAULT_DATE_TIME);
    }
}