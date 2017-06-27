package training.impl;

import training.InstructionMessageParser;

/**
 * Created by Oleksandra_Holovina on 6/27/2017.
 */
public class InstructionMessageParserImpl implements InstructionMessageParser {
    @Override
    public String[] splitTextIntoMessages(String text) {
        return text.split("\\r?\\n");
    }

    @Override
    public String[] splitMessageBySpaces(String message) {
        return message.split("\\s");
    }
}
