package training;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionQueue {
    private Queue<InstructionMessage> queue = new PriorityQueue<>(typeComparator);

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

    private static Comparator<InstructionMessage> typeComparator =
            (message1, message2) -> {
                int order = findOrderByPriority(message1, message2);
                return order == 0 ?
                        message1.getSerialNum() - message2.getSerialNum():
                        order;
            };

    private static int findOrderByPriority(InstructionMessage message1, InstructionMessage message2){
        int priority1 = message1.getInstructionType().getPriority();
        int priority2 = message2.getInstructionType().getPriority();

        return priority1 - priority2;
    }
}
