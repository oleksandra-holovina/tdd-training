package training.impl;

import training.InstructionQueue;
import training.impl.InstructionMessage;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionQueueImpl implements InstructionQueue {
    private Queue<InstructionMessage> queue = new PriorityQueue<>(typeComparator);

    public static Comparator<InstructionMessage> typeComparator =
            Comparator.comparing(InstructionMessage::getInstructionType);

    public void enqueue(InstructionMessage message) {
        queue.add(message);
    }

    public InstructionMessage dequeue() {
        return queue.poll();
    }

    public InstructionMessage peek() {
        return queue.peek();
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
