package training.impl;

import org.junit.Before;
import org.junit.Test;
import training.InstructionQueue;
import training.impl.InstructionMessage;
import training.impl.InstructionQueueImpl;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionQueueImplTest {
    private InstructionQueue queue;
    private InstructionMessage message;

    @Before
    public void setUp() throws Exception {
        queue = new InstructionQueueImpl();
        message = new InstructionMessage("msg","A","MZ89",  5678,
                50, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse("2015-03-05T10:04:56.012Z"));
    }

    @Test
    public void enqueue() throws Exception {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void dequeue() throws Exception {
        queue.enqueue(message);
        InstructionMessage message = queue.dequeue();
        assertEquals(0, queue.count());
    }

    @Test
    public void peek() throws Exception {
        queue.enqueue(message);
        InstructionMessage message = queue.peek();
        assertNotNull(message);
    }

    @Test
    public void count() throws Exception {
        queue.enqueue(message);
        assertEquals(1, queue.count());
    }

    @Test
    public void isEmpty() throws Exception {
        assertTrue(queue.isEmpty());
    }

}