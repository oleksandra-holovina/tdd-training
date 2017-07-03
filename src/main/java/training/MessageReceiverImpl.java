package training;

import training.entities.InstructionMessage;
import training.interfaces.MessageReceiver;
import training.parsing.InstructionMessageParser;
import training.validation.InstructionMessageValidator;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class MessageReceiverImpl implements MessageReceiver {
    private InstructionMessageParser parser = new InstructionMessageParser();
    private InstructionMessageValidator validator = new InstructionMessageValidator();
    private InstructionQueue queue = new InstructionQueue();

    @Override
    public void receive(String text) {
        InstructionMessage message = parser.parse(text);
        validator.validate(message);
        queue.enqueue(message);
    }
}
