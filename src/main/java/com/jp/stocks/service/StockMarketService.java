package com.jp.stocks.service;

import com.jp.stocks.entity.Stock;
import com.jp.stocks.entity.Trade;
import com.jp.stocks.exception.CalculationException;
import com.jp.stocks.exception.InstrumentNotFoundException;

import java.math.BigDecimal;

/**
 * Interface for child classes to implement stock market service.
 *
 * <p> This class performs calculations in BigDecimal. This is a deliberate decision as these are monitory calculations.
 * Although BigDecimal is more expensive than doubles, accuracy is chosen over performance.</p>
 *
 * <p>Please note the methods which are performing calculations will <blockquote>return rounded</blockquote> result.
 * This rounding can be configured through properties file through properties file. The client who is calling these method can
 * convert the return result to double if required</p>
 *
**/
public interface StockMarketService {

    /**
     * Calculates dividend yield for the given instrument and price
     * For Common stock it will be -> (lastDividend/Price)
     * For preferred stock it will be -> (fixed Dividend * Par Value)/Price
     * @param ticker
     *      Stock symbol to calculate dividend yield
     * @Param price
     *      price used in PE ration calculation
     *
     * @throws InstrumentNotFoundException
     *      If the given instrument is not found
     *
     * @throws CalculationException
     *      In case of any exceptions while calculation
     *
     **/
    BigDecimal calculateDividendYield(String ticker, double price) throws InstrumentNotFoundException, CalculationException;

    /**
     * Calculates PE Ration for the given instrument and price
     * The calculation will use the formula -> (price/dividend)
     * First dividend will be calculated according to the {@link #calculateDividendYield} and
     * then use the above formula to calculate PE ratio
     *
     * @param ticker
     *      Stock symbol to calculate PE Ratio
     * @Param price
     *      price used in PE ration calculation
     *
     * @throws InstrumentNotFoundException
     *      If the given instrument is not found
     *
     * @throws CalculationException
     *      In case of any exceptions while calculation
     **/
    BigDecimal calculatePERatio(String ticker, double price) throws InstrumentNotFoundException, CalculationException;

    /**
     * Saves a given trade to persistence layer. This method is a placeholder, it will simply call persistence class to save,
     * any transformation, business logic can be enhanced in future
     *
     * @param trade
     *      trade details to be saved
     *
     * @return
     *      returns success failure as a @{@link Boolean}
     * **/
    boolean saveTrade(Trade trade);

    /**
     * Saves a given stock to persistence layer. This method is a placeholder, it will simply call persistence class to save,
     * any transformation, business logic can be enhanced in future
     *
     * @param stock
     *      stock details to be saved
     *
     * @return
     *      returns success failure as a @{@link Boolean}
     * **/
    boolean saveStock(Stock stock);

    /**
     * Calculates VWAP price for given stock.
     *
     * It will use the below formula for the given stock
     * ∑(i) Traded Price(i) × Quantity(i) / ∑(i) Quantity(i).
     *
     * Please note the time of VWAP can be configured, by default it will calculate for the last 15 minutes.
     *
     * @Param ticker
     *      Instrument for which VWAP calculation is performed
     *
     * @throws InstrumentNotFoundException
     *      If the given instrument is not found
     *
     * @throws CalculationException
     *      In case of any exceptions while calculation
     **/
    BigDecimal calculateVwapPrice(String ticker) throws CalculationException;


    /**
     * Calculates geometric mean for all the stocks.
     *
     * It will use the below formula for the given stock
     * n√p1p2p3 ... pn
     *
     * @throws InstrumentNotFoundException
     *      If there are no instruments to calculate
     *
     * @throws CalculationException
     *      In case of any exceptions while calculation
     **/
    double calculateGbceShareIndex();
}
