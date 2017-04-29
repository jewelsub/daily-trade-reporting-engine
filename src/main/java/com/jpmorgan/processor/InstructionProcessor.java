package com.jpmorgan.processor;

import com.jpmorgan.book.InstructionBook;
import com.jpmorgan.exception.CustomException;
import com.jpmorgan.model.Instruction;
import com.jpmorgan.parser.InstructionParser;
import com.jpmorgan.receiver.InstructionReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InstructionProcessor {

    private static final Logger log = LoggerFactory.getLogger(InstructionProcessor.class);

    private final InstructionReceiver instructionReceiver;
    private final InstructionBook instructionBook;
    private final DateProcessor dateProcessor;

    public InstructionProcessor(InstructionBook instructionBook, String filePath) {
        this.instructionBook = instructionBook;
        instructionReceiver = new InstructionReceiver(new InstructionParser(filePath));
        dateProcessor = new DateProcessor();
    }

    public void process() {
        List<Instruction> instructions = null;
        try {
            instructions = instructionReceiver.receive();
        } catch (CustomException e) {
            log.debug("Instruction processing error: {}", e.getMessage());
        }
        if (instructions == null || instructions.isEmpty()) {
            log.debug("No instruction found, nothing to process!");
        } else {
            instructions.forEach(this::addInstructionToBook);
        }
    }

    private void addInstructionToBook(Instruction instruction) {
        dateProcessor.processInstructionDate(instruction);
        instructionBook.addInstruction(instruction);
    }
}
