package com.jp.stocks.service;

import com.google.common.base.Preconditions;
import com.jp.stocks.dao.InstrumentDao;
import com.jp.stocks.dao.TradeDao;
import com.jp.stocks.entity.Stock;
import com.jp.stocks.entity.StockType;
import com.jp.stocks.entity.Trade;
import com.jp.stocks.exception.DaoException;
import com.jp.stocks.exception.InstrumentNotFoundException;
import com.jp.stocks.exception.StockServiceException;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@PropertySource("classpath:application.properties")
@Data
public class StockMarketServiceImpl implements StockMarketService {

    private static final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    InstrumentDao<Stock> instrumentDao;

    @Autowired
    Environment env;

    private final int scale = 5;
    private final int vwapTime = 15;

    @Autowired
    TradeDao tradeDao;

    @Override
    public BigDecimal calculateDividendYield(final String ticker, final double price) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ticker), "ticker cannot be null or blank");
        Preconditions.checkArgument(price > 0, "price cannot be negative", price);

        try {
            final Stock stock = instrumentDao.getInstrument(ticker);
            final StockType type = stock.getStockType();
            final BigDecimal parValue = BigDecimal.valueOf(stock.getParValue());

            if (type == StockType.COMMON) {
                return getCommonStockDividendYield(stock, BigDecimal.valueOf(price));
            } else if (type == StockType.PREFEREED) {
                return getPreferredStockDividendYield(stock, parValue, BigDecimal.valueOf(price));
            } else {
                throw new NotImplementedException("Cannot handle " + type + " instrument type");
            }

        } catch (DaoException | InstrumentNotFoundException e) {
            final String errorMsg = "Exception while retrieving stock instrument from DAO";
            throw new StockServiceException(errorMsg, e);
        }
    }

    @Override
    public BigDecimal calculatePERatio(final String ticker, final double price) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ticker), "ticker cannot be null or blank");
        Preconditions.checkArgument(price > 0, "price cannot be negative %f", price);

        BigDecimal currentPrice = BigDecimal.valueOf(price);

        try {
            Stock stock = instrumentDao.getInstrument(ticker);
            double dividend = stock.getLastDividend();

            if (dividend == 0) {
                logger.warn("Cannot calculate PE ration for the given stock as dividend is 0, ticker = " + stock.getTicker());
                return BigDecimal.ZERO;
            }

            return currentPrice.divide(BigDecimal.valueOf(dividend), scale, BigDecimal.ROUND_HALF_UP);

        } catch (DaoException | InstrumentNotFoundException e) {
            final String errorMsg = "Exception while retrieving stock instrument from DAO";
            throw new StockServiceException(errorMsg, e);
        }
    }

    @Override
    public boolean saveTrade(final Trade trade) {
        Preconditions.checkNotNull(trade);
        try {
            return tradeDao.saveTrade(trade);
        } catch (DaoException e) {
            final String errorMsg = "Exception while saving trade";
            logger.error(errorMsg, e);
            throw new StockServiceException(errorMsg, e);
        }
    }

    @Override
    public boolean saveStock(final Stock stock) {
        Preconditions.checkNotNull(stock);
        try {
            return instrumentDao.saveInstrument(stock);
        } catch (DaoException e) {
            final String errorMsg = "Exception while saving stock instrument";
            logger.error(errorMsg, e);
            throw new StockServiceException(errorMsg, e);
        }
    }

    @Override
    public BigDecimal calculateVwapPrice(final String ticker) {
        Preconditions.checkArgument(StringUtils.isNotBlank(ticker), "ticker cannot be null or blank");
        List<Trade> trades;
        try {
            trades = tradeDao.getVwapTrades(ticker, vwapTime);

            BigDecimal totalPrice = BigDecimal.ZERO;
            int quantity = 0;

            for (Trade trade : trades) {
                totalPrice = totalPrice.add(trade.getPrice().multiply(BigDecimal.valueOf(trade.getQuantity())));
                quantity += trade.getQuantity();
            }

            if (quantity == 0) {
                logger.warn("Unable to calculate VWAP price as total quantity is 0, please check trade information for ticker = " + ticker);
                return BigDecimal.ZERO;
            }

            return totalPrice.divide(BigDecimal.valueOf(quantity), scale, RoundingMode.HALF_UP);

        } catch (DaoException e) {
            String errorMsg = "Exception while getting trades for ticker " + ticker;
            logger.error(errorMsg, e);
            throw new StockServiceException(errorMsg, e);
        }
    }

    @Override
    public double calculateGbceShareIndex() {
        try {

            List<Stock> stocks = instrumentDao.getInstruments();

            List<Trade> trades = tradeDao.getAllTrades();

            //match trades where we have tickers available
            //TODO is this necessary to filter the trades here?
            List<Trade> matchedTrades = trades.stream()
                    .filter(trade -> stocks.stream().anyMatch(stock -> trade.getSymbol().equals(stock.getTicker())))
                    .collect(Collectors.toList());

            BigDecimal totalTradePrices = matchedTrades.stream()
                    .map(Trade::getPrice)
                    .reduce(BigDecimal::multiply).get();

            return Math.pow(totalTradePrices.doubleValue(), 1d / trades.size());

        } catch (DaoException | InstrumentNotFoundException e) {
            final String errorMsg = "Exception while retrieving trades from DAO";
            logger.error(errorMsg, e);
            throw new StockServiceException(errorMsg, e);
        }
    }


    //(fixed Dividend * Par Value)/Price
    private BigDecimal getPreferredStockDividendYield(Stock stock, BigDecimal parValue, BigDecimal currentPrice) {
        if (stock.getFixedDividend() == 0) {
            logger.info("Cannot calculate dividendYield as FixedDividend for preferred stock [ " + stock.getTicker() + " ] is 0");
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(stock.getFixedDividend()).multiply(parValue)
                .divide(currentPrice, scale, BigDecimal.ROUND_HALF_UP);
    }

    //(lastDividend/Price)
    private BigDecimal getCommonStockDividendYield(Stock stock, BigDecimal currentPrice) {
        if (stock.getLastDividend() == 0) {
            logger.info("Cannot calculate dividendYield as LastDividend for common stock [ " + stock.getTicker() + " ] is 0");
            return BigDecimal.ZERO;
        }

        return BigDecimal.valueOf(stock.getLastDividend())
                .divide(currentPrice, scale, BigDecimal.ROUND_HALF_UP);
    }
}
