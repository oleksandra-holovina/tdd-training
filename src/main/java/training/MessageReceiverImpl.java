package training;

import training.exceptions.ValidationException;
import training.interfaces.MessageReceiver;

import java.util.List;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class MessageReceiverImpl implements MessageReceiver {
    private InstructionMessageParser parser = new InstructionMessageParser();
    private InstructionMessageValidator validator = new InstructionMessageValidator();
    private InstructionQueue queue = new InstructionQueue();

    @Override
    public void receive(String text) {
        List<InstructionMessage> messages = parser.parse(text);

        try {
            validator.validate(messages);
            enqueueMultipleMessages(messages);
        }catch (ValidationException ignored){}
    }

    private void enqueueMultipleMessages(List<InstructionMessage> messages){
        messages.forEach(message -> queue.enqueue(message));
    }
}
