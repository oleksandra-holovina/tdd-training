package training;

import org.junit.Before;
import org.junit.Test;
import training.entities.InstructionMessage;
import training.entities.MessageType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionQueueTest {
    private InstructionQueue queue;
    private InstructionMessage message;

    @Before
    public void setUp() throws Exception {
        queue = new InstructionQueue();
        message = createInstructionMessage(MessageType.B);
    }

    @Test
    public void enqueue() throws Exception {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void dequeue() throws Exception {
        queue.enqueue(message);
        assertEquals(message, queue.dequeue());
    }

    @Test
    public void dequeueIfNoMessages() throws Exception {
        InstructionMessage message = queue.dequeue();
        assertNull(message);
    }

    @Test
    public void dequeueHigherPriorityFirst() throws Exception {
        InstructionMessage messageWithLowerPriotity = createInstructionMessage(MessageType.C);
        queue.enqueue(message);
        queue.enqueue(messageWithLowerPriotity);

        assertEquals(message, queue.dequeue());
    }

    @Test
    public void dequeueLowerPriorityFirst() throws Exception {
        InstructionMessage messageWithHigherPriotity = createInstructionMessage(MessageType.A);
        queue.enqueue(message);
        queue.enqueue(messageWithHigherPriotity);

        assertEquals(messageWithHigherPriotity, queue.dequeue());
    }

    @Test
    public void dequeueEqualPriority() throws Exception {
        InstructionMessage secondlyAdded = createInstructionMessage(message.getInstructionType());

        queue.enqueue(message);
        queue.enqueue(secondlyAdded);

        assertEquals(message, queue.dequeue());
    }

    @Test
    public void peek() throws Exception {
        queue.enqueue(message);
        InstructionMessage message = queue.peek();
        assertNotNull(message);
    }

    @Test
    public void peekIfNoMessages() throws Exception {
        InstructionMessage message = queue.peek();
        assertNull(message);
    }

    @Test
    public void peekHigherPriorityFirst() throws Exception {
        InstructionMessage messageWithLowerPriotity = createInstructionMessage(MessageType.C);
        queue.enqueue(message);
        queue.enqueue(messageWithLowerPriotity);

        assertEquals(message, queue.peek());
    }

    @Test
    public void peekLowerPriorityFirst() throws Exception {
        InstructionMessage messageWithHigherPriotity = createInstructionMessage(MessageType.A);
        queue.enqueue(message);
        queue.enqueue(messageWithHigherPriotity);

        assertEquals(messageWithHigherPriotity, queue.peek());
    }

    @Test
    public void peekEqualPriority() throws Exception {
        InstructionMessage secondlyAdded = createInstructionMessage(message.getInstructionType());

        queue.enqueue(message);
        queue.enqueue(secondlyAdded);

        assertEquals(message, queue.peek());
    }


    @Test
    public void count() throws Exception {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void showIsEmpty() throws Exception {
        assertTrue(queue.isEmpty());
    }

    @Test
    public void showIsNotEmpty() throws Exception {
        queue.enqueue(message);
        assertFalse(queue.isEmpty());
    }

    private InstructionMessage createInstructionMessage(MessageType type) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z");
        return new InstructionMessage("msg", type, "MZ89", 5678,
                50, date);
    }

}