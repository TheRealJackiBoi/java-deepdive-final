package org.dat3.dto;

import org.dat3.model.Value;
import org.dat3.utils.TradeCalculator;

public class TradeDTO {

    private Value base;
    private Value quote;

    private double change;

    private String symbol;


    public TradeDTO(Value base, Value quote) {
        this.base = base;
        this.quote = quote;
        this.change = TradeCalculator.calculateChange(base, quote);
        this.symbol = TradeCalculator.createSymbol(base, quote);
    }
}
