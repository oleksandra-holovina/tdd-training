package training.impl;

import org.junit.Before;
import org.junit.Test;
import training.InstructionMessageParser;

import static org.junit.Assert.assertEquals;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageParserTest {
    private InstructionMessageParser parser;

    @Before
    public void setUp() throws Exception {
        parser = new InstructionMessageParser();
    }

    @Test
    public void splitTextIntoMessages() throws Exception {
        String text = "Here comes some text with \n new lines";
        assertEquals(2,parser.splitTextIntoMessages(text).length);
    }

    @Test
    public void splitMessageIntoParts() throws Exception {
        String msg = "Here comes some text";
        assertEquals(4,parser.splitMessageBySpaces(msg).length);
    }

}