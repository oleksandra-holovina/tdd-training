package training.impl;

import org.junit.Before;
import org.junit.Test;
import training.InstructionMessageValidator;
import training.exceptions.ValidationException;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageValidatorImplTest {
    private InstructionMessageValidator validator;

    @Before
    public void setUp() throws Exception {
        validator = new InstructionMessageValidatorImpl();
    }

    @Test(expected = ValidationException.class)
    public void validateWrongNumberOfParameters() throws Exception{
        String [] messageParts = {"aa","aa"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongType() throws Exception{
        String [] messageParts = {"msg","5","MZ89",  "5678",
                "50", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongCode() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "5678",
                "50", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongQuantityNan() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "a",
                "50", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongQuantityLessThanZero() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "-9",
                "50", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongUOMNan() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "9",
                "aa", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongUOMLessThanZero() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "9",
                "-9", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongUOMGreaterThan256() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "9",
                "11111", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

    @Test(expected = ValidationException.class)
    public void validateWrongTimestamp() throws Exception{
        String [] messageParts = {"msg","A","Mm89",  "9",
                "11111", "2015-03-05"};
        validator.getInstructionMessage(messageParts);
    }

    @Test
    public void validateCorrectMessage() throws Exception{
        String [] messageParts = {"msg","A","MZ89",  "5678",
                "50", "2015-03-05T10:04:56.012Z"};
        validator.getInstructionMessage(messageParts);
    }

}