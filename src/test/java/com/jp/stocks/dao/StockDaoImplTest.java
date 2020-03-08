package com.jp.stocks.dao;

import com.google.common.collect.Lists;
import com.jp.stocks.TestUtils;
import com.jp.stocks.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static com.jp.stocks.TestUtils.getAleStock;
import static com.jp.stocks.TestUtils.getJoeStock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class StockDaoImplTest {

    @InjectMocks
    private StockDaoImpl stockDao;

    @Test
    public void saveInstrument() throws Exception {
        assertTrue(stockDao.saveInstrument(getAleStock()));
    }

    @Test
    public void deleteInstrument() throws Exception {
        stockDao.saveInstrument(getAleStock());
        assertTrue(stockDao.deleteInstrument(getAleStock()));
    }

    @Test
    public void getInstrument() throws Exception {
        stockDao.saveInstrument(getAleStock());
        assertThat(stockDao.getInstrument(TestUtils.tickerAle), is(equalTo(getAleStock())));
    }

    @Test
    public void getInstruments() throws Exception {
        stockDao.saveInstrument(getAleStock());
        stockDao.saveInstrument(getJoeStock());
        List<Stock> stocks = stockDao.getInstruments();
        assertThat(stocks.size(), is(equalTo(2)));
        assertTrue(stocks.equals(Lists.newArrayList(getAleStock(), getJoeStock())));
    }

}