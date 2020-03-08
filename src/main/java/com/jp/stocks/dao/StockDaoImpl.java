package com.jp.stocks.dao;

import com.google.common.base.Preconditions;
import com.jp.stocks.entity.Stock;
import com.jp.stocks.exception.InstrumentNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockDaoImpl implements InstrumentDao<Stock> {

    private final List<Stock> stocks = new ArrayList<>();

    @Override
    public boolean saveInstrument(final Stock stock) {
        Preconditions.checkNotNull(stock);
        return stocks.add(stock);
    }

    @Override
    public boolean deleteInstrument(final Stock stock) {
        Preconditions.checkNotNull(stock);
        return stocks.remove(stock);
    }

    @Override
    public Stock getInstrument(String ticker) throws InstrumentNotFoundException {
       return stocks.stream().filter(stock -> stock.getTicker().equals(ticker)).findFirst()
                .orElseThrow(() ->new InstrumentNotFoundException("Given ticker can not be found " + ticker));
    }

    @Override
    public List<Stock> getInstruments(){
        return stocks==null || stocks.isEmpty() ? Collections.EMPTY_LIST : stocks;
    }
}
