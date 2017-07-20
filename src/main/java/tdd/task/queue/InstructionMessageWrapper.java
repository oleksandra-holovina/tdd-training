package tdd.task.queue;

import tdd.task.entities.InstructionMessage;

import java.util.Comparator;

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

    private int getPriority(){
        return message.getInstructionType().getPriority();
    }

}