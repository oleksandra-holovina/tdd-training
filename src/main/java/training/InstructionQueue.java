package training;

import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
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
        if (wrapper == null){
            return null;
        }
        return wrapper.getMessage();
    }

    private class InstructionMessageWrapper implements Comparable<InstructionMessageWrapper>{
        private final int serialNum;
        private AtomicInteger serialNumGenerator = new AtomicInteger(0);

        private InstructionMessage message;

        InstructionMessageWrapper(InstructionMessage message){
            this.serialNum = generateSerialNumber();
            this.message = message;
        }

        public InstructionMessage getMessage() {
            return message;
        }

        public int getSerialNum() {
            return serialNum;
        }

        @Override
        public int compareTo(InstructionMessageWrapper messageWrapper) {
            int priorityOrder = compareByPriority(this, messageWrapper);

            if (priorityOrder == 0){
                return compareBySerialNumber(this, messageWrapper);
            }
            return priorityOrder;
        }

        private int compareByPriority(InstructionMessageWrapper wrapper1, InstructionMessageWrapper wrapper2){
            MessageType type1 = wrapper1.getMessage().getInstructionType();
            MessageType type2 = wrapper2.getMessage().getInstructionType();

            return Integer.compare(type1.getPriority(), type2.getPriority());
        }

        private int compareBySerialNumber(InstructionMessageWrapper wrapper1, InstructionMessageWrapper wrapper2){
            return Integer.compare(wrapper1.getSerialNum(), wrapper2.getSerialNum());
        }

        private int generateSerialNumber(){
            return serialNumGenerator.getAndIncrement();
        }
    }

}
