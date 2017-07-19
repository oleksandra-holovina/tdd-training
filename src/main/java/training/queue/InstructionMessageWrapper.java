package training.queue;

import training.entities.InstructionMessage;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class InstructionMessageWrapper implements Comparable<InstructionMessageWrapper>{
    private final InstructionMessage message;
    private final int serialNum;

    InstructionMessageWrapper(InstructionMessage message, int serialNum){
        this.message = message;
        this.serialNum = serialNum;
    }

    public InstructionMessage getMessage() {
        return message;
    }

    @Override
    public int compareTo(InstructionMessageWrapper messageWrapper) {

        return Comparator.comparing(InstructionMessageWrapper::getPriority).
                thenComparing(InstructionMessageWrapper::getSerialNum).
                compare(this, messageWrapper);
    }

    private int getSerialNum() {
        return serialNum;
    }

    private static int getPriority(InstructionMessageWrapper wrapper){
        return wrapper.getMessage().getInstructionType().getPriority();
    }

}