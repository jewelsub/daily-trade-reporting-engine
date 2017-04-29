package com.jpmorgan.model;


import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Instruction {

    private final String entity;
    private final InstructionType type;
    private final Double agreedFx;
    private final String currency;
    private final Date instructionDate;
    private final Integer unit;
    private final Double price;
    private Date settlementDate;

    public Instruction(String entity, InstructionType type, Double agreedFx, String currency,
        Date instructionDate, Date settlementDate, Integer unit, Double price) {
        checkNotNull(entity, "entity cannot be null");
        checkNotNull(currency, "currency cannot be null");
        checkNotNull(instructionDate, "instructionDate cannot be null");
        checkNotNull(settlementDate, "settlementDate cannot be null");
        checkNotNull(agreedFx, "agreedFx cannot be null");
        checkNotNull(unit, "unit cannot be null");
        checkNotNull(price, "price cannot be null");
        checkState(agreedFx > 0, "agreedFx must be greater than 0");
        checkState(unit > 0, "unit must be greater than 0");
        checkState(price > 0, "price must be greater than 0");

        this.entity = entity;
        this.type = type;
        this.agreedFx = agreedFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.unit = unit;
        this.price = price;
    }


    public String getEntity() {
        return entity;
    }

    public InstructionType getType() {
        return type;
    }

    public Double getAgreedFx() {
        return agreedFx;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Integer getUnit() {
        return unit;
    }

    public Double getPrice() {
        return price;
    }

    @Override public String toString() {
        return "Instruction{" +
            "entity='" + entity + '\'' +
            ", type=" + type +
            ", agreedFx=" + agreedFx +
            ", currency='" + currency + '\'' +
            ", instructionDate=" + instructionDate +
            ", settlementDate=" + settlementDate +
            ", unit=" + unit +
            ", price=" + price +
            '}';
    }
}
