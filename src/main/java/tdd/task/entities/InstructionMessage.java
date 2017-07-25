package tdd.task.entities;

import java.time.LocalDateTime;

public class InstructionMessage {
    private MessageType instructionType;
    private String productCode;
    private int quantity;
    private int uom;
    private LocalDateTime timestamp;

    public InstructionMessage(MessageType instructionType, String productCode, int quantity, int uom,
                              LocalDateTime timestamp) {
        this.instructionType = instructionType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.uom = uom;
        this.timestamp = timestamp;
    }

    public MessageType getInstructionType() {
        return instructionType;
    }

    public String getProductCode() {
        return productCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getUom() {
        return uom;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
