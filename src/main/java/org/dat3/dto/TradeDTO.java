package org.dat3.dto;

import org.dat3.model.Currency;
import org.dat3.model.Value;

public record TradeDTO(Currency base, Currency quote, Value value) { }
