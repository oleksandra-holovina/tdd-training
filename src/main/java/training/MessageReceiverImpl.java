package training;

import training.entities.InstructionMessage;
import training.queue.InstructionQueue;
import training.parsing.InstructionMessageParser;
import training.validation.InstructionMessageValidator;

public class MessageReceiverImpl implements MessageReceiver {
    private InstructionMessageParser parser;
    private InstructionMessageValidator validator;
    private InstructionQueue queue;

    public MessageReceiverImpl(InstructionMessageParser parser, InstructionMessageValidator validator, InstructionQueue queue) {
        this.parser = parser;
        this.validator = validator;
        this.queue = queue;
    }

    @Override
    public void receive(String text) {
        InstructionMessage message = parser.parse(text);
        validator.validate(message);
        queue.enqueue(message);
    }
}
