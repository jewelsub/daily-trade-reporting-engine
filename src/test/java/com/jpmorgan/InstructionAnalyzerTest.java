package com.jpmorgan;


import com.jpmorgan.book.InstructionBook;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertTrue;

public class InstructionAnalyzerTest {

    private static final String DATA_FILE_PATH = ClassLoader.getSystemResource("sampledata.txt").getFile();
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Test public void shouldReturnValidStatisticForBuySummary() {
        TradeReportingEngine analyzer = new TradeReportingEngine();
        InstructionBook instructionBook = analyzer.analyzeAndPrintReport(DATA_FILE_PATH);
        assertTrue(instructionBook.getBuyAmountForEntity().keySet().size() == 3);
        assertTrue(instructionBook.getBuyAmountForEntity().get("foo3").equals(100.0));
        assertTrue(instructionBook.getBuyAmountForEntity().get("foo1").equals(40.0));
        assertTrue(instructionBook.getBuyAmountForEntity().get("foo2").equals(20.0));
    }

    @Test public void shouldReturnValidStatisticForSellSummary() {
        TradeReportingEngine analyzer = new TradeReportingEngine();
        InstructionBook instructionBook = analyzer.analyzeAndPrintReport(DATA_FILE_PATH);
        assertTrue(instructionBook.getSellAmountForEntity().keySet().size() == 3);
        assertTrue(instructionBook.getSellAmountForEntity().get("bar3").equals(60.0));
        assertTrue(instructionBook.getSellAmountForEntity().get("bar1").equals(180.0));
        assertTrue(instructionBook.getSellAmountForEntity().get("bar2").equals(220.0));
    }

    @Test public void shouldReturnValidStatisticForIncomingSettledAmount() throws ParseException {
        TradeReportingEngine analyzer = new TradeReportingEngine();
        InstructionBook instructionBook = analyzer.analyzeAndPrintReport(DATA_FILE_PATH);
        assertTrue(instructionBook.getAmountIncomingDaily().keySet().size() == 2);
        assertTrue(instructionBook.getAmountIncomingDaily().get(dateFormat.parse("2017-04-10"))
            .equals(230.0));
        assertTrue(instructionBook.getAmountIncomingDaily().get(dateFormat.parse("2017-04-07"))
            .equals(230.0));
    }

    @Test public void shouldReturnValidStatisticForOutgoingSettledAmount() throws ParseException {
        TradeReportingEngine analyzer = new TradeReportingEngine();
        InstructionBook instructionBook = analyzer.analyzeAndPrintReport(DATA_FILE_PATH);
        assertTrue(instructionBook.getAmountOutgoingDaily().keySet().size() == 1);
        assertTrue(instructionBook.getAmountOutgoingDaily().get(dateFormat.parse("2017-05-01"))
            .equals(160.0));
    }
}
