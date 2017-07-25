package tdd.task.queue;

import tdd.task.entities.InstructionMessage;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;


public class InstructionQueue {
    private AtomicInteger sequenceNumberGenerator = new AtomicInteger();

    private Queue<InstructionMessageWrapper> queue = new PriorityQueue<>();

    public void enqueue(InstructionMessage message) {
        int serialNum = sequenceNumberGenerator.getAndIncrement();
        queue.add(wrap(message, serialNum));
    }

    public InstructionMessage dequeue() {
        return unwrap(queue.poll());
    }

    public InstructionMessage peek() {
        return unwrap(queue.peek());
    }

    public int count() {
        return queue.size();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    private InstructionMessageWrapper wrap(InstructionMessage message, int serialNum){
        return new InstructionMessageWrapper(message, serialNum);
    }

    private InstructionMessage unwrap(InstructionMessageWrapper wrapper) {
        Optional<InstructionMessageWrapper> o = Optional.ofNullable(wrapper);
        return o.map(InstructionMessageWrapper::getMessage).orElse(null);
    }
}
