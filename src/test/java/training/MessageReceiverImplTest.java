package training;

import org.junit.Before;
import org.junit.Test;
import training.queue.InstructionQueue;
import training.parsing.InstructionMessageParser;
import training.parsing.ParsingException;
import training.validation.InstructionMessageValidator;
import training.validation.ValidationException;

public class MessageReceiverImplTest {
    private static final String CORRECT_MESSAGE = "InstructionMessage B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
    private static final String MESSAGE_WITH_PARSE_EXCEPTION = "InstructionMessage a MZ89 5678 50\n";
    private static final String MESSAGE_WITH_VALIDATION_EXCEPTION = "InstructionMessage A M89 5678 50 " +
            "2015-03-05T10:04:56.012Z\n";

    private MessageReceiver receiver;

    @Before
    public void setUp() {
        InstructionMessageParser parser = new InstructionMessageParser();
        InstructionMessageValidator validator = new InstructionMessageValidator();
        InstructionQueue queue = new InstructionQueue();

        receiver = new MessageReceiverImpl(parser, validator, queue);
    }
    @Test
    public void shouldReceiveCorrectMessage() {
        receiver.receive(CORRECT_MESSAGE);
    }

    @Test(expected = ParsingException.class)
    public void shouldReceiveMessageWithParsingException() {
        receiver.receive(MESSAGE_WITH_PARSE_EXCEPTION);
    }

    @Test(expected = ValidationException.class)
    public void shouldReceiveMessageWithValidationException() {
        receiver.receive(MESSAGE_WITH_VALIDATION_EXCEPTION);
    }
}