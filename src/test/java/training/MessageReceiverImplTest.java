package training;

import org.junit.Before;
import org.junit.Test;
import training.parsing.ParsingException;
import training.validation.ValidationException;
import training.interfaces.MessageReceiver;

/**
 * Created by Oleksandra_Holovina on 6/30/2017.
 */
public class MessageReceiverImplTest {
    private MessageReceiver receiver;

    @Before
    public void setUp() throws Exception {
        receiver = new MessageReceiverImpl();
    }
    @Test
    public void receiveCorrectMessage() throws Exception {
        String messages = "msg B MZ89 5678 50 2015-03-05T10:04:56.012Z\n";
        receiver.receive(messages);
    }

    @Test(expected = ParsingException.class)
    public void receiveMessageWithParsingException() throws Exception {
        String messages = "msg a MZ89 5678 50\n";
        receiver.receive(messages);
    }

    @Test(expected = ValidationException.class)
    public void receiveMessageWithValidationException() throws Exception {
        String messages = "msg A M89 5678 50 2015-03-05T10:04:56.012Z\n";
        receiver.receive(messages);
    }
}