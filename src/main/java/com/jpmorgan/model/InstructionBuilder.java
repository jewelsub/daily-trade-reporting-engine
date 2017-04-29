package com.jpmorgan.model;

import java.util.Date;

public class InstructionBuilder {
    private String entity;
    private InstructionType type;
    private Double agreedFx;
    private String currency;
    private Date instructionDate;
    private Date settlementDate;
    private Integer unit;
    private Double price;

    public static InstructionBuilder anInstructionBuilder() {
        return new InstructionBuilder();
    }

    public InstructionBuilder withEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public InstructionBuilder withType(InstructionType type) {
        this.type = type;
        return this;
    }

    public InstructionBuilder withAgreedFx(Double agreedFx) {
        this.agreedFx = agreedFx;
        return this;
    }

    public InstructionBuilder withCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public InstructionBuilder withInstructionDate(Date instructionDate) {
        this.instructionDate = instructionDate;
        return this;
    }

    public InstructionBuilder withSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
        return this;
    }

    public InstructionBuilder withUnit(Integer unit) {
        this.unit = unit;
        return this;
    }

    public InstructionBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public Instruction createInstruction() {
        return new Instruction(entity, type, agreedFx, currency, instructionDate, settlementDate,
            unit, price);
    }
}
