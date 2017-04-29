package com.jpmorgan.processor;

import com.jpmorgan.model.Instruction;

import java.util.Calendar;
import java.util.Date;

public class DateProcessor {

    public void processInstructionDate(Instruction instruction) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instruction.getSettlementDate());

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            instruction.setSettlementDate(shiftDate(instruction.getSettlementDate(), 2));
        } else if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            instruction.setSettlementDate(shiftDate(instruction.getSettlementDate(), 1));
        }
    }

    private Date shiftDate(Date date, int addDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, addDay); //minus number would decrement the days
        return cal.getTime();
    }
}
