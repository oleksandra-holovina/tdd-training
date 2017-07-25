package tdd.task.queue;

import tdd.task.entities.InstructionMessage;

import java.util.Comparator;

class InstructionMessageWrapper implements Comparable<InstructionMessageWrapper>{
    private final InstructionMessage message;
    private final int sequenceNumber;

    InstructionMessageWrapper(InstructionMessage message, int sequenceNumber){
        this.message = message;
        this.sequenceNumber = sequenceNumber;
    }

    public InstructionMessage getMessage() {
        return message;
    }

    @Override
    public int compareTo(InstructionMessageWrapper messageWrapper) {

        return Comparator.comparing(InstructionMessageWrapper::getPriority).
                thenComparing(InstructionMessageWrapper::getSequenceNumber).
                compare(this, messageWrapper);
    }

    private int getSequenceNumber() {
        return sequenceNumber;
    }

    private int getPriority(){
        return message.getInstructionType().getPriority();
    }

}