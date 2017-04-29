package com.jpmorgan.processor;

import com.jpmorgan.model.Instruction;
import com.jpmorgan.model.InstructionType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import static com.jpmorgan.model.InstructionBuilder.anInstructionBuilder;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class) public class InstructionDateProcessorTest {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final String settlementDate;
    private final String updateDate;
    private DateProcessor dateProcessor;

    @Before public void beforeTest() throws ParseException {
        dateProcessor = new DateProcessor();
    }

    @Parameterized.Parameters(name = "SettlementDate, UpdatedDate")
    public static Iterable<Object[]> data() {
        return Arrays.asList(
            new Object[][] {{"2017-04-29", "2017-05-01"}, {"2017-04-30", "2017-05-01"},
                {"2017-04-25", "2017-04-25"},});
    }

    public InstructionDateProcessorTest(String settlementDate, String updateDate) {
        this.settlementDate = settlementDate;
        this.updateDate = updateDate;
    }

    @Test public void switchDateIfSunday() throws ParseException {
        Instruction instruction =
            anInstructionBuilder().withEntity("A").withPrice(2.0).withType(InstructionType.B)
                .withAgreedFx(2.0).withCurrency("USD").withUnit(7)
                .withInstructionDate(dateFormat.parse("2017-04-05"))
                .withSettlementDate(dateFormat.parse(settlementDate)).createInstruction();

        dateProcessor.processInstructionDate(instruction);

        assertTrue(instruction.getSettlementDate().equals(dateFormat.parse(updateDate)));
    }

}
