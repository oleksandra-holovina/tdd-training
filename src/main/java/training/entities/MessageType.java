package training.entities;

public enum MessageType {
    A(TypeConstants.HIGH), B(TypeConstants.MEDIUM), C(TypeConstants.LOW), D(TypeConstants.LOW);

    private int priority;

    MessageType(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    public static class TypeConstants{
        private static final int HIGH = 1;
        private static final int MEDIUM = 2;
        private static final int LOW = 3;
    }
}
