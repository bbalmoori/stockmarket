package com.jp.stocks.dao;

import com.google.common.base.Preconditions;
import com.jp.stocks.entity.Trade;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TradeDaoImpl implements TradeDao {

    private final List<Trade> trades = new ArrayList<>();

    @Override
    public boolean saveTrade(Trade trade) {
        Preconditions.checkNotNull(trade);
        return trades.add(trade);
    }

    @Override
    public boolean deleteTrade(Trade trade) {
        Preconditions.checkNotNull(trade);
        return trades.remove(trade);
    }

    @Override
    public List<Trade> getVwapTrades(String symbol, int elapsedTime) {
        LocalDateTime timeNow = LocalDateTime.now();
        return trades.stream()
                .filter(trade -> trade.getSymbol().equals(symbol))
                .filter(trade -> trade.getTradeTime().until(timeNow, ChronoUnit.MINUTES) < elapsedTime)
                .collect(Collectors.toList());
    }

    @Override
    public List<Trade> getAllTrades() {
        return trades;
    }
}
