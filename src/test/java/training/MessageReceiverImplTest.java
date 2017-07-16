package training;

import org.junit.Before;
import org.junit.Test;
import training.instruction_queue.InstructionQueue;
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
    private InstructionMessageParser parser = new InstructionMessageParser();
    private InstructionMessageValidator validator = new InstructionMessageValidator();
    private InstructionQueue queue = new InstructionQueue();

    @Before
    public void setUp() {
        parser = new InstructionMessageParser();
        validator = new InstructionMessageValidator();
        queue = new InstructionQueue();

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