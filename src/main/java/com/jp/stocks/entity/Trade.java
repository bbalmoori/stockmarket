package com.jp.stocks.entity;

import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Trade {
    @NonNull
    private final String symbol;
    private final LocalDateTime tradeTime;
    private final int quantity;
    private final TradeLeg tradeLeg;
    private final BigDecimal price;
}
