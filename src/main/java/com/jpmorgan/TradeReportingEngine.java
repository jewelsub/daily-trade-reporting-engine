package com.jpmorgan;


import com.jpmorgan.book.InstructionBook;
import com.jpmorgan.processor.InstructionProcessor;
import com.jpmorgan.reporter.InstructionReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TradeReportingEngine {

    private static final Logger log = LoggerFactory.getLogger(InstructionProcessor.class);


    public InstructionBook analyzeAndPrintReport(String filePath) {
        InstructionBook instructionBook = new InstructionBook();
        InstructionProcessor instructionProcessor =
            new InstructionProcessor(instructionBook, filePath);
        instructionProcessor.process();
        InstructionReporter instructionReporter = new InstructionReporter(instructionBook);
        instructionReporter.printReport();

        return instructionBook;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            log.error("Please provide file path as application parameter.");
        } else {
            TradeReportingEngine analyzer = new TradeReportingEngine();
            analyzer.analyzeAndPrintReport(args[0]);
        }
    }
}
