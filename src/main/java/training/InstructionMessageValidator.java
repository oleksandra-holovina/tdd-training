package training;

import training.impl.InstructionMessage;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public interface InstructionMessageValidator {
    InstructionMessage getInstructionMessage(String [] messageParts);
}
