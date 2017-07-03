package training.entities;

/**
 * Created by Oleksandra_Holovina on 6/29/2017.
 */
public enum MessageType {
    A(1), B(2), C(3), D(3);

    private int priority;

    MessageType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }
}
