package training.queue;

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

    private int generateSerialNumber(){
        return serialNumGenerator.getAndIncrement();
    }
}