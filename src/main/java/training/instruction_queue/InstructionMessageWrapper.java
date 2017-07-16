package training.instruction_queue;

import training.entities.InstructionMessage;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

public class InstructionMessageWrapper implements Comparable<InstructionMessageWrapper>{
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

        return Comparator.comparing((InstructionMessageWrapper wrapper)->
                            wrapper.getMessage().getInstructionType().getPriority()).
                thenComparing(InstructionMessageWrapper::getSerialNum).
                compare(this, messageWrapper);
    }

    private int generateSerialNumber(){
        return serialNumGenerator.getAndIncrement();
    }
}