package training.entities;

import java.time.LocalDateTime;

public class InstructionMessage {
    private MessageType instructionType;
    private String productCode;
    private int quantity;
    private int UOM;
    private LocalDateTime timestamp;

    public InstructionMessage(MessageType instructionType, String productCode, int quantity, int UOM,
                              LocalDateTime timestamp) {
        this.instructionType = instructionType;
        this.productCode = productCode;
        this.quantity = quantity;
        this.UOM = UOM;
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

    public int getUOM() {
        return UOM;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
