package training.impl;

import training.InstructionMessageValidator;
import training.InstructionQueue;
import training.InstructionMessageParser;
import training.MessageReceiver;
import training.exceptions.ValidationException;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class MessageReceiverImpl implements MessageReceiver {
    private InstructionMessageParser parser = new InstructionMessageParserImpl();
    private InstructionMessageValidator validator = new InstructionMessageValidatorImpl();
    private InstructionQueue queue = new InstructionQueueImpl();

    private InstructionMessage processStringMessage(String message) {
        String[] messageParts = parser.splitMessageBySpaces(message);

        InstructionMessage validMessage = null;
        try {
            validMessage = validator.getInstructionMessage(messageParts);
        } catch (ValidationException e) {
            System.out.println("Wrong message: " + e.toString());
        }
        return validMessage;
    }

    @Override
    public void receive(String text) {
        String[] messages = parser.splitTextIntoMessages(text);
        for (String message : messages) {
            InstructionMessage validMessage = processStringMessage(message);
            if (validMessage != null) {
                queue.enqueue(validMessage);
            }
        }
    }
}
