package training;

import training.enums.MESSAGE_TYPE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessage {
    private AtomicInteger serialNumGenerator = new AtomicInteger(0);
    private final int serialNum;

    private String instructionMessage;
    private MESSAGE_TYPE instructionType;
    private String productCode;
    private int quantity;
    private int UOM;
    private Date timestamp;

    public InstructionMessage(String instructionMessage, MESSAGE_TYPE instructionType, String productCode, int quantity, int UOM, Date timestamp) {
        this.serialNum = serialNumGenerator.getAndIncrement();

        this.instructionMessage = instructionMessage;
        this.instructionType = instructionType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.UOM = UOM;
        this.timestamp = timestamp;
    }

    public int getSerialNum() {
        return serialNum;
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    public MESSAGE_TYPE getInstructionType() {
        return instructionType;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUOM() {
        return UOM;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
