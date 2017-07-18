package training.queue;

import org.junit.Before;
import org.junit.Test;
import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class InstructionQueueTest {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DEFAULT_MESSAGE_CODE = "AA55";
    private static final int DEFAULT_MESSAGE_QUANTITY = 5;
    private static final int DEFAULT_MESSAGE_UOM = 5;
    private static final String DEFAULT_MESSAGE_TIMESTAMP = "2015-03-05T10:04:56.012Z";

    private InstructionQueue queue;

    @Before
    public void setUp() {
        queue = new InstructionQueue();
    }

    @Test
    public void shouldDequeueWhenQueueIsNotEmpty() {
        InstructionMessage message = createInstructionMessage(MessageType.B);
        queue.enqueue(message);
        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldReturnNullWhenDequeueIfNoMessages() {
        InstructionMessage message = queue.dequeue();
        assertNull(message);
    }

    @Test
    public void shouldDequeueByPriority() {
        InstructionMessage messageWithHigherPriority = createInstructionMessage(MessageType.B);
        InstructionMessage messageWithLowerPriority = createInstructionMessage(MessageType.C);
        queue.enqueue(messageWithHigherPriority);
        queue.enqueue(messageWithLowerPriority);

        assertEquals(messageWithHigherPriority, queue.dequeue());
    }

    @Test
    public void shouldDequeueByFifo() {
        InstructionMessage firstlyAdded = createInstructionMessage(MessageType.C);
        InstructionMessage secondlyAdded = createInstructionMessage(MessageType.D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.dequeue());
    }

    @Test
    public void shouldPeekWhenQueueIsNotEmpty() {
        InstructionMessage newMessage = createInstructionMessage(MessageType.B);
        queue.enqueue(newMessage);

        InstructionMessage message = queue.peek();
        assertNotNull(message);
    }

    @Test
    public void shouldReturnNullWhenPeekNoMessages() {
        InstructionMessage message = queue.peek();
        assertNull(message);
    }

    @Test
    public void shouldPeekByPriority() {
        InstructionMessage messageWithHigherPriority = createInstructionMessage(MessageType.B);
        InstructionMessage messageWithLowerPriority = createInstructionMessage(MessageType.C);

        queue.enqueue(messageWithHigherPriority);
        queue.enqueue(messageWithLowerPriority);

        assertEquals(messageWithHigherPriority, queue.peek());
    }

    @Test
    public void shouldPeekByFifo() {
        InstructionMessage firstlyAdded = createInstructionMessage(MessageType.C);
        InstructionMessage secondlyAdded = createInstructionMessage(MessageType.D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.peek());
    }


    @Test
    public void shouldIncrementCount() {
        assertEquals(0, queue.count());

        InstructionMessage newMessage = createInstructionMessage(MessageType.B);
        queue.enqueue(newMessage);

        assertEquals(1, queue.count());
    }

    @Test
    public void shouldShowIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void shouldShowIsNotEmpty() {
        InstructionMessage newMessage = createInstructionMessage(MessageType.B);
        queue.enqueue(newMessage);
        assertFalse(queue.isEmpty());
    }

    private InstructionMessage createInstructionMessage(MessageType type)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        LocalDateTime date =  LocalDateTime.parse(DEFAULT_MESSAGE_TIMESTAMP, formatter);

        return new InstructionMessage(type, DEFAULT_MESSAGE_CODE, DEFAULT_MESSAGE_QUANTITY, DEFAULT_MESSAGE_UOM, date);
    }

}