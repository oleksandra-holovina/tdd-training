package tdd.task.entities;

public enum MessageType {
    A(MessagePriority.HIGH), B(MessagePriority.MEDIUM), C(MessagePriority.LOW), D(MessagePriority.LOW);

    private int priority;

    MessageType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    private static class MessagePriority {
        private static final int HIGH = 1;
        private static final int MEDIUM = 2;
        private static final int LOW = 3;
    }
}
