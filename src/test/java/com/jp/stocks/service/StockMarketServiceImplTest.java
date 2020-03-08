package com.jp.stocks.service;

import com.google.common.collect.Lists;
import com.jp.stocks.dao.InstrumentDao;
import com.jp.stocks.dao.TradeDao;
import com.jp.stocks.entity.Stock;
import com.jp.stocks.entity.Trade;
import com.jp.stocks.entity.TradeLeg;
import com.jp.stocks.exception.DaoException;
import com.jp.stocks.exception.InstrumentNotFoundException;
import com.jp.stocks.exception.StockServiceException;
import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import static com.jp.stocks.TestUtils.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.BigDecimalCloseTo.closeTo;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockMarketServiceImplTest {

    @Mock
    InstrumentDao<Stock> instrumentDao;

    @Mock
    TradeDao tradeDao;

    @InjectMocks
    private StockMarketServiceImpl underTest;

    private final BigDecimal error = new BigDecimal("0.0000001");


    @Before
    public void setup() throws InstrumentNotFoundException, DaoException {
        when(instrumentDao.getInstrument(tickerTea)).thenReturn(getTeaStock());
        when(instrumentDao.getInstrument(tickerGin)).thenReturn(getGinStock());
        when(instrumentDao.getInstrument(tickerPop)).thenReturn(getPopStock());
        when(instrumentDao.getInstrument(tickerJoe)).thenReturn(getJoeStock());
        when(instrumentDao.getInstrument(tickerXXX)).thenReturn(getUnknownStockType());
        when(tradeDao.getAllTrades()).thenReturn(getMockedTrades());
        when(instrumentDao.getInstruments()).thenReturn(Lists.newArrayList(getTeaStock(), getAleStock(), getGinStock(), getPopStock(), getJoeStock()));
    }

    @Test
    public void testCalculateDividendYieldCommonStockTea() throws Exception {
        BigDecimal divYield = underTest.calculateDividendYield(tickerTea, 200);
        assertThat(divYield, is(notNullValue()));
        assertThat(divYield, closeTo(BigDecimal.ZERO, error));
    }

    @Test
    public void testCalculateDividendYieldPreferredStockGin() throws Exception {
        BigDecimal divYield = underTest.calculateDividendYield(tickerGin, 100);
        assertThat(divYield, is(notNullValue()));
        assertThat(divYield, closeTo(BigDecimal.valueOf(2), error));
    }


    @Test
    public void testCalculateDividendYieldCommonStockPop() throws Exception {
        BigDecimal divYield = underTest.calculateDividendYield(tickerPop, 200);
        assertThat(divYield, is(notNullValue()));
        assertThat(divYield, closeTo(BigDecimal.valueOf(0.04), error));
    }

    @Test
    public void testCalculateDividendYieldCommonStockJoe() throws Exception {
        BigDecimal divYield = underTest.calculateDividendYield(tickerJoe, 10);
        assertThat(divYield, is(notNullValue()));
        assertThat(divYield, closeTo(BigDecimal.valueOf(1.3), error));
    }

    @Test
    public void testCalculateDividendYieldZeroPrice() {
        Assert.assertThrows(IllegalArgumentException.class, () -> underTest.calculateDividendYield(tickerJoe, 0));
    }

    @Test
    public void testCalculateDividendYieldNegativePrice() {
        Assert.assertThrows(IllegalArgumentException.class, () -> underTest.calculateDividendYield(tickerJoe, -1));
    }

    @Test
    public void testCalculateDividendYieldUnknownStockType() {
        Assert.assertThrows(NotImplementedException.class, () -> underTest.calculateDividendYield(tickerXXX, 1));
    }

    @Test
    public void testCalculateDividendYieldStockServiceException() throws InstrumentNotFoundException, DaoException {
        when(instrumentDao.getInstrument(tickerJoe)).thenThrow(new InstrumentNotFoundException("Instrument not found"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.calculateDividendYield(tickerJoe, 1));
    }

    @Test
    public void testCalculatePERatioStockTea() throws Exception {
        BigDecimal peRatio = underTest.calculatePERatio(tickerTea, 100);
        assertThat(peRatio, is(notNullValue()));
        assertThat(peRatio, closeTo(BigDecimal.ZERO, error));
    }

    @Test
    public void testCalculatePERatioStockPop() throws Exception {
        BigDecimal peRatio = underTest.calculatePERatio(tickerPop, 100);
        assertThat(peRatio, is(notNullValue()));
        assertThat(peRatio, closeTo(BigDecimal.valueOf(12.5), error));
    }

    @Test
    public void testCalculatePERatioStockException() throws Exception {
        when(instrumentDao.getInstrument(tickerPop)).thenThrow(new DaoException("Some DAO exception"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.calculatePERatio(tickerPop, 100));
    }

    @Test
    public void testSaveTrade() throws Exception {
        when(tradeDao.saveTrade(any(Trade.class))).thenReturn(true);
        assertTrue(underTest.saveTrade(getTeaTrade()));
    }

    @Test
    public void testSaveTradeException() throws Exception {
        when(tradeDao.saveTrade(any(Trade.class))).thenThrow(new DaoException("Some DAO exception"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.saveTrade(getTeaTrade()));
    }

    @Test
    public void testSaveStock() throws Exception {
        when(instrumentDao.saveInstrument(getAleStock())).thenReturn(true);
        assertTrue(underTest.saveStock(getAleStock()));
    }

    @Test
    public void testSaveStockException() throws Exception {
        when(instrumentDao.saveInstrument(getAleStock())).thenThrow(new DaoException("Some DAO exception"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.saveStock(getAleStock()));
    }

    @Test
    public void testCalculateVwapPriceTickerTea() throws Exception {
        when(tradeDao.getVwapTrades(tickerTea, 15)).thenReturn(
                Lists.newArrayList(
                        new Trade(tickerTea, timeNow.minus(5, ChronoUnit.MINUTES), 10, TradeLeg.BUY, BigDecimal.TEN),
                        new Trade(tickerTea, timeNow.minus(5, ChronoUnit.MINUTES), 50, TradeLeg.SELL, BigDecimal.ONE),
                        new Trade(tickerTea, timeNow.minus(5, ChronoUnit.MINUTES), 1234, TradeLeg.BUY, new BigDecimal(100))));

        BigDecimal vwapPrice = underTest.calculateVwapPrice(tickerTea);
        assertThat(vwapPrice, is(notNullValue()));
        assertThat(vwapPrice, closeTo(BigDecimal.valueOf(95.47913), error));
    }

    @Test
    public void testCalculateVwapPriceTickerGin() throws Exception {
        when(tradeDao.getVwapTrades(tickerGin, 15)).thenReturn(
                Lists.newArrayList(
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 10, TradeLeg.SELL, BigDecimal.TEN),
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 50, TradeLeg.BUY, BigDecimal.ONE),
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 10, TradeLeg.BUY, new BigDecimal(100))));

        BigDecimal vwapPrice = underTest.calculateVwapPrice(tickerGin);
        assertThat(vwapPrice, is(notNullValue()));
        assertThat(vwapPrice, closeTo(BigDecimal.valueOf(16.42857), error));
    }

    @Test
    public void testCalculateVwapPriceZeroQuantity() throws Exception {
        when(tradeDao.getVwapTrades(tickerGin, 15)).thenReturn(
                Lists.newArrayList(
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 0, TradeLeg.SELL, BigDecimal.TEN),
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 0, TradeLeg.BUY, BigDecimal.ONE),
                        new Trade(tickerGin, timeNow.minus(5, ChronoUnit.MINUTES), 0, TradeLeg.BUY, new BigDecimal(100))));

        BigDecimal vwapPrice = underTest.calculateVwapPrice(tickerGin);
        assertThat(vwapPrice, is(notNullValue()));
        assertThat(vwapPrice, closeTo(BigDecimal.ZERO, error));
    }

    @Test
    public void testCalculateVwapPriceException() throws Exception {
        when(tradeDao.getVwapTrades(tickerGin, 15)).thenThrow(new DaoException("Some DAO Exception"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.calculateVwapPrice(tickerGin));
    }

    @Test
    public void testCalculateGbceShareIndex() throws Exception {
        double gbceIndex = underTest.calculateGbceShareIndex();
        assertThat(gbceIndex, is(notNullValue()));
        assertThat(gbceIndex, is(equalTo(3.9810717055349727)));
    }

    @Test
    public void testCalculateGbceShareIndexException() throws Exception {
        when(instrumentDao.getInstruments()).thenThrow(new InstrumentNotFoundException("Instrument not found"));
        Assert.assertThrows(StockServiceException.class, () -> underTest.calculateGbceShareIndex());
    }


}