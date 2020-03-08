package com.jp.stocks;

import com.google.common.collect.Lists;
import com.jp.stocks.entity.Stock;
import com.jp.stocks.entity.StockType;
import com.jp.stocks.entity.Trade;
import com.jp.stocks.entity.TradeLeg;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TestUtils {

    public static final String tickerTea = "TEA";
    public static final String tickerPop = "POP";
    public static final String tickerAle = "ALE";
    public static final String tickerGin = "GIN";
    public static final String tickerJoe = "JOE";
    public static final String tickerXXX = "XXX";

    public static final LocalDateTime timeNow = LocalDateTime.now();
    
    public static Stock getTeaStock() {
        return new Stock(tickerTea, StockType.COMMON, 0, 0, 100);
    }

    public static Stock getAleStock(){
        return new Stock(tickerAle, StockType.COMMON, 23, 0, 60);
    }

    public static Stock getGinStock() {
        return new Stock(tickerGin, StockType.PREFEREED, 8, 2, 100);
    }

    public static Stock getPopStock() {
        return new Stock(tickerPop, StockType.COMMON, 8, 0, 100);
    }

    public static Stock getJoeStock() {
        return new Stock(tickerJoe, StockType.COMMON, 13, 0, 250);
    }

    public static Stock getUnknownStockType() {
        return new Stock(tickerXXX, null, 13, 0, 250);
    }

    public static List<Trade> getMockedTrades() {
        return Lists.newArrayList(
                new Trade(tickerTea, timeNow.minus(5, ChronoUnit.MINUTES), 100, TradeLeg.BUY, BigDecimal.TEN),
                new Trade(tickerPop, timeNow.minus(6, ChronoUnit.MINUTES), 1000, TradeLeg.SELL, new BigDecimal(20)),
                new Trade(tickerAle, timeNow.minus(4, ChronoUnit.MINUTES), 10, TradeLeg.BUY, BigDecimal.ONE),
                new Trade(tickerGin, timeNow.minus(10, ChronoUnit.MINUTES), 250, TradeLeg.SELL, new BigDecimal(1)),
                new Trade(tickerJoe, timeNow.minus(14, ChronoUnit.MINUTES), 1, TradeLeg.BUY, BigDecimal.valueOf(5)));
    }

    public static Trade getTeaTrade(){
        return
                new Trade(tickerTea, timeNow.minus(5, ChronoUnit.MINUTES), 100, TradeLeg.BUY, BigDecimal.TEN);
    }
}
