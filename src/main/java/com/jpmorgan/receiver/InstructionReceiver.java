package com.jpmorgan.receiver;


import com.jpmorgan.exception.CustomException;
import com.jpmorgan.model.Instruction;
import com.jpmorgan.parser.InstructionParser;

import java.util.List;

public class InstructionReceiver {

    private final InstructionParser instructionParser;

    public InstructionReceiver(InstructionParser instructionParser) {
        this.instructionParser = instructionParser;
    }

    public List<Instruction> receive() throws CustomException {
        return instructionParser.parseCSV();
    }
}
