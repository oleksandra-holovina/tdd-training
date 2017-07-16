package training.instruction_queue;

import training.entities.InstructionMessage;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;


public class InstructionQueue {
    private Queue<InstructionMessageWrapper> queue = new PriorityQueue<>();

    public void enqueue(InstructionMessage message) {
        queue.add(wrap(message));
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

    private InstructionMessageWrapper wrap(InstructionMessage message){
        return new InstructionMessageWrapper(message);
    }

    private InstructionMessage unwrap(InstructionMessageWrapper wrapper) {
        Optional<InstructionMessageWrapper> o = Optional.ofNullable(wrapper);
        return o.map(InstructionMessageWrapper::getMessage).orElse(null);
    }
}
