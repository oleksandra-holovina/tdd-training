package training.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessage {
    private String instructionMessage;
    private String instructionType;
    private String productCode;
    private int quantity;
    private int UOM;
    private Date timestamp;

    public InstructionMessage(String instructionMessage, String instructionType, String productCode, int quantity, int UOM, Date timestamp) {
        this.instructionMessage = instructionMessage;
        this.instructionType = instructionType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.UOM = UOM;
        this.timestamp = timestamp;
    }

    public String getInstructionMessage() {
        return instructionMessage;
    }

    public String getInstructionType() {
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