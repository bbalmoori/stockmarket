package com.jp.stocks.dao;

import com.jp.stocks.entity.Instrument;
import com.jp.stocks.entity.Trade;
import com.jp.stocks.exception.DaoException;
import com.jp.stocks.exception.InstrumentNotFoundException;

import java.util.List;

/**
 * This is persistence class for the given instrument.
 * The implementors of these shall consider whether to store in filesystem, Database or keep them in memory.
 * This can be extended to any type of financial instrument
 *
 **/
public interface TradeDao {

    /**
     * Persists a given instrument
     *
     * @param trade
     *      trade to save
     *
     * @return boolean if saved successfully
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     **/
    boolean saveTrade(Trade trade) throws DaoException;

    /**
     * Delete a given trade
     *
     * @param trade
     *      trade to delete
     *
     *  @return boolean if removed successfully
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     *
     * @throws InstrumentNotFoundException
     *      if the given trade is not found
     **/
    boolean deleteTrade(Trade trade) throws DaoException, InstrumentNotFoundException;

    /**
     * Returns trades for a given instrument
     *
     * @param symbol
     *      trades to retrieve for a symbol
     *
     * @param elapsedTime
     *      elapsed time for the trade to be retrieved
     *
     * @return List of trades
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     **/
    List<Trade> getVwapTrades(String symbol, int elapsedTime) throws DaoException;

    /**
     * Returns all trades for all instruments
     *
     * @return List of trades
     *
     * @throws DaoException
     *      in case of any connection issues or cache issues (if it is stored in memory)
     **/
    List<Trade> getAllTrades() throws DaoException;
}
