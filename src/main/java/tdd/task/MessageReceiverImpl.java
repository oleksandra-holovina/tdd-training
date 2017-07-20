package tdd.task;

import tdd.task.entities.InstructionMessage;
import tdd.task.parsing.InstructionMessageParser;
import tdd.task.queue.InstructionQueue;
import tdd.task.validation.InstructionMessageValidator;

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
