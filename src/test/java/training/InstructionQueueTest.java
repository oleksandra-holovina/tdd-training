package training;

import org.junit.Before;
import org.junit.Test;
import training.entities.InstructionMessage;
import training.entities.MessageType;
import training.instruction_queue.InstructionQueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class InstructionQueueTest {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DEFAULT_MESSAGE_START = "InstructionMessage";
    private static final String DEFAULT_MESSAGE_CODE = "AA55";
    private static final int DEFAULT_MESSAGE_QUANTITY = 5;
    private static final int DEFAULT_MESSAGE_UOM = 5;
    private static final String DEFAULT_MESSAGE_TIMESTAMP = "2015-03-05T10:04:56.012Z";

    private InstructionQueue queue;
    private InstructionMessage message;

    @Before
    public void setUp() {
        queue = new InstructionQueue();
        message = createInstructionMessage(MessageType.B);
    }

    @Test
    public void shouldEnqueue() {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void shouldDequeue() {
        queue.enqueue(message);
        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldDequeueIfNoMessages() {
        InstructionMessage message = queue.dequeue();
        assertNull(message);
    }

    @Test
    public void shouldDequeueHigherPriorityFirst() {
        InstructionMessage messageWithLowerPriotity = createInstructionMessage(MessageType.C);
        queue.enqueue(message);
        queue.enqueue(messageWithLowerPriotity);

        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldDequeueLowerPriorityFirst() {
        InstructionMessage messageWithHigherPriotity = createInstructionMessage(MessageType.A);
        queue.enqueue(message);
        queue.enqueue(messageWithHigherPriotity);

        assertEquals(messageWithHigherPriotity, queue.dequeue());
    }

    @Test
    public void shouldDequeueEqualPriority() {
        InstructionMessage secondlyAdded = createInstructionMessage(message.getInstructionType());

        queue.enqueue(message);
        queue.enqueue(secondlyAdded);

        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldDequeueEqualPriorityButDifferentTypes() {
        InstructionMessage firstlyAdded = createInstructionMessage(MessageType.C);
        InstructionMessage secondlyAdded = createInstructionMessage(MessageType.D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.dequeue());
    }

    @Test
    public void shouldPeek() {
        queue.enqueue(message);
        InstructionMessage message = queue.peek();
        assertNotNull(message);
    }

    @Test
    public void shouldPeekIfNoMessages() {
        InstructionMessage message = queue.peek();
        assertNull(message);
    }

    @Test
    public void shouldPeekHigherPriorityFirst() {
        InstructionMessage messageWithLowerPriotity = createInstructionMessage(MessageType.C);
        queue.enqueue(message);
        queue.enqueue(messageWithLowerPriotity);

        assertEquals(message, queue.peek());
    }

    @Test
    public void shouldPeekLowerPriorityFirst() {
        InstructionMessage messageWithHigherPriotity = createInstructionMessage(MessageType.A);
        queue.enqueue(message);
        queue.enqueue(messageWithHigherPriotity);

        assertEquals(messageWithHigherPriotity, queue.peek());
    }

    @Test
    public void shouldPeekEqualPriority() {
        InstructionMessage secondlyAdded = createInstructionMessage(message.getInstructionType());

        queue.enqueue(message);
        queue.enqueue(secondlyAdded);

        assertEquals(message, queue.peek());
    }


    @Test
    public void shouldCount() {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void shouldShowIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void shouldShowIsNotEmpty() {
        queue.enqueue(message);
        assertFalse(queue.isEmpty());
    }

    private InstructionMessage createInstructionMessage(MessageType type)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern((DEFAULT_DATE_TIME_FORMAT));
        LocalDateTime date =  LocalDateTime.parse(DEFAULT_MESSAGE_TIMESTAMP, formatter);

        return new InstructionMessage(DEFAULT_MESSAGE_START, type, DEFAULT_MESSAGE_CODE,
                DEFAULT_MESSAGE_QUANTITY, DEFAULT_MESSAGE_UOM, date);
    }

}