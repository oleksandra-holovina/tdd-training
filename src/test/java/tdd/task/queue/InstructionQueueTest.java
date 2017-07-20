package tdd.task.queue;

import tdd.task.entities.InstructionMessage;
import tdd.task.entities.MessageType;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class InstructionQueueTest {
    private static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static final String DEFAULT_MESSAGE_CODE = "AA55";
    private static final int DEFAULT_MESSAGE_QUANTITY = 5;
    private static final int DEFAULT_MESSAGE_UOM = 5;
    private static final String DEFAULT_MESSAGE_TIMESTAMP = "2015-03-05T10:04:56.012Z";

    private static final MessageType MESSAGE_TYPE_A = MessageType.A;
    private static final MessageType MESSAGE_TYPE_B = MessageType.B;
    private static final MessageType MESSAGE_TYPE_C = MessageType.C;
    private static final MessageType MESSAGE_TYPE_D = MessageType.D;

    private InstructionQueue queue;

    @Before
    public void setUp() {
        queue = new InstructionQueue();
    }

    @Test
    public void shouldDequeueNotEmptyQueue() {
        InstructionMessage message = createInstructionMessage(MessageType.B);
        queue.enqueue(message);
        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldReturnNullWhenDequeueEmptyQueue() {
        InstructionMessage message = queue.dequeue();
        assertNull(message);
    }

    @Test
    public void shouldDequeueByPriority() {
        InstructionMessage messageWithHigherPriority = createInstructionMessage(MESSAGE_TYPE_B);
        InstructionMessage messageWithLowerPriority = createInstructionMessage(MESSAGE_TYPE_C);
        queue.enqueue(messageWithHigherPriority);
        queue.enqueue(messageWithLowerPriority);

        assertEquals(messageWithHigherPriority, queue.dequeue());
    }

    @Test
    public void shouldDequeueByFifo() {
        InstructionMessage firstlyAdded = createInstructionMessage(MESSAGE_TYPE_C);
        InstructionMessage secondlyAdded = createInstructionMessage(MESSAGE_TYPE_D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.dequeue());
    }

    @Test
    public void shouldDequeueFirstByPriorityThenByFifo(){
        InstructionMessage lowPriorityFirstlyAddedMessage = createInstructionMessage(MESSAGE_TYPE_C);
        InstructionMessage lowPrioritySecondlyAddedMessage = createInstructionMessage(MESSAGE_TYPE_D);
        InstructionMessage mediumPriorityMessage = createInstructionMessage(MESSAGE_TYPE_B);
        InstructionMessage messageWithHighPriority = createInstructionMessage(MESSAGE_TYPE_A);

        queue.enqueue(lowPriorityFirstlyAddedMessage);
        queue.enqueue(lowPrioritySecondlyAddedMessage);
        queue.enqueue(mediumPriorityMessage);
        queue.enqueue(messageWithHighPriority);

        assertEquals(messageWithHighPriority, queue.dequeue());
        assertEquals(mediumPriorityMessage, queue.dequeue());
        assertEquals(lowPriorityFirstlyAddedMessage, queue.dequeue());
        assertEquals(lowPrioritySecondlyAddedMessage, queue.dequeue());
    }

    @Test
    public void shouldPeekNotEmptyQueue() {
        InstructionMessage newMessage = createInstructionMessage(MESSAGE_TYPE_B);
        queue.enqueue(newMessage);

        InstructionMessage message = queue.peek();
        assertNotNull(message);
    }

    @Test
    public void shouldReturnNullWhenPeekEmptyQueue() {
        InstructionMessage message = queue.peek();
        assertNull(message);
    }

    @Test
    public void shouldPeekByPriority() {
        InstructionMessage messageWithHigherPriority = createInstructionMessage(MESSAGE_TYPE_B);
        InstructionMessage messageWithLowerPriority = createInstructionMessage(MESSAGE_TYPE_C);

        queue.enqueue(messageWithHigherPriority);
        queue.enqueue(messageWithLowerPriority);

        assertEquals(messageWithHigherPriority, queue.peek());
    }

    @Test
    public void shouldPeekByFifo() {
        InstructionMessage firstlyAdded = createInstructionMessage(MESSAGE_TYPE_C);
        InstructionMessage secondlyAdded = createInstructionMessage(MESSAGE_TYPE_D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.peek());
    }


    @Test
    public void shouldIncrementCount() {
        assertEquals(0, queue.count());

        InstructionMessage newMessage = createInstructionMessage(MESSAGE_TYPE_B);
        queue.enqueue(newMessage);

        assertEquals(1, queue.count());
    }

    @Test
    public void shouldShowIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void shouldShowIsNotEmpty() {
        InstructionMessage newMessage = createInstructionMessage(MESSAGE_TYPE_B);
        queue.enqueue(newMessage);
        assertFalse(queue.isEmpty());
    }

    private InstructionMessage createInstructionMessage(MessageType type)  {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        LocalDateTime date =  LocalDateTime.parse(DEFAULT_MESSAGE_TIMESTAMP, formatter);

        return new InstructionMessage(type, DEFAULT_MESSAGE_CODE, DEFAULT_MESSAGE_QUANTITY, DEFAULT_MESSAGE_UOM, date);
    }

}