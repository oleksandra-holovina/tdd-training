package training;

import training.impl.InstructionMessage;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public interface InstructionQueue {
    void enqueue(InstructionMessage message);
    InstructionMessage dequeue();
    InstructionMessage peek();
    int count();
    boolean isEmpty();
}
