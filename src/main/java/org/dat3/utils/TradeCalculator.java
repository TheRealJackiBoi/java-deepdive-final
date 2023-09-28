package org.dat3.utils;

import org.dat3.model.Value;

public class TradeCalculator {

    public static double calculateChange(Value base, Value quote) {

        double baseCurrency = base.getValue() / 100;
        double quoteCurrency = quote.getValue() / 100;

        return baseCurrency / quoteCurrency;
    }

    public static String createSymbol(Value base, Value quote) {

        return base.getCurrency().getCode() + quote.getCurrency().getCode() + "=X";
    }
}
