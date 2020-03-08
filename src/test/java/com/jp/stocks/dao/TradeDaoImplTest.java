package com.jp.stocks.dao;

import com.jp.stocks.TestUtils;
import com.jp.stocks.entity.Trade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.jp.stocks.TestUtils.getMockedTrades;
import static com.jp.stocks.TestUtils.getTeaTrade;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TradeDaoImplTest {

    @InjectMocks
    private TradeDaoImpl tradeDao;

    @Test
    public void testSaveTrade() {
        assertTrue(tradeDao.saveTrade(getTeaTrade()));
    }

    @Test
    public void testDeleteTrade() {
        tradeDao.saveTrade(getTeaTrade());
        assertTrue(tradeDao.deleteTrade(getTeaTrade()));
    }

    @Test
    public void testGetVwapTrades() {
        tradeDao.saveTrade(getTeaTrade());
        List<Trade> trades = tradeDao.getVwapTrades(TestUtils.tickerTea, 10);
        assertThat(trades.size(), is(equalTo(1)));
        assertThat(trades.get(0), is(equalTo(getTeaTrade())));

    }

    @Test
    public void testGetAllTrades() {
        List<Trade> trades = getMockedTrades();
        trades.forEach(trade -> tradeDao.saveTrade(trade));

        List<Trade> retTrades = tradeDao.getAllTrades();
        assertThat(retTrades.size(), is(equalTo(trades.size())));
        assertTrue(retTrades.equals(trades));
    }

}