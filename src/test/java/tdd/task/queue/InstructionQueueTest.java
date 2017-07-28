package tdd.task.queue;

import tdd.task.entities.InstructionMessage;
import tdd.task.entities.MessageType;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.*;

public class InstructionQueueTest {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final String DEFAULT_CODE = "AA55";
    private static final int DEFAULT_QUANTITY = 5;
    private static final int DEFAULT_UOM = 5;
    private static final LocalDateTime DEFAULT_TIMESTAMP = LocalDateTime.parse("2015-03-05T10:04:56.012Z", formatter);

    private static final MessageType TYPE_A = MessageType.A;
    private static final MessageType TYPE_B = MessageType.B;
    private static final MessageType TYPE_C = MessageType.C;
    private static final MessageType TYPE_D = MessageType.D;

    private InstructionQueue queue = new InstructionQueue();

    @Test
    public void shouldReturnTrueWhenQueueIsEmpty() {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void shouldReturnFalseWhenQueueIsNotEmpty() {
        InstructionMessage newMessage = createInstructionMessage(TYPE_B);
        queue.enqueue(newMessage);
        assertFalse(queue.isEmpty());
    }

    @Test
    public void shouldReturnCountZeroWhenNothingWasEnqueued() {
        assertEquals(0, queue.count());
    }

    @Test
    public void shouldIncrementCountWhenMessageIsEnqueued() {
        InstructionMessage newMessage = createInstructionMessage(TYPE_B);
        queue.enqueue(newMessage);

        assertEquals(1, queue.count());
    }

    @Test
    public void shouldReturnMessageWhenDequeueFromNonEmptyQueue() {
        InstructionMessage message = createInstructionMessage(MessageType.B);
        queue.enqueue(message);
        assertEquals(message, queue.dequeue());
    }

    @Test
    public void shouldReturnNullWhenDequeueFromEmptyQueue() {
        InstructionMessage message = queue.dequeue();
        assertNull(message);
    }

    @Test
    public void shouldDequeueByPriorityWhenTypesAreEqual() {
        InstructionMessage firstlyAdded = createInstructionMessage(TYPE_B);
        InstructionMessage secondlyAdded = createInstructionMessage(TYPE_B);

        queue.enqueue(secondlyAdded);
        queue.enqueue(firstlyAdded);

        assertEquals(secondlyAdded, queue.dequeue());
    }

    @Test
    public void shouldDequeueByPriorityWhenTypesAreDifferent() {
        InstructionMessage messageWithLowerPriority = createInstructionMessage(TYPE_C);
        InstructionMessage messageWithHigherPriority = createInstructionMessage(TYPE_B);

        queue.enqueue(messageWithLowerPriority);
        queue.enqueue(messageWithHigherPriority);

        assertEquals(messageWithHigherPriority, queue.dequeue());
    }

    @Test
    public void shouldDequeueByFifoWhenPrioritiesAreEqual() {
        InstructionMessage firstlyAdded = createInstructionMessage(TYPE_C);
        InstructionMessage secondlyAdded = createInstructionMessage(TYPE_D);

        queue.enqueue(firstlyAdded);
        queue.enqueue(secondlyAdded);

        assertEquals(firstlyAdded, queue.dequeue());
    }

    @Test
    public void shouldDequeueFirstByPriorityThenByFifoWhenDifferentTypes(){
        InstructionMessage lowPriorityFirstlyAddedMessage = createInstructionMessage(TYPE_C);
        InstructionMessage lowPrioritySecondlyAddedMessage = createInstructionMessage(TYPE_D);
        InstructionMessage mediumPriorityMessage = createInstructionMessage(TYPE_B);
        InstructionMessage highPriorityMessage = createInstructionMessage(TYPE_A);

        queue.enqueue(lowPriorityFirstlyAddedMessage);
        queue.enqueue(lowPrioritySecondlyAddedMessage);
        queue.enqueue(mediumPriorityMessage);
        queue.enqueue(highPriorityMessage);

        assertEquals(highPriorityMessage, queue.dequeue());
        assertEquals(mediumPriorityMessage, queue.dequeue());
        assertEquals(lowPriorityFirstlyAddedMessage, queue.dequeue());
        assertEquals(lowPrioritySecondlyAddedMessage, queue.dequeue());
    }

    @Test
    public void shouldReturnMessageWhenPeekNonEmptyQueue() {
        InstructionMessage newMessage = createInstructionMessage(TYPE_B);
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
    public void shouldNotRemoveMessageWhenPeek() {
        InstructionMessage newMessage = createInstructionMessage(TYPE_B);
        queue.enqueue(newMessage);

        assertEquals(1, queue.count());
        queue.peek();
        assertEquals(1, queue.count());
    }

    private InstructionMessage createInstructionMessage(MessageType type)  {
        return new InstructionMessage(type, DEFAULT_CODE, DEFAULT_QUANTITY, DEFAULT_UOM, DEFAULT_TIMESTAMP);
    }

}