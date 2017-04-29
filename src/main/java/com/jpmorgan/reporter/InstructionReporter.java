package com.jpmorgan.reporter;


import com.jpmorgan.book.InstructionBook;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class InstructionReporter {

    private final InstructionBook instructionBook;
    private static final String AMOUNT_SETTLED_OUT_GOING_LABEL = "\nAmount settled outgoing:";
    private static final String AMOUNT_SETTLED_IN_COMING_LABEL = "\nAmount settled incoming:";
    private static final String RANK_INCOMING_LABEL = "\nRanking of entities based on incoming amount:";
    private static final String RANK_OUT_GOING_LABEL = "\nRanking of entities based on out going amount:";


    public InstructionReporter(InstructionBook instructionBook) {
        this.instructionBook = instructionBook;
    }

    public void printReport(){
        System.out.println(AMOUNT_SETTLED_OUT_GOING_LABEL);
        instructionBook.getAmountOutgoingDaily().entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .forEach(this::printAmountEveryday);

        System.out.println(AMOUNT_SETTLED_IN_COMING_LABEL);
        instructionBook.getAmountIncomingDaily().entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .forEach(this::printAmountEveryday);

        int[] rankOutgoing = {1};
        int[] rankIncoming = {1};

        System.out.println(RANK_OUT_GOING_LABEL);
        instructionBook.getBuyAmountForEntity().entrySet().stream()
            .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .forEach(e -> printWithRank(rankOutgoing[0]++, e));

        System.out.println(RANK_INCOMING_LABEL);
        instructionBook.getSellAmountForEntity().entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
            .forEach(e -> printWithRank(rankIncoming[0]++, e));
    }

    private void printWithRank(int rank, Map.Entry<String, Double> productPrice ){
        System.out.println("rank " + rank + ": " + productPrice.getKey() + ", trade amount:" +productPrice.getValue());
    }

    private void printAmountEveryday(Map.Entry<Date, Double> productPrice ){
        System.out.println("Date:"+productPrice.getKey() + ", Amount:" +productPrice.getValue());
    }

}
