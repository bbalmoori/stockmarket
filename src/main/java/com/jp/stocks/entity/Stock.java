package com.jp.stocks.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode(callSuper = false)
public class Stock extends Instrument {
    @NonNull //ticker cannot be null
    private final String ticker;
    private final StockType stockType;
    private final double lastDividend;
    private final double fixedDividend;
    private final int parValue;
}
