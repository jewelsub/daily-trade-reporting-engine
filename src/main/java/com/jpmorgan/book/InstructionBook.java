package com.jpmorgan.book;

import com.jpmorgan.model.Instruction;
import com.jpmorgan.model.InstructionType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class InstructionBook {
    private final Map<String, Double> buyAmountForEntity = new HashMap<>();
    private final Map<String, Double> sellAmountForEntity = new HashMap<>();
    private final Map<Date, Double> amountIncomingDaily = new HashMap<>();
    private final Map<Date, Double> amountOutgoingDaily = new HashMap<>();

    public void addInstruction(Instruction instruction) {
        updateOrderAmountForEntity(instruction);
        updateAmountDaily(instruction);
    }

    private void updateOrderAmountForEntity(Instruction instruction) {
        if (instruction.getType().equals(InstructionType.B)) {
            addAmountForEntity(buyAmountForEntity, instruction);
        } else {
            addAmountForEntity(sellAmountForEntity, instruction);
        }
    }

    private void addAmountForEntity(Map<String, Double> orders, Instruction instruction) {
        Double amount;
        if (orders.containsKey(instruction.getEntity())) {
            amount = orders.get(instruction.getEntity()) + (instruction.getUnit() * instruction
                .getPrice());
        } else {
            amount = instruction.getUnit() * instruction.getPrice();
        }

        orders.put(instruction.getEntity(), amount);
    }

    private void updateAmountDaily(Instruction instruction) {
        if (instruction.getType().equals(InstructionType.B)) {
            addToAmountDaily(amountOutgoingDaily, instruction);
        } else {
            addToAmountDaily(amountIncomingDaily, instruction);
        }
    }

    private void addToAmountDaily(Map<Date, Double> settlementSummary, Instruction instruction) {
        Double updatedAmount;
        if (settlementSummary.containsKey(instruction.getSettlementDate())) {
            updatedAmount = settlementSummary.get(instruction.getSettlementDate()) + getTradeAmount(
                instruction);
        } else {
            updatedAmount = getTradeAmount(instruction);
        }

        settlementSummary.put(instruction.getSettlementDate(), updatedAmount);
    }

    private Double getTradeAmount(Instruction instruction) {
        return instruction.getPrice() * instruction.getUnit() * instruction.getAgreedFx();
    }

    public Map<String, Double> getBuyAmountForEntity() {
        return buyAmountForEntity;
    }

    public Map<String, Double> getSellAmountForEntity() {
        return sellAmountForEntity;
    }

    public Map<Date, Double> getAmountIncomingDaily() {
        return amountIncomingDaily;
    }

    public Map<Date, Double> getAmountOutgoingDaily() {
        return amountOutgoingDaily;
    }
}
