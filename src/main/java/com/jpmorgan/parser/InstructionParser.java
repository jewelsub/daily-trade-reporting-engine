package com.jpmorgan.parser;


import com.jpmorgan.exception.CustomException;
import com.jpmorgan.exception.InstructionFileReaderException;
import com.jpmorgan.exception.InstructionMessageFormatException;
import com.jpmorgan.model.Instruction;
import com.jpmorgan.model.InstructionType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jpmorgan.model.InstructionBuilder.anInstructionBuilder;

public class InstructionParser {
    private final Logger logger = LoggerFactory.getLogger(InstructionParser.class);

    private static final String FIELD_ENTITY = "Entity";
    private static final String FIELD_BUY_SALE = "Buy/Sell";
    private static final String FIELD_AGREED_FX = "AgreedFx";
    private static final String FIELD_CURRENCY = "Currency";
    private static final String FIELD_INSTRUCTION_DATE = "InstructionDate";
    private static final String FIELD_SETTLEMENT_DATE = "SettlementDate";
    private static final String FIELD_UNIT = "Unit";
    private static final String FIELD_PRICE = "Price";
    private static final String INSTRUCTION_FILE_READING_EXCEPTION =
        "Instruction file reading error";
    private static final String INVALID_INSTRUCTION_MESSAGE_FORMAT = "Invalid instruction message";

    private final String filePath;

    public InstructionParser(String filePath) {
        this.filePath = filePath;
    }

    public List<Instruction> parseCSV() throws CustomException {
        List<Instruction> instructions = new ArrayList<>();
        Reader fileReader;

        try {
            fileReader = new FileReader(ClassLoader.getSystemResource(filePath).getFile());
        } catch (FileNotFoundException e) {
            throw new InstructionFileReaderException(INSTRUCTION_FILE_READING_EXCEPTION, filePath);
        }

        Iterable<CSVRecord> records;
        try {
            records = CSVFormat.RFC4180
                .withHeader(FIELD_ENTITY, FIELD_BUY_SALE, FIELD_AGREED_FX, FIELD_CURRENCY,
                    FIELD_INSTRUCTION_DATE, FIELD_SETTLEMENT_DATE, FIELD_UNIT, FIELD_PRICE)
                .withTrim().withIgnoreEmptyLines().parse(fileReader);

        } catch (IOException e) {
            throw new InstructionMessageFormatException(INVALID_INSTRUCTION_MESSAGE_FORMAT);
        }

        for (CSVRecord record : records) {
            if (record.size() > 0) {
                addToInstructionList(record, instructions);
            }
        }

        return instructions;
    }

    private void addToInstructionList(CSVRecord record, List<Instruction> instructions) {
        Instruction instruction = anInstructionBuilder().withEntity(record.get(FIELD_ENTITY))
            .withType(InstructionType.valueOf(record.get(FIELD_BUY_SALE)))
            .withAgreedFx(Double.valueOf(record.get(FIELD_AGREED_FX)))
            .withCurrency(record.get(FIELD_CURRENCY))
            .withInstructionDate(new Date(record.get(FIELD_INSTRUCTION_DATE)))
            .withSettlementDate(new Date(record.get(FIELD_SETTLEMENT_DATE)))
            .withUnit(new Integer(record.get(FIELD_UNIT)))
            .withPrice(new Double(record.get(FIELD_PRICE))).createInstruction();

        logger.debug("Found instructions: " + instruction);
        instructions.add(instruction);
    }


}
